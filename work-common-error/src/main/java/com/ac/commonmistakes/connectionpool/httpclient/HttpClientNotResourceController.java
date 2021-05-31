package com.ac.commonmistakes.connectionpool.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 线程池复用Controller
 * @Author: zhangyadong
 * @Date: 2021/5/31 11:58
 * @Version: v1.0
 */
@RestController
@RequestMapping("httpclientnotreuse")
@Slf4j
public class HttpClientNotResourceController {

    private static CloseableHttpClient httpClient = null;

    static {
        // 当然,也可以把CloseableHttpClient,然后在@PreDestory标记的方法内close
        httpClient = HttpClients.custom()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .evictIdleConnections(60, TimeUnit.SECONDS).build();
        // JVM 关闭之前通过 addShutdownHook 钩子关闭连接池
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            try {
                httpClient.close();
            }catch (IOException e) {

            }
        }));
    }

    @GetMapping("right")
    public String right() {
        try(CloseableHttpResponse response = httpClient.execute(new HttpGet("http://127.0.0.1:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @GetMapping("wrong1")
    public String wrong1() {
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();
        try (CloseableHttpResponse response = client.execute(new HttpGet("http://127.0.0.1:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @GetMapping("wrong2")
    public String wrong2() {
        try (CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();
             CloseableHttpResponse response = client.execute(new HttpGet("http://127.0.0.1:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @description: 测试连接
     * @param:
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/5/31 12:04
     */
    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
