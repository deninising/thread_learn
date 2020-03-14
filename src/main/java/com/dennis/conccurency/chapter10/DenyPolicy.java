package com.dennis.conccurency.chapter10;

/**
 * 描述：当任务队列满时，对应解决的拒绝策略
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/13 11:50
 */
public interface DenyPolicy {

    void reject(Runnable runnable, ThreadPool threadPool);

    /**
     * 该拒绝策略会直接将任务丢弃
     */
    class DiscardDenyPolicy implements DenyPolicy {

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            // do nothing
        }
    }

    /**
     * 该拒绝策略会任务提交者抛出异常
     */
    class AbortDenyPolicy implements DenyPolicy {

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            throw new RunnableDenyException("The Runnable " + runnable + "will be abort.");
        }
    }

    /**
     * 改拒绝策略会在任务提交者的线程中执行任务
     */
    class RunnerDenyPolicy implements DenyPolicy {
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            if (!threadPool.isShutDown())
                runnable.run();
        }
    }

    class RunnableDenyException extends RuntimeException {
        public RunnableDenyException(String errorMsg) {
            super(errorMsg);
        }
    }

}
