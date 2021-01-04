package com.java.collection.arraylist;

import java.util.Arrays;

/**
 * @Description: 手些ArrayList
 * @Author: zhangyadong
 * @Date: 2021/1/3 11:08
 * @Version: v1.0
 */
public class ExtArrayList<E> implements ExtList<E> {

    /*
        1.jdk1.7 之后数组默认数据大小代码存放在add方法(Jdk1.6默认在构造函数初始化大小)
        2.arraylist底层采用数组实现 数组名称elementData
        3.arraylist底层默认初始化大小为10
     */

    // arrayList底层采用数组存放
    private Object[] elementData;

    /**
     * 默认数组容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 记录实际ArrayList大小
     */
    private int size;


    // ArrayList 指定数组初始容量
    public ExtArrayList(int initialCapacity){
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("初始容量不能小于0");
        }
        elementData = new Object[initialCapacity];
    }

    // 默认初始容量为10
    public ExtArrayList() {
        this(DEFAULT_CAPACITY);
    }

    // 线程安全问题 ArrayList 每次扩容是以1.5倍
    public void add (E e) {
        // 1.判断实际存放的数据容量是否大于elementData,进行扩容
        ensureExplicitCapacity(size + 1);
        // 2.使用下标进行赋值
        elementData[size++] = e;
    }

    /**
     * 插入到指定位置
     */
    public void add (int index, Object object) {
        rangeCheckForAdd(index);
        // 1.判断实际存放的数据容量是否大于elementData,进行扩容
        ensureExplicitCapacity(size + 1);

        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        elementData[index] = object;
        size++;
    }

    @Override
    public E set(int index, Object object) {
        //校验
        rangeCheckForAdd(index);

        E oldValue = elementData(index);
        elementData[index] = object;

        return oldValue;
    }

    /**
     * 校验添加数组下表越界
     */
    public void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("数组下标越界了！>>"+index);
    }

    // int minCapacity 最小为当前size+1  （扩容算法和jdk相同）
    private void ensureExplicitCapacity(int minCapacity) {
        if (size == elementData.length) {
            // 原来本身elementData容量大小 假如原来为2
            int oldCapacity = elementData.length;
            //新的数组容量大小 (oldCapacity >> 1)=oldCapacity/2
            int newCapacity = oldCapacity + (oldCapacity >> 1); // (2+ 2/2)=3
            // 如果初始容量为1，那么他扩容的大小为多少(答案为2,即 size+1)？ jdk源码使用下面代码实现
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity; //最少保证容量和minCapacity一样
            }
            //将老数组中的值赋值到新数组中去
            elementData = Arrays.copyOf(elementData, newCapacity);
            /*
            // 通俗代码实现扩容
            Object[] newObject = new Object[newCapacity];
            for (int i = 0; i < elementData.length; i++) {
                newObject[i] = elementData[i];
            }
            elementData = newObject;
            */
        }
    }

    /**
     * 删除元素
     */
    public E remove(int index) {
        rangeCheck(index);

        // 1.使用下标查询该值是否存在
        E oldValue = elementData(index);
        // 计算删除元素后面的长度
        int numMoved = size - index - 1;
        /*
            src--这是源数组 srcPos--这是源数组中的起始复制位置 dest--这是目标数组 destPos--这是目标数据中的起始覆盖位置  length--这是一个要复制的数组元素的数目
         */
        // 2.删除原理分析：使用arraycopy往前移动数据,将最后一个变为空
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        elementData[--size] = null;// 将最后一元素变为空
        return oldValue;
    }

    /**
     * 删除对象, 相同元素只删除第一个
     */
    public boolean remove(Object object) {
        for (int i =0; i < elementData.length; i++) {
            Object value = elementData[i];
            if (value.equals(object)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    // 使用下表获取数组
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("数组下标越界了");
    }

    /**
     *  获取数组长度大小
     */
    public int getSize() {
        return size;
    }
}
