package com.dennis.conccurency.chapter09;

/**
 * 描述：线程组
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/12 9:04
 */
public class ThreadGroupCreator {
    public static void main(String[] args) {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        // 未显示指定父线程组
        ThreadGroup group1 = new ThreadGroup("group1");
        // 检查
        System.out.println(group1.getParent() == currentGroup);

        ThreadGroup group2 = new ThreadGroup(group1, "group2");
        // 检查父线程组
        System.out.println(group2.getParent() == group1);
        // 检查线程组所属关系
        System.out.println(group1.parentOf(group2));
    }
}
