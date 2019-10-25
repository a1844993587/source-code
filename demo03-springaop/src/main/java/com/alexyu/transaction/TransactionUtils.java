package com.alexyu.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * 编程式事务 需要手动begin  手动回滚 手动提交
 *
 * @author Alex Yu
 * @date 2019/7/31 3:01
 */
@Component
@Scope("prototype")  // 设置多列
public class TransactionUtils {

    private TransactionStatus transactionStatus;
    // 获取事务源
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    // 开启事务
    public TransactionStatus begin() {
        System.out.println("开启事务");
        transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transactionStatus;
    }

    // 提交事务
    public void commit(TransactionStatus transaction) {
        System.out.println("提交事务");
        dataSourceTransactionManager.commit(transaction);
    }

    // 回滚事务
    public void rollback() {
        System.out.println("回滚事务");
        dataSourceTransactionManager.rollback(transactionStatus);
    }
}
