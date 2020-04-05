package com.dennis.conccurency.utils.condition;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：condition版生产者消费者实现方式2
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/3 22:05
 */
public class ConditionExample02 {
    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition PRODUCE_COND = LOCK.newCondition();
    private final static Condition CONSUME_COND = LOCK.newCondition();
    private final static LinkedList<Long> shareData = new LinkedList<>();
    private final static int CAPACITY = 100;

    private static void produce() {
        try {
            LOCK.lock();
            if (shareData.size() == CAPACITY) {
                PRODUCE_COND.wait();
            }

            long data = System.currentTimeMillis();
            shareData.addLast(data);
            System.out.println("P has produced the shared-data :" + data);
            CONSUME_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void consume() {
        try {
            LOCK.lock();
            if (shareData.size() == 0) {
                CONSUME_COND.wait();
            }
            Long data = shareData.removeFirst();
            System.out.println("C has consumed the shared-data :" + data);
            PRODUCE_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }


    public static void main(String[] args) {
        Runnable consumeTask = () -> {
            while (true) {
                consume();
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable produceTask = () -> {
            while (true) {
                produce();
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        threadPool.submit(consumeTask);
        threadPool.submit(consumeTask);
        threadPool.submit(consumeTask);
        threadPool.submit(produceTask);
        threadPool.submit(produceTask);
    }
}
