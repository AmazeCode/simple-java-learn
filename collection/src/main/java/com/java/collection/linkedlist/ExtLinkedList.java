package com.java.collection.linkedlist;

/**
 * @Description: 纯手写LinkedList
 * @Author: zhangyadong
 * @Date: 2021/1/4 0004 下午 10:50
 * @Version: v1.0
 */
public class ExtLinkedList<E> {

    /*
        LinkedList底层实现的方式:
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

    // 链表实际存储元素
    private int size;

    // 第一个元素(头节点,为了查询开始)
    private Node first;

    // 最后个元素(尾结点,为了添加开始)
    private Node last;

    /**
     * @description: 添加方法(线程不安全需要加同步)
     * @params: [e]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 8:12
     */
    public void add(E e) {
        linkLast(e);
    }

    void linkLast(E e) {
        //创建节点
        Node node = new Node();
        //给节点赋值
        node.object = e;
        if (first == null) {
            //添加第一个元素,给节点赋值
            //上个节点(node)
            //node.prev = null;
            //下个节点(node)
            //node.next = null;
            //给第一个元素赋值(node节点赋值)
            first = node;
            //last = node;
            //第一个元素的头和尾都属于自己
        } else {
            //添加第二个或以上数据
            node.prev = last;
            //如果此时添加的是第二个节点时,last.next指的是第一个节点
            last.next = node;//将上一个元素的next赋值,标识为最新的
            //last = node;
        }
        last = node;
        //实际长度++
        size++;
    }

    /**
     * @description: 使用下标添加节点(线程不安全,size是公用参数)
     * @params: [index, e]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 10:37
     */
    public void add(int index, E e) {
        // 下标验证
        checkElementIndex(index);

        if (index == size)
            linkLast(e);
        else
            linkBefore(index,e);

    }

    /**
     * @description: 添加元素,非最后一个元素
     * @params: [index, e]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 11:05
     */
    void linkBefore(int index, E e) {
        // 1.获取当前Node节点
        Node oldNode = getNode(index);
        if (oldNode != null) {
            //2.获取当前元素的上下节点
            Node oldPrevNode = oldNode.prev;//node1
            Node oldNextNode = oldNode.next;//node3
            //新节点 node4
            Node newNode = new Node();
            newNode.object = e;
            // node4 下一个节点为node2
            newNode.next = oldNode;
            if (oldPrevNode == null) {
                // 将新元素置为头部节点
                first = newNode;
            } else {
                // node4 上一个节点为node1
                newNode.prev = oldPrevNode;
                // node1 下个节点变为node4
                oldPrevNode.next = newNode;
            }
            // node2 上个节点变为node4
            oldNode.prev = newNode;
            size++;
        }
    }

    /**
     * @description: 获取指定节点内容（从头查到尾效率不高,可以使用折半查找查找算法进行优化）
     * @params: [index]
     * @return: java.lang.Object
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 8:11
     */
    public Object get(int index) {
        // 下标验证
        checkElementIndex(index);
        return getNode(index).object;
    }

    /**
     * @description: 获取当前node节点
     * @params: [index]
     * @return: com.java.collection.linkedlist.ExtLinkedList<E>.Node
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 10:05
     */
    public Node getNode(int index) {
        // 下标验证
        checkElementIndex(index);

        Node node = null;
        if (first != null) {
            node = first;//默认取第0个
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    /**
     * @description: 指定下标删除(线程不安全需要加同步)
     * @params: [index]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 10:01
     */
    public synchronized void remove(int index) {
        // 下标验证
        checkElementIndex(index);
        // 1.获取当前Node节点
        Node oldNode = getNode(index);
        if (oldNode != null) {
            //2.获取删除元素的上下节点
            Node oldPrevNode = oldNode.prev;//node1
            Node oldNextNode = oldNode.next;//node3
            //
            if (oldPrevNode == null){
                //删除元素为第一个 重新换头
                first = oldNextNode;//直接把下个节点置为头节点
            } else {
                //将node1的下一个节点变为node3
                oldPrevNode.next = oldNextNode;
                oldNode.prev = null;//让垃圾回收机制进行回收
            }

            if (oldNextNode == null) {
                // 删除元素为最后一个元素
                last = oldPrevNode;
            } else {
                //将node3的上一个节点变为node1
                oldNextNode.prev = oldPrevNode;
                oldNode.next = null;//让垃圾回收机制进行回收
            }
            oldNode.object = null;//让垃圾回收机制进行回收
            size--;
        }
    }
    
    /**
     * @description: 获取集合大小
     * @params: []
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/7 0007 下午 8:16
     */
    public int getSize () {
        return size;
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
        Node prev;
        // 下个节点的值
        Node next;
    }

    public static void main(String[] args) {
        ExtLinkedList<String> extLinkedList = new ExtLinkedList<String>();
        extLinkedList.add("a");
        extLinkedList.add("b");
        extLinkedList.add("c");
        extLinkedList.add(0,"e");//在头部插入圆度
        System.out.println("删除之前："+extLinkedList.get(2));
        extLinkedList.remove(2);
        System.out.println("删除之后："+extLinkedList.get(2));
    }
}
