package com.alexyu.map.hashmap;

import java.util.LinkedList;

/**
 * 基于LinkedList实现hashMap集合(效率低)
 * JDK1.7 使用数据+链表实现
 *
 * @author Alex Yu
 * @date 2019/8/7 19:36
 */
public class ExtLinkedListHashMap {

    // Map存放Entry对象
    private LinkedList<Entry>[] tables = new LinkedList[998];

    public void put(Object key, Object value) {
        Entry newEntry = new Entry(key, value);
        int hashCode = key.hashCode();
        int hash = hashCode % tables.length;
        LinkedList<Entry> entryLinkedList = tables[hash];
        if (entryLinkedList == null) {
            // 没有hash冲突
            entryLinkedList = new LinkedList<>();
            entryLinkedList.add(newEntry);
            tables[hash] = entryLinkedList;
        } else {
            // 发生hash冲突
            for (Entry entry : entryLinkedList) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                } else {
                    entryLinkedList.add(newEntry);
                }
            }
        }
    }

    public Object get(Object key) {
        int hashCode = key.hashCode();
        int hash = hashCode % tables.length;
        LinkedList<Entry> linkedList = tables[hash];
        for (Entry entry : linkedList) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ExtLinkedListHashMap extLinkedListHashMap = new ExtLinkedListHashMap();
        extLinkedListHashMap.put("a", "aaa");
        extLinkedListHashMap.put("a", "ccc");
        System.out.println(extLinkedListHashMap.get("a"));
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
