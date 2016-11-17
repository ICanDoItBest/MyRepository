package com.fourapeteam.snacksmall.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MenuBean {

    private Bitmap bitmap;
    private String name;
    private String type;

    public MenuBean(Bitmap bitmap, String name, String type) {
        this.bitmap = bitmap;
        this.name = name;
        this.type = type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MenuBean{" +
                "bitmap=" + bitmap +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
