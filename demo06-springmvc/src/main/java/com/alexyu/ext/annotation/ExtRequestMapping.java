package com.alexyu.ext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义RequestMapping
 *
 * @author Alex Yu
 * @date 2019/8/1 23:09
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtRequestMapping {

    String value() default "";

}
