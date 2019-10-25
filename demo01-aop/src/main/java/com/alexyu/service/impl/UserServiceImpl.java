package com.alexyu.service.impl;

import com.alexyu.service.UserService;

/**
 * @author Alex Yu
 * @date 2019/7/31 1:14
 */
public class UserServiceImpl implements UserService {

    @Override
    public void add() {
        System.out.println("往数据库添加数据");
    }
}
