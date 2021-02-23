package com.java.collection.hashmap;

import java.util.LinkedList;

/**
 * @Description: 基于LinkecList实现HashMap(效率低不推荐)
 * @Author: zhangyadong
 * @Date: 2021/1/8 17:21
 * @Version: v1.0
 * jdk1.7的时候使用的是数组+链表方式实现 </br>
 */
public class ExtLinkedListHashMap {

    // map存放Entry对象
    LinkedList<LinkedEntry>[] tables = new LinkedList[998];

    /*
            问题：假如2 % 13 = 2 15 % 13 = 2,取模后都是2,那么会产生冲突
            1、如果hashcode相同的情况下,可能会发生覆盖问题?
            2、如何去解决？链表技术

            如果hashCode相同的对象存放在相同链表集合中
         */
    // put方法
    public void put(Object key, Object value) {
        LinkedEntry newEntry = new LinkedEntry(key,value);
        //两个对象做比较时,如果hashCode相同，对象值不一定相同
        //两个对象做比较时,如果equals比较相同，对象值一定相同
        int hashCode = key.hashCode();
        // 哈希算法(hash使用取模获取余数)
        int hash = hashCode % tables.length;//取模后的值在998以内(0-997)
        /*
            存放步骤:
            1、获取该下标元素,是否有LinkedList
         */
        LinkedList<LinkedEntry> entryLinkedList = tables[hash];//具体 hash的数组值
        if (entryLinkedList == null) { // 不存在说明没有冲突的可能
            entryLinkedList = new LinkedList<LinkedEntry>();
            entryLinkedList.add(newEntry);
            // tables 数组
            tables[hash] = entryLinkedList;
        } else {
            for (LinkedEntry linkedEntry : entryLinkedList) {
                // 判断entry key值是否相等
                if (linkedEntry.key.equals(key)) {
                    //key相等,由key获得的hashcode一定相同 说明是同一个对象
                    linkedEntry.value = value;//直接覆盖
                } else {
                    // hashcode相等,对象值不一定相等,直接新增
                    // 发生hash冲突,直接在后边添加链表节点
                    entryLinkedList.add(newEntry);
                }
            }
        }
    }

    /**
     * @description: 获取元素
     * @params: [key]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2021/1/11 9:26
     */
    public Object get(Object key) {
        /*
            使用hash值直接定位数组在哪个位置
         */
        int hashCode = key.hashCode();
        int hash = hashCode % tables.length;//取模后的值在998以内(0-997)
        LinkedList<LinkedEntry> linkList = tables[hash];
        for (LinkedEntry linkedEntry : linkList) {
            if (linkedEntry.key.equals(key)) {
                return linkedEntry.value;
            }
        }
        return tables[hash];
    }

    public static void main(String[] args) {
        ExtLinkedListHashMap linkedListHashMap = new ExtLinkedListHashMap();
        //linkedListHashMap.add("a","aaa");
        System.out.println(2 % 13);
    }
}

class LinkedEntry<Key,Value>{

    // HashMap 集合存储key
    Key key;
    // HashMap 集合存储value
    Value value;

    /**
     * @description: 构造方法
     * @params: [key, value]
     * @return:
     * @author: zhangyadong
     * @date: 2021/1/8 16:42
     */
    public LinkedEntry(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
}