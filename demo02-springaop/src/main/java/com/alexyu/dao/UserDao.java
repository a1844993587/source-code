package com.alexyu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Yu
 * @date 2019/7/31 2:34
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void add(String name, Integer age) {
        String sql = "INSERT INTO user(name, age) VALUES(?, ?)";
        int updateResult = jdbcTemplate.update(sql, name, age);
        System.out.println("updateResult: " + updateResult);
    }

}
