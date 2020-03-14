package com.dennis.conccurency.chapter04;

import java.util.stream.IntStream;

/**
 * 描述：线程礼让,可将CPU的执行权让给其他急需被调度的线程
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/24 22:03
 */
public class ThreadYield {
    public static void main(String[] args) {
        IntStream.range(0, 3).mapToObj(ThreadYield::create).forEach(Thread::start);
    }

    private static Thread create(int index) {
        return new Thread() {
            @Override
            public void run() {
                System.out.println(this.getName() + " is running");
//                if (index == 0) {
//                    yield();
//                }// 注释起来后输出顺序基本按0 1 2 ,当取消时发现顺序基本时1 2 0,因为thread-0线程执行了CPU执行权礼让
                System.out.println(index);
            }
        };
    }
}
