package com.java.design.pattern.builder;

/**
 * 创建人体的Builder:把整体拆分出各个零部件
 * 在使用设计模式的时候一定要学会使用抽象类或者接口。
 */
public interface PersonBuilder {

    //构造头部
    void builderHead();

    //构造身体
    void builderBody();

    //构造尾部
    void builderFoot();

    //组装部件
    Person builderPerson();
}
