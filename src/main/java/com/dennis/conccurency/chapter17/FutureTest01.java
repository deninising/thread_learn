package com.dennis.conccurency.chapter17;

import java.util.concurrent.TimeUnit;

/**
 * 描述： 测试类
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 17:01
 */
public class FutureTest01 {
    public static void main(String[] args) throws InterruptedException {
        /*FutureService<Void, Void> futureService = FutureService.newFutureService();
        Future<?> future = futureService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务执行结束！");
        });
        System.out.println("异步调用开始，异步任务正在执行......");
        // future的get方法将造成线程阻塞
        future.get();*/

        FutureService<String, Integer> futureService = FutureService.newFutureService();
        Future<Integer> future = futureService.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务执行结束！");
            return input.length();
        }, "hello world");
        System.out.println("异步调用开始，异步任务正在执行......");
        // future的get方法将造成线程阻塞
        System.out.println(future.get());
    }
}
