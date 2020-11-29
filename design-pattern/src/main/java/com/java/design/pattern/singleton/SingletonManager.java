package com.java.design.pattern.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 容器实现单例
 * @Author: zhangyadong
 * @Date: 2020/11/26 15:07
 * @Version: v1.0
 */
public class SingletonManager {

    private static Map<String,Object> obj = new HashMap<String,Object>();

    /*
        构造方法
     */
    private SingletonManager() {

    }

    /*
        用SingletonManager 将多种的单例类统一管理，在使用时根据key获取对象对应类型的对象。
        这种方式使得我们可以管理多种类型的单例，并且在使用时可以通过统一的接口进行获取操作，
        降低了用户的使用成本，也对用户隐藏了具体实现，降低了耦合度。

        包含相同的key时,不进行创建对象
     */
    public static void registerService(String key, Object instance) {
        if (!obj.containsKey(key)) {
            obj.put(key, instance);
        }
    }

    /*
        获取实例
     */
    public static Object getService(String key) {
        return obj.get(key);
    }

    /*
        验证结果
     */
    public static void main(String[] args) {
        SingletonManager.registerService("singlon",new SingletonManager());
        System.out.println(SingletonManager.getService("singlon"));
        SingletonManager.registerService("singlon",new SingletonManager());
        System.out.println(SingletonManager.getService("singlon"));
    }
}
