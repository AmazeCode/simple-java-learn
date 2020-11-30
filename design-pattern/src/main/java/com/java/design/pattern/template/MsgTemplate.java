package com.java.design.pattern.template;

/**
 * 模板方法模式
 * 将一类业务封装成一个统一的模板方法
 */
public abstract class MsgTemplate {

    /*
        为了防止子类重写父类的骨架方法，可以在父类中对骨架方法使用final
     */
    public final void sendMsg(){
        //1、开始日志
        addHeadLog();
        //2、调用具体的供应商发送
        httpRequest();
        //3、结束日志报文
        addFootLog();
    }

    /*
        开始日志
     */
    private void addHeadLog(){
        System.out.println("调用运营商开始日志。。。。。。。。。。。");
    }

    /*
        调用具体的供应商,定义成抽象的让子类去实现(继承抽象类必须要实现抽象方法)
        一般声明为protected，使得这些方法对外部客户端不可见
     */
    protected abstract void httpRequest ();

    /*
        结束日志报文
     */
    private void addFootLog(){
        System.out.println("调用运营商结束记录日志。。。。。。。。。。。");
    }
}
