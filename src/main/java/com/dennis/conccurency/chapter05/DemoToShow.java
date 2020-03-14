package com.dennis.conccurency.chapter05;

import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/27 21:51
 */
public class DemoToShow {
    public static void main(String[] args) throws InterruptedException {
        ThreadService threadService = new ThreadService();
        long startTime = System.currentTimeMillis();
        threadService.execute(() -> {
            while (true) {
                // 模拟一个很重的任务永远都执行不完
            }

//            try {
//                // 模拟一个1秒钟就能执行完成的任务
//                Thread.sleep(1_000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });
        // 2秒钟执行不完就强制关闭
        threadService.shutdownForceExtend(2_000);
        TimeUnit.MILLISECONDS.sleep(20);
        long endTime = System.currentTimeMillis();
        System.out.println("【5】客户端线程("+Thread.currentThread().getName()+")总共耗时：" + (endTime - startTime) + "millis");
    }
}
