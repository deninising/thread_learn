package com.dennis.conccurency.chapter14;

import java.util.concurrent.TimeUnit;

/**
 * 描述：安检类充当共享资源
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/17 19:02
 */
public class FlightSecurity {
    /**
     * 乘客名
     */
    private String passengerName;
    /**
     * 登机牌Id
     */
    private String passId;
    /**
     * 身份Id
     */
    private String idCard;

    /**
     * 当前检测人编号
     */
    int count;

    /**
     * 检测方法
     */
    public synchronized void  pass(String passengerName, String idCard, String passId) {

        //=======线程不安全=========
        this.idCard = idCard;
        this.passId = passId;
        //=======线程不安全=========

        this.passengerName = passengerName;
        count++;
        check(passId, idCard);
    }

    private void check(String passId, String idCard) {
        if (passId.charAt(0) != idCard.charAt(0)) {
            throw new RuntimeException("====Exception====" + toString());
        } else {
            System.out.println("passenger:" + count +" " +passengerName + " has passed");
        }
    }

    @Override
    public String toString() {
        return "the count " + count + "passenger:" + passengerName + ",idCard" + idCard + ",passId" + passId + "pass failure";
    }


}
