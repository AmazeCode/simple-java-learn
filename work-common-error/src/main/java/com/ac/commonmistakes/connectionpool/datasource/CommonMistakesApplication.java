package com.ac.commonmistakes.connectionpool.datasource;

import com.ac.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {

        // 正常文件
        Utils.loadPropertySource(CommonMistakesApplication.class, "good.properties");
        // 错误文件
        // Utils.loadPropertySource(CommonMistakesApplication.class, "bad.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
