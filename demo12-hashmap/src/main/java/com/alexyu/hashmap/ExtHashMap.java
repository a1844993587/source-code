package com.alexyu.hashmap;

/**
 * @author Alex Yu
 * @date 2019/8/10 13:48
 */
public class ExtHashMap<K, V> implements ExtMap<K, V> {

    // 定义table 存放hashMap 数组元素 默认没有初始化
    private Node<K, V>[] tables = null;

    // 实际table 存储容量 大小
    private int size;

    // 负载因子 默认负载因子 0.75 扩容的时候才会用到
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // table 默认初始大小 16
    static int DEFAULT_INITIAL_CAPACITY = 16;

    @Override
    public V put(K key, V value) {
        // 1 判断table 数组大小是否为空 如果为空的情况下做初始化操作
        if (tables == null) {
            tables = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        // 2 判断数组是否需要扩容
        // hashMap 扩容机制 扩容数组之后有什么影响
        // 实际存储大小 = 负载因子 * 初始容量 = 0.75 * 16 = 12
        // 如果size > 12 的时候就需要扩容数组 扩容数组大小 之前两倍
        if (size > (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR)) {
            // 需要对tables进行数组扩容
            resize();
        }
        // 3 计算hash值指定下标位置
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = tables[index];
        if (node == null) {
            // 没有冲突
            node = new Node<>(key, value, null);
            size++;
        } else {
            Node<K, V> newNode = node;
            while (newNode != null) {
                // 已经发生冲突
                if (newNode.getKey().equals(key) || newNode.getKey() == key) {
                    // hashCode 相同 equals 相等情况 说明是同一个对象修改值
//                node.value = value;
                    return newNode.setValue(value);
                } else {
                    // 继续添加
                    if (newNode.next == null) {
                        node = new Node<>(key, value, node);
                        size++;
                    }
                }
                newNode = newNode.next;
            }
        }
        tables[index] = node;
        return null;
    }

    // 对table进行扩容
    private void resize() {
        // 生成新的table 是之前的两倍大小
        Node<K, V>[] newTables = new Node[DEFAULT_INITIAL_CAPACITY << 1];
        // 重新计算index的索引位置 存放再心的table里面
        for (int i = 0; i < tables.length; i++) {
            // 老的Node
            Node<K, V> oldNode = tables[i];
            while (oldNode != null) {
                tables[i] = null;
                K oldK = oldNode.getKey();
                // 重新计算index
                int index = getIndex(oldK, newTables.length);
                Node<K, V> oldNext = oldNode.next;
                // 同一位置下 放后面  关键一步 不然数据重复
                oldNode.next = newTables[index];
                // 将之前的node赋值给 newTable
                newTables[index] = oldNode;
                // 判断是否继续循环遍历
                oldNode = oldNext;
            }
        }
        // 将新的newTable赋值给老的table
        tables = newTables;
        DEFAULT_INITIAL_CAPACITY = newTables.length;
        newTables = null;
    }

    private int getIndex(K k, int length) {
        int hashCode = k.hashCode();
        int index = hashCode % length;
        return index;
    }

    @Override
    public V get(K k) {
        Node<K, V> node = getNode(tables[getIndex(k, DEFAULT_INITIAL_CAPACITY)], k);
        return node == null ? null : node.getValue();
    }

    private Node<K, V> getNode(Node<K, V> node, K k) {
        while (node != null) {
            if (node.getKey().equals(k)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    public void print() {
        for (int i = 0; i < tables.length; i++) {
            Node<K, V> node = tables[i];
            System.out.print("下标位置[" + i + "]");
            while (node != null) {
                System.out.print("[key: " + node.getKey() + ", value: " + node.getValue() + "]");
                node = node.next;
//                if (node.next != null) {
//                    node = node.next;
//                } else {
//                    node = null;
//                }
            }
            System.out.println();
        }
    }

    // 定义节点
    class Node<K, V> implements ExtMap.Entry<K, V> {

        // 存放Map 集合 Key
        private K key;

        // 存放Map 集合 value
        private V value;

        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

    }

}
