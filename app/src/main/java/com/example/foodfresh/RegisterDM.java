package com.example.foodfresh;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
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

class RefrigAddDM {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    public RefrigAddDM(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
class RefrigDM {
    @SerializedName("rid")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    @SerializedName("manager")
    @Expose
    private String manager;

    @SerializedName("sharedUsers")
    @Expose
    private String[] sharedUsers;

    @SerializedName("isShared")
    @Expose
    private boolean isShared;

    public RefrigDM(String id, String name, String description, String createdDate, String manager, String[] sharedUsers, boolean isShared) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.manager = manager;
        this.sharedUsers = sharedUsers;
        this.isShared = isShared;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
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

    public MessageDM() {

    }

    public String getMessage() {
        return message;
    }
}

class MemberDM {
    @SerializedName("sharedUserIds")
    @Expose
    private String[] sharedUsers;

    public MemberDM(String[] sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public String[] getSharedUsers() {
        return sharedUsers;
    }
}

class FoodDM implements Serializable {
    @SerializedName("fid")
    @Expose
    private String fid;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;

    @SerializedName("registeredDate")
    @Expose
    private String registeredDate;

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

    public FoodDM(String fid, String imageURL, String name, String expirationDate, String registeredDate, int quantity, String category, String barcode, String description) {
        this.fid = fid;
        this.imageURL = imageURL;
        this.name = name;
        this.expirationDate = expirationDate;
        this.registeredDate = registeredDate;
        this.quantity = quantity;
        this.category = category;
        this.barcode = barcode;
        this.description = description;
    }

    public String getId() {
        return fid;
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

    public String getRegisteredDate() {
        return registeredDate;
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