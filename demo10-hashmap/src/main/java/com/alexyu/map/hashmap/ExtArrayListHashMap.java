package com.alexyu.map.hashmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于ArrayList实现HashMap(性能低)
 *
 * @author Alex Yu
 * @date 2019/8/7 19:09
 */
public class ExtArrayListHashMap<Key, Value> {

    // Map集合存储容量
    private List<Entry<Key, Value>> tables = new ArrayList<>();

//    private int size;

    public void put(Key key, Value value) {
        Entry<Key, Value> entry = getEntry(key);
        if (entry != null) {
            // 已经存在
            entry.value = value;
        } else {
            Entry<Key, Value> newEntry = new Entry<>(key, value);
            // 调用put的时候 将该hash存储对象存入到ArrayList中
            tables.add(newEntry);
        }
    }

    public Value get(Key key) {
        Entry<Key, Value> entry = this.getEntry(key);
        return entry == null ? null : entry.value;
    }

    public int size() {
        return tables.size();
    }

    private Entry<Key, Value> getEntry(Key key) {
        for (Entry<Key, Value> entry : tables) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ExtArrayListHashMap<String, String> map = new ExtArrayListHashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("a", "ccc");
        System.out.println(map.get("a"));
        System.out.println(map.size());
    }

}

// hash存储对象
class Entry<Key, Value> {

    // 集合存储的key
    Key key;
    // 集合存储的value
    Value value;

    public Entry(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
}
