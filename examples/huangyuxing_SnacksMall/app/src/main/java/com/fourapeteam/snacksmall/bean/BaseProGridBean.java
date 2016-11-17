package com.fourapeteam.snacksmall.bean;

/**
 * Created by qf on 2016/11/3.
 */
public class BaseProGridBean {
    String img_url;
    String title;
    double current;
    double prime;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseProGridBean(String img_url, String title, double current, double prime, int id) {
        this.img_url = img_url;
        this.title = title;
        this.current = current;
        this.prime = prime;
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getPrime() {
        return prime;
    }

    public void setPrime(double prime) {
        this.prime = prime;
    }

    @Override
    public String toString() {
        return "BaseProGridBean{" +
                "img_url='" + img_url + '\'' +
                ", title='" + title + '\'' +
                ", current=" + current +
                ", prime=" + prime +
                ", id=" + id +
                '}';
    }
}
