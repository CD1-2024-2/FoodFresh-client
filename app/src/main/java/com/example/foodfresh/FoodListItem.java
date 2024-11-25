package com.example.foodfresh;

import android.media.Image;
public class FoodListItem {
    private String id;
    private Image img;
    private String name;
    private String createdDate;
    private String expiryDate;
    private String category;
    private String barcode;
    private int num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    FoodListItem (String id) {
        this.id = id;
        this.name = "name";
        this.img = null;
        this.createdDate = "0000-00-00";
        this.expiryDate = "0000-00-00";
        this.category = "category";
        this.barcode = "barcode";
        this.num = 1;
    }

    FoodListItem (String id, String name, Image img, String createdDate, String expiryDate, String category, String barcode, int num) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.createdDate = createdDate;
        this.expiryDate = expiryDate;
        this.category = category;
        this.barcode = barcode;
        this.num = num;
    }
}
