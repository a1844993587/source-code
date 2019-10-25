package com.alexyu.test;

import com.alexyu.service.UserService;
import com.alexyu.spring.ext.ExtClassPathXmlApplicationContext;

/**
 * @author Alex Yu
 * @date 2019/8/1 0:42
 */
public class Test01 {

    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext app = new ExtClassPathXmlApplicationContext("com.alexyu.service.impl");
        UserService userService = (UserService) app.getBean("userServiceImpl");
        userService.add();
        System.out.println(userService);
    }

}