package com.dennis.conccurency.chapter15;

/**
 * 描述：锁顶层接口
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/18 11:09
 */
public interface Lock {
    /**
     * 加锁
     */
    void lock() throws InterruptedException;

    /**
     * 解锁
     */
    void unlock();
}
