package com.java.collection.hashmap;

/**
 * @Description: 纯手写Map接口
 * @Author: zhangyadong
 * @Date: 2021/1/11 11:34
 * @Version: v1.0
 */
public interface ExtMap<K,V> {

    /**
     * @description: 向集合中插入数据
     * @params: [key, value]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/11 11:35
     */
    public V put(K key,V value);

    /**
     * @description: 获取集合中的数据
     * @params: [key]
     * @return: V
     * @author: zhangyadong
     * @date: 2021/1/11 11:37
     */
    public V get(K key);

    /**
     * @description: 获取集合大小
     * @params: []
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/11 11:37
     */
    public int size();

    // Entry的作用===Node节点
    interface Entry<K,V>{

        K getKey();

        V getValue();

        V setValue(V value);
    }
}
