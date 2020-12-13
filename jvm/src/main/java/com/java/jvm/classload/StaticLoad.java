package com.java.jvm.classload;

/**
 * 静态加载顺序
 */
public class StaticLoad {

    private static int age = 500;

    /*
        静态代码块只会初始化一次
        编译器自动收藏类中的所有类变量赋值动作和静态语句块中的语句合并执行，代码从上往下执行,即age显示的是最下面的赋值
     */
    static{
        System.out.println("2静态代码块初始化");
        age = 700;
    }

    public StaticLoad() {
        System.out.println("1构造函数");
    }

    public static void main(String[] args) throws Exception {
        StaticLoad s1 = new StaticLoad();
        StaticLoad s2 = new StaticLoad();
    }
}
