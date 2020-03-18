package com.dennis.conccurency.chapter15;

import java.util.stream.IntStream;

/**
 * 描述：读写锁测试
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/18 16:25
 */
public class ReadWriteLockTest {
    // 定义异常待写入的字符串
    private static final String textToWrite = "this string is for writing into the container";

    public static void main(String[] args) {
        final ShareData data = new ShareData(50);
        // 2个线程用于写操作
        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                for (int n = 0; n < textToWrite.length(); n++) {
                    try {
                        data.write(textToWrite.charAt(n));
                        System.out.println(Thread.currentThread().getName() + "write:" + textToWrite.charAt(n));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "write" + i).start();
        });
        // 10个线程用于读操作
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "read:" + new String(data.read()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "read" + i).start();
        });
    }
}
