package com.example.togetherdelivery;

import java.io.Serializable;

public class ProductsModel implements Serializable {
    String storeName;
    String storeAddr;
    String storeId;



    private ProductsModel() {}

    private ProductsModel(String storeName, String storeAddr, String storeId) {
        this.storeName = storeName;
        this.storeAddr = storeAddr;
        this.storeId = storeId;

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
