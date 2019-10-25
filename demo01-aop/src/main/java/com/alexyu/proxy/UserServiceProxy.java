package com.alexyu.proxy;

import com.alexyu.service.UserService;

/**
 * 静态代理设计模式
 *
 * @author Alex Yu
 * @date 2019/7/31 1:19
 */
public class UserServiceProxy {

    private UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    public void add() {
        System.out.println("静态代理开启事务");
        userService.add();
        System.out.println("静态代理提交事务");
    }
}
