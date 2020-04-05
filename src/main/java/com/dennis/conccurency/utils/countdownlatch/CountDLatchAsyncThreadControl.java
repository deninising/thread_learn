package com.dennis.conccurency.utils.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 描述：异步执行场景中,采用CountDownLatch进行线程间的控制
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/2 10:30
 */
public class CountDLatchAsyncThreadControl {
    // 线程池:不建议利用Executors工具类进行线程池的创建
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        // [1]查询到数据
        int[] data = queryData();
        // 创建CountDownLatch变量
        CountDownLatch latch = new CountDownLatch(data.length);

        // [2]对查询到的数据进行处理:通过线程池中的线程进行异步处理
        for (int i = 0; i < data.length; i++) {
            pool.execute(new CountDLatchAsyncThreadControl.DataHandleTask(data, i, latch));
        }
        // [3]后续操作
        // 1.主线程虽然完成但是线程池中的线程任然为active状态
//        System.out.println("the master thread has finished!!!");

        // 2.线程池执行完成,则池中线程也将关闭,该方法仍然是非阻塞方法,后续代码可被执行
//        pool.shutdown();
//        System.out.println("the master thread has finished!!!");

        // 3.阻塞当前线程1个小时的时间
//        pool.awaitTermination(1, TimeUnit.HOURS);
//        System.out.println("the master thread has finished!!!");

        // 4.预期阻塞当前线程1个小时的时间,在池期间线程池中任务若是全部成功执行完成,则阻塞结束,所有线程结束（推荐）
//        pool.shutdown();
//        pool.awaitTermination(1, TimeUnit.HOURS);

        // 5.采用countDownLatch来阻塞住当前线程(优点,逻辑清晰,控制较传统方式根加灵活)
        // 预期阻塞当前线程1个小时的时间,在池期间线程池中任务若是全部成功执行完成,则阻塞结束,所有线程结束（推荐）
        pool.shutdown();
        latch.await(1, TimeUnit.HOURS);
        System.out.println("the master thread has finished!!!");
    }

    static int[] queryData() {
        int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        return data;
    }

    private static class DataHandleTask implements Runnable {
        private final int[] data;
        private final int index;
        private final CountDownLatch latch;

        private DataHandleTask(int[] data, int index, CountDownLatch latch) {
            this.data = data;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (data[index] % 2 == 0) {
                data[index] = data[index] * 2;
            }
            latch.countDown();
            System.out.println("the data of index of " + index + " has been precessed already");
        }
    }
}
