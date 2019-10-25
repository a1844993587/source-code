package com.alexyu.orm.annotation;

import java.lang.annotation.*;

/**
 * @author Alex Yu
 * @date 2019/8/4 13:39
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtSelect {
    String value();
}
