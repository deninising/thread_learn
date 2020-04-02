package com.dennis.conccurency.chapter17;

/**
 * 描述：   FutureService主要用于提交任务,提交的任务主要有两种，第-一种不需要返回值，第
 * 二种则需要获得最终的计算结果。FutureService 接口中提供了对FutureServiceImpl构建的
 * 工厂方法，JDK8中不仅支持default方法还支持静态方法,JDK9甚至还支持接口私有方法。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 15:51
 */
public interface FutureService<IN, OUT> {
    // 无需返回结果
    Future<?> submit(Runnable runnable);

    // 提交任务且有返回结果，其中Task接口代替了Runnable接口
    Future<OUT> submit(Task<IN, OUT> task, IN input);

    // 提交任务且有返回结果，其中Task接口代替了Runnable接口
    Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback);

    // 使用静态工厂方法创建了一个FutureService的实现类
    static <T, R> FutureService<T, R> newFutureService() {
        return new FutureServiceImpl<>();
    }
}
