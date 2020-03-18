package com.dennis.conccurency.chapter11;

import java.util.Arrays;

/**
 * 描述： 系统类加载器,加载classPath类路径下的所有第三方jar中的类资源
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/15 11:42
 */
public class ApplicationClassLoader {
    public static void main(String[] args) {
        System.out.println();
        String[] paths = System.getProperty("java.class.path").split(";");
        Arrays.asList(paths).forEach(System.out::println);

        System.out.println(ApplicationClassLoader.class.getClassLoader());
    }
}
