package com.alexyu.list;

/**
 * 手写LinkedList
 *
 * @author Alex Yu
 * @date 2019/8/6 13:52
 */
public class ExtLinkedList<E> {

    // 链表实际存储元素
    private int size;
    // 第一个元素 (为了查询开始)
    private Node first;
    // 最后一个元素 (为了添加开始)
    private Node last;

    public void add(E e) {
        // 创建节点
        Node node = new Node();
        // 为节点赋值
        node.object = e;
        if (first == null) {
            // 添加第一个元素
            // 给第一个元素赋值
            first = node;
            // 第一个元素头和尾都是自己
        } else {
            // 添加第二个或以上数据
            node.prev = last;
            // 将上一个元素的next赋值
            last.next = node;
        }
        last = node;
        // 实际长度++
        size++;
    }

    public void add(int index, E e) {
        if (index == size) {
            add(e);
            return;
        }
        checkElementIndex(index);
        Node oldNode = getNode(index);
        if (oldNode != null) {
            Node oldPrevNode = oldNode.prev;
            Node newNode = new Node();
            newNode.object = e;
            newNode.next = oldNode;
            if (oldPrevNode == null) {
                first = newNode;
            } else {
                newNode.prev = oldPrevNode;
                oldPrevNode.next = newNode;
            }
            oldNode.prev = newNode;
            size++;
        }
    }

    public Object get(int index) {
        // 下标验证
        checkElementIndex(index);
        return getNode(index).object;
    }

    public void remove(int index) {
        checkElementIndex(index);
        Node oldNode = getNode(index);
        if (oldNode != null) {
            // 获取删除元素的上下节点
            // node 3
            Node oldNextNode = oldNode.next;
            // node 1
            Node oldPrevNode = oldNode.prev;
            // 将node1 的下一个节点变为node3
            if (oldPrevNode == null) {
                // 删除第一个元素
                first = oldNextNode;
            } else {
                oldPrevNode.next = oldNextNode;
                oldNode.prev = null;
            }
            // 将node1 的下一个节点变为node3
            if (oldNextNode == null) {
                last = oldPrevNode;
            } else {
                oldNextNode.prev = oldPrevNode;
                oldNode.next = null;
            }
            oldNode.object = null;
            size--;
        }
    }

    private Node getNode(int index) {
        checkElementIndex(index);
        Node node = null;
        if (first != null) {
            node = first; // 默认取第0个
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("查询越界");
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    public int getSize() {
        return size;
    }

    // 链表节点存储元素
    private class Node {
        // 存放元素的值
        Object object;
        // 上一个节点Node
        Node prev;
        // 下一个节点Node
        Node next;
    }

    public static void main(String[] args) {
        ExtLinkedList<String> extLinkedList = new ExtLinkedList<>();
        extLinkedList.add("a");
        extLinkedList.add("b");
        extLinkedList.add("c");
        extLinkedList.add("d");
//        System.out.println(extLinkedList.get(1));
//        extLinkedList.remove(3);
        extLinkedList.add(4, "e");
//        System.out.println(extLinkedList.get(2));
//        System.out.println(extLinkedList.getSize());
//        System.out.println("---" + extLinkedList.get(3));
        for (int i = 0; i < extLinkedList.getSize(); i++) {
            System.out.println(extLinkedList.get(i));
        }
    }
}