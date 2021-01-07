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
##### 序列化的作用
序列化的原本意图是希望对一个Java对象作一下“变换”,变成字节序列,这样一来方便持久化存储到磁盘，避免程序运行结束后对象就从内存里消失,另外变换成字节序列也更便于网络运输和传播,所以概念上很好理解:序列化:把Java对象转换为字节序列。反序列化:把字节序列恢复为原先的Java对象。
##### transient关键字特点
(1)、一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法被访问    
(2)、transient关键字只能修饰变量,而不能修饰方法和类。注意,本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量,则该类需要实现Serializable接口。    
(3)、一个静态变量不管是否被transient修饰,均不能被序列化(如果反序列化后类中static变量还有值,则值为当前JVM中对应static变量的值)。序列化保存的是对象状态，静态变量保存的是类状态，因此序列化并不保存静态变量。    
##### 看源码时先看构造函数的目的？
目的是确定下是否在构造函数内，进行了初始化操作
##### 如果在链表中存放一个节点，那么first和last分别指向谁？(LinkedList原理)
1、如果只有一个节点node1：first和last都指向node1    
2、如果有两个节点node1和node2：first指向node1,last指向node2    
3、如果有三个节点node1、node2、node3：first指向node1,last指向node2        
为什么要有first节点,目的是为了查询,从哪里开始(first),到哪里结束(下标结束)      
为什么要有last节点,目的是为了标识添加元素开始。 
##### LinkedList底层如何查询的？
如果查询第一个元素，那么执行next为0次,如果查询第二个元素,那么执行next为1次，如果查询第三个元素,那么执行next为2次  
查询原理:就是从头开始往下next,直到查找到需要的节点，下标为几,就next几次 



