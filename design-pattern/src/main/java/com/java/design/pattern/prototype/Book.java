package com.java.design.pattern.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * 原型模式实例
 * 使用原型模式的话一定要实现Cloneable接口
 * 因为String类型被final修饰所以不能被更改，并不违背浅克隆规则（只拷贝基本类型，不拷贝引用类型）
 */
public class Book implements Cloneable{

    //名称
    private String title;

    //图片
    private ArrayList<String> listImg = new ArrayList<>();

    private int age;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getListImg() {
        return listImg;
    }

    public void setListImg(ArrayList<String> listImg) {
        this.listImg = listImg;
    }

    public void addImg(String imgName){
        listImg.add(imgName);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void showBook(){
        System.out.println("....................start..........................");
        System.out.println("title:"+title);

        for (String imgs : listImg){
            System.out.println("imgs:"+imgs);
        }
        System.out.println("age:"+this.age);
        System.out.println("....................end..........................");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //浅克隆(默认) 深克隆
        Book book = (Book)super.clone();
        //属性深克隆
        book.listImg = (ArrayList<String>) this.listImg.clone();//将应用类型深clone
        return book;
    }
}
