package com.fourapeteam.snacksmall.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/7.
 */
public class User extends BmobObject {
    private String userName;
    private String passWord;
    private String emil;
    private String phoneNumber;

    public User(String userName, String passWord, String emil, String phoneNumber) {
        this.userName = userName;
        this.passWord = passWord;
        this.emil = emil;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmil() {
        return emil;
    }

    public void setEmil(String emil) {
        this.emil = emil;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", emil='" + emil + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
