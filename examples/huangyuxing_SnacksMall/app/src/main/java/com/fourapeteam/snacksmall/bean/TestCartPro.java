package com.fourapeteam.snacksmall.bean;

/**
 * Created by jun on 2016/11/2.
 */
public class TestCartPro {
    String url;
    String taste="别问用心去感受";
    String title;
    int number=1;
    int id;
    float currentPrice;
    float oldPrice;
    boolean isChecked=true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TestCartPro(float currentPrice, float oldPrice, String title, String url, int id) {
        this.oldPrice = oldPrice;
        this.currentPrice = currentPrice;
        this.title = title;
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "TestCartPro{" +
                "url='" + url + '\'' +
                ", taste='" + taste + '\'' +
                ", title='" + title + '\'' +
                ", number=" + number +
                ", currentPrice=" + currentPrice +
                ", oldPrice=" + oldPrice +
                ", isChecked=" + isChecked +
                '}';
    }
}
