package com.dennis.conccurency.chapter12;
/**
* 描述：测试类
* @author   Dennis
* @date     2020/3/16 13:21
* @version  1.0
*/
public class Test4Enum {
    public static void main(String[] args) {
        Singleton4Enum singleton = Singleton4Enum.getInstance();
        singleton.calculate();
    }
}
