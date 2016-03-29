package com.softtek.lai.module.assistant.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ContactListInfo implements Serializable {

    private String Mobile;     //助教电话
    private String UserName;     //助教名字
    private Bitmap Photo;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap photo) {
        Photo = photo;
    }

    public ContactListInfo(Bitmap photo, String userName, String mobile) {
        Photo = photo;
        UserName = userName;
        Mobile = mobile;
    }

    @Override
    public String toString() {
        return "ContactListInfo{" +
                "Mobile='" + Mobile + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }
}
