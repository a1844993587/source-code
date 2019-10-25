package com.alexyu.orm.annotation;

import java.lang.annotation.*;

/**
 * 自定义参数注解
 *
 * @author Alex Yu
 * @date 2019/8/4 12:38
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtParam {
    String value();
}
