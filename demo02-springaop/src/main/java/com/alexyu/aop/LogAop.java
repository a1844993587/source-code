package com.alexyu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 切面类
 * aop 几个通知 前置通知 后置通知 运行通知 异常通知 环绕通知
 *
 * @author Alex Yu
 * @date 2019/7/31 1:57
 */
//@Aspect
//@Component
public class LogAop {

    @Before("execution(* com.alexyu.service.UserService.*(..))")
    public void before() {
        System.out.println("前置通知 在方法之前执行..");
    }

    @After("execution(* com.alexyu.service.UserService.*(..))")
    public void after() {
        System.out.println("后置通知 在方法之后执行..");
    }

    @AfterReturning("execution(* com.alexyu.service.UserService.*(..))")
    public void returning() {
        System.out.println("运行通知");
    }

    @AfterThrowing("execution(* com.alexyu.service.UserService.*(..))")
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    @Around("execution(* com.alexyu.service.UserService.*(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 调用方法之前执行
        System.out.println("环绕通知 调用方法之前执行");
        proceedingJoinPoint.proceed(); // 代理调用方法
        // 调用方法之后执行
        System.out.println("环绕通知 调用方法之后执行");
    }
}
