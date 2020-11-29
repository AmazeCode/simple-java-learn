package com.java.design.pattern.builder;

/**
 * 构建日本人
 */
public class JPbuilder implements PersonBuilder {

    private Person person;

    /*
        构造函数
     */
    public JPbuilder(){
        person = new Person();
    }

    @Override
    public void builderHead() {
        person.setHead("日本人 头部 圆脸");
    }

    @Override
    public void builderBody() {
        person.setBody("日本人 身体 比较矮");
    }

    @Override
    public void builderFoot() {
        person.setFoot("日本人 尾部 腿短");
    }

    //返回每个部位的整合
    @Override
    public Person builderPerson(){
        return person;
    }
}
