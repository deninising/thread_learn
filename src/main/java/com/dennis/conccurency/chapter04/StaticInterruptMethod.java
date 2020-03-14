package com.dennis.conccurency.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 描述：获取到当前线程的interrupt flag状态值，并擦除标记
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/26 21:30
 */
public class StaticInterruptMethod {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            String msg1 = "=====================";
            String msg2 = "+++++++++++++++++++++";
            String msg3 = null;


            @Override
            public void run() {

                while (true) {
                    boolean b = Thread.interrupted();
                    // 但凡b能有可能两次为true,输出就会先输出====,最后变为输出+++++
                    if (b) {
                        msg3 = msg1;
                        msg1 = msg2;
                    }
                    System.out.println(msg3);
                }
            }
        };

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        // 【1】让b获得一次变为true的机会
        thread.interrupt();

//        TimeUnit.SECONDS.sleep(1);
//        // 【2】让b有一次获得变为true的机会
//        thread.interrupt();
    }
}
