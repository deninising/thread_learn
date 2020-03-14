package com.dennis.conccurency.chapter02;

public class AppDemo {
    public static void main(String[] args) {
//        TicketWindow window01 = new TicketWindow("一号柜台");
//        TicketWindow window02 = new TicketWindow("二号柜台");
//        TicketWindow window03 = new TicketWindow("三号柜台");
//        TicketWindow window04 = new TicketWindow("四号柜台");
//
//        window01.start();
//        window02.start();
//        window03.start();
//        window04.start();

        TicketWindowRunnable task = new TicketWindowRunnable();
        Thread t1 = new Thread(task, "一号柜台");
        Thread t2 = new Thread(task, "二号柜台");
        Thread t3 = new Thread(task, "三号柜台");
        Thread t4 = new Thread(task, "四号柜台");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
