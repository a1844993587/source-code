package com.alexyu.connection;

import java.sql.Connection;

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
 * @date 2019/8/3 22:09
 */
public class Test01 {

    public static void main(String[] args) {
        ThreadConnection threadConnection = new ThreadConnection();
        for (int i = 0; i < 3; i++) {
            new Thread(threadConnection, "线程" + i).start();
        }
    }
}

class ThreadConnection implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Connection connection = ConnectionPoolManager.getConnection();
            System.out.println(Thread.currentThread().getName() + ", connection:" + connection);
            ConnectionPoolManager.releaseConnection(connection);
        }
    }

}
