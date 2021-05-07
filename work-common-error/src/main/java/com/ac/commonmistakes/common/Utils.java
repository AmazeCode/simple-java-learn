package com.ac.commonmistakes.common;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: 工具类
 * @Author: zhangyadong
 * @Date: 2021/5/7 12:02
 * @Version: v1.0
 */
@Slf4j
public class Utils {

    /**
     * @description: 加载配置文件工具类
     * @params: [clazz, fileName]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/5/7 12:05
     */
    public static void loadPropertySource(Class clazz,String fileName){
        try {
            Properties p = new Properties();
            InputStream inputStream = clazz.getResourceAsStream(fileName);
            p.load(inputStream);
            p.forEach((k,v)->{
                log.info("{}={}",k,v);
                System.setProperty(k.toString(),v.toString());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
