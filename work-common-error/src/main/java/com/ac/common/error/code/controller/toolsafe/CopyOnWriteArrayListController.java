package com.ac.common.error.code.controller.toolsafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description: CopyOnWriteArrayList 读写性能测试
 * @Author: zhangyadong
 * @Date: 2021/4/18 16:58
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("copyonwritearraylist")
public class CopyOnWriteArrayListController {

    /*
        结论:synchronizedList写的性能高于CopyOnWriteArrayList的性能
        6241326200  099%  Write:copyOnWriteArrayList
        040660100  001%  Write:synchronizedList
     */
    // 测试并发写性能
    @GetMapping("write")
    public Map testWrite() {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        StopWatch stopWatch = new StopWatch();
        int loopCount = 100000;
        stopWatch.start("Write:copyOnWriteArrayList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ ->copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("Write:synchronizedList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map result = new HashMap();
        result.put("copyOnWriteArrayList",copyOnWriteArrayList.size());
        result.put("synchronizedList",synchronizedList.size());
        return result;
    }

    // 帮助方法用来填充list
    private void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList()));
    }

    /*
        结论:copyOnWriteArrayList读的性能高于synchronizedList
        023764900  005%  Read:copyOnWriteArrayList
        414425000  095%  Read:synchronizedList
     */
    @GetMapping("read")
    public Map testRead() {
        // 创建两个测试对象
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        // 填充数据
        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);
        StopWatch stopWatch = new StopWatch();
        int loopCount = 1000000;
        int count = copyOnWriteArrayList.size();
        stopWatch.start("Read:copyOnWriteArrayList");
        // 循环1000000次并发从CopyOnWriteArrayList随机查询元素
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("Read:synchronizedList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> synchronizedList.get(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map result = new HashMap();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }
}
