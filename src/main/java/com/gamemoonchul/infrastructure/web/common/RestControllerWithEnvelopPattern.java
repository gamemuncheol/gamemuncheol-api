package com.gamemoonchul.infrastructure.web.common;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * 봉투패턴을 사용하는 API Controller 에 {@link RestController} 를 대체하여 선언합니다.
 * <p/>
 *
 * <pre>
 * {
 *     "code": "0000",
 *     "message": "OK",
 *     "data": { data }
 * }
 * </pre>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@RestController
public @interface RestControllerWithEnvelopPattern {
}
