package com.dennis.conccurency.atomic.chapter01;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class UnBlockedLockTest {
    public static void main(String[] args) {
        UnBlockedLock lock = new UnBlockedLock();
        // 起5个工作线程
        IntStream.rangeClosed(1, 5).forEach(workThreadNum -> {
            new Thread(() -> {
                try {
                    lock.tryLock();
                    // do working;
                    for (int i = 0; i <= 30; i++) {
                        System.out.println(Thread.currentThread().getName() + " is working......");
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                } catch (GetLockException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unLock();
                }
            }, "thread_" + workThreadNum).start();
        });
    }
}
