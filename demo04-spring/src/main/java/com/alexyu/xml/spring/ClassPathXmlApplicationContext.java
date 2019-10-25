package com.alexyu.xml.spring;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 自定义spring容器框架xml实现方法
 *
 * @author Alex Yu
 * @date 2019/8/1 0:10
 */
public class ClassPathXmlApplicationContext {

    private String xmlPath;

    public ClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId不能为空");
        }
        // 1 解析xml文件
        List<Element> readerXML = this.readerXML();
        if (readerXML == null || readerXML.isEmpty()) {
            throw new Exception("配置文件中没有配置bean信息");
        }
        // 2 使用方法参数beanId查找配置文件中beanId是否一致
        String className = this.findByElementClass(readerXML, beanId);
        // 3 使用反射机制初始化对象
        if (StringUtils.isEmpty(className)) {
            throw new Exception("该bean对象没有配置class地址");
        }
        return this.newInstance(className);
    }

    // 使用方法参数beanId查找配置文件中beanId是否一致 返回class地址
    private String findByElementClass(List<Element> readerXML, String beanId) {
        for (Element element : readerXML) {
            String xmlBeanId = element.attributeValue("id");
            if (StringUtils.isEmpty(xmlBeanId)) {
                continue;
            }
            if (xmlBeanId.equals(beanId)) {
                String xmlClass = element.attributeValue("class");
                // 3 获取class信息地址 反射机制初始化信息
                return xmlClass;
            }
        }
        return null;
    }

    // 初始化对象
    private Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> classInfo = Class.forName(className);
        return classInfo.newInstance();
    }

    // 解析xml
    private List<Element> readerXML() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        // 读取xml文件
        Document document = saxReader.read(this.getResourceAsStream(xmlPath));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        return elements;
    }

    // 获取当前上下文路径
    private InputStream getResourceAsStream(String xmlPath) {
        return this.getClass().getClassLoader().getResourceAsStream(xmlPath);
    }

}
