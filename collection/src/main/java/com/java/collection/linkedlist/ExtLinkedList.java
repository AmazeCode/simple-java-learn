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

    // add 方法
    public void add(E e) {
        //创建节点
        Node node = new Node();
        //给节点赋值
        node.object = e;
        if (first == null) {
            //添加第一个元素,给节点赋值
            //上个节点(node)
            //node.pre = null;
            //下个节点(node)
            //node.next = null;
            //给第一个元素赋值(node节点赋值)
            first = node;
            //last = node;
            //第一个元素的头和尾都属于自己
        } else {
            //添加第二个或以上数据
            node.pre = last;
            //如果此时添加的是第二个节点时,last.next指的是第一个节点
            last.next = node;//将上一个元素的next赋值,标识为最新的
            //last = node;
        }
        last = node;
        //实际长度++
        size++;
    }

    public Node get(int index) {
        // 下标验证
        checkElementIndex(index);
        return null;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("查询下标越界了！");
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index <= size;
    }

    // 双向链表节点存储元素
    private class Node{
        // 存放元素的值
        Object object;
        // 上个节点的值
        Node pre;
        // 下个节点的值
        Node next;
    }

    public static void main(String[] args) {
        ExtLinkedList extLinkedList = new ExtLinkedList();
        extLinkedList.add("a");
        extLinkedList.add("b");
        extLinkedList.add("c");
        System.out.println(extLinkedList.first.object);
        System.out.println(extLinkedList.first.next.object);
        System.out.println(extLinkedList.first.next.next.object);
    }
}
