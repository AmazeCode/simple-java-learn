package com.ac.commonmistakes.connectionpool.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Redis连接池,多线程调用场景
 * @Author: zhangyadong
 * @Date: 2021/5/8 15:51
 * @Version: v1.0
 */
@Slf4j
@RequestMapping("jedismisreuse")
@RestController
public class JedisMisreuseController {

    // 实例化JedisPool redis连接池
    private static JedisPool jedisPool = new JedisPool("127.0.0.1",6379);

    @PostConstruct // 服务启动初始化,类似于spring bean注入
    public void init () {

        try (Jedis jedis = new Jedis("127.0.0.1",6379)) {
            // 设置redis连接密码
            jedis.auth("123456");
            Assert.isTrue("OK".equals(jedis.set("a", "1")), "set a = 1 return OK");
            Assert.isTrue("OK".equals(jedis.set("b", "2")), "set b = 2 return OK");
        }
        // 关闭线程池
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            jedisPool.close();
        }));
    }

    /*
        错误示例,开发时不要这样用
        执行示例结果：
        Expect a to be 1 but found 2
        Expect a to be 2 but found 1
     */
    @GetMapping("wrong")
    public void wrong () throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("123456");
        // 正常情况下警告不应该出现,key为a对应的value只是为"1"
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                // 获取key值
                String result = jedis.get("a");
                if (!"1".equals(result)) {
                    log.warn("Expect a to be 1 but found {}", result);
                    return;
                }
            }
        }).start();

        // 正常情况下警告不应该出现,key为b对应的value只是为"2"
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                // 获取key值
                String result = jedis.get("b");
                if (!"2".equals(result)) {
                    log.warn("Expect a to be 2 but found {}", result);
                    return;
                }
            }
        }).start();
        // 休眠5秒钟
        TimeUnit.SECONDS.sleep(5);
    }

    /*
        正确示例
        总结:如果不使用jedisPool,每个Thread单独new Jedis("127.0.0.1",6379);同样也不会出现线程安全问题,也就是说在多线程环境中,只要不公用资源便不会出现线程安全问题
     */
    @GetMapping("right")
    public void right () throws InterruptedException {

        new Thread(() -> {
            // 使用 jedisPool 连接池
            try (Jedis jedis = jedisPool.getResource()) {
                // 设置redis访问权限
                jedis.auth("123456");
                for (int i = 0; i < 1000; i++) {
                    // 获取key值
                    String result = jedis.get("a");
                    if (!"1".equals(result)) {
                        log.warn("Expect a to be 1 but found {}", result);
                        return;
                    }
                    log.info("right a result: {}",result);
                }
            }
        }).start();

        new Thread(() -> {
            // 使用 jedisPool 连接池
            try (Jedis jedis = jedisPool.getResource()) {
                // 设置redis访问权限
                jedis.auth("123456");
                for (int i = 0; i < 1000; i++) {
                    // 获取key值
                    String result = jedis.get("b");
                    if (!"2".equals(result)) {
                        log.warn("Expect a to be 2 but found {}", result);
                        return;
                    }
                    log.info("right b result: {}",result);
                }
            }
        }).start();
        // 休眠5秒钟
        TimeUnit.SECONDS.sleep(5);
    }

    /*
        超时示例
     */
    @GetMapping("timeout")
    public String timeout (@RequestParam("waittimeout") int waittimeout,
                         @RequestParam("conntimeout") int conntimeout) throws InterruptedException {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1);
        jedisPoolConfig.setMaxWaitMillis(waittimeout);

        // 连接redis
        try (JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, conntimeout,"123456");
             Jedis jedis = jedisPool.getResource();){
            return jedis.set("test","test");
        }
    }
}
