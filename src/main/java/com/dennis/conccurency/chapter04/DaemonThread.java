package com.dennis.conccurency.chapter04;

/**
 * 描述：守护线程
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/24 21:25
 */
public class DaemonThread {
    public static void main(String[] args) {

        Thread t = new Thread("MyThread") {
            @Override
            public void run() {
                new Thread("child thread of my thread") {
                    @Override
                    public void run() {
                        if (this.isDaemon()) {
                            System.out.println(this.getName() + " is a daemon thread");
                        } else {
                            System.out.println(this.getName() + " is not a daemon thread");
                        }
                    }
                }.start();

                for (; ; ) {
                    System.out.println(Thread.currentThread().getName() + "is running");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(this.getName() + " has be interrupted");
                    }
                }

            }
        };

        t.setDaemon(true); // 将线程t设置为守护线程 ,注释掉该操作，会发现main线程结束后JVM不会自动退出,同时发现其子线程isDaemon属性与其相同
        t.start();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main thread has finished its lifecycle");
    }
}
