package com.dennis.conccurency.chapter06;

import java.util.concurrent.TimeUnit;

/**
 * 描述：交叉获取互斥资源引起的死锁
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/29 17:25
 */
public class DeadMutex {
    private static final Object READLOCK = new Object();
    private static final Object WRITELOCK = new Object();

    public void read() {
        synchronized (READLOCK) {
            System.out.println(Thread.currentThread().getName() + " get READLOCK");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (WRITELOCK) {
                System.out.println(Thread.currentThread().getName() + " get WRITELOCK");
            }
            System.out.println(Thread.currentThread().getName() + " release WRITELOCK");
        }
        System.out.println(Thread.currentThread().getName() + " release READLOCK");
    }

    public void write() {
        synchronized (WRITELOCK) {
            System.out.println(Thread.currentThread().getName() + " get WRITELOCK");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (READLOCK) {
                System.out.println(Thread.currentThread().getName() + " get READLOCK");
            }
            System.out.println(Thread.currentThread().getName() + " release READLOCK");
        }
        System.out.println(Thread.currentThread().getName() + " release WRITELOCK");
    }

    public static void main(String[] args) {
        DeadMutex deadMutex = new DeadMutex();
        new Thread(deadMutex::read).start();
        new Thread(deadMutex::write).start();
    }
}
