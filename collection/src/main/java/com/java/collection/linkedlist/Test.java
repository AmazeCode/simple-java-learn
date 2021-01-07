package com.java.collection.linkedlist;

import java.util.LinkedList;

/**
 * @Description: 手写LinkedList测试
 * @Author: zhangyadong
 * @Date: 2021/1/4 0004 下午 10:36
 * @Version: v1.0
 */
public class Test {

    /*
        transient 修饰的字段属性,不会被序列化,注意static修饰的静态变量,天然是不可序列化的
        特点：
        (1)、一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法被访问
        (2)、transient关键字只能修饰变量,而不能修饰方法和类。注意,本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量,则该类需要实现Serializable接口。
        (3)、一个静态变量不管是否被transient修饰,均不能被序列化(如果反序列化后类中static变量还有值,则值为当前JVM中对应static变量的值)。序列化保存的是对象状态，静态变量保存的是类状态，因此序列化并不保存静态变量。
        transient int size = 0; //实际存储大小
        transient Node<E> first; //第一个元素
        transient Node<E> last; //最后一个元素
        // first和last的关系
        1、如果只有一个节点node1：first和last都指向node1
        2、如果有两个节点node1和node2：first指向node1,last指向node2
        3、如果有三个节点node1、node2、node3：first指向node1,last指向node2
        linkLast(e) //目的需要在last后面添加元素
     */
    public static void main(String[] args) {

    }
}
