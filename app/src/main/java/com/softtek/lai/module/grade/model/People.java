package com.softtek.lai.module.grade.model;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public class People {

    private String Photo;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    @Override
    public String toString() {
        return "People{" +
                "Photo='" + Photo + '\'' +
                '}';
    }
}
