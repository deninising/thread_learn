package com.dennis.conccurency.chapter09;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述：递归复制和非递归复制
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/12 9:40
 */
public class ThreadGroupEnumerateThreads {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup myGroup = new ThreadGroup("myGroup");
        Thread thread = new Thread(myGroup, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "customerThread");

        thread.start();

        TimeUnit.MILLISECONDS.sleep(1);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        // 将mainGroup中线程复制到threads中
        Thread[] threads = new Thread[mainGroup.activeCount()];
        int enumerateNum = mainGroup.enumerate(threads);
        System.out.println(enumerateNum);
        Arrays.asList(threads).forEach(System.out::print);
        System.out.println();

        // 非递归方式复制线程
        Thread[] threads1 = new Thread[mainGroup.activeCount()];
        int enumerateNum1 = mainGroup.enumerate(threads1, false);
        System.out.println(enumerateNum1);
        Arrays.asList(threads1).forEach(System.out::print);
    }

}
