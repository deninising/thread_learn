package com.dennis.conccurency.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 描述：线程中断方法
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/26 20:47
 */
public class InterruptMethod {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            // 【1】线程中执行可中断方法，且当线程被中断时,虽然其interrupt flag被设置成过true,
            // 但当interruptedException的signal被检测到时,线程的interrupt flag又会被清除，
            // 即设置为false
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("oh I am interrupted once!");
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("oh I am interrupted twice!");
            }

            // 【2】调用线程的interrupt()线程的interrupt flag被设置成为true,线程的生命周期并不会结束
            while (true) {
                System.out.println("I am always running");
            }
        });

        thread.start();

        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println(thread.isInterrupted() + "====================================");
        // 【3】会将线程的interrupt flag 设置成true,线程的生命周期并不会结束
        thread.interrupt();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();

        TimeUnit.SECONDS.sleep(1);
        // 查看当前线程interrupt flag状态,【1】执行时结果为false;【2】执行时结果为true
        System.out.println(thread.isInterrupted() + "====================================");
        System.out.println(thread.isInterrupted() + "====================================");
    }
}
