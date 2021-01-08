package com.java.collection.hashmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 基于ArrayList实现HashMap集合(不推荐,效率特别低)
 * @Author: zhangyadong
 * @Date: 2021/1/8 16:21
 * @Version: v1.0
 */
public class ExtArrayListHashMap<Key,Value> {

    /*
        实现步骤：
        1、定义HashMap容器
        2、调用put的时候,将该hash存储对象存入到ArrayList集合中

        添加时key相同的时候是需要做覆盖处理
     */

    // Map 存储容量 1、定义HashMap容器
    private List<Entry<Key,Value>> tables = new ArrayList<Entry<Key,Value>>();
    // Map 容器的实际容量
    //private int size;

    /**
     * @description: 添加方法
     * @params: [key, newValue]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/1/8 16:36
     */
    public void put(Key key, Value value) {
        // 避免重复,先去查下是否存在,如果存在,进行修改
        Entry entry = getEntry(key);
        if (entry != null) {
            // 已经存在
            entry.value = value;
        } else {
            Entry newEntry = new Entry(key,value);
            //2、调用put的时候,将该hash存储对象存入到ArrayList集合中
            tables.add(newEntry);
            //size++;//这里已经不需要size++了,因为ArrayList底层已经封装好了会自动size++
        }
    }

    /**
     * @description: 查询方法
     * @params: [key]
     * @return: Value
     * @author: zhangyadong
     * @date: 2021/1/8 16:37
     */
    public Value get(Key key){
        Entry<Key,Value> entry = getEntry(key);
        return entry == null ? null : entry.value;
    }

    /**
     * @description: 获取Entry对象
     * @params: [key]
     * @return: com.java.collection.hashmap.Entry<Key,Value>
     * @author: zhangyadong
     * @date: 2021/1/8 16:36
     */
    private Entry<Key,Value> getEntry(Key key){
        // 从头查询到尾效率低,可以做优化,可以选用查找算法进行优化
        for (Entry<Key,Value> entry : tables) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ExtArrayListHashMap hashMap = new ExtArrayListHashMap<String,String>();
        hashMap.put("a","aaa");
        hashMap.put("b","bbb");
        hashMap.put("c","ccc");
        hashMap.put("a","dde");
        System.out.println(hashMap.get("a"));
    }
}

class Entry<Key,Value>{

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
    public Entry(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
}
