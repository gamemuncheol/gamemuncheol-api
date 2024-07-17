package com.gamemoonchul.infrastructure.web.common;

import com.gamemoonchul.common.status.ApiStatus;
import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final ApiStatusInfo status;
    private final T data;

    // Builder
    public ApiResponse(boolean success, T data, ApiStatusIfs status) {
        this.success = success;
        this.data = data;
        this.status = new ApiStatusInfo(status);
    }

    public static ApiResponse OK() {
        return new ApiResponse(
                true,
                null,
                ApiStatus.OK
        );
    }

    public static <T> ApiResponse<T> OK(T data) {
        return new ApiResponse<>(true, data, ApiStatus.OK);
    }

    public static <T> ApiResponse<T> ERROR(ApiStatusIfs status) {
        return new ApiResponse<>(false, null, status);
    }

    public static <T> ApiResponse<T> ERROR(ApiStatusIfs status, T data) {
        return new ApiResponse<>(false, data, status);
    }

    @Getter
    @AllArgsConstructor
    public static class ApiStatusInfo {
        private final Integer statusCode;
        private final String message;

        public ApiStatusInfo(ApiStatusIfs status) {
            this.statusCode = status.getStatusCode();
            this.message = status.getMessage();
        }
    }
}
