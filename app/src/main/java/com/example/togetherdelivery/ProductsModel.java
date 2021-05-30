package com.example.togetherdelivery;

public class ProductsModel {
    String storeName;
    String storeAdd;

    private ProductsModel() {}

    private ProductsModel(String storeName, String storeAdd) {
        this.storeName = storeName;
        this.storeAdd = storeAdd;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAdd() {
        return storeAdd;
    }

    public void setStoreAdd(String storeAddr) {
        this.storeAdd = storeAddr;
    }
}
