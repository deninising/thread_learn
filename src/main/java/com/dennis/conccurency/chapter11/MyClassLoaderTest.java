package com.dennis.conccurency.chapter11;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述：自定义类加载器的定义(没有屏蔽双亲委派机制)
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/15 13:01
 */
public class MyClassLoaderTest {
    public static void main(String[] args)
            throws ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException {
        // 申明一个类加载器
        MyClassLoader myClassLoader = new MyClassLoader();
        // 使用myClassLoader加载class文件(class文件已编译到对用的文件夹中)
        Class<?> aClass = myClassLoader.loadClass("com.dennis.conccurency.chapter11.HelloWorld");
        System.out.println(aClass.getClassLoader());

        // 到该步骤以前，虽然类被加载,且输出了类的加载器信息,但是HelloWorld.java中的静态代码块并没有被输出,
        // 因为类的loadClass方法并不会导致类的主动初始化,其仅仅完成了加载过程中的加载阶段而已
        Object helloWorld = aClass.newInstance();// 主动加载,则静态代码块中的内容将初始化输出
        System.out.println(helloWorld);

        Method welcome = aClass.getMethod("welcome");
        String result = (String) welcome.invoke(helloWorld);
        System.out.println(result);
    }
}
