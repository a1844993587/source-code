package com.alexyu.test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Alex Yu
 * @date 2019/8/6 13:31
 */
public class Test {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        System.out.println(list.size());
        list.forEach(System.out::println);
    }

}
