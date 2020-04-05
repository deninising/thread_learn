package com.dennis.conccurency.utils.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/4 16:31
 */
public class StampedLockExample02 {
    private final static StampedLock LOCK = new StampedLock();

    private final static List<Long> SHARED_DATA = new ArrayList<>();

    private void write() {
        long stamp = -1;
        try {
            stamp = LOCK.writeLock();
//            System.out.println("w-stamp:"+stamp);
            long data = System.currentTimeMillis();
            System.out.println("w-data-" + data);
            SHARED_DATA.add(data);
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock(stamp);
        }
    }

    // 乐观读
    private void read() {
        long stamp = LOCK.tryOptimisticRead();  // 非阻塞获取版本信息
        List<Long> currentData = SHARED_DATA;   // 拷贝变量到线程本地堆栈
        if (!LOCK.validate(stamp)) {            // 校验不通过则转为悲观锁
            try {
                stamp = LOCK.readLock();        // 获取读锁
                currentData = SHARED_DATA;      // 拷贝变量到线程本地堆栈
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOCK.unlockRead(stamp);         // 释放悲观锁
            }
        }

        // 使用线程本地堆栈里面的数据进行操作
        String result = currentData.stream().map(String::valueOf).collect(Collectors.joining(">>", "R-data-", ""));
        System.out.println(result);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StampedLockExample02 example = new StampedLockExample02();
        Runnable readTask = () -> {
            for (; ; ) {
                example.read();
            }
        };

        Runnable writeTask = () -> {
            for (; ; ) {
                example.write();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(11);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(readTask);
        pool.submit(writeTask);
    }
}
