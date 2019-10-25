package com.alexyu.spring.ext;

import com.alexyu.spring.extannotation.ExtResource;
import com.alexyu.spring.extannotation.ExtService;
import com.alexyu.utils.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 手写spring Ioc 注解版本
 *
 * @author Alex Yu
 * @date 2019/8/1 0:46
 */
public class ExtClassPathXmlApplicationContext {

    // 扫包范围
    private String packageName;

    private ConcurrentHashMap<String, Object> beans = null;

    public ExtClassPathXmlApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<>();
        this.packageName = packageName;
        this.initBean();
        // 遍历所有bean容器对象
        beans.forEach((k, v) -> {
            try {
                this.attriAssign(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 判断属性上面是否有加注解
    }

    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId不能为空!");
        }
        Object object = beans.get(beanId);
//        this.attriAssign(object);
        return object;
    }

    private Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    // 初始化对象
    public void initBean() throws Exception {
        // 1 使用java反射机制扫包 获取当前包下所有类
        List<Class<?>> classes = ClassUtil.getClasses(packageName);
        // 2 判断类上是否存在注入bean的注解
        ConcurrentHashMap<String, Object> classExisAnnotation = this.findClassExisAnnotation(classes);
        if (classExisAnnotation == null || classExisAnnotation.isEmpty()) {
            throw new Exception("该包没有任何类加上注解");
        }
        // 3 使用java的反射机制进行初始化

    }

    // 判断类上是否存在注入bean的注解
    public ConcurrentHashMap<String, Object> findClassExisAnnotation(List<Class<?>> classes) throws InstantiationException, IllegalAccessException {
        for (Class<?> classInfo : classes) {
            ExtService annotation = classInfo.getAnnotation(ExtService.class);
            if (annotation != null) {
                //  beanId 类名小写
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                Object newInstance = this.newInstance(classInfo);
                beans.put(beanId, newInstance);
            }
        }
        return beans;
    }

    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    // 依赖注入原理
    public void attriAssign(Object object) throws Exception {
        Class<?> classInfo = object.getClass();
        // 使用反射机制 获取当前类的所有属性
        Field[] declaredFields = classInfo.getDeclaredFields();
        // 判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            ExtResource extResource = field.getAnnotation(ExtResource.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = this.getBean(beanId);
                if (bean != null) {
                    // 1 参数 当前对象 2 参数 属性赋值对象
                    field.setAccessible(true);
                    field.set(object, bean);
                    field.setAccessible(false);
                }
            }
        }
        // 使用默认属性名称 查找bean容器对象
    }

}
