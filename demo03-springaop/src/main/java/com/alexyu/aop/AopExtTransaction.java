package com.alexyu.aop;

import com.alexyu.annotation.ExtTransaction;
import com.alexyu.transaction.TransactionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

/**
 * 自定义事务注解具体实现
 *
 * @author Alex Yu
 * @date 2019/7/31 13:21
 */
@Aspect
@Component
public class AopExtTransaction {

    @Autowired
    private TransactionUtils transactionUtils;

    @AfterThrowing("execution(* com.alexyu.service.*.*.*(..))")
    public void afterThrowing() {
        // 获取当前事务 直接回滚
        transactionUtils.rollback();
    }

    @Around("execution(* com.alexyu.service.*.*.*(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        // 1 获取代理对象的方法
        ExtTransaction extTransaction = this.getMethodExtTransaction(pjp);
        TransactionStatus transactionStatus = this.begin(extTransaction);
        // 3 调用目标代理对象方法
        pjp.proceed();
        // 4 判断该方法上是否加上注解
        this.commit(transactionStatus);
    }

    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            // 5 如果存在注解 提交事务
            this.transactionUtils.commit(transactionStatus);
        }
    }

    private TransactionStatus begin(ExtTransaction extTransaction) {
        if (extTransaction == null) {
            return null;
        }
        return this.transactionUtils.begin();
    }

    /**
     * 获取方法上是否存在事务注解
     *
     * @return
     */
    private ExtTransaction getMethodExtTransaction(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        String methodName = pjp.getSignature().getName();
        Class<?> classTarget = pjp.getTarget().getClass();
        // 获取目标对象类型
        Class[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        ExtTransaction extTransaction = objMethod.getDeclaredAnnotation(ExtTransaction.class);
        return extTransaction;
    }

}
