package com.gamemoonchul.common.exception;

import com.gamemoonchul.infrastructure.web.common.dto.ApiResponse;
import com.gamemoonchul.infrastructure.web.common.dto.ApiResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gamemoonchul.common.BusinessErrorCode.BAD_REQUEST;
import static com.gamemoonchul.common.BusinessErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {
    private static final Logger LOG =
            LoggerFactory.getLogger(GlobalRestControllerExceptionHandler.class);

    @ExceptionHandler({BusinessException.class, RuntimeException.class})
    protected ResponseEntity<ApiResponse<Void>> handle(BusinessException businessException) {
        if (businessException.isNecessaryToLog()) {
            LOG.error("[BusinessException] {}", businessException.getMessage(), businessException);
        }

        var errorCode = businessException.getErrorCode();
        var httpStatus =
                errorCode.isClientError()
                        ? HttpStatus.BAD_REQUEST
                        : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(httpStatus)
                .body(
                        ApiResponseGenerator.fail(
                                errorCode.getCode(), businessException.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    protected ApiResponse<Void> handle(Throwable throwable) {
        LOG.error("[InternalServerError] {}", throwable.getMessage(), throwable);

        return ApiResponseGenerator.fail(
                INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConversionFailedException.class)
    protected ApiResponse<Void> handle(ConversionFailedException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof IllegalArgumentException illegalArgumentException) {
            return this.handle(illegalArgumentException);
        }

        LOG.error("[InternalServerError][ConversionFailed] {}", exception.getMessage(), exception);

        return ApiResponseGenerator.fail(
                INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    protected ApiResponse<Void> handle(AccessDeniedException exception) {
        LOG.warn("[AccessDenied] {}", exception.getMessage());

        return ApiResponseGenerator.fail(INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            DateTimeParseException.class
    })
    protected ApiResponse<Void> handle(Exception exception) {
        LOG.debug("[BadRequest] {}", exception.getMessage());

        return ApiResponseGenerator.fail(BAD_REQUEST.getCode(), "잘못된 요청입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BindException.class
    })
    protected ApiResponse<Map<String, String>> handle(BindException exception) {
        LOG.debug("[BadRequest] {}", exception.getMessage());

        var errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        var errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage).orElse(BAD_REQUEST.getMessage());

        return ApiResponseGenerator.of(BAD_REQUEST.getCode(), errorMsg, errors);
    }

}

