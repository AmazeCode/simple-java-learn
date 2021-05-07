package com.ac.commonmistakes.concurrenttool.threadlocal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description: ThreadLocal使用排序
 * @Author: zhangyadong
 * @Date: 2021/5/7 11:19
 * @Version: v1.0
 */
@RestController
@RequestMapping("threadlocalmemoryleak")
public class ThreadLocalMemoryLeakController {

    // ThreadLocal初始化
    private static final ThreadLocal<List<String>> data = ThreadLocal.withInitial(() -> null);

    @GetMapping("wrong")
    public void wrong() {
        List<String> d = IntStream.rangeClosed(1, 10).mapToObj(i -> IntStream.rangeClosed(1, 1000000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString())
                .collect(Collectors.toList());
        data.set(d);
    }
}
