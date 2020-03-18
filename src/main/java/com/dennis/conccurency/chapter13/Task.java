package com.dennis.conccurency.chapter13;

/**
 * 描述： 任务函数式接口
 * 由于我们需要对线程中的任务执行增加可观察的能力，并且需要获得最后的计算结果，因此Runnable接口
 * 在可观察的线程中将不再使用，取而代之的是Task接口，其作用与Runnable类似，主要用于承载任务的
 * 逻辑执行单元。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 12:16
 */
@FunctionalInterface
public interface Task<T> {
    // 唯一调用方法，不接受参数，有一返回值
    T call();
}
