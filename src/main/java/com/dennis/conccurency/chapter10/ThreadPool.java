package com.dennis.conccurency.chapter10;

/**
 * 描述：线程池
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 10:55
 */
public interface ThreadPool {

    /**
     * 提交任务到线程池
     */
    void execute(Runnable runnable);

    /**
     * 关闭线程池
     */
    void shutDown();

    /**
     * 获取线程池初始容量大小
     */
    int getInitSize();

    /**
     * 获取线程池最大容量大小
     */
    int getMaxSize();

    /**
     * 获取线程池核心容量大小
     */
    int getCoreSize();

    /**
     * 获取线程池中用于缓存对列的大小
     */
    int getQueueSize();

    /**
     * 获取线程池中活跃线程的数量
     */
    int getActiveCount();

    /**
     * 判断线程池时候已经关闭
     */
    boolean isShutDown();
}
