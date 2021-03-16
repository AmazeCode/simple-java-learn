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

    /*
        负载因子设置为0.75原因:提高空间利用率和 减少查询成本的折中，主要是泊松分布，0.75的话碰撞最小
        也就是说用0.75作为加载因子，每个碰撞位置的链表长度超过８个是几乎不可能的
     */
    // 3.负载因子,默认0.75,扩容的时候才会用到(负载因子越小hash冲突机率越低)
    float DEFAULT_LOAD_FACTOR = 0.75f;
    // 4.table默认初始大小16
    static int DEFAULT_INITIAL_CAPACITY = 1 << 4;


    /*
        jdk1.7和jdk1.8区别：
        1、实现方式不同: jdk7使用数组+链表实现,jdk8使用数组+链表+红黑树
        2、jdk7插入在头部,看addEntry的createEntry方法;jdk8插入在尾部(原因在于都要进行遍历,放在尾部能够省去位移操作,提升效率)
        3、jdk8的右移比较简单,没有jdk7那么复杂。为什么jdk8的hash函数会变简单？jdk8中我们知道用的是链表过度到红黑树，效率会提高，所以jdk8提高查询效率的地方由红黑树去实现，没必要像jdk那样右移那么复杂。
        4、jdk7里面addEntry方法扩容的条件size>threshold，还有一个很容易忽略的，就是null!=table[bucketIndex],这个是什么意思？意思是如果当前放进来的值的那个位置也被占用了，才进行扩容，否则还是放到那个空的位置就行了，反正不存在hash冲突
        5、HashMap 中链表红黑树转换界限:若桶中链表元素个数大于等于8时,链表转换成树结构;若桶中链表元素个数小于等于6时,树结构还原成链表;
     */

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
            resize();
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
                    return newNode.setValue(value);
                } else {
                    // 遍历到最后一个node,添加node
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
                // 如果newIndex下标在newTable中有值的时候(发生了hash冲突即已经有值存入到链表中),以链表进行存储 //原来的node的下一个是最新的(与扩容前链表存储的顺序相反,原来在第一个存储到了最后一个)
                oldNode.next = newTable[newIndex];
                // 将之前的node赋值给 newTable[newIndex]
                newTable[newIndex] = oldNode;
                // 继续循环下个node
                oldNode = oldNext;
            }
        }
        // 3.将newTable赋值给老的table
        table = newTable;
        DEFAULT_INITIAL_CAPACITY = newTable.length;
        newTable = null;// 赋值为null--为了垃圾回收机制能够回收
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
        //System.out.println("k:" + key + ",hashCode=" + hashCode);
        int index = hashCode % length; //取模算法
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
        return size;
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
