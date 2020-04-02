package com.dennis.conccurency.chapter17;

/**
 * 描述：回调接口
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 17:20
 */
@FunctionalInterface
public interface Callback<R> {
    // 任务完成后会调用该方法，其中R为任务执行后的结果
    void call(R result);
}
