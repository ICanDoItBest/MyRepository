package com.fourapeteam.snacksmall.bean;

/**
 * Created by Administrator on 2016/11/3.
 */
public class HomeMenuBean {
    private int id;
    private String title;
    private String desc;
    private String img_url;

    public HomeMenuBean(int id, String title, String desc, String img_url) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "HomeMenuBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
