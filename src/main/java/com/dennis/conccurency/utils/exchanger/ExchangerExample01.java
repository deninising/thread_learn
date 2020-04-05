package com.dennis.conccurency.utils.exchanger;

import com.dennis.conccurency.utils.semapphore.SemaphoreExample01;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 描述：线程间（pair）数据交换工具类：线程间交互数据,另一个匹配线程若是没有交换点,则当前线程将处于阻塞状态
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/2 19:06
 */
public class ExchangerExample01 {
    public static void main(String[] args) {
        Exchanger<Student> exchanger = new Exchanger<>();

        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            Student studentA = new Student(threadName, 20);

            for (; ; ) {
                System.out.println(threadName + " is go along to change the data...");
                try {
                    Student studentB = exchanger.exchange(studentA,1,TimeUnit.SECONDS);
                    System.out.println(threadName + " has get the data from the partner thread:" + studentB);
                } catch (InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                }
            }

        }, "thread-A").start();


        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            Student studentB = new Student(threadName, 30);

            for (; ; ) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " is go along to change the data...");
                try {
                    Student studentA = exchanger.exchange(studentB);
                    System.out.println(threadName + " has get the data from the partner thread:" + studentA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "thread-B").start();
    }

    static class Student {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
