package com.java.collection.linkedlist;

/**
 * @Description: 纯手写LinkedList
 * @Author: zhangyadong
 * @Date: 2021/1/4 0004 下午 10:50
 * @Version: v1.0
 */
public class ExtLinkedList<E> {

    // 链表实际存储元素
    private int size;

    // 第一个元素(头节点,为了查询开始)
    private Node first;

    // 最后个元素(尾结点,为了添加开始)
    private Node last;

    // add
    public void add() {

    }

    // 链表节点存储元素
    private class Node{
        // 存放元素的值
        Object object;
        // 上个节点的值
        Node pre;
        // 下个节点的值
        Node next;
    }
}
