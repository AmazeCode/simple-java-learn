package com.java.springboot.redis.controller;

import com.java.springboot.redis.pojo.User;
import com.java.springboot.redis.util.ControlRedisUtil;
import com.java.springboot.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Description: Redis测试
 * @Author: zhangyadong
 * @Date: 2021/6/7 0007 下午 8:58
 * @Version: v1.0
 */
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(@RequestBody User user){
        //ControlRedisUtil controlRedisUtil = new ControlRedisUtil();
        //Set<Object> set1 = controlRedisUtil.sGet("Key1");
        Set<Object> set1 = redisTemplate.opsForSet().members("Key1");
        if (!CollectionUtils.isEmpty(set1)) {
            for (Object obj : set1) {
                User user1 = (User)obj;
                if (user.getAge() == user1.getAge()) {
                    // 移除对象
                    //controlRedisUtil.setRemove("Key1",obj);
                    redisTemplate.opsForSet().remove("Key1",((User) obj).getName());
                    redisTemplate.opsForSet().add("Key1",user1.getName());
                    //controlRedisUtil.sSet("Key1",user1);
                }
            }
        } else {
            redisTemplate.opsForSet().add("Key1",user.getName());
            //controlRedisUtil.sSet("Key1",user);
        }
    }

   /* @PostMapping(value = "/test1")
    public void test1(@RequestBody User user){
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(stringRedisTemplate);
        Set<Object> set1 = redisUtil.sGet("Key1");
        if (!CollectionUtils.isEmpty(set1)) {
            for (Object obj : set1) {
                User user1 = (User)obj;
                if (user.getAge() == user1.getAge()) {
                    controlRedisUtil("Key1",obj);
                    controlRedisUtil.sSet("Key1",user1);
                }
            }
        }
    }*/
}
