package com.dennis.conccurency.chapter12;

/**
 * 描述： 【特点一】枚举类版本的单例模式,来源《Effective Java》,
 *       【特点二】利用枚举类本身就是final且只会被实例化一次的特性,
 *       【特点三】当且仅当枚举只有一个枚举属性时,该枚举属性就是该类型的一个实例
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/16 13:15
 */
public enum Singleton4Enum {
    INSTANCE;

    // 实例变量
    byte[] data = new byte[1024];

    public static Singleton4Enum getInstance() {
        return INSTANCE;
    }

    public void calculate(){
        // handle the data......
        System.out.println("handle the data......");
    }
}
