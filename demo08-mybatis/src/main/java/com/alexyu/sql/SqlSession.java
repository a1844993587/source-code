package com.alexyu.sql;

import com.alexyu.orm.mybatis.aop.MyInvocationHandlerMybatis;

import java.lang.reflect.Proxy;

/**
 * @author Alex Yu
 * @date 2019/8/4 12:45
 */
public class SqlSession {

    /**
     * 加载mapper接口
     *
     * @param classz
     * @param <T>
     * @return
     */
    public static <T> T getMapper(Class classz) {
        return (T) Proxy.newProxyInstance(classz.getClassLoader(), new Class[]{classz}, new MyInvocationHandlerMybatis(classz));
    }
}
