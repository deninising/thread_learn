package com.dennis.conccurency.chapter10;

import java.util.LinkedList;

/**
 * 描述：任务队列
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 13:05
 */
public class LinkedRunnableQueue implements RunnableQueue {
    /**
     * 任务对列的最大容量,构造时传入
     */
    private final int limit;

    /**
     * 拒绝策略
     */
    private final DenyPolicy denyPolicy;

    /**
     * 存放任务的队列
     */
    private final LinkedList<Runnable> runnableLinkedList = new LinkedList<>();

    /**
     * 线程池成员变量
     */
    private final ThreadPool threadPool;


    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }


    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableLinkedList) {
            if (runnableLinkedList.size() > limit) {
                // 无法添加任务时,执行拒绝策略
                denyPolicy.reject(runnable, this.threadPool);
            } else {
                // 将任务加入队列,并唤醒线程
                runnableLinkedList.addLast(runnable);
                runnableLinkedList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableLinkedList) {
            while (runnableLinkedList.isEmpty()) {
                try {
                    // 进入runnableLinkedList关联的monitor waitset中等待唤醒
                    runnableLinkedList.wait();
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            // 从队列中移除一个用于执行的任务
            return runnableLinkedList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (runnableLinkedList) {
            // 返回当前任务队列中的任务数
            return runnableLinkedList.size();
        }
    }
}
