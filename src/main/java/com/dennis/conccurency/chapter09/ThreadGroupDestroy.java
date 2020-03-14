package com.dennis.conccurency.chapter09;

import java.util.concurrent.TimeUnit;

/**
 * 描述： 调用destroy方法可将当前线程组从父线程中除去
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/12 15:33
 */
public class ThreadGroupDestroy {
    public static void main(String[] args) {
        ThreadGroup myGroup = new ThreadGroup("myGroup");
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        System.out.println("myGroup isDestroyed: " + myGroup.isDestroyed());
        mainGroup.list();

        // 有active的线程则调用destroy方法将会抛出异常
      /*  new Thread(myGroup, () -> {
            while (true) {

            }
        }, "myThread").start();*/

        myGroup.destroy();
        System.out.println("myGroup isDestroyed: " + myGroup.isDestroyed());
        mainGroup.list();
    }
}
