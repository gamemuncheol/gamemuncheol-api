package com.gamemoonchul.common.status;

/***
 * Custom하게 정의되서 사용할 ApiStatus의 Interface
 * 예) UserApiStatus의 경우 2000번대로 시작하게 된다면
 * statusCode : 2404
 * message : "User Not Found"
 * 이런식으로 된다.
 */
public interface ApiStatusIfs {
    public Integer getStatusCode();

    public String getMessage();
}
