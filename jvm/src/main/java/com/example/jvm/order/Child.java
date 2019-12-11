package com.example.jvm.order;

public class Child extends Parent{

    static {
        System.out.println("Child 静态代码块");
    }

    {
        System.out.println("Child 对象代码块");
    }

    public Child(){
        super();
        System.out.println("Child 构造方法");
    }



}
