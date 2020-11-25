package com.java.design.pattern.found.singleton;

/**
 * 静态内部类方式实现单例；
 */
public class StaticInnerClassPattern {

    //静态内部类(静态方法存放在永久区,)
    private static class SingletonHolder {
        //静态初始化器机制初始化本数据（保证了同步控制，线程安全）
        private static StaticInnerClassPattern instance = new StaticInnerClassPattern();
    }

    //私有构造方法(一定层度上防止反射攻击)
    private StaticInnerClassPattern() {}

    //获得对象实例
    public static StaticInnerClassPattern getInstance() {
        return SingletonHolder.instance;
    }

    /*
        验证
     */
    public static void main(String[] args) {
        StaticInnerClassPattern si_1 = StaticInnerClassPattern.getInstance();
        StaticInnerClassPattern si_2 = StaticInnerClassPattern.getInstance();
        System.out.println(si_1 == si_2);
    }
}
