package com.java.springboot.hot.deploy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 热部署测试Controller
 * @Author: zhangyadong
 * @Date: 2021/1/18 14:14
 * @Version: v1.0
 */
@RestController
public class IndexController {

    /*
        在热部署测试的时候,不要改当前方法测试
        专业:对class文件新增方法去做测试
        devtools 每次保存的时候自动保存,有些工具会默认自带热部署功能,项目大的时候不要用热部署功能
     */

    @RequestMapping("/indexDev")
    public String indexDev() {
        String result = "springboot 2.0 devtool";
        return result;
    }

    @RequestMapping("/myDev")
    public String myDev() {
        String result = "springboot 1.0 devtool";
        return result;
    }
}
