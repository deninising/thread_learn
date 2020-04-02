package com.dennis.conccurency.atomic.chapter02;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 描述：自定义原子类型测试
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/4/1 15:20
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Student> atomicStudent = new AtomicReference<>();
        Student student = new Student("张三", 20);
        atomicStudent.set(student);
        boolean success01 = atomicStudent.compareAndSet(new Student("zhangsan", 20), new Student("lisi", 25));
        if (success01) {
            System.out.println("替换成功！！！");
        } else {
            System.out.println("替换失败！！！");
        }

        // 第2步进行替换时,期望的值要是还没被其他线程修改,即任然与第1部中获取到的值相等,那么将成功替换否则替换失败
        Student expect = atomicStudent.get(); // 1
        boolean success02 = atomicStudent.compareAndSet(expect, new Student("lisi", 25));  //2

        if (success02) {
            System.out.println("替换成功！！！");
        } else {
            System.out.println("替换失败！！！");
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
