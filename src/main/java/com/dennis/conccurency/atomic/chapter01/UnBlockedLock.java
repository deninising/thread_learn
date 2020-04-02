package com.dennis.conccurency.atomic.chapter01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述： 利用AtomicInteger.compareAndSet(Int expect,Int value)的快速失败特性,来创建一个非阻塞锁
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/31 16:36
 */
public class UnBlockedLock {
    private final int UNLOCKED = 0;
    private final int LOCKED = 1;

    private final AtomicInteger LOCK_STATUS = new AtomicInteger(this.UNLOCKED);

    // 存放正持有锁资源的线程
    private Thread localThread;

    // 加锁,若获取锁资源失败,则直接抛异常-->fail-fast,放弃竞争锁资源
    public void tryLock() throws GetLockException {
        boolean success = LOCK_STATUS.compareAndSet(UNLOCKED, LOCKED);
        if (!success) {
            throw new GetLockException("it is failed to get the lock");
        }
        localThread = Thread.currentThread();
    }

    // 解锁
    public void unLock() {
        if (Thread.currentThread() != localThread || LOCK_STATUS.get() == UNLOCKED) {
            return;
        }
        LOCK_STATUS.compareAndSet(LOCKED, UNLOCKED);
    }
}
