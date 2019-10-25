package com.alexyu.hashmap;

/**
 * map接口
 *
 * @author Alex Yu
 * @date 2019/8/10 13:44
 */
public interface ExtMap<K, V> {

    /**
     * 像集合中插入数据
     *
     * @param k
     * @param v
     * @return
     */
    V put(K k, V v);

    /**
     * 根据k 从Map集合中查询元素
     *
     * @param k
     * @return
     */
    V get(K k);

    /**
     * 获取集合个数
     *
     * @return
     */
    int size();

    interface Entry<K, V> {

        K  getKey();

        V getValue();

        V setValue(V value);

    }

}
