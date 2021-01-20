##### 什么是SpringBoot?
SpringBoot是一个快速整合第三方框架,简化XML,完全采用注解化,内置Http服务器(Jetty、Tomcat),最终以Java应用程序进行执行。                  
重点：快速整合第三方框架,内置Http服务器        
误区：SpringBoot只是快速开发框架,并不是微服务框架;SpringCloud才是微服务框架(rpc远程调用),两者之间关系,
SpringCloud底层依赖于SpringBoot实现微服务接口(SpringBoot web组件集成SpringMVC),采用SpringMVC书写接口       
微服务 http接口 服务治理 客户端调用 网关 短路器......    
##### 分析SpringBoot原理(网上大部分上是将的SpringBoot的实现的流程)
重点:     
1、快速整合第三方框架原理:通过Maven子父依赖关系,相当于需要整合的环境的jar封装好依赖信息                      
2、完全无配置文件(采用注解化)         
    2.1、如何初始化呢?
        没有web.xml,那么tomcat是如何启动呢?注解什么时候产生的?
        spring3.0以上(提供注解),SpringMVC内置注解加载整个SpringMVC容器,相当于java代码编写SpringMVC配置初始化          
    2.2、传统web项目,通过什么配置文件加载整个项目流程?              
    通过web.xml,配置启动                         
3、内置Http服务器          
    Java语言创建Tomcat容器(先执行),然后再执行class文件。
##### SpringBoot内置Tomcat
SpringBoot2.0之后Tomcat版本是8.5以上的

##### 手写SpringBoot步骤
1、手写SpringBoot注解启动方式SpringMVC      
    1.1、SpringMVC 无配置文件 采用注解方式启动步骤：    
        1.1.1、加载Spring容器、加载dispatcherServlet


