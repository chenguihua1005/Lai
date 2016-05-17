package com.softtek.lai.module.bodygamest.model;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class GifModel {

    private String gifname;

    @Override
    public String toString() {
        return "GifModel{" +
                "gifname='" + gifname + '\'' +
                '}';
    }

    public String getGifname() {
        return gifname;
    }

    public void setGifname(String gifname) {
        this.gifname = gifname;
    }
}
