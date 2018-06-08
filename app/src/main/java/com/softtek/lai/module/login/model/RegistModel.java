/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-16
 */

package com.softtek.lai.module.login.model;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class RegistModel {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegistModel{" +
                "token='" + token + '\'' +
                '}';
    }
}
