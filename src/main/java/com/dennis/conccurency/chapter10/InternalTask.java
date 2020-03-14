package com.dennis.conccurency.chapter10;

/**
 * 描述: 主要用于线程池内部,会使用到RunnableQueue,不断从里面获取任务runnable,并执行run方法
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 12:04
 */
public class InternalTask implements Runnable {
    /**
     * 持有任务队列属性
     */
    private final RunnableQueue runnableQueue;

    /**
     * 运行状态
     */
    private volatile boolean isRunning = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        while (isRunning && !Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = runnableQueue.take();
                task.run();
            } catch (Exception e) {
                isRunning = false;
                break;
            }
        }
    }

    /**
     * 停止当前任务，主要会在线程池的shutdown()方法中使用
     */
    public void stop() {
        this.isRunning = false;
    }
}
