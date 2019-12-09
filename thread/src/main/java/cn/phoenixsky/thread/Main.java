package cn.phoenixsky.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("thread0");
            }
        };

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1");
            }
        });

        Thread.sleep(30);

        thread0.start();
        thread1.start();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread());
            }
        };

//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        executorService.execute(runnable);
//        executorService.execute(runnable);
//        executorService.execute(runnable);
//        executorService.execute(runnable);
//        executorService.execute(runnable);
//        executorService.shutdown();


        Main main = new Main();

        int i = 10;
        new Thread(){
            @Override
            public void run() {

            }
        };
        main.print(1);

    }

    private synchronized void print(int value) {
        System.out.println(value++);
    }

}