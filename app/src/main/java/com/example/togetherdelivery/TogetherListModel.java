package com.example.togetherdelivery;

import java.io.Serializable;

// 받을 장소,
public class TogetherListModel implements Serializable {
    String storeName;
    String storeId;
    String orderId;
    String peopleNum;
    String curStatus;
    String ranNum;
    String place;
    String finishTime;
    String curPeople;


    private TogetherListModel() {}

    private TogetherListModel(String storeName, String storeId, String orderId, String peopleNum, String curStatus, String ranNum, String place, String finishTime, String curPeople) {
        this.storeName = storeName;
        this.storeId = storeId;
        this.orderId = orderId;
        this.peopleNum = peopleNum;
        this.curStatus = curStatus;
        this.ranNum = ranNum;
        this.place = place;
        this.finishTime = finishTime;
        this.curPeople = curPeople;

    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }



    public String getRanNum() {
        return ranNum;
    }

    public void setRanNum(String ranNum) {
        this.ranNum = ranNum;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCurPeople() {
        return curPeople;
    }
    public void setCurPeople(String curPeople) {
        this.curPeople = curPeople;
    }


}
