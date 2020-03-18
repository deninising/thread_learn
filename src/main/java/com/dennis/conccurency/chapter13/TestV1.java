package com.dennis.conccurency.chapter13;

import java.util.concurrent.TimeUnit;

/**
 * 描述：最简单的测试类
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 13:39
 */
public class TestV1 {
    /**
     * 这段程序与你平时使用Thread并没有太大的区别，只不过ObservableThread是一个泛
     * 型类，我们将其定义为Void类型，表示不关心返回值，默认的EmptyLifecycle同样表示不
     * 关心生命周期的每一个阶段
     */
    public static void main(String[] args) {
        ObservableThread observableThread = new ObservableThread<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("finished done");
            return null;
        });
        observableThread.start();
    }
}
