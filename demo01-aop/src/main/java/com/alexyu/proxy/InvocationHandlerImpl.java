package com.alexyu.proxy;

import com.alexyu.service.MemberService;
import com.alexyu.service.impl.MemberServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 *
 * @author Alex Yu
 * @date 2019/7/31 1:27
 */
public class InvocationHandlerImpl implements InvocationHandler {

    private Object target;

    public InvocationHandlerImpl(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDk动态代理开启事务");
        Object result = method.invoke(target, args);
        System.out.println("JDk动态代理提交事务");
        return result;
    }

    public static void main(String[] args) throws Exception{
//        UserService userService = new UserServiceImpl();
        MemberService memberService = new MemberServiceImpl();
        InvocationHandlerImpl invocationHandler = new InvocationHandlerImpl(memberService);
        MemberService newProxyInstance = (MemberService) Proxy.newProxyInstance(memberService.getClass().getClassLoader(), memberService.getClass().getInterfaces(), invocationHandler);
        newProxyInstance.memberAdd();
    }
}
