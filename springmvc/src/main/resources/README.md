##### Servlet声明周期
加载->实例化->服务->销毁    
##### Servlet是单例还是多例?
servlet是单例的,会有线程安全问题(如何判断单例还是多例:判断构造函数是否被加载多次)
##### springmvc运行流程
参考:resources/pic/SpringMvc运行流程.png 面试之前最好自己口述说下   
##### 参数绑定--反射机制在jdk1.8之前有一个缺点:不能够获取方法参数名称,自己手些需要注意
springmvc源码中如果判断是jdk1.7时,会采用 asm字节码技术，获取方法参数名称,如果判断是1.8时,则会采用jdk1.8的反射机制去获取