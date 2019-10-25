package com.alexyu.ext.springmvc.servlet;

import com.alexyu.ext.annotation.ExtController;
import com.alexyu.ext.annotation.ExtRequestMapping;
import com.alexyu.utils.ClassUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义前端控制器
 * 手写springmvc 原理分析<br>
 * 1.创建一个前端控制器（）ExtDispatcherServlet 拦截所有请求(springmvc 基于servlet实现)<br>
 * ####2.初始化操作重写servlet init 方法<br>
 * #######2.1 将扫包范围所有的类,注入到springmvc容器里面，存放在Map集合中 key为默认类名小写，value 对象<br>
 * #######2.2 将url映射和方法进行关联 <br>
 * ##########2.2.1 判断类上是否有注解,使用java反射机制循环遍历方法 ,判断方法上是否存在注解，进行封装url和方法对应存入集合中<br>
 * ####3.处理请求 重写Get或者是Post方法 <br>
 * #######3.1获取请求url,从urlBeans集合获取实例对象,获取成功实例对象后,调用urlMethods集合获取方法名称,使用反射机制执行
 *
 * @author Alex Yu
 * @date 2019/8/1 23:11
 */
public class DispatcherServlet extends HttpServlet {

    // springmvc 容器对象 key:类名id ,value 对象
    private ConcurrentHashMap<String, Object> springmvcBeans = new ConcurrentHashMap<>();
    // springmvc 容器对象 key:请求地址 ,value 类
    private ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<>();
    // springmvc 容器对象 key:请求地址 ,value 方法名称
    private ConcurrentHashMap<String, String> urlMethods = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        // 获取当前包下所有类
        List<Class<?>> classes = ClassUtil.getClasses("com.alexyu.controller");
        // 判断类上是否有加注解
        try {
            findClassMVCAnnotation(classes);
        } catch (Exception e) {

        }
        // 将rul映射和方法进行关联
        handlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // #############处理请求#############
        // 获取请求url地址
        String requestURI = req.getRequestURI();
        if (StringUtils.isEmpty(requestURI)) {
            return;
        }
        // 从map集合中获取控制对象
        Object object = urlBeans.get(requestURI);
        if (object == null) {
            resp.getWriter().println("not found 404");
            return;
        }
        // 使用url地址获取方法
        String methodName = urlMethods.get(requestURI);
        if (StringUtils.isEmpty(methodName)) {
            resp.getWriter().println("not found method");
        }
        // 使用java反射机制调用方法
        // 使用java反射机制获取方法返回结果
        String resultPage = (String) methodInvoke(object, methodName);
        // 调用视图转换器 渲染页面展示
        extResourceViewResolver(resultPage, req, resp);
    }

    private void extResourceViewResolver(String pageName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 根路径
        String prefix = "/";
        String suffix = ".jsp";
        request.getRequestDispatcher(prefix + pageName + suffix).forward(request, response);
    }

    private Object methodInvoke(Object object, String methodName) {
        try {
            Class<?> classInfo = object.getClass();
            Method method = classInfo.getMethod("test");
            Object result = method.invoke(object);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private void findClassMVCAnnotation(List<Class<?>> classes) {
        classes.forEach(classInfo -> {
            ExtController extController = classInfo.getDeclaredAnnotation(ExtController.class);
            if (extController != null) {
                String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
                Object object = null;
                try {
                    object = ClassUtil.newInstance(classInfo);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                springmvcBeans.put(beanId, object);
            }
        });
    }

    private void handlerMapping() {
        // 获取springmvc bean容器对象
        // 遍历springmvc bean 容器判断类上是否有url映射注解
        springmvcBeans.forEach((key, object) -> {
            // 遍历所有的方法上是否有url注解
            Class<?> classInfo = object.getClass();
            ExtRequestMapping declaredAnnotation = classInfo.getDeclaredAnnotation(ExtRequestMapping.class);
            String baseUrl = "";
            if (declaredAnnotation != null) {
                // 获取类上url映射地址
                baseUrl = declaredAnnotation.value();
            }
            // 判断方法上是否有加url地址
            Method[] declaredMethods = classInfo.getDeclaredMethods();
            for (Method method : declaredMethods) {
                // 判断方法上是否有加Url映射注解
                ExtRequestMapping methodExtRequestMapping = method.getDeclaredAnnotation(ExtRequestMapping.class);
                if (methodExtRequestMapping != null) {
                    String methodUrl = baseUrl + methodExtRequestMapping.value();
                    urlBeans.put(methodUrl, object);
                    urlMethods.put(methodUrl, method.getName());
                }
            }
        });
    }
}
