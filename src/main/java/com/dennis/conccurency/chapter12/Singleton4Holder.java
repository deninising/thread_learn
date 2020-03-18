package com.dennis.conccurency.chapter12;

/**
 * 描述：Holder版单例模式
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/16 12:01
 */
public class Singleton4Holder {
    // 实例变量
    private byte[] data = new byte[1024];

    // 构造器私有化
    private Singleton4Holder() {
    }

    // 在静态内部类中持有单例对象,并且可被直接初始化
    private static class Holder {
        private static Singleton4Holder INSTANCE = new Singleton4Holder();
    }

    // 对提供getInstance方法,获取单例对象
    public static Singleton4Holder getInstance() {
        return Holder.INSTANCE;
    }
}
