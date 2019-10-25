package com.alexyu.orm.mybatis.aop;

import com.alexyu.orm.annotation.ExtInsert;
import com.alexyu.orm.annotation.ExtParam;
import com.alexyu.orm.annotation.ExtSelect;
import com.alexyu.orm.utils.JDBCUtils;
import com.alexyu.utils.SQLUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用反射动态代理技术 拦截接口
 *
 * @author Alex Yu
 * @date 2019/8/4 12:42
 */
public class MyInvocationHandlerMybatis implements InvocationHandler {

    private Object object;

    public MyInvocationHandlerMybatis(Object object) {
        this.object = object;
    }

    /**
     * @param proxy  代理对象
     * @param method 拦截方法
     * @param args   方法上的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("使用动态代理技术拦截接口方法开始");
        // 判断方法存在ExtInsert
        ExtInsert extInsert = method.getDeclaredAnnotation(ExtInsert.class);
        if (extInsert != null) {
            return extInsert(extInsert, proxy, method, args);
        }
        ExtSelect extSelect = method.getDeclaredAnnotation(ExtSelect.class);
        if (extSelect != null) {
            String selectSQL = extSelect.value();
            ConcurrentHashMap<Object, Object> paramMap = paramMap(proxy, method, args);
            List<String> sqlSelectParameter = SQLUtils.sqlSelectParameter(selectSQL);
            List<Object> sqlParams = new ArrayList<>();
            for (String parameterName : sqlSelectParameter) {
                Object paramValue = paramMap.get(parameterName);
                sqlParams.add(paramValue);
            }
            String newSQL = SQLUtils.parameQuestion(selectSQL, sqlSelectParameter);
            System.out.println("sql:" + newSQL + " , params:" + sqlParams.toString());
            ResultSet rs = JDBCUtils.query(newSQL, sqlParams);
            // 判断是否存在值
            if (!rs.next()) {
                return null;
            }
            // 下标往上移动一位
            rs.previous();
            Class<?> returnType = method.getReturnType();
            Object obj = returnType.newInstance();
            while (rs.next()) {
                Field[] declaredFields = returnType.getDeclaredFields();
                for (Field field : declaredFields) {
                    String fieldName = field.getName();
                    Object fieldValue = rs.getObject(fieldName);
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                    field.setAccessible(false);
                }
                /*  for (String parameterName : sqlSelectParameter) {
                    Object resultValue = rs.getObject(parameterName);
                    Field field = returnType.getDeclaredField(parameterName);
                    field.setAccessible(true);
                    field.set(obj, resultValue);
                    field.setAccessible(false);*//*
                }*/
            }
            return obj;
        }
        return null;
    }

    private Object extInsert(ExtInsert extInsert, Object proxy, Method method, Object[] args) {
        // 方法存在@ExtInsert 获取SQL语句
        // 获取SQL语句 获取注解INSERT语句
        String insertSQL = extInsert.value();
//            System.out.println("insertSQL:" + insertSQL);
        // 获取方法的参数和SQL参数进行匹配
        ConcurrentHashMap<Object, Object> paramMap = paramMap(proxy, method, args);
        // 存放sql执行参数
        List<Object> sqlParams = new ArrayList<>();
        String[] sqlInsertParameter = SQLUtils.sqlInsertParameter(insertSQL);
        for (String paramName : sqlInsertParameter) {
            Object paramValue = paramMap.get(paramName);
            sqlParams.add(paramValue);
        }
        // 替换参数为?
        String newSQL = SQLUtils.parameQuestion(insertSQL, sqlInsertParameter);
        System.out.println("SQL:" + newSQL + ", params:" + sqlParams.toString());
        // 调用JDBC底层代码执行语句
        return JDBCUtils.insert(newSQL, false, sqlParams);
    }

    private ConcurrentHashMap<Object, Object> paramMap(Object proxy, Method method, Object[] args) {
        // 定义一个map集合 key为 @ExtParam Value结果为参数值
        ConcurrentHashMap<Object, Object> paramMap = new ConcurrentHashMap<>();
        // 获取方法上的参数
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            ExtParam extParam = parameter.getDeclaredAnnotation(ExtParam.class);
            if (extParam != null) {
                String paramName = extParam.value();
                Object paramValue = args[i];
                paramMap.put(paramName, paramValue);
//                    System.out.println(paramName + ", " + paramValue);
            }
        }
        return paramMap;
    }

}