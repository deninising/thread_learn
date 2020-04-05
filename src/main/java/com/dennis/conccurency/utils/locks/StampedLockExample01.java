package com.dennis.conccurency.utils.locks;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * 描述：强力推荐使用的锁StampLock(乐观锁),采用StampedLock代替ReentrantReadWriteLock锁实现读写分离
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/3 22:56
 */
public class StampedLockExample01 {
    private final static StampedLock LOCK = new StampedLock();

    private final static ArrayList<Long> SHARED_DATA = new ArrayList<>();

    private void write() {
        long stamped = -1;
        try {
            stamped = LOCK.writeLock();
            long data = System.currentTimeMillis();
            System.out.println("W-data-" + data);
            SHARED_DATA.add(data);
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock(stamped);
        }
    }

    // 悲观读
    private void read() {
        long stamped = -1;
        try {
            stamped = LOCK.readLock();
            String result = SHARED_DATA.stream().map(String::valueOf).collect(Collectors.joining(">>", "R-data-", ""));
            System.out.println(result);
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock(stamped);
        }
    }

    public static void main(String[] args) {
        StampedLockExample01 example = new StampedLockExample01();
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
