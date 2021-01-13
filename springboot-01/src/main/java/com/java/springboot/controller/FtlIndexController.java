package com.java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Description: 整合Freemarker
 * @Author: zhangyadong
 * @Date: 2021/1/13 0013 下午 9:10
 * @Version: v1.0
 */
@Controller
public class FtlIndexController {

    /*
        在/src/main/resources下新建templates文件夹，SpringBoot默认能识别templates下的.ftl文件
     */
    @RequestMapping("/ftlIndex")
    public String index(Map<String,Object> map){
        map.put("name","小米");
        map.put("age","18");
        map.put("sex","0");
        return "ftlIndex";
    }
}
