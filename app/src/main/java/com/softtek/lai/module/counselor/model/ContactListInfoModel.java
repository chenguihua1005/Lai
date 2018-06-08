/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ContactListInfoModel implements Serializable ,Cloneable{

    private String Mobile;     //助教电话
    private String UserName;     //助教名字

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

    public ContactListInfoModel(String userName, String mobile) {
        UserName = userName;
        Mobile = mobile;
    }

    public ContactListInfoModel() {
    }

    @Override
    public String toString() {
        return "ContactListInfoModel{" +
                "Mobile='" + Mobile + '\'' +
                ", UserName='" + UserName + '\'' +
                '}';
    }

    @Override
    public ContactListInfoModel clone() {
        ContactListInfoModel model;
        try {
            model= (ContactListInfoModel) super.clone();
        } catch (CloneNotSupportedException e) {
            model=new ContactListInfoModel();
        }
        return model;
    }
}
