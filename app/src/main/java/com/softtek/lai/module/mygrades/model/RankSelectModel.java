package com.softtek.lai.module.mygrades.model;

/**
 * Created by julie.zhu on 5/18/2016.
 */
public class RankSelectModel {
    private String name;

    public RankSelectModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RankSelectModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
