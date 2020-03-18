package com.dennis.conccurency.chapter15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 读写锁的使用
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/18 14:36
 */
public class ShareData {
    // 定义共享数据（资源）
    private final List<Character> container = new ArrayList<>();
    // 读写锁工厂
    private final ReadWriteLock readWriteLock = ReadWriteLock.getReadWriteLockImpl();
    // 获取读取锁
    private ReadLock readLock = readWriteLock.readLock();
    // 获取写入锁
    private WriteLock writeLock = readWriteLock.writeLock();

    private final int length;

    // 初始化共享数据
    public ShareData(int length) {
        this.length = length;
        for (int i = 0; i < length; i++) {
            container.add(i, 'C');
        }
    }

    // 读方法采用读取锁进行控制
    public char[] read() throws InterruptedException {
        try {
            readLock.lock();
            char[] chars;
            int size = container.size();
            chars = new char[size];
            for (int i = 0; i < length; i++) {
                chars[i] = container.get(i);
            }
            slowly();
            return chars;
        } finally {
            // 操作关闭将锁释放
            readLock.unlock();
        }
    }

    // 写操作采用写入锁进行控制
    public void write(char c) throws InterruptedException {
        try {
            writeLock.lock();
            for (int i = 0; i < length; i++) {
                container.add(i, c);
            }
            slowly();
        } finally {
            // 释放锁
            writeLock.unlock();
        }
    }

    private void slowly() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
