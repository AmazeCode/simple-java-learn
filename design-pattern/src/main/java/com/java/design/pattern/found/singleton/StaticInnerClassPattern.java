package com.java.design.pattern.found.singleton;

/**
 * 静态内部类方式实现单例(推荐使用)
 */
public class StaticInnerClassPattern {

    //静态内部类(静态方法存放在永久区,)
    private static class SingletonHolder {
        //静态初始化器机制初始化本数据（保证了同步控制，线程安全）
        private static StaticInnerClassPattern instance = new StaticInnerClassPattern();
    }

    //私有构造方法(一定层度上防止反射攻击)
    private StaticInnerClassPattern() {}

    /*
        第一次加载StaticInnerClassPattern类时并不会初始化instance，只有第一次调用getInstance方法时虚拟机加载SingletonHolder 并初始化instance ，
        这样不仅能确保线程安全也能保证Singleton类的唯一性，所以推荐使用静态内部类单例模式。
     */
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
