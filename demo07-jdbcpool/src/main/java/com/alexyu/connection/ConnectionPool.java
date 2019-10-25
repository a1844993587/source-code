package com.alexyu.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库实现原理
 * ###核心参数###
 * 1 空闲链接(空闲线程) 没有被使用的链接存放
 * 2 活动线程 正在使用的链接
 * ###核心步奏###
 * 初始化线程池(初始化空闲线程)
 * 调用getConnection方法 获取链接请求
 * 先去releaseConnection获取当前链接 存放在activeConnection
 * 调用releaseConnection方法 释放链接 -- 资源回收
 * 获取activeConnection结合链接 转移到releaseConnection集合中
 *
 * @author Alex Yu
 * @date 2019/8/3 22:43
 */
public class ConnectionPool implements IConnectionPool {

    // 使用线程安全的集合 空闲线程 容器 没有被使用的链接存放
    private List<Connection> freeConnection = new Vector<>();
    // 使用线程安全的集合 活动线程 容器 容器正在使用的链接
    private List<Connection> activeConnection = new Vector<>();

//    private int countConn = 0;

    private AtomicInteger countConn = new AtomicInteger();

    private DbBean dbBean;

    public ConnectionPool(DbBean dbBean) {
        // 获取配置文件信息
        this.dbBean = dbBean;
        init();
    }

    private void init() {
        if (dbBean == null) {
            return; // 最好抛出异常
        }
        // 获取初始化链接
        for (int i = 0; i < dbBean.getInitConnections(); i++) {
            // 创建Connection
            Connection newConnection = newConnection();
            if (newConnection != null) {
                // 添加到空闲线程
                // 存放在freeConnection集合
                freeConnection.add(newConnection);
            }
        }
    }

    private synchronized Connection newConnection() {
        try {
            Class.forName(dbBean.getDriverName());
            Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(), dbBean.getPassword());
            countConn.incrementAndGet();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 调用getConnection方法 获取链接请求
    @Override
    public synchronized Connection getConnection() {
        try {
            Connection connection = null;
            if (countConn.get() < dbBean.getMaxActiveConnections()) {
                // 小于最大活动链接数
                // 判断空闲线程是否有数据
                if (freeConnection.size() > 0) {
                    // 空闲线程存在链接
                    connection = freeConnection.remove(0); // 拿到删除
                } else {
                    // 创建新的链接
                    connection = newConnection();
                }
                // 判断链接是否可用
                boolean available = isAvailable(connection);
                if (available) {
                    // 往活动线程存
                    activeConnection.add(connection);
                } else {
                    countConn.decrementAndGet();
                    connection = getConnection(); // 重试获取
                }
            } else {
                // 大于最大活动链接数, 进行等待
                wait(dbBean.getConnTimeOut());
                // 重试
                connection = getConnection();
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 判断连接是否可用
    private boolean isAvailable(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public synchronized void releaseConnection(Connection connection) {
        try {
            // 判断链接是否可用
            if (isAvailable(connection)) {
                // 判断空闲线程是否大于活动线程
                if (freeConnection.size() < dbBean.getMaxConnections()) {
                    // 空闲线程没满
                    freeConnection.add(connection); // 回收链接
                } else {
                    // 已满
                    connection.close();
                }
                activeConnection.remove(connection);
                countConn.decrementAndGet();
                notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
