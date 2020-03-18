package com.dennis.conccurency.chapter13;

import java.util.concurrent.TimeUnit;

/**
 * 描述： 一个能返回任务执行结果的测试
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 13:43
 */
public class TestV2 {
    /**
     * 下面这段程序代码定义了一个需要返回值的ObservableThread,并且通过重写
     * EmptyLifecycle的onFinish方法监听（观察）输出最终的返回结果。
     */
    public static void main(String[] args) {
        final TaskLifecycle<String> taskLifecycle = new TaskLifecycle.EmptyTaskLifecycle<String>() {
            @Override
            public void onFinished(Thread thread, String result) {
                System.out.println("the result of the task executed by the thread," + thread.getName() + ", is:" + result);
            }
        };

        ObservableThread<String> stringObservableThread = new ObservableThread<>(taskLifecycle, () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // call（）方法的返回结果如下
            return "hello observer!";
        });
        stringObservableThread.start();
    }
}
