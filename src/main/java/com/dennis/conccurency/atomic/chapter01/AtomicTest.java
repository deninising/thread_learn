package com.dennis.conccurency.atomic.chapter01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：原子类型测试
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/31 12:40
 */
public class AtomicTest {
    public static void main(String[] args) {
//        AtomicInteger atoInt01 = new AtomicInteger();
//        int i = atoInt01.getAndAdd(1);
//        System.out.println(i);
//        int i1 = atoInt01.getAndAdd(1);
//        System.out.println(i1);
//
//
        AtomicInteger atoInt02 = new AtomicInteger();
        int set01 = atoInt02.getAndSet(20);
        System.out.println(set01);

        boolean setSuccess = atoInt02.compareAndSet(20, 30);
        if (setSuccess) {
            System.out.println("设值成功！！！");
        } else {
            System.out.println("设值失败！！！");
        }

    }
}
