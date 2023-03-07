package com.example.myapplicationtest;

import android.widget.TextView;

public class Contact {
    private int id;
    private  String images, imgphone,imgsms;
    private String name;
    private  String phone;

    public String getImgphone() {
        return imgphone;
    }

    public void setImgphone(String imgphone) {
        this.imgphone = imgphone;
    }

    public String getImgsms() {
        return imgsms;
    }

    public void setImgsms(String imgsms) {
        this.imgsms = imgsms;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check;
    public Contact(){

    }
    public Contact(int id, String images, String name, String phone, String imgphone, String imgsms) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.phone = phone;
        this.imgphone = imgphone;
        this.imgsms = imgsms;
        check = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
