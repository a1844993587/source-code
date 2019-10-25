package com.alexyu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Yu
 * @date 2019/7/31 14:01
 */
@Repository
public class LogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String name) {
        String sql = "INSERT INTO t_log(log_name) VALUES (?)";
        int update = jdbcTemplate.update(sql, name);
        System.out.println("updateResult:" + update);
    }

}
