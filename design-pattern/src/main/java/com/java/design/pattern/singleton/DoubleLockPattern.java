package com.java.design.pattern.singleton;

/**
 * 单例-双重锁实现，线程安全
 */
public class DoubleLockPattern {

    //私有的 静态的 本类属性(volatile禁止重新排序)
    private volatile static DoubleLockPattern _instance;

    /*
        私有构造方法
     */
    private DoubleLockPattern(){}

    /*
        版本三:双重检查模式 （DCL）(不推荐使用),可以使用“静态内部类”的实现方式代替
        执行双重检查是因为，如果多个线程同时了通过了第一次检查，并且其中一个线程首先通过了第二次检查并实例化了对象，
        那么剩余通过了第一次检查的线程就不会再去实例化对象,提高性能。

        _instance添加volation禁用重排序。实例化对象的那行代码（标记为error的那行），实际上可以分解成以下三个步骤：
        1、分配内存空间
        2、初始化对象
        3、将对象指向刚分配的内存空间
        但是有些编译器为了性能的，可能会将第二步和第三步进行重排序，顺序就成了：
        1、分配内存空间
        2、将对象指向刚分配的内存空间 （可能为空）
        3、初始化对象

        在这里使用volatile会或多或少的影响性能，但考虑到程序的正确性，牺牲这点性能还是值得的。
        DCL优点是资源利用率高，第一次执行getInstance时单例对象才被实例化，效率高。
        缺点是第一次加载时反应稍慢一些，在高并发环境下也有一定的缺陷，虽然发生的概率很小。
        DCL虽然在一定程度解决了资源的消耗和多余的同步，线程安全等问题，但是他还是在某些情况会出现失效的问题，
        也就是DCL失效，在《java并发编程实践》一书建议用静态内部类单例模式来替代DCL。
     */
    public static DoubleLockPattern getInstance() {
        if (_instance == null) {//第一次检查
            synchronized (DoubleLockPattern.class) {
                if (_instance == null) { //第二次检查   //线程1创建完对象后，线程会判断一次就不会创建对象了。解决了首次创建对象的唯一性问题。
                    _instance = new DoubleLockPattern(); //可能error,通过_instance添加error修饰
                }
            }
        }
        return _instance;
    }

    public static void main(String[] args) {
        DoubleLockPattern d1 = DoubleLockPattern.getInstance();
        DoubleLockPattern d2 = DoubleLockPattern.getInstance();
        System.out.println(d1 == d2);
    }
}
