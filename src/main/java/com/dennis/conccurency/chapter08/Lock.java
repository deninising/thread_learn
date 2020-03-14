package com.dennis.conccurency.chapter08;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 描述：锁顶层接口
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/1 20:27
 */
public interface Lock {
    /**
     * 支持可中断
     */
    void lock() throws InterruptedException;

    /**
     * 支持中断和超时时间设定
     */
    void lock(long millis) throws InterruptedException, TimeoutException;

    void unLock();

    List<Thread> getBlockedThreads();

}
