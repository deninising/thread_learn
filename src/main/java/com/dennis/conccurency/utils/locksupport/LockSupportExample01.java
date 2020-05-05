package com.dennis.conccurency.utils.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 描述：线程阻塞工具类
 *
 * LockSupport类使用类似信号量的机制。它为每一个线程准备了一个许可，如
 * 果许可可用，那么park()方法会立即返回，并且消费这个许可(也就是将许可变为不可用)，
 * 如果许可不可用，就会阻塞，而unpark(方法则使得一个许可变为可用(但是和信号量不同
 * 的是，许可不能累加，你不可能拥有超过一个许可，它永远只有一个)。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/6 12:44
 */
public class LockSupportExample01 {
    private final static Object LOCK = new Object();
    private final static ChangeObjectThread thread1 = new ChangeObjectThread("thread1");
    private final static ChangeObjectThread thread2 = new ChangeObjectThread("thread2");

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (LOCK) {
                System.out.println("in thread :" + this.getName() + " and got blocked......");
//                LockSupport.park();
                /* 使用park(Object)函数，那么还可以为当前
                线程设置一个阻塞对象。这个阻塞对象会出现在线程Dump中。这样在分析问题时，就更
                加方便了*/
                LockSupport.park(this);

                System.out.println(this.getName() + " turns to released......");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(50000);
        thread2.start();

        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);

        thread2.join();
        thread1.join();
    }
}
