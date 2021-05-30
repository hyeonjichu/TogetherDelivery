package com.example.togetherdelivery;

public class MenuModel {
    String menuName, menuPrice;

    private MenuModel() {}

    private MenuModel(String menuName, String menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }
}
