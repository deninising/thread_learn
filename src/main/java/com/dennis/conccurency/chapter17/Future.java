package com.dennis.conccurency.chapter17;

/**
 * 描述：Future提供了获取计算结果和判断任务是否完成的两个接口，其中获取计算结果将会
 * 导致调用阻塞(在任务还未完成的情况下)。
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 15:46
 */
public interface Future<R> {
    // 用于获取结果
    R get() throws InterruptedException;

    //  用于判断内部任务是否执行结束
    boolean done();
}
