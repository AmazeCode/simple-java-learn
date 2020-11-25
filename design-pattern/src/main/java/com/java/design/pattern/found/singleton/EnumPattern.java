package com.java.design.pattern.found.singleton;

/**
 * 枚举单例：
 * 枚举--用于定义常量，天生具备JVM保障单例，防止反射攻击
 */
public class EnumPattern {

    /*
        私有化无参构造函数
     */
    private EnumPattern(){

    }

    //本身枚举就是单例的
    static enum SingletonUserEnum{

        INSTANCE;//拥有枚举属性就会执行构造方法,拥有几个就会执行几次

        private EnumPattern enumPattern;
        /*
            枚举无参构造
         */
        private SingletonUserEnum(){
            enumPattern = new EnumPattern();
        }

        /*
            声明为public用于对外部返回
         */
        public EnumPattern getInstance(){
            return enumPattern;
        }
    }

    public static EnumPattern getInstance(){
        return SingletonUserEnum.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        EnumPattern e1 = EnumPattern.getInstance();
        EnumPattern e2 = EnumPattern.getInstance();
        System.out.println(e1 == e2);
    }
}
