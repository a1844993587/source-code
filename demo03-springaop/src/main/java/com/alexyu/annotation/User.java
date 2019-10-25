package com.alexyu.annotation;

import java.lang.reflect.Method;

/**
 * @author Alex Yu
 * @date 2019/7/31 12:59
 */
public class User {

    @AddAnnotation(userId = 18, userName = "张三", arrays = {"1"})
    public void add() {

    }

    public void del() {

    }

    public static void main(String[] args) {
        Class<User> claszz = User.class;
        Method[] declaredMethods = claszz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("###method:" + declaredMethod.getName());
            AddAnnotation addAnnotation = declaredMethod.getDeclaredAnnotation(AddAnnotation.class);
            if (addAnnotation == null) {
                // 该方法没注解
                System.out.println("该方法没有注解");
                continue;
            }
            // 已经存在
            System.out.println("userId:" + addAnnotation.userId());
            System.out.println("userName:" + addAnnotation.userName());
            System.out.println("arrays:" + addAnnotation.arrays());
        }
    }

}
