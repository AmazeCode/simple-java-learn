package com.ac.commonmistakes.concurrenttool.ciavspia;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 测试putIfAbsent与computeIfAbsent区别
 * @Author: zhangyadong
 * @Date: 2021/5/7 10:17
 * @Version: v1.0
 */
@Slf4j
public class CommonMistakesApplication {

    // 测试
    public static void main(String[] args) {
        test(new HashMap<>());
        test(new ConcurrentHashMap<>());
    }

    /*
        测试结论:
        putIfAbsent:返回旧值,如果没有则返回null(先计算value,再判断key是否存在) 不支持lambda表达式
        computeIfAbsent:存在时返回存在的值,不存在时返回新值(当key不存在时，执行value计算方法，计算value)  赋值支持lambda表达式
        HashMap的put值可以为null,ConcurrentHashMap的put值不能为null
     */
    private static void test(Map<String, String> map) {
        log.info("class : {}", map.getClass().getName());
        try {
            log.info("putIfAbsent null value : {}", map.putIfAbsent("test1", null));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log.info("test containsKey after putIfAbsent : {}", map.containsKey("test1"));
        log.info("computeIfAbsent null value : {}", map.computeIfAbsent("test2", k -> null));
        log.info("test containsKey after computeIfAbsent : {}", map.containsKey("test2"));
        log.info("putIfAbsent non-null value : {}", map.putIfAbsent("test3", "test3"));
        log.info("computeIfAbsent non-null value : {}", map.computeIfAbsent("test4", k -> "test4"));
        log.info("putIfAbsent expensive value : {}", map.putIfAbsent("test4", getValue()));
        log.info("computeIfAbsent expensive value : {}", map.computeIfAbsent("test4", k -> getValue()));
    }

    private static String getValue() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return UUID.randomUUID().toString();
    }
}
