package com.alexyu;

import com.alexyu.proxy.UserServiceProxy;
import com.alexyu.service.UserService;
import com.alexyu.service.impl.UserServiceImpl;

/**
 * @author Alex Yu
 * @date 2019/7/31 1:16
 */
public class Test01 {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
//        userService.add();
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);
        userServiceProxy.add();
    }

}