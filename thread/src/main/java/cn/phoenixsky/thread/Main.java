package cn.phoenixsky.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    static int times = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition t1 = lock.newCondition();
        Condition t2 = lock.newCondition();
        Condition t3 = lock.newCondition();

//        print(lock, t1, t2, t3, 0);
//        print(lock, t2, t1, t3, 1);
//        print(lock, t3, t1, t2, 2);

        print2(0);
        print2(1);
        print2(2);




    }

    private static void print2(int mod) {
        new Thread(() -> {
            synchronized (Main.class) {
                while (times < 100) {
                    while (times % 3 != mod) {
                        try {
                            Main.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (times <= 100) {
                        System.out.println(Thread.currentThread().getName() + "  " + times++);
                    }
                    Main.class.notifyAll();
                }

            }

        }).start();
    }

    private static void print(Lock lock, Condition self, Condition other1, Condition other2, int mod) {
        new Thread(() -> {
            lock.lock();
            while (times < 100) {
                if ((times % 3) != mod) {
                    System.out.println(times);
                    try {
                        self.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (times <= 100) {
                    System.out.println(Thread.currentThread().getName() + "  " + times++);
                }
                other1.signal();
                other2.signal();
            }
            lock.unlock();
        }).start();
    }


}