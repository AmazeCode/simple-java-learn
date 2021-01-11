package com.java.collection.hashmap;

import java.util.HashMap;

/**
 * @Description: 手写HashMap集合
 * @Author: zhangyadong
 * @Date: 2021/1/11 14:48
 * @Version: v1.0
 */
public class ExtHashMap<K,V> implements ExtMap<K,V>{

    // 1.定义table 存放HashMap 数组元素,默认没有初始化容器 懒加载功能
    Node<K,V>[] table = null;
    // 2.实际用到table 存储容量大小
    int size;
    // 3.负载因子,默认0.75,扩容的时候才会用到(负载因子越小hash冲突机率越低)
    float DEFAULT_LOAD_FACTOR = 0.75f;
    // 4.table默认初始大小16
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;


    @Override
    public V put(K key, V value) {
        // 1. 判断table 数组大小是否为空(如果为空的情况下,做初始化操作)
        if (table == null) {
            //初始容器大小
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        // 2. hashMap 扩容机制 为什么要扩容?扩容数组之后,有什么影响? hashMap中是从什么时候开始扩容的?
        // 实际存储大小 = 负载因子 * 初始容量DEFAULT_LOAD_FACTOR0.75*DEFAULT_INITIAL_CAPACITY16=12
        // 如果size>12的时候就要开始扩容,扩容大小是之前两倍
        if (size > (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY)) {
            //需要开始对table进行扩容

        }

        // 3. 计算hash值指定下标位置
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table[index];
        if (node == null) {
            // 没有hash冲突 index冲突
            node = new Node<K,V>(key,value,null);
            size++;
        } else {
            Node<K,V> newNode = node;
            while (newNode != null) {
                // 已经发生hash冲突 (单向链表) -- index冲突
                if (newNode.getKey().equals(key) || newNode.getKey() == key) {
                    // hashCode相同,而且equals相等情况,说明是同一个对象 进行修改操作
                    //node.value = value;
                    return newNode.setValue(value);
                } else {
                    // 说明遍历到最后一个node,添加node
                    if (newNode.next == null) {
                        // hashCode取模的余数相同index相同,对象不同
                        // 继续添加 (直接添加冲突node在最前面,不是在最后面)
                        node = new Node<K,V>(key,value,node);
                        size++;
                    }
                }
                newNode = newNode.next;
            }

        }
        table[index] = node;
        return null;
    }

    // 对table进行扩容
    public void resize() {
        // 1.生成新的table是之前的两倍的大小DEFAULT_INITIAL_CAPACITY*2
        Node<K,V>[] newTable = new Node[DEFAULT_INITIAL_CAPACITY << 1];
        // 2.重新计算index索引,存放新的table里面
        for (int i = 0; i < table.length; i++) {
            // 存放在之前的table,原来的node
            Node<K,V> oldNode = table[i];
            // 如果 a 的 index=1 b的index = 1
            while (oldNode != null) {
                table[i] = null;// 赋值为 null--为了让垃圾回收机制去回收
                // 存放在之前的table 原来的node key
                K oldKey = oldNode.key;
                // 重新计算index
                int newIndex = getIndex(oldKey, newTable.length);
                // 存放在之前的table 原来的node next
                Node<K,V> oldNext = oldNode.next;
                // 如果index下标在newTable发生相同index时候,以链表进行存储 //原来的node的下一个是最新的(原来的node)
                oldNode.next = newTable[newIndex];
                // 将之前的node赋值给 newTable[newIndex]
                newTable[newIndex] = oldNode;
                // 继续循环下个node
                oldNode = oldNext;
            }
        }
        // 3.将newTable赋值给老的table
    }

    // 测试方法，打印所有的链表
    public void print() {
        for (int i = 0; i<table.length; i++) {
            Node<K,V> node = table[i];
            System.out.print("下标位置["+i+"]");
            while (node != null) {
                System.out.print("[key:"+node.getKey()+",value:"+node.getValue()+"]");
                node = node.next;
                /*if (node.next != null) {
                    node = node.next;
                } else {
                    // 结束循环
                    node = null;
                }*/
            }
            System.out.println();
        }
    }

    //获取hash下标
    private int getIndex(K key,int length){
        int hashCode = key.hashCode();
        System.out.println("k:" + key + ",hashCode=" + hashCode);
        int index = hashCode % length;
        return index;
    }

    @Override
    public V get(K key) {
        // 1.使用取模算法定义链表
        Node<K, V> node = getNode(table[getIndex(key, DEFAULT_INITIAL_CAPACITY)],key);
        return node == null ? null : node.value;
    }

    /**
     * @description: 获取node
     * @params: [node, key]
     * @return: com.java.collection.hashmap.ExtHashMap<K,V>.Node
     * @author: zhangyadong
     * @date: 2021/1/11 16:35
     */
    public Node getNode (Node<K, V> node, K key) {
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    // 定义节点
    class Node<K,V> implements Entry<K,V>{

        //存放Map 集合key
        private K key;
        //存放Map 集合value
        private V value;
        //下一个节点Node
        private Node<K,V> next;

        /**
         * @description: 构造方法
         * @params: [key, value, next]
         * @return:
         * @author: zhangyadong
         * @date: 2021/1/11 15:19
         */
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

        //设置新值时返回老的值
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
