package com.alexyu.test;

import com.alexyu.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Alex Yu
 * @date 2019/7/31 2:12
 */
public class Test01 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring.xml");
        UserService userService = (UserService) app.getBean("userServiceImpl");
        userService.add();
    }

}
