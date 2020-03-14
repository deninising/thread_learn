package com.dennis.conccurency.chapter08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 描述：自定义显示锁
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/1 20:26
 */
public class BooleanLock implements Lock {
    /**
     * 存放获取到锁资源的当前线程
     */
    private Thread currentThread;
    /**
     * 标识锁的状态
     */
    private boolean locked;
    /**
     * 存放阻塞状态的线程
     */
    private final List<Thread> blockedThreadList = new ArrayList<>();


    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {
                if (!blockedThreadList.contains(Thread.currentThread()))
                    blockedThreadList.add(Thread.currentThread());
                this.wait();
            }

            this.locked = true;
            blockedThreadList.remove(Thread.currentThread());
            this.currentThread = Thread.currentThread();
        }

    }

    @Override
    public void lock(long millis) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (millis <= 0)
                lock();
            // 获取到允许阻塞的最终时间
            long endTime = millis + System.currentTimeMillis();
            long timeAllowedToBlock = endTime - System.currentTimeMillis();
            while (locked) {
                // 判断是否超时,再决定是否有必要继续等待
                if (timeAllowedToBlock <= 0)
                    throw new TimeoutException("the time of getting lock has been timeout");
                if (!blockedThreadList.contains(Thread.currentThread()))
                    blockedThreadList.add(Thread.currentThread());
                wait(millis);
                timeAllowedToBlock = endTime - System.currentTimeMillis();
            }
            this.locked = true;
            currentThread = Thread.currentThread();
            blockedThreadList.remove(Thread.currentThread());
        }
    }

    @Override
    public void unLock() {
        synchronized (this) {
            if (this.currentThread == Thread.currentThread()) {
                this.locked = false;
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return blockedThreadList;
    }
}
