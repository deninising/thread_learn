package com.dennis.conccurency.chapter01;

/**
 * 描述：通过直接重写Thread的run()方法来创建一个线程
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/23 20:43
 */
public class TryConcurrency {
    public static void main(String[] args) {

        // 创建一个Thread匿名对象，重修其run()方法,调通start方法启动线程,其内部将调用本地方法start0，start0()底层将调用当前线程的执行单元run()
        // 其整个执行逻辑框架为以典型的模板方法模式

        new Thread("writeThread") {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("write to file > > >");
                    try {
                        Thread.sleep(490);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread("readThread") {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("read from file > > >");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
