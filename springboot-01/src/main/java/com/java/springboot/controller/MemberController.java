package com.java.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 这是第二个Controller
 * @Author: zhangyadong
 * @Date: 2021/1/13 14:14
 * @Version: v1.0
 */
@RestController
public class MemberController {

    @RequestMapping("/getMember")
    public Map<String,Object> getMembers(){
        Map<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("errorCode",200);
        hashMap.put("errMsg","魔码");
        return hashMap;
    }
}
