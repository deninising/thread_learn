package com.dennis.conccurency.utils.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：采用condition实现生产者-消费者模式--->与com.dennis.conccurency.chapter07中的生产者消费模式（Object.wait, Object.notify和 synchronized()）对比
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/3 21:21
 */
public class ConditionExample01 {

    // the Lock is associated with Condition,can treat them as a atomic-source
    private final static Lock LOCK = new ReentrantLock();
    private final static Condition CONDITION = LOCK.newCondition();
    // the Lock is associated with Condition,can treat them as a atomic-source

    private static int sharedData = 0;

    private static volatile boolean consumed = true;

    private void produce() {
        try {

           /*The lock associated with this {@code Condition} is atomically
            released and the current thread becomes disabled for thread scheduling
            purposes and lies dormant*/

            LOCK.lock(); // the same function as monitor enter
            while (!consumed) {
                CONDITION.await(); // the same function as monitor await
            }

            sharedData++;
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " has already produced the data:" + sharedData);
            consumed = false;
            CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }

    }

    private void consume() {
        try {
            /*The lock associated with this {@code Condition} is atomically
            released and the current thread becomes disabled for thread scheduling
            purposes and lies dormant*/

            LOCK.lock(); // monitor enter
            while (consumed) { // monitor await
                CONDITION.await(); // object.await()
            }

            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " has already consume the data：" + sharedData);
            consumed = true;
            CONDITION.signalAll(); // object.notifyAll()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }

    }

    public static void main(String[] args) {
        ConditionExample01 example01 = new ConditionExample01();

        Runnable consumeTask = () -> {
            for (; ; ) {
                example01.consume();
            }
        };
        Runnable produceTask = () -> {
            for (; ; ) {
                example01.produce();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(consumeTask);
        pool.submit(consumeTask);
        pool.submit(consumeTask);
        pool.submit(produceTask);
        pool.submit(produceTask);
    }

}
