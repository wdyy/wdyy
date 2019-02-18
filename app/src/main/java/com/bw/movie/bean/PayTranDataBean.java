package com.bw.movie.bean;

/**
 * Author: 范瑞旗
 * Date: 2019/2/13 20:03
 * Description: 选择支付传递数据Bean
 */
public class PayTranDataBean {

    private String orderId;
    private int totalPrice;

    public PayTranDataBean(String orderId, int totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
