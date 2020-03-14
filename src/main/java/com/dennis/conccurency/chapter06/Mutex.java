package com.dennis.conccurency.chapter06;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 描述：synchronised关键字和互斥资源
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/29 13:44
 */
public class Mutex {
    private static final Object MUTEX = new Object();

    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final Mutex mutex = new Mutex();
        IntStream.range(0, 5).forEach(
                i -> new Thread(mutex::accessResource).start()
        );
    }
}
