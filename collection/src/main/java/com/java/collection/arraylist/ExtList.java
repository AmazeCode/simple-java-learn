package com.java.collection.arraylist;

/**
 * @Description: 自定义List接口,ArrayList 泛型接口
 * @Author: zhangyadong
 * @Date: 2021/1/3 15:59
 * @Version: v1.0
 */
public interface ExtList<E> {

    /**
     * @description: 添加元素
     * @params: [e]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/3 16:11
     */
    public void add (E e);

    /**
     * @description: 获取元素
     * @params: [index]
     * @return: E
     * @author: zhangyadong
     * @date: 2021/1/3 16:11
     */
    public E get(int index);

    /**
     * @description: 移除元素
     * @params: [index]
     * @return: E
     * @author: zhangyadong
     * @date: 2021/1/3 16:15
     */
    public E remove(int index);

    /**
     * @description: 移除指定元素,只能移除第一个
     * @params: [object]
     * @return: boolean
     * @author: zhangyadong
     * @date: 2021/1/3 16:42
     */
    public boolean remove(Object object);

    /**
     * @description: 获取数组大小
     * @params: []
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/3 16:16
     */
    public int getSize();
}
