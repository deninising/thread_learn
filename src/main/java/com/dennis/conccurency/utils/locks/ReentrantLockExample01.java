package com.dennis.conccurency.utils.locks;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 描述：ReentrantLock:可重入显示锁 -->相较于synchronized关键字更加灵活可控
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/3 16:43
 */
public class ReentrantLockExample01 {
    // 公平锁：确保所有获取锁资源线程的获取机率基本相等,但会降低程序总体的吞吐量-->当线程持有锁的时间相对较长或者线程请求锁的平均时间间隔较长时，
    // 可以考虑使用公平策略。此时线程调度产生的耗时间隔占比较小,吞吐量影响会较小。
    private final static ReentrantLock REENTRANT_LOCK = new ReentrantLock(true);

    public static void main(String[] args) {

      /*  IntStream.rangeClosed(1, 5).forEach(num -> {
            new Thread(() -> {
                for (; ; ) {
                    workWithLock();
                }
            }, "thread-reentrant-" + num).start();
        });

        IntStream.rangeClosed(1, 5).forEach(num -> {
            new Thread(() -> {
                for (; ; ) {
                    workWithSync();
                }
            }, "thread-sync-" + num).start();
        });*/

        ArrayList<Thread> arrayList = new ArrayList<>();
        IntStream.rangeClosed(1, 5).forEach(num -> {
            Thread thread = new Thread(() -> {
                for (; ; ) {
                    workWithInterruptLock();
                }
            }, "thread-reentrant-" + num);

            thread.start();

            arrayList.add(thread);
        });
        int count = 1;
        // 每隔2秒钟选择一个线程去打断它,打断一个线程仅仅是改变了一下打断标记,线程任然在循行且其标记会恢复到原始状态
        while (true) {
            int index = count / 5;
            count++;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread = arrayList.get(index);

            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }

    }

    // 类似于普通的锁
    private static void workWithLock() {
        try {
            REENTRANT_LOCK.lock(); // 类似于synchronised monitor entry
            System.out.println("thread:" + Thread.currentThread().getName() + " has got the reentrantLock and working......");
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            REENTRANT_LOCK.unlock(); // synchronised end the monitor
        }
    }

    // 可支持打断的锁
    private static void workWithInterruptLock() {
        try {
            REENTRANT_LOCK.lockInterruptibly(); // 类似于synchronised monitor entry
            System.out.println("thread:" + Thread.currentThread().getName() + " has got the reentrantLock and working......");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " has been interrupted when it is blocked waiting to get the lock source");
        } finally {
            if (REENTRANT_LOCK.isHeldByCurrentThread()) {
                REENTRANT_LOCK.unlock(); // synchronised end the monitor
            }
        }
    }

    private static void workWithSync() {
        synchronized (ReentrantLockExample01.class) {
            System.out.println("thread:" + Thread.currentThread().getName() + " has got the syn syncLock and working.....");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
