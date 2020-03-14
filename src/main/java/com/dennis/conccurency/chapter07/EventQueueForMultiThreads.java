package com.dennis.conccurency.chapter07;

import java.util.LinkedList;

/**
 * 描述： 支持多线程通信版本
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/29 23:35
 */
public class EventQueueForMultiThreads {

    // 生产满了才能消费，消费空了才能生产
    private boolean isProduced = false;

    static class Event {
    }

    private final int max;

    private static final int DEFAULT_MAX_EVENT = 10;

    private final LinkedList<EventQueueForSingleThread.Event> eventQueue = new LinkedList<>();

    public EventQueueForMultiThreads() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueueForMultiThreads(int max) {
        this.max = max;
    }

    //添加事件
    public void addEvent(EventQueueForSingleThread.Event event) {
        synchronized (eventQueue) {
//          while (eventQueue.size() >= max) {
            while (isProduced) {
                console("event queue is already full");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            eventQueue.addLast(event);
            console(Thread.currentThread().getName() + "->" + "a new event has be submitted");
            if (eventQueue.size() == max) {
                isProduced = true;
                eventQueue.notifyAll();
            }
//            eventQueue.notifyAll();
        }
    }

    //处理事件
    public void handleEvent() {
        synchronized (eventQueue) {
//            while (eventQueue.isEmpty()) {
            while (!isProduced) {
                console("there is no even left need to be handled");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            EventQueueForSingleThread.Event event = eventQueue.removeFirst();
            console(Thread.currentThread().getName() + "->" + "the event:" + event + " has be handled");
            if (eventQueue.isEmpty()) {
                isProduced = false;
                eventQueue.notifyAll();
            }
//            eventQueue.notifyAll();
        }
    }

    private void console(String message) {
        System.out.println(Thread.currentThread().getName() + ":" + message);
    }
}
