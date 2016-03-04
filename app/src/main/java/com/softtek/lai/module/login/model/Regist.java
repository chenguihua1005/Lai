package com.softtek.lai.module.login.model;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class Regist {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Regist{" +
                "token='" + token + '\'' +
                '}';
    }
}
