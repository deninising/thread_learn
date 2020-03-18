package com.dennis.conccurency.chapter15;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 描述：读写锁工厂顶层接口
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/18 11:12
 */
public interface ReadWriteLockFactory {
    /**
     * 创建读锁
     */
    void readLock();

    /**
     * 创建写锁
     */
    void writeLock();

    /**
     * 获取当前有多少线程正在执行写操作
     */
    int getWritingWriters();

    /**
     * 获取当前有多少线程正在等待获取写操作锁
     */
    int getWaitingWriters();

    /**
     * 获取当前有多少线程正在执行读操作
     */
    int getReadingReaders();

    /**
     * 工厂方法创建读写锁
     */
    static ReadWriteLoc
}
