package com.dennis.conccurency.chapter01;

/**
 * 描述：模板方法：类比通过通过重写thread对象的run()方法来创建线程
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/2/23 19:50
 */
public class TemplateMethod {

    // 类似于Thread.start()方法,内部流程执行框架固定
    public final void print(String message) {
        System.out.println(System.getProperties());
        System.out.println("+++++++++++++++++++++");
        wrapPrint(message);
        System.out.println("+++++++++++++++++++++");
    }

    // 类似于Thread.run()方法,具体的业务逻辑执行单元
    protected void wrapPrint(String message) {
    }

    public static void main(String[] args) {
        TemplateMethod templateMethod = new TemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println(">" + message + "<");
            }
        };
        templateMethod.print("do things in one way");

        TemplateMethod templateMethod1 = new TemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println(">>" + message + "<<");
            }
        };
        templateMethod1.print("do things in another way");
    }
}
