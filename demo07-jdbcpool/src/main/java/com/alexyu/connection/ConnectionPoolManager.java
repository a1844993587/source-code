package com.alexyu.connection;

import java.sql.Connection;

/**
 * 管理线程池
 *
 * @author Alex Yu
 * @date 2019/8/3 23:32
 */
public class ConnectionPoolManager {

    private static DbBean dbBean = new DbBean();

    private static ConnectionPool connectionPool = new ConnectionPool(dbBean);

    // 获取链接(重复利用机制)
    public static Connection getConnection() {
        return connectionPool.getConnection();
    }

    // 释放链接(可回收机制)
    public static void releaseConnection(Connection connection) {
        connectionPool.releaseConnection(connection);
    }

}
