package com.ac.common.error.code.controller.lock;

import com.ac.common.error.code.controller.lock.pojo.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * @Description: 锁测试
 * @Author: zhangyadong
 * @Date: 2021/4/20 11:03
 * @Version: v1.0
 */
@RestController
@RequestMapping("locklevelcontroller")
public class LockLevelController {

    /*
        加锁前先要清楚锁和对象是不是一个层面的：
            静态字段属于类,类级别的锁才能保护;而非静态字段属于实例,实例级别的锁就可以保护
     */
    // 反例
    @GetMapping("wrong")
    public int wrong (@RequestParam(value = "count",defaultValue = "1000000") int count) {

        Data.reset();
        //多线程循环一定次数调用Data类不同实列的wrong方法
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
        return Data.getCounter();
    }

    // 正例
    @GetMapping("right")
    public int right (@RequestParam(value = "count",defaultValue = "1000000") int count) {

        Data.reset();
        //多线程循环一定次数调用Data类不同实列的right方法
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().right());
        return Data.getCounter();
    }


}
