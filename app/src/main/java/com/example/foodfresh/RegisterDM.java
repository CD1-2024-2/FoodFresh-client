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

