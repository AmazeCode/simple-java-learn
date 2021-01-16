##### spring事物分类
1.声明式事物     
2.编程式事物
##### spring事物原理
Aop技术 环绕通知进行拦截
##### 使用spring事物注意事项
不要try,因为要将捕获异常抛给外层
##### SpringBoot默认是集成事物,只需要在方法上加上@Transactional即可
##### SpringBoot分布式事务管理
使用springboot+jta+atomikos分布式事物管理(Atomikos是一个为java平台提供增值服务的并且开源类事务管理器)
##### 多数据源使用场景
公司分为两个数据库，一个数据库专门存放共同配置文件,一个数据库垂直业务数据库.         
垂直 根据业务划分具体数据库。  
在一个项目中有多少个数据源(连接不同库jdbc):无限大，具体根据内存多少去确定      
##### 在一个项目中多数据源如何划分
1.：根据分包名（业务）    
例如：    
com.java.springboot.test01 ---- datasource01      
com.java.springboot.test02 ---- datasource02       
2.注解方式      



