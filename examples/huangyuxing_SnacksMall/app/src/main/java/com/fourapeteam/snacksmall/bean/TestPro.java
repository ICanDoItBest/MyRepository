package com.fourapeteam.snacksmall.bean;

import android.graphics.Bitmap;

/**
 * Created by jun on 2016/10/31.
 */
public class TestPro {
    Bitmap bitmap;
    String label;

    public TestPro(Bitmap bitmap, String label) {
        this.bitmap = bitmap;
        this.label = label;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "TestPro{" +
                "bitmap=" + bitmap +
                ", label='" + label + '\'' +
                '}';
    }
}
