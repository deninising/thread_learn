package com.dennis.conccurency.chapter02;

import java.util.concurrent.TimeUnit;

/**
 * 描述：重构：采用Runnable接口来封装原本thread.run()方法中执行的业务逻辑单元，本质上是一种策略模式的体现：把业务逻辑提取成一个策略接口
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/23 22:34
 */
public class TicketWindowRunnable implements Runnable {

    /**
     * 最大处理业务量
     */
    private static final Long MAX = 50L;

    /**
     * 当前序号
     */
    private static int index = 1;

    public final static Object LOCK = new Object();

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (LOCK) {
                if (index > MAX) {
                    break;
                }
                System.out.println("柜台：" + Thread.currentThread().getName() + "当前号码：" + index++);
            }
        }
    }
}
