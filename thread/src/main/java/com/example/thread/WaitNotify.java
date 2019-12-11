package com.example.thread;

public class WaitNotify {

    String name;

    synchronized void setName(String name) {
        this.name = name;
        notifyAll();
    }

    synchronized void printName() {
        while (name == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name);
    }


    public static void main(String[] args) {


        final WaitNotify demo = new WaitNotify();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.printName();
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.setName("123");
            }
        };

        thread1.start();
        thread2.start();
    }
}
