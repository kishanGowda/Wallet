package com.example.wallet.Api;

public class tokenRequest {
    String orderCurrency;
    int orderAmount;
    String orderId;

    public tokenRequest(String orderCurrency, int orderAmount, String orderId) {
        this.orderCurrency = orderCurrency;
        this.orderAmount = orderAmount;
        this.orderId = orderId;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getOrderId() {
        return orderId;
    }
}
