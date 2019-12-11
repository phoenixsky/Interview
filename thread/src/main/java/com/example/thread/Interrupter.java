package com.example.thread;

public class Interrupter {

    public static void main(String[] args) {


        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 1_000_000; i++) {
                    if(Thread.interrupted()){
                        return;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }

                    System.out.println("number: "+i);
                }

            }
        };
        thread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

        System.out.println("hhaah");

    }
}
