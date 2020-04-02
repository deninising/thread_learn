package com.dennis.conccurency.chapter17;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：FutureServiceImpl的主要作用在于当提交任务时创建一个新的线程来受理该任务,进而达到任务异步执
 * 行的效果
 *
 * 在FutureServiceImpl的submit方法中，分别启动了新的线程运行任务，起到了异步的
 * 作用，在任务最终运行成功之后，会通知FutureTask任务已完成。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 15:59
 */
public class FutureServiceImpl<IN, OUT> implements FutureService<IN, OUT> {
    // 为执行线程指定名字前缀
    private final static String FUTURE_THREAD_PREFIX = "FUTURE-";

    private final AtomicInteger nextCounter = new AtomicInteger(0);

    // 获取名称
    protected String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        new Thread(() -> {
            runnable.run();
            // 任务执行结束将null作为结果传给future;
            future.finish(null);
        }, getNextName()).start();
        return future;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input) {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() -> {
            OUT out = task.run(input);
            // 任务执行结束之后，将真实的结果通过finish方法传递给future
            future.finish(out);
        }, getNextName()).start();
        return future;
    }

    public Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback) {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() -> {
            OUT result = task.run(input);
            future.finish(result);
            /**
             * 修改后的submit方法，增加了一个Callback参数，主要用来接受并处理任务的计算结
             * 果，当提交的任务执行完成之后，会将结果传递给Callback接口进行进一步的执行， 这样
             * 在提交任务之后,无需调用get方法获取执行的结果，因此避开在主线中因为调用get方法获得结果而陷入阻塞。
             */
            if (callback != null)
                callback.call(result);
        }, getNextName()).start();
        return future;
    }
}
