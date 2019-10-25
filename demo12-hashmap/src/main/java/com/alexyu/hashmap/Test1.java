package com.alexyu.hashmap;

/**
 * @author Alex Yu
 * @date 2019/8/10 13:46
 */
public class Test1 {

    public static void main(String[] args) {
        ExtHashMap<String, String> extHashMap = new ExtHashMap<>();
        extHashMap.put("1号", "aaaa");
        extHashMap.put("2号", "bbbb");
        extHashMap.put("3号", "aaaa");
        extHashMap.put("4号", "bbbb");
        extHashMap.put("5号", "aaaa");
        extHashMap.put("6号", "bbbb");
        extHashMap.put("7号", "bbbb");
        extHashMap.put("14号", "bbbb");
        extHashMap.put("3号", "cccc");
        extHashMap.put("25号", "ddd");
        extHashMap.put("14号", "eee");
        extHashMap.print();
        System.out.println(extHashMap.size());
        System.out.println(extHashMap.get("3号"));
    }

}
