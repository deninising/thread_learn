package com.dennis.conccurency.chapter16;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 描述： 不可变类，用final修饰类防止被继承重写
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 11:38
 */
public final class ImmutableIntAccumulator {
    private int init;

    public ImmutableIntAccumulator(int init) {
        this.init = init;
    }
    // 构造新的累加器，需要用到另外一个immutableIntAccumulator和初始值
    private ImmutableIntAccumulator(ImmutableIntAccumulator intAccumulator, int init) {
        this.init = intAccumulator.getValue() + init;
    }

    // 每次相加都会产生一个新的ImmutableIntAccumulator
    public final ImmutableIntAccumulator add(int inc) {
        return new ImmutableIntAccumulator(this, inc);
    }

    public int getValue() {
        return this.init;
    }

    public static void main(String[] args) {
        final ImmutableIntAccumulator[] accumulator = {new ImmutableIntAccumulator(0)};
        // 采用synchronized关键字解决同步解决线程安全问题
        IntStream.range(0, 3).forEach(item -> {
            new Thread(() -> {
                int inc = 0;
                while (true) {
                    int oldValue = accumulator[0].getValue();
                    ImmutableIntAccumulator newCalculator = accumulator[0].add(inc);
                    int result = newCalculator.getValue();
                    accumulator[0] = newCalculator;
                    if (oldValue + inc != result) {
                        System.out.println("error:" + oldValue + " + " + inc + " != " + result);
                    } else {
                        System.out.println("correct:" + oldValue + " + " + inc + " = " + result);
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    inc++;
                }
            }, "thread-" + item).start();
        });
    }
}
