package com.dennis.conccurency.utils.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 描述： LockSupport支持中断方法
 * <p>
 * LockSupport.park()方法还能支持中断影响。但是和其他接收
 * 中断的函数很不一样，LockSupport.park()方法不会抛出InterruptedException 异常。它只会
 * 默默返回，但是我们可以从Thread.interrupted()等方法中获得中断标记。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/6 13:09
 */
public class LockSupportExample02 {
    private final static Object LOCK = new Object();

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (LOCK) {
                System.out.println("in thread :" + this.getName() + " and got blocked......");
                LockSupport.park();
                if (this.isInterrupted()) {
                    System.out.println(this.getName() + " has been interrupted");
                }
                System.out.println(this.getName() + " finished");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread thread01 = new ChangeObjectThread("thread01");
        thread01.start();

        TimeUnit.MILLISECONDS.sleep(5000);

        thread01.interrupt();
        thread01.join();

    }
}
