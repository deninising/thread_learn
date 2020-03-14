package com.dennis.conccurency.chapter10;

import java.util.concurrent.TimeUnit;

/**
 * 描述：测试类
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 17:00
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        BasicThreadPool threadPool = new BasicThreadPool(2, 4, 6, 1000);

        for (int i = 0; i <= 30; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName() + " is running and done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (; ; ) {
            // 不断地输出线程池信息
            System.out.println("getActiveCount:" + threadPool.getActiveCount());
            System.out.println("getQueueSize:" + threadPool.getQueueSize());
            System.out.println("getCoreSize:" + threadPool.getCoreSize());
            System.out.println("getMaxSize:" + threadPool.getMaxSize());
            System.out.println("==============================");
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
