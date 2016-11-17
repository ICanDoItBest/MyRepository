package com.fourapeteam.snacksmall.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/10/31.
 */
public class HomeCommodityBean {
    private Bitmap bitmap;
    private String name;
    private String price;

    public HomeCommodityBean(Bitmap bitmap, String price, String name) {
        this.bitmap = bitmap;
        this.price = price;
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "HomeCommodityBean{" +
                "bitmap=" + bitmap +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
