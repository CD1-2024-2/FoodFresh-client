package com.example.foodfresh;

import android.media.Image;

import java.time.LocalDate;

public class ConsumptionListItem {
    static String[] tags = {
            "정상소모",
            "유통기한 만료",
            "신선도 하락",
            "기타"
    };

    static int[] colors = {
            0xff32a8b8,
            0xffba0c00,
            0xffeb8e02,
            0xff02b302
    };

    private String id;
    private Image img;
    private LocalDate date;
    private String name;
    private String description;
    private int tag;
    private String amount;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate name) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = this.description;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    ConsumptionListItem (String id, Image img, LocalDate date, String name, String description, int tag, String amount) {
        this.id = id;
        this.img = img;
        this.date = date;
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.amount = amount;
    }
}
