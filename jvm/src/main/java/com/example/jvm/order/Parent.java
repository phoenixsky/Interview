package com.example.jvm.order;

public class Parent {

    static {
        System.out.println("parent 静态代码块");
    }

    {
        System.out.println("parent 对象代码块");
    }

    public Parent() {
        System.out.println("parent 构造方法");
    }



}
