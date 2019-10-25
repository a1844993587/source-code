package com.alexyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author Alex Yu
 * @date 2019/7/31 12:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddAnnotation {

    int userId() default 0;

    String userName() default "默认名称";

    String[] arrays();
}
