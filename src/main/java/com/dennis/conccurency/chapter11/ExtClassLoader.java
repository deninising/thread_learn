package com.dennis.conccurency.chapter11;

import java.util.Arrays;

/**
 * 描述：扩展类加载器,加载JAVA_HOME下的jre/lb/ext子目录中的类库
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/15 11:38
 */
public class ExtClassLoader {
    public static void main(String[] args) {
        // 获取加载资源的路径
        String[] splits = System.getProperty("java.ext.dirs").split(";");
        Arrays.asList(splits).forEach(System.out::println);
    }
}
