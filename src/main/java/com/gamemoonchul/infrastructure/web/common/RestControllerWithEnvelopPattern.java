package com.gamemoonchul.infrastructure.web.common;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@RestController
public @interface RestControllerWithEnvelopPattern {
}
