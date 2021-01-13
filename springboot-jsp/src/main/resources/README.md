##### SpringBoot整合jsp注意点（步骤）
1、SpringBoot整合Jsp时,必须要创建为war项目,否则会找不到页面;     
2、引入tomcat-embed-jasper外部tomcat    
3、在application.properties中配置jsp文件识别配置    
4、不要把JSP页面存放在resources下,jsp不能被访问到
##### 出现的问题
IDEA开发工具使用SpringBoot自带的tomact时,@SpringBootApplication注释的类进行启动项目,否则jsp页面会找不到报404，
正确的启动方式采用maven插件启动(官方推荐),idea中找到项目的Plugins然后再找到spring-boot:run运行然后访问即可。
