package com.alexyu.aop;

import com.alexyu.transaction.TransactionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 基于手动事务封装
 *
 * @author Alex Yu
 * @date 2019/7/31 3:10
 */
@Aspect
@Component
public class AopTransaction {

    @Autowired
    private TransactionUtils transactionUtils;

    @AfterThrowing("execution(* com.alexyu.service.UserService.*(..))")
    public void afterThrowing() {
        System.out.println("回滚事务");
        // 获取当前事务 直接回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    @Around("execution(* com.alexyu.service.UserService.*(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("开启事务");
        TransactionStatus transactionStatus = this.transactionUtils.begin();
        proceedingJoinPoint.proceed(); // 代理调用方法
        System.out.println("提交事务");
        this.transactionUtils.commit(transactionStatus);
    }

}
