package com.dennis.conccurency.chapter10;

/**
 * 描述：任务队列
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 11:04
 */
public interface RunnableQueue {
    /**
     * 当有新的任务进来时，会首先offer到队列中
     */
    void offer(Runnable runnable);

    /**
     * 工作线程通过take方法从队列中获取任务
     */
    Runnable take() throws InterruptedException;

    /**
     * 获取队列中任务的数量
     */
    int size();
}
