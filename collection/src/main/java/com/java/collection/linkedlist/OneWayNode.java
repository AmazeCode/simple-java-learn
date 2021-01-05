package com.java.collection.linkedlist;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @Description: 单向链表简单使用
 * @Author: zhangyadong
 * @Date: 2021/1/5 10:47
 * @Version: v1.0
 */
public class OneWayNode {

    // 初始化头节点
    private OneNode head = new OneNode("1");

    /**
     * @description: 添加节点(在链表尾部插入,尾插法)
     * @params: [oneNode]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/5 11:20
     */
    public void add(OneNode oneNode){
        OneNode temp = head;
        /*
            原理：从头节点判断下个节点是否为null,如果不为空,下次判断是从下个节点开始判断,一次循环,直到找到最后一个节点,然后在最后一个节点后面新增一个节点
         */
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = oneNode;
    }

    /**
     * @description: 删除节点
     * @params: [object]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/5 14:16
     */
    public void remove(Object object){
        if (head.next == null) {
            System.out.println("链表为空不能进行删除操作");
            return;
        }
        OneNode temp = head;
        boolean canRemove = false;
        // 下个节点有值
        while (temp.next != null && temp.next.object != null) {
            // 当前节点的下个节点是要删除的节点
            if (temp.next.object.equals(object)) {
                canRemove = true;
                break;
            }
            temp = temp.next;
        }

        if (canRemove) {
            temp.next = temp.next.next;
        }else{
            System.out.println("要删除的节点不存在！");
        }
    }

    /**
     * @description: 更新节点
     * @params: [oneNode]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/5 14:51
     */
    public void update(OneNode oneNode){
        if (head.next == null) {
            System.out.println("链表为空不能进行修改操作");
            return;
        }

        boolean isCanUpdate = false;
        OneNode temp = head;
        while (temp.next != null) {
            if (temp.object == oneNode.object) {
                // 进行数值更改,单个参数没有意义,只能多个参数时才有意义
                isCanUpdate = true;
                break;
            }
            temp = temp.next;
        }
        if (!isCanUpdate) {
            System.out.println("要修改的值不存在！");
        }
    }

    /**
     * @description: 单向链表结构
     * @author: zhangyadong
     * @date: 2021/1/5 10:51
     */
    static class OneNode{

        //数据域
        private Object object;

        //指向下一个节点
        private OneNode next;

        public OneNode(Object object) {
            this.object = object;
        }
    }

    public static void main(String[] args) {
        OneWayNode oneWayNode = new OneWayNode();
        oneWayNode.add(new OneNode("2"));
        oneWayNode.add(new OneNode("3"));
        oneWayNode.add(new OneNode("4"));
        oneWayNode.add(new OneNode("3"));
        oneWayNode.add(new OneNode("4"));
        // 移除
        oneWayNode.remove("3");
        // 修改
        OneNode oneNode = new OneNode("3");
        oneWayNode.update(oneNode);
    }
}


