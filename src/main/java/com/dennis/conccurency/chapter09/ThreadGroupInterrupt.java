package com.dennis.conccurency.chapter09;

import java.util.concurrent.TimeUnit;

/**
 * 描述：线程组的打断方法
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/12 15:05
 */
public class ThreadGroupInterrupt {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup myGroup = new ThreadGroup("myGroup");

        new Thread(myGroup, () -> {
            try {
                System.out.println(Thread.currentThread().getName() + "is running");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "is done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "myThread01").start();

        new Thread(myGroup, () -> {
            try {
                System.out.println(Thread.currentThread().getName() + "is running");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "is done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "myThread02").start();

        TimeUnit.MILLISECONDS.sleep(10);
        myGroup.interrupt();
    }
}
