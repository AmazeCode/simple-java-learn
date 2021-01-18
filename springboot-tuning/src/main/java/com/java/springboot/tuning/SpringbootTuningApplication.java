package com.java.springboot.tuning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description: springboot性能调优
 * @params:
 * @return: 
 * @author: zhangyadong
 * @date: 2021/1/18 16:30
 */
@SpringBootApplication
public class SpringbootTuningApplication {

    /*
       SpringBoot性能调优(使用jconsole工具可以进行性能监控)
       1、JVM参数调优：(JVM内存大小默认是4G) 对于Java8来说,一般堆内存的初始容量为物理内存大小的1/64,最大内存不超过物理内存的1/4或1G.
           影响到整体运行,吞吐量
           调优策略：初始化堆内存与最大值相同，减少垃圾回收次数
           堆内存分配
            JVM初始分配的内存由-Xms指定，默认是物理内存的1/64；JVM最大分配的内存由-Xmx指定，默认是物理内存的1/4。默认空余堆内存小于40%时，
            JVM就会增大堆直到-Xmx的最大限制；空余堆内存大于70%时，JVM会减少堆直到-Xms的最小限制。因此服务器一般设置-Xms、
            -Xmx相等以避免在每次GC后调整堆的大小。
           非堆内存分配
            JVM使用-XX:PermSize设置非堆内存初始值，默认是物理内存的1/64；由XX:MaxPermSize设置最大非堆内存的大小，默认是物理内存的1/4。

           内部启动调优：
            -Xmx32m -Xms1m -XX:+PrintGCDetails：打印详细日志,最大堆内存时32M,初始堆内存为1M(经测试在32G的电脑上这么配置是起不来的)
            最好设置为：-Xmx512m -Xms512m -XX:+PrintGCDetails 如果实际中还不行,可适当调整大小

           外部启动调优:在启动命令加上调优参数
           java -server -Xmx32m -Xms1m -jar springboot-turning.jar
       2、扫包优化:
           是在启动优化@SpringBootApplication等同于@ComponentScan(basePackages = {"com.java.springboot"})+@Configuration,
           扫包的核心是扫同级包递归遍历包下子类，如果项目特别大的时候,会扫到无用包，会影响到项目启动时间
           解决方案：项目特别大时,使用@ComponentScan(basePackages = {"com.java.springboot"})+@Configuration替代@SpringBootApplication
       3、默认Tomcat容器修改为Undertow
       4、tomcat参数调优
    */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootTuningApplication.class, args);
    }

}
