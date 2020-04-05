package com.dennis.conccurency.utils.locks;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * 描述：读写锁:只用lock>>>问题：要是大量线程为读线程，那么写线程将很难抢到锁资源(反之亦然)-->悲观锁
 * 100 threads
 * 99-->read-threads
 * 01-->write-thread
 * <p>
 * W   W  --> X （排他执行）
 * W   R  --> X （排他执行）
 * R   W  --> X （排他执行）
 * R   R  --> O （同步执行）
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/3 17:44
 */
public class ReadWriteLockExample01 {
    private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = READ_WRITE_LOCK.readLock();
    private static final Lock WRITE_LOCK = READ_WRITE_LOCK.writeLock();
    private static final LinkedList<Long> sharedData = new LinkedList<>();


    private void read() {
        try {
            READ_LOCK.lock();
            String result = sharedData.stream().map(String::valueOf).collect(Collectors.joining(" >>> ", "R-", ""));
            int priority = Thread.currentThread().getPriority();
            System.out.println(priority + "--" + Thread.currentThread().getName() + " has read the data:" + result);
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
        }
    }

    private void write() {
        try {
            WRITE_LOCK.lock();
            long result = System.currentTimeMillis();
            sharedData.addLast(result);
            int priority = Thread.currentThread().getPriority();
            System.out.println(priority + "--" + Thread.currentThread().getName() + " has write the data: w-" + result);
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample01 example01 = new ReadWriteLockExample01();

        ExecutorService executorService = Executors.newFixedThreadPool(11);

        Runnable readTask = () -> {
            for (; ; ) {
                example01.read();
            }
        };

        Runnable writeTask = () -> {
            for (; ; ) {
                example01.write();
            }
        };
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        // ?? 写线程较读线程貌似更容易获取到cpu的执行权
        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
//        executorService.submit(writeTask);
    }
}
