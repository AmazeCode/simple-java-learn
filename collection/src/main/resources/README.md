## 手些集合框架专题
##### ArrayList 删除原理分析
使用arraycopy往前移动数据,将最后一个变为空
##### java反射机制缺点
java反射机制,不能够获取泛型类型,可以通过字节码技术去获取
##### vector 底层实现原理
Vector 是线程安全的,但是性能比ArrayList要低，Vector 每次扩容为原来的2倍，而ArrayList每次扩容为原来的1.5倍
##### Vector和ArrayList区别
1. Vector是线程安全的，源码中有很多synchronized可以看出，而ArrayList不是。导致Vector效率无法和ArrayList相比    
2. ArrayList和Vector 都采用线性连需存储空间，当存储空间不足的时候，ArrayList默认增加原来的50%,增加后是扩容前的1.5倍(当初始容量为1时,扩容后容量是2),Vector默认增加原来的一倍,增加后是扩容前的2倍        
3. Vector可以设置capacityIncrement,而ArrayList不可以,从字面理解是capacity容量,Increment增加,容量增长的参数.  
##### 单向链表原理
1、为什么需要有Fist：目的是为了查询,从哪里开始(first)到哪里结束(下标结束)   
2、为什么要有last: 添加元素开始   
3、添加原理:在last节点后关联    
4、查询原理:默认从first开始next，从开始节点查询到需要的节点

