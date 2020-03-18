package com.dennis.conccurency.chapter13;

import com.sun.crypto.provider.PBEWithMD5AndDESCipher;

/**
 * 描述：观察者线程实现类：ObservableThread是任务监控的关键，它继承自Thread类和Observable接口，并且在构造期间需要传人Task的具体实现。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 12:22
 */
public class ObservableThread<T> extends Thread implements Observable {
    private final TaskLifecycle<T> lifecycle;
    private final Task<T> task;

    private Cycle cycle;


    // 指定Task的实现，但是TaskLifecycle默认为空实现
    public ObservableThread(Task<T> task) {
        this(new TaskLifecycle.EmptyTaskLifecycle<>(), task);
    }

    public ObservableThread(TaskLifecycle<T> taskLifecycle, Task<T> task) {
        super();
        // task不能为null
        if (task == null)
            throw new IllegalArgumentException("the task is required");
        this.lifecycle = taskLifecycle;
        this.task = task;
    }

    /**
     * 重写父类的run方法，并且将其修饰为final类型，不允许子类再次对其进行重写，run
     * 方法在线程的运行期间，可监控任务在执行过程中的各个生命周期阶段，任务每经过一个
     * 阶段相当于发生了-次事件。
     */
    @Override
    public final void run() {
        // 在执行任务逻辑单元的时候，分别触发相应的事件
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            // 执行任务，没有输入参数，有一返回结果
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            e.printStackTrace();
            this.update(Cycle.ERROR, null, e);
        }
    }

    /**
     * update方法用于通知时间的监听者，此时任务在执行过程中发生了什么，最主要的通
     * 知是异常的处理。如果监听者也就是TaskLifecycle, 在响应某个事件的过程中出现了意外，
     * 则会导致任务的正常执行受到影响，因此需要进行异常捕获，并忽略这些异常信息以保证
     * TaskLifecycle的实现不影响任务的正确执行，但是如果任务执行过程中出现错误并且抛出
     * 了异常，那么update方法就不能忽略该异常，需要继续抛出异常，保持与call方法同样的
     * 意图。
     */
    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;

        if (lifecycle == null)
            return;
        try {
            switch (cycle) {
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinished(currentThread(), result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(), e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }
}
