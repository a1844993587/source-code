package com.alexyu.connection;

import java.sql.Connection;

/**
 * 链接数据库池
 *
 * @author Alex Yu
 * @date 2019/8/3 22:42
 */
public interface IConnectionPool {

    // 获取链接(重复利用机制)
    Connection getConnection();

    // 释放链接(可回收机制)
    void releaseConnection(Connection connection);
}
