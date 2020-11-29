package com.java.design.pattern.builder;

/**
 * 构建人物 整合所有部件
 */
public class PersonDirector {

    /*
        创建人物
     */
    public Person createPerson(PersonBuilder personBuilder){
        personBuilder.builderHead();
        personBuilder.builderBody();
        personBuilder.builderFoot();
        return personBuilder.builderPerson();
    }

    public static void main(String[] args) {
        PersonDirector personDirector = new PersonDirector();
        //创建美国人
        Person person = personDirector.createPerson(new ManBuilder());
        System.out.println(person.getHead());
        System.out.println(person.getBody());
        System.out.println(person.getFoot());
    }
}
