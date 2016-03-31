/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class PeopleModel {

    private String Photo;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    @Override
    public String toString() {
        return "PeopleModel{" +
                "Photo='" + Photo + '\'' +
                '}';
    }
}
