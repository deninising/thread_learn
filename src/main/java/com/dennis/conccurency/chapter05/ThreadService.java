package com.dennis.conccurency.chapter05;

import sun.font.TrueTypeFont;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * 描述：通过封装执行线程+任务线程（守护线程）+join()的方法避免进程假死问题
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/27 21:09
 */
public class ThreadService {
    /**
     * 任务线程是否执行完成标识
     */
    private boolean finished = false;

    /**
     * 维持着一个执行线程成员变量,任务线程的启动通过执行线程来调用,任务线程作为执行线程的子线程
     */
    private Thread executeThread;

    /**
     * 封装执行方法
     */
    public void execute(Runnable task) {
        executeThread = new Thread() {
            @Override
            public void run() {
                Thread taskThread = new Thread(task);
                // 设置为守护线程，当执行线程的生命周期结束时,其跟着结束
                taskThread.setDaemon(true);

                taskThread.start();
                // 使任务线程插入到执行线程的执行中去,阻塞执行线程
                try {
                    System.out.println("【1】执行线程(" + this.getName() + ")的执行权将被子线程(" + taskThread.getName() + ")剥夺!");
                    taskThread.join();
                    // 任务线程在规定的时间内执行完成,改变标志符号
                    System.out.println("【2】任务线(" + taskThread.getName() + ")程执行完毕！");
                    finished = true;
                } catch (InterruptedException e) {
                    System.out.println("【2】任务线程(" + taskThread.getName() + ")执行时间超时,已被强制关闭!");
                }
                System.out.println("【3】执行线程(" + this.getName() + ")重新获取到执行权!");
                System.out.println("【4】执行线程(" + this.getName() + ")的生命周期结束......");
            }
        };

        // 启动执行线程
        executeThread.start();
    }

    /**
     * 封装执行时间可控的关闭方法:多长时间任务线程还没执行完,就强制关闭
     */
    public void shutdownForceExtend(long millis) {
        long currentTime = System.currentTimeMillis();
        // 在给定时间内反复检测任务线程的执行情况
        while (!finished) {
            if (System.currentTimeMillis() - currentTime >= millis) {
                // 任务线程执行时间超时,将被强制关闭！
                executeThread.interrupt();
                break;
            }

            try {
                // 【1】防止线程while循环大量占用CPU资源
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
