##### SpringBoot启动方式三种：
1、在类上添加@EnableAutoConfiguration,类中创建main方法,启动main方法即可(这种方式只是适用于当前类),可参考IndexController   
2、在类上添加@EnableAutoConfiguration和@ComponentScan("com.java.springboot.controller"),使得扫包范围内的类生效,使用main方法启动,可参考App
3、在启动类上添加@SpringBootApplication当前包下所有的类以及子包下的所有类都可以扫到,使用main方法启动,参考Springboot01Application
##### 规范问题
1、如果项目中的类加上了@RestController,可能是一个微服务项目,那么就需要统一接口,提供服务接口,注意规范。      
2、项目使用页面SpringBoot整合JSP、ftl。     
3、动态页面静态化--->将动态页面转成伪HTML元素 目的：提高SEO,搜索优化。SEO就是搜索引擎优化,页面提交给搜索引擎,让搜索引擎找到该页面