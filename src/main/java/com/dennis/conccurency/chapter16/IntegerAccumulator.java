package com.dennis.conccurency.chapter16;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 描述：非线程安全的累加器
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/19 11:21
 */
public class IntegerAccumulator {
    private int init;

    public IntegerAccumulator(int init) {
        this.init = init;
    }

    // 加i
    public int add(int i) {
        this.init += i;
        return this.init;
    }

    // 获取值
    public int getValue() {
        return this.init;
    }

    public static void main(String[] args) {
        IntegerAccumulator accumulator = new IntegerAccumulator(0);

       /* IntStream.range(0, 3).forEach(item -> {
            new Thread(() -> {
                int inc = 0;
                while (true) {
                    int oldValue = accumulator.getValue();
                    int result = accumulator.add(inc);
                    if (oldValue + inc != result) {
                        System.out.println("error:" + oldValue + "+" + inc + " does not equal " + result);
                    } else {
                        System.out.println("calculate correctly!!!");
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    inc++;
                }
            }, "thread-" + item).start();
        });*/

        // 采用synchronized关键字解决同步解决线程安全问题
        IntStream.range(0, 3).forEach(item -> {
            new Thread(() -> {
                int inc = 0;
                while (true) {
                    int oldValue;
                    int result;
                    synchronized (IntegerAccumulator.class) {
                        oldValue = accumulator.getValue();
                        result = accumulator.add(inc);
                    }
                    if (oldValue + inc != result) {
                        System.out.println("error:" + oldValue + "+" + inc + " does not equal " + result);
                    } else {
                        System.out.println("calculate correctly!!!");
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    inc++;
                }
            }, "thread-" + item).start();
        });
    }
}
