package com.dennis.conccurency.chapter04;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 描述：
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/26 22:28
 */
public class JoinMethod {
    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = IntStream.range(1, 3).mapToObj(JoinMethod::createThread).collect(Collectors.toList());

        threads.forEach(Thread::start);

        System.out.println("===========================");
        for (Thread thread : threads) {
            // 当thread-0执行的join()之后,只能等到thread-0的线程生命周期结束后,main线程才能够调用thread-1的join()方法
            System.out.println("thread " + thread.getName() + " is going to join in a minute");
            thread.join();
        }
        System.out.println("===========================");

        IntStream.range(0, 10).forEach(i -> {
            System.out.println(Thread.currentThread().getName() + "#" + i);
        });
    }

    private static void shortSleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread createThread(int i) {
        return new Thread(() -> {
            IntStream.range(0, 10).forEach(t -> {
                System.out.println(Thread.currentThread().getName() + "#" + t);
                shortSleep();
            });
        });
    }
}
