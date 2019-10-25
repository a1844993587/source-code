package com.alexyu.orm.mybatis.aop;

import com.alexyu.entity.User;
import com.alexyu.mapper.UserMapper;
import com.alexyu.sql.SqlSession;

/**
 * @author Alex Yu
 * @date 2019/8/4 12:49
 */
public class Test {

    public static void main(String[] args) {
        // 使用动态代理技术 虚拟调用方法
        UserMapper userMapper = (UserMapper) SqlSession.getMapper(UserMapper.class);
//        int insertUser = userMapper.insertUser("AlexYu", 10);
//        System.out.println(insertUser);
        User user = userMapper.selectUser("AlexYu", 20);
        System.out.println(user.toString());
    }

}
