package com.alexyu.test;

import com.alexyu.service.UserService;
import com.alexyu.xml.spring.ClassPathXmlApplicationContext;

/**
 * 测试SpringIOC代码
 *
 * @author Alex Yu
 * @date 2019/8/1 0:30
 */
public class Test02 {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) app.getBean("userService1");
        userService.add();
    }
}
