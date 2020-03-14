package com.dennis.conccurency.chapter07;

import java.util.LinkedList;

/**
 * 描述： 自定义消息队列
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/29 23:02
 */
public class EventQueueForSingleThread {
    static class Event {
    }

    private final int max;

    private static final int DEFAULT_MAX_EVENT = 10;

    private final LinkedList<Event> eventQueue = new LinkedList<>();

    public EventQueueForSingleThread() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueueForSingleThread(int max) {
        this.max = max;
    }

    //添加事件
    public void addEvent(Event event) {
        synchronized (eventQueue) {
            if (eventQueue.size() >= max) {
                console("event queue is already full");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            eventQueue.addLast(event);
            console("a new event has be submitted");
            eventQueue.notify();
        }
    }

    //处理事件
    public void handleEvent() {
        synchronized (eventQueue) {
            if (eventQueue.isEmpty()) {
                console("there is no even left need to be handled");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Event event = eventQueue.removeFirst();
            console("the event:" + event + " has be handled");
            eventQueue.notify();
        }
    }

    private void console(String message) {
        System.out.println(Thread.currentThread().getName() + ":" + message);
    }
}
