package com.ac.common.error.code.controller.toolsafe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: ThreadLocal 没有意识到线程重用导致用户信息错乱的 Bug
 * @Author: zhangyadong
 * @Date: 2021/4/16 14:05
 * @Version: v1.0
 */
@RestController
@RequestMapping(value="threadlocal")
public class ThreadHoleController {

    private ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    /*
        反例结果展示：
        http://localhost:8080/threadlocal/wrong?userId=1
        {
        "before": "http-nio-8080-exec-1:null",
        "after": "http-nio-8080-exec-1:1"
        }

        http://localhost:8080/threadlocal/wrong?userId=2
        {
        "before": "http-nio-8080-exec-1:1",
        "after": "http-nio-8080-exec-1:2"
        }
        从两次产生的结果来看,threadlocal会缓存上一次执行后的结果,如果存放用户信息的时候,肯能产生当前用户获取的确实上个用户的信息

        注意点:
        使用类似 ThreadLocal 工具来存放一些数据时，需要特别注意在代码运行完后，显式地去清空设置的数据。如果在代码中使用了自定义的线程池，也同
        样会遇到这个问题。
     */
    @GetMapping("wrong")
    public Map wrong (@RequestParam("userId") Integer userId) {
        // 设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        // 设置用户信息到ThreadLocal
        currentUser.set(userId);
        // 设置用户信息之后再查询一次ThreadLocal中的用户信息
        String after = Thread.currentThread().getName() + ":" + currentUser.get();
        // 汇总输出两次查询结果
        Map result = new HashMap();
        result.put("before",before);
        result.put("after",after);
        return result;
    }

    /*
        正例结果展示：
        {
        "before": "http-nio-8080-exec-1:null",
        "after": "http-nio-8080-exec-1:1"
        }

        {
        "before": "http-nio-8080-exec-1:null",
        "after": "http-nio-8080-exec-1:2"
        }
     */
    @GetMapping("right")
    public Map right (@RequestParam("userId") Integer userId) {
        // 设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        // 设置用户信息到ThreadLocal
        currentUser.set(userId);
        try {
            // 设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + currentUser.get();
            // 汇总输出两次查询结果
            Map result = new HashMap();
            result.put("before",before);
            result.put("after",after);
            return result;
        } finally {
            // 在finally代码块中删除ThreadLocal中的数据,确保数据不串
            currentUser.remove();
        }
    }
}
