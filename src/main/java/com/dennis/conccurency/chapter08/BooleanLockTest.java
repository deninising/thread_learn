package com.dennis.conccurency.chapter08;

        import java.util.concurrent.TimeUnit;
        import java.util.concurrent.TimeoutException;
        import java.util.stream.Collectors;
        import java.util.stream.IntStream;

/**
 * 描述：多线程抢锁测试
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/1 21:02
 */
public class BooleanLockTest {
    public static void main(String[] args) throws InterruptedException {
        BooleanLock lock = new BooleanLock();

        // 常规抢锁
       /* IntStream.range(0, 4).forEach(i -> {
            new Thread(() -> {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " is doing work");
                    System.out.println("thread blocked:" + lock.getBlockedThreads().stream().map(Thread::getName).collect(Collectors.toList()));
                    // 模拟3秒的执行任务的时间
                    TimeUnit.SECONDS.sleep(3);
                    lock.unLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "T-" + i).start();
        });*/

        // 测试timeout特性
        IntStream.range(0, 4).forEach(i -> {
            new Thread(() -> {
                try {
                    try {
                        // 两秒后抢不到锁就抛超时异常
                        lock.lock(2_000);
                        System.out.println(Thread.currentThread().getName() + " is doing work");
                        System.out.println("thread blocked:" + lock.getBlockedThreads().stream().map(Thread::getName).collect(Collectors.toList()));
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    // 模拟3秒的执行任务的时间
                    TimeUnit.SECONDS.sleep(3);
                    lock.unLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "T-" + i).start();
        });

        // 测试显示锁抢锁时可自定中断的特性
        /*Thread Ta = new Thread(() -> {
            try {
                lock.lock();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
        }, "Thread_A");

        Thread Tb = new Thread(() -> {
            try {
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                System.out.println("Tb线程被主动中断！");
            }
            lock.unLock();
        }, "Thread_B");

        Ta.start();
        Tb.start();

        TimeUnit.MILLISECONDS.sleep(2_000);
        Tb.interrupt();*/
    }
}
