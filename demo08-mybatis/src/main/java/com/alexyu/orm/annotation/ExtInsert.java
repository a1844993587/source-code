package com.alexyu.orm.annotation;

import java.lang.annotation.*;

/**
 * 自定义插入注解
 *
 * @author Alex Yu
 * @date 2019/8/4 12:36
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtInsert {
    String value();
}
