package com.dennis.conccurency.chapter18;

import java.util.LinkedList;

/**
 * 描述：确保挂起模式，条件不满足则挂起，反之开启
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/20 11:31
 */
public class GuardedSuspensionQueue {
    // 资源共享队列
    private final LinkedList<Integer> queue = new LinkedList<>();
    // 条件
    private final int LIMIT = 100;

    public void offer(Integer data) {
        synchronized (this) {
            while (this.queue.size() >= LIMIT) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.queue.addLast(data);
            this.notifyAll();
        }
    }

    public Integer take() {
        synchronized (this) {
            while (this.queue.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Integer integer = this.queue.removeFirst();
            this.notifyAll();
            return integer;
        }
    }
}
