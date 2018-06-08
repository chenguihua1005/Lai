/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.File.model;

/**
 * Created by julie.zhu on 3/16/2016.
 */
public class FilterModel {

    private String nick;


    public FilterModel() {

    }

    public FilterModel(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterModel filter = (FilterModel) o;

        return nick != null ? nick.equals(filter.nick) : filter.nick == null;

    }

    @Override
    public int hashCode() {
        return nick != null ? nick.hashCode() : 0;
    }
}
