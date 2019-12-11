package com.example.thread;


public class OrderPrint {

    Thread a;
    Thread b;

    void test() {

        a = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A");
            }
        });


        b = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    a.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");

            }
        });

        a.start();
        b.start();

    }


    public static void main(String[] args) {
        new OrderPrint().test();
    }
}
