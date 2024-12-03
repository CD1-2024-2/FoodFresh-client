package com.example.foodfresh;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

class RefrigDM {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("manager")
    @Expose
    private String manager;

    @SerializedName("sharedUsers")
    @Expose
    private String[] sharedUsers;

    @SerializedName("isShared")
    @Expose
    private boolean isShared;

    public RefrigDM(String id, String name, String manager, String[] sharedUsers, boolean  isShared) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.sharedUsers = sharedUsers;
        this.isShared = isShared;
    }

    public RefrigDM() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public String[] getSharedUsers() {
        return sharedUsers;
    }

    public boolean isShared() {
        return isShared;
    }
}

class MessageDM {
    @SerializedName("message")
    @Expose
    private String message;

    public MessageDM(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class MemberDM {
    @SerializedName("sharedUsers")
    @Expose
    private String[] sharedUsers;

    public MemberDM(String[] sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public String[] getSharedUsers() {
        return sharedUsers;
    }
}

class FoodDM {
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("barcode")
    @Expose
    private String barcode;

    @SerializedName("description")
    @Expose
    private String description;

    public FoodDM(String imageURL, String name, String expirationDate, int quantity, String category, String barcode, String description) {
        this.imageURL = imageURL;
        this.name = name;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.category = category;
        this.barcode = barcode;
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDescription() {
        return description;
    }
}