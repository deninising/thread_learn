package com.dennis.conccurency.utils.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 描述：其工作发放时类似于CountDownLatch,但他工作线程之间相互阻塞,
 * 而CountDownLatch是调用latch.await（）方法所在的线程线程将被阻塞
 *
 * 整体执行循序：【2】-->【1】-->【3】
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/2 14:15
 */
public class CyclicBarrierExample01 {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            // 【1】
            System.out.println("they three worker-thread has all finished and their blocked status will be canceled !!!");
            // 【1】
        });
        IntStream.rangeClosed(1, 3).forEach(num -> {
            new Thread(() -> {
                // 【2】
                try {
                    TimeUnit.SECONDS.sleep(num-1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int limit = 1;
                for (; limit <= 10; ) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "has finished the lift the stone " + limit + " times");
                    limit++;
                }
                System.out.println(Thread.currentThread().getName() + "has finished all the task and waiting others");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // 【2】

                // 【3】
                System.out.println("ok let·s go:says " + Thread.currentThread().getName());
                // 【3】
            }, "thread-" + num).start();
        });

        TimeUnit.MILLISECONDS.sleep(2);
        barrier.reset();
    }
}

