package com.dennis.conccurency.chapter11;

import java.util.Arrays;

/**
 * 描述：根类加载器
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/15 11:28
 */
public class BootStrapClassLoader {
    public static void main(String[] args) {
        // 根类加载器没有指向它的引用
        ClassLoader classLoader = String.class.getClassLoader();
        System.out.println("bootStrap:" + classLoader);

        // 根类加载器所在的加载路径
        String bootPath = System.getProperty("sun.boot.class.path");
        Arrays.asList(bootPath.split(";")).forEach(System.out::println);
    }
}
