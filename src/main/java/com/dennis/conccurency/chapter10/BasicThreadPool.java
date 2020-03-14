package com.dennis.conccurency.chapter10;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：线程池的具体实现
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 13:23
 */
public class BasicThreadPool extends Thread implements ThreadPool {

    /**
     * 初始大小
     */
    private final int initSize;

    /**
     * 最大值
     */
    private final int maxSize;

    /**
     * 核心线程数
     */
    private final int coreSize;

    /**
     * 活跃线程数
     */
    private int activeCount;

    /**
     * 创建线程所需的工厂
     */
    private final ThreadFactory threadFactory;

    /**
     * 任务队列
     */
    private final RunnableQueue runnableQueue;

    /**
     * 线程池是否被关闭
     */
    private volatile boolean isShutdown = false;

    /**
     * 工作线程队列
     */
    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

    /**
     * 默认初始化拒绝策略
     */
    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    /**
     * 创建线程的默认工厂
     */
    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepAliveTime;

    private final TimeUnit timeUnit;

    public BasicThreadPool(int initSize, int coreSize, int maxSize, int queueSize) {
        this(initSize, coreSize, maxSize, DEFAULT_THREAD_FACTORY
                , queueSize, DEFAULT_DENY_POLICY, 10, TimeUnit.SECONDS);
    }


    public BasicThreadPool(int initSize, int coreSize, int maxSize, ThreadFactory threadFactory,
                           int queueSize, DenyPolicy denyPolicy, long keepAlive, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAlive;
        this.timeUnit = timeUnit;
        this.init();
    }

    // 初始化线程池
    private void init() {
        start();
        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    private void newThread() {
        // 创建任务线程并启动
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.isShutdown) {
            throw new IllegalStateException("this pool is destroyed");
        }
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void run() {
        while (!isShutdown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true;
                break;
            }

            synchronized (this) {
                if (isInterrupted())
                    break;

                // 当前任务尚有任务未处理，且activeCount<coreSize,则继续扩容
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    // 防止大小直接扩容到maxSize
                    continue;
                }
                // 当前任务尚有任务未处理，且activeCount<maxSize,则继续扩容
                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    for (int i = initSize; i < maxSize; i++) {
                        newThread();
                    }
                }
                // 如果队列中没有任务，则将大小回收到coreSize即可
                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    for (int i = coreSize; i < activeCount; i++) {
                        removeThread();
                    }
                }
            }
        }
    }

    void removeThread() {
        // 从线程池中删除某个线程
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }

    @Override
    public void shutDown() {
        synchronized (this) {
            if (isShutdown)
                return;
            isShutdown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }
    }


    @Override
    public int getInitSize() {
        if (isShutdown)
            throw new IllegalStateException("the threadPool is destroyed");
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutdown)
            throw new IllegalStateException("the threadPool is destroyed");
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutdown)
            throw new IllegalStateException("the threadPool is destroyed");
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutdown)
            throw new IllegalStateException("the threadPool is destroyed");
        return runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutDown() {
        return this.isShutdown;
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);

        private static final ThreadGroup group
                = new ThreadGroup("MyThreadPool_" + GROUP_COUNTER.getAndIncrement());

        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group, runnable, "thread-pool-" + COUNTER.getAndIncrement());
        }
    }

    /**
     * 描述： Thread和RunnableTask的组合
     *
     * @author Dennis
     * @version 1.0
     * @date 2020/3/13 13:32
     */
    private static class ThreadTask {
        private Thread thread;
        private InternalTask internalTask;
        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }
}

