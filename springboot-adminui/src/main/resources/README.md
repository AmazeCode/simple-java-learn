##### 1.什么是SpringBoot监控中心
针对微服务服务器监控，服务器内存变化(堆内存、线程、日志管理等)、检测服务配置连接地址是否可用(模拟访问，懒加载)、统计现在有多少Bean(是Spring容器中的Bean)，统计SpringMVC@RequestMapping(统计http接口)                  
Actuator监控中心(没有界面，返回json格式)                  
AdminUI底层使用Actuator监控应用，实现可视化界面           
应用场景：生产环境          
默认情况下，监控中心提供三个接口权限。                    
在SpringBoot2.0之后，监控中心 接口地址发生变化，在访问监控中心接口 前面加上/actuator/                  
在2.0之前，访问监控中心接口 前面没有/actuator/                   
##### 2、为什么要用SpringBoot监控中心？
Actuator是SpringBoot的一个附加功能，可帮助你在应用程序生产环境实时监控和管理应用程序。可以使用HTTP的各种请求来监管，审计，收集应用程序的运行情况，特别对于微服务管理十分有意义，缺点：没有可视化界面

