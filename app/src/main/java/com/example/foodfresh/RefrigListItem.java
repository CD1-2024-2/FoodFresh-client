package com.example.foodfresh;

public class RefrigListItem {
    private String id;
    private boolean isPrivate;
    private String name;
    private int num;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
    RefrigListItem (String id, String name, int num, String date, boolean isprivate) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.date = date;
        this.isPrivate = isprivate;
    }
}
