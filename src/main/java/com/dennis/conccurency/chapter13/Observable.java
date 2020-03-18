package com.dennis.conccurency.chapter13;

/**
 * 描述：观察者模式
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 12:01
 */
public interface Observable {
    // 任务生命周期的枚举类
    enum Cycle {
        STARTED, RUNNING, DONE, ERROR;
    }

    // 获取当前任务的生命周期
    Cycle getCycle();

    // 定义任务线程的启动方法，主要是为了屏蔽Thread的其他方法
    void start();

    // 定义任务的中断方法，主要是为了屏蔽Thread的其他方法
    void interrupt();
}
