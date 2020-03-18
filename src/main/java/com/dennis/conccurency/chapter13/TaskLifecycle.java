package com.dennis.conccurency.chapter13;

/**
 * 描述：主要定义任务执行的生命周期中将会被触发的接口
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 12:10
 */
public interface TaskLifecycle<T> {
    // 任务启动时将会触发onStart方法
    void onStart(Thread thread);

    // 运行时触发
    void onRunning(Thread thread);

    // 结束时触发
    void onFinished(Thread thread, T result);

    // 异常时触发
    void onError(Thread thread, Exception e);

    // 生命周期接口的空实现
    class EmptyTaskLifecycle<T> implements TaskLifecycle<T> {

        @Override
        public void onStart(Thread thread) {
            // do nothing
        }

        @Override
        public void onRunning(Thread thread) {
            // do nothing
        }

        @Override
        public void onFinished(Thread thread, T result) {
            // do nothing
        }

        @Override
        public void onError(Thread thread, Exception e) {
            // do nothing
        }
    }
}
