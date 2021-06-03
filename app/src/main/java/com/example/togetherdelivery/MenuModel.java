package com.example.togetherdelivery;

import java.io.Serializable;

public class MenuModel implements Serializable {
    String menuInfo;
    String menuName;
    String menuPrice;

    private boolean isSelected;
    public MenuModel(){}


    public MenuModel(String menuInfo, String menuName, String menuPrice, boolean isSelected) {
        this.menuInfo = menuInfo;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.isSelected = isSelected;
    }

    public String getMenuInfo() {
        return menuInfo;
    }

   public void setMenuInfo(String menuInfo) {
        this.menuInfo = menuInfo;
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

    public boolean isSelected() {return isSelected;}

    public void setSelected(boolean isSelected) {this.isSelected = isSelected;}
}
