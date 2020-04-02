package com.dennis.conccurency.atomic.chapter02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 描述：解决A->B->A问题案例
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/1 15:38
 */
public class AtomicStampedReferenceTest {

    public static void main(String[] args) {
        Node<Student> studentNode01 = new Node<>(new Student("z01", 11));
        studentNode01.setPre(null);
        studentNode01.setNext(null);
        AtomicStampedReference<Node<Student>> atomicStudentNode = new AtomicStampedReference<>(studentNode01, 1);

        // 可以用AtomicReference<T>来包装匿名内部类将要访问并进行修改的对象T,不用数组进行包装
        AtomicReference<String> a = new AtomicReference<>("abc");
//        a = "abc";
        new Thread(() -> {
            a.set("sdf");
        // 可以用AtomicReference<T>来包装匿名内部类将要访问并进行修改的对象T,不用数组进行包装：

            int stamp = atomicStudentNode.getStamp();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Node<Student> reference = atomicStudentNode.getReference();
            Node<Student> studentNode02 = new Node<>(new Student("z02", 12));
            int realityStamp = atomicStudentNode.getStamp();
            boolean success = atomicStudentNode.compareAndSet(reference, Node.appendAndNew(reference, studentNode02), stamp, stamp + 1);
            if (success) {
                System.out.println("1-成功设值！！！");
                System.out.println(atomicStudentNode.getReference());
                System.out.println("expect stamp ->" + stamp);
                System.out.println("reality stamp ->" + realityStamp);
            } else {
                System.out.println("1-设置失败！！！");
                System.out.println("expect stamp ->" + stamp);
                System.out.println("reality stamp ->" + realityStamp);
            }
        }).start();
        new Thread(() -> {
            int stamp = atomicStudentNode.getStamp();
            Node<Student> reference = atomicStudentNode.getReference();
            Node<Student> studentNode02 = new Node<>(new Student("z02", 12));
            int realityStamp = atomicStudentNode.getStamp();
            boolean success = atomicStudentNode.compareAndSet(reference, Node.appendAndNew(reference, studentNode02), stamp, stamp + 1);
            if (success) {
                System.out.println("2-成功设值！！！");
                System.out.println(atomicStudentNode.getReference());
                System.out.println("expect stamp ->" + stamp);
                System.out.println("reality stamp ->" + realityStamp);
            } else {
                System.out.println("2-设置失败！！！");
                System.out.println("expect stamp ->" + stamp);
                System.out.println("reality stamp ->" + realityStamp);
            }
        }).start();
    }

    static class Node<T> {
        private Node<T> pre;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPre() {
            return pre;
        }

        public void setPre(Node<T> pre) {
            this.pre = pre;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> setNext(Node<T> next) {
            this.next = next;
            return this;
        }

        static <T> Node<T> appendAndNew(Node<T> oldNode, Node<T> tail) {
            oldNode.recAppend(oldNode, tail);
            return new Node<>(oldNode.getValue()).setNext(oldNode.getNext());
        }

        // 递归添加设置尾部节点,一般还是推荐使用循环
        private void recAppend(Node<T> chainNode, Node<T> tail) {
            Node<T> next;
            if ((next = chainNode.getNext()) != null) {
                recAppend(next, tail);
            } else {
                chainNode.setNext(tail);
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "pre=" + pre +
                    ", next=" + next +
                    ", value=" + value +
                    '}';
        }
    }

    static class Student {
        private String name;
        private Integer age;

        public Student() {
        }

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
