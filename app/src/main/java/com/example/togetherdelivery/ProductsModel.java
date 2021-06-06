package com.example.togetherdelivery;

import java.io.Serializable;

public class ProductsModel implements Serializable {
    String storeName;
    String storeAddr;
    String storeId;
    String limitMoney;

    private ProductsModel() {}

    public String getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(String limitMoney) {
        this.limitMoney = limitMoney;
    }

    private ProductsModel(String storeName, String storeAddr, String storeId, String limitMoney) {
        this.storeName = storeName;
        this.storeAddr = storeAddr;
        this.storeId = storeId;
        this.limitMoney = limitMoney;

    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) { this.storeAddr = storeAddr; }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) { this.storeId = storeId; }


}
