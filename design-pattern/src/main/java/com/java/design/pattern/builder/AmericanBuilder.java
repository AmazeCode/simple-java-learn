package com.java.design.pattern.builder;

/**
 * 构造人物：构造美国人
 */
public class AmericanBuilder implements PersonBuilder{

    private Person person;

    /*
        构造函数
     */
    public AmericanBuilder(){
        person = new Person();
    }

    @Override
    public void builderHead() {
        person.setHead("美国人 头部 鼻子尖、长脸、蓝眼睛");
    }

    @Override
    public void builderBody() {
        person.setBody("美国人 身体 长得比较高、块头大");
    }

    @Override
    public void builderFoot() {
        person.setFoot("美国人 尾部 腿长。。。。。。");
    }

    //返回每个部位的整合
    @Override
    public Person builderPerson(){
        return person;
    }
}
