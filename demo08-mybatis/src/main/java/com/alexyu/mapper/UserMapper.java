package com.alexyu.mapper;

import com.alexyu.entity.User;
import com.alexyu.orm.annotation.ExtInsert;
import com.alexyu.orm.annotation.ExtParam;
import com.alexyu.orm.annotation.ExtSelect;

/**
 * @author Alex Yu
 * @date 2019/8/4 12:34
 */
public interface UserMapper {

    @ExtInsert("INSERT INTO user (name, age) VALUES(#{name},#{age})")
    int insertUser(@ExtParam("name") String name, @ExtParam("age") Integer age);

    @ExtSelect(" SELECT * FROM user where name=#{name} and age=#{age}")
    User selectUser(@ExtParam("name") String name, @ExtParam("age") Integer age);

}
