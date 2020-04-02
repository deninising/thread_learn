package com.dennis.conccurency.chapter17;

import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 17:34
 */
public class FutureTest02 {
    public static void main(String[] args) {
        FutureService<String, Integer> futureService = FutureService.newFutureService();
        futureService.submit(input -> {
            try {
                // 模拟任务执行耗时
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务执行结束！结果如下：");
            return input.length();
        }, "hello world", System.out::println);
        System.out.println("异步调用开始，异步任务正在执行......");
    }
}
