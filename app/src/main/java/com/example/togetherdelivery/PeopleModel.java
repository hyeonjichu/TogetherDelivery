package com.example.togetherdelivery;

import java.io.Serializable;

public class PeopleModel implements Serializable {
    String orderId;
    String payment;
    String price;

    public PeopleModel(){}

    public PeopleModel(String orderId, String payment, String price) {
        this.orderId = orderId;
        this.payment = payment;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
