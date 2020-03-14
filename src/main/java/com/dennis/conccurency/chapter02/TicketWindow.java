package com.dennis.conccurency.chapter02;

/**
 * 描述：营业厅叫号机
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/23 20:56
 */
public class TicketWindow extends Thread {
    private static final String LOCK = "lock";

    /**
     * 柜台名称
     */
    private String name;

    /**
     * 最大处理业务量
     */
    private static final Long MAX = 50L;

    /**
     * 当前序号
     */
    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (LOCK) {
                if (index > MAX) {
                    break;
                }
                System.out.println("柜台：" + name + "当前号码：" + index++);
            }
        }
    }
}
