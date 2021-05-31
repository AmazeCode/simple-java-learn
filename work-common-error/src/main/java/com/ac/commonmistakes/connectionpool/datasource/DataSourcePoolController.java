package com.ac.commonmistakes.connectionpool.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 控制层
 * @Author: zhangyadong
 * @Date: 2021/5/31 11:44
 * @Version: v1.0
 */
@RestController
@RequestMapping("dataSourcePoolController")
@Slf4j
public class DataSourcePoolController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    private Object test() {
        return userService.register();
    }
}
