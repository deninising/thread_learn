package com.dennis.conccurency.chapter07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 描述： 客户端程通信
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/29 23:24
 */
public class EventClient {
    public static void main(String[] args) throws InterruptedException {
        // 单线程版本
       /* EventQueueForSingleThread eventQueue = new EventQueueForSingleThread();

        new Thread(() -> {
            while (true) {
                eventQueue.addEvent(new EventQueueForSingleThread.Event());
            }
        }, "producer").start();
        new Thread(() -> {
            while (true) {
                eventQueue.handleEvent();
            }
        }, "consumer").start();*/

        // 多线程版本
        EventQueueForMultiThreads queueForMultiThreads = new EventQueueForMultiThreads();
        Stream.of("p1", "p2", "p3").map(name -> new Thread(() -> {
            while (true) {
                queueForMultiThreads.addEvent(new EventQueueForSingleThread.Event());
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, name)).forEach(Thread::start);

        Stream.of("c1", "c2", "c3").map(name -> new Thread(() -> {
            while (true) {
                queueForMultiThreads.handleEvent();
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, name)).forEach(Thread::start);
    }
}
