package com.example.foodfresh;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterDM {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("password")
    @Expose
    private String password;

    public RegisterDM (String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

class LoginDM {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("password")
    @Expose
    private String password;

    public LoginDM () {}
    public LoginDM (String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}

class FoodDM {
    private String imageURL;
    private String name;
    private String expirationDate;
    private int quantity;
    private String category;
    private String barcode;
    private String description;

    public FoodDM(
            String imageURL,
            String name,
            String expirationDate,
            int quantity,
            String category,
            String barcode,
            String description
    ) {
        this.imageURL = imageURL;
        this.name = name;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.category = category;
        this.barcode = barcode;
        this.description = description;
    }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imgURL) { this.imageURL = imageURL; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
