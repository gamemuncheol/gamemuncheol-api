package com.gamemoonchul.common;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BusinessErrorCode {


    // COMMON
    SUCCESS("0000", "정상"),

    // Client
    BAD_REQUEST("C" + "400", "잘못된 요청입니다. 요청내용을 확인하세요."),
    UNAUTHORIZED("C" + "401", "인증되지 않았습니다. 인증을 확인하세요."),
    FORBIDDEN("C" + "403", "권한이 없습니다. 권한을 확인하세요."),
    NOT_FOUND("C" + "404", "요청내용을 찾을 수 없습니다. 요청내용을 확인하세요."),
    TOO_MANY_REQUESTS("C" + "429", "요청량이 많아 처리할 수 없습니다. 잠시 후 다시 시도해주세요."),

    // Application(S로 시작하기)
    INTERNAL_SERVER_ERROR("S" + "500", "시스템 내부오류가 발생했습니다. 담당자에게 문의바랍니다."),

    // Infrastructure(I로 시작하기)
    INFRASTRUCTURE_ERROR("I" + "600", "외부 인프라스트럭처에서 오류가 발생했습니다. 담당자에게 문의바랍니다.");

    private static final Map<String, BusinessErrorCode> MAP_OF_CODE =
            Stream.of(values())
                    .collect(
                            Collectors.toUnmodifiableMap(
                                    BusinessErrorCode::getCode, Function.identity()));

    private final String code;
    private final String message;

    BusinessErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BusinessErrorCode of(String code) {
        var result = MAP_OF_CODE.get(code);
        if (result == null) {
            throw new IllegalArgumentException(String.format("요청하신 코드(%s)를 찾을 수 없습니다.", code));
        }

        return result;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isClientError() {
        return code.startsWith("C");
    }
}

