package com.dennis.conccurency.chapter10;

/**
 * 描述： 创建线程的工厂
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 11:48
 */
public interface ThreadFactory {
    /**
     * 创建线程
     */
    Thread createThread(Runnable runnable);
}
