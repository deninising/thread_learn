package com.dennis.conccurency.utils.semapphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 描述：采用Semaphore实现显示锁
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/2 21:02
 */
public class SemaphoreExample01 {
    private  static String name = "信号量";

    public static void main(String[] args) {
        SemaphoreLock lock = new SemaphoreLock();
        new Thread(() -> {
            try {
                lock.lock();
                String name = Thread.currentThread().getName();
                for (int i = 0; i <= 10; i++) {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println(name + " is deal with shared data!!!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }, "thread-A").start();

        Thread tB = new Thread(() -> {

            try {
                // 阻塞锁
                lock.lock();
                // 非阻塞锁
//                lock.tryLock();
                // 自定义阻塞时长锁
//                lock.tryLock(1, TimeUnit.SECONDS);
                String name = Thread.currentThread().getName();
                for (int i = 0; i <= 10; i++) {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println(name + " is deal with shared data!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }, "thread-B");
        // jvm停止时间->jvm中所有非守护线程退出了,则jvm退出
//        tB.setDaemon(true);
        tB.start();
    }

    static class SemaphoreLock extends Semaphore {
        private Thread currentThread;

        public SemaphoreLock() {
            // 持有一张许可证
            super(1);
        }

        // 阻塞锁
        private void lock() {

            try {
                // 获取许可证，若没有许可证可取,则当前线程将处于阻塞状态
                this.acquire();
                currentThread = Thread.currentThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 非阻塞锁
        private void tryLock() throws Exception {
            // 获取许可证，若没有许可证可取,则返回false否则返回true
            if (!this.tryAcquire())
                throw new Exception("failed to get the lock source");
            currentThread = Thread.currentThread();
        }

        // 自定义阻塞时长锁
        private void tryLock(long time, TimeUnit timeUnit) throws Exception {
            // 获取许可证，若没有许可证可取,则返回false否则返回true
            if (!this.tryAcquire(time, timeUnit))
                throw new Exception("failed to get the lock source");
            currentThread = Thread.currentThread();
        }

        private void unLock() {
            if (Thread.currentThread() != currentThread) {
                return;
            }
            this.release();
        }
    }
}

