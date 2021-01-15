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


