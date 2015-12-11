package com.move10x.totem.design;

/**
 * Created by Ravi on 10/24/2015.
 */
public class DrawerListItem {
    String ItemName;
    int imgResID;
    String imagePath;

    public DrawerListItem(String itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
