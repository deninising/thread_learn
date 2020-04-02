package com.dennis.conccurency.chapter17;

/**
 * 描述：相当与Runnable接口，只是其接口方法改为有一传入参数且有返回值
 * Task接口主要是提供给调用者实现计算逻辑之用的，可以接受-一个参数并且返回最终
 * 的计算结果，这一点非常类似于JDK1.5中的Callable接口.
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 16:01
 */
@FunctionalInterface
public interface Task<IN, OUT> {
    // 相当于Runnable的run方法,给定一个参数,经计算得到返回结果
    OUT run(IN input);
}
