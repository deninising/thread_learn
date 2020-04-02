package com.dennis.conccurency.chapter17;

/**
 * 描述：FutureTask是Future的一个实现，除了实现Future中定义的get()以及done() 方法，还
 * 额外增加了protected 方法finish， 该方法主要用于接收任务被完成的通知.
 *
 * FutureTask中充分利用了线程间的通信:wait和notifyAll，当任务没有被完成之前通过
 * get方法获取结果，调用者会进入阻塞，直到任务完成并接收到其他线程的唤醒信号，finish
 * 方法接收到了任务完成通知，唤醒了因调用get而进人阻塞的线程。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 16:06
 */
public class FutureTask<R> implements Future<R> {
    // 计算结果
    private R result;
    // 任务呢是否完成
    private boolean isDone;
    // 对象锁
    private final Object LOCK = new Object();

    @Override
    public R get() throws InterruptedException {
        synchronized (LOCK) {
            // 未计算完成,调用该方法的接口阻塞
            while (!isDone) {
                LOCK.wait();
            }
            // 计算完成返回结果
            return this.result;
        }
    }

    //finish方法主要用于为FutureTask设置计算结果
    protected void finish(R result) {
        synchronized (LOCK) {
            if (isDone)
                return;
            this.result = result;
            this.isDone = true;
            LOCK.notifyAll();
        }
    }

    @Override
    public boolean done() {
        return this.isDone;
    }
}
