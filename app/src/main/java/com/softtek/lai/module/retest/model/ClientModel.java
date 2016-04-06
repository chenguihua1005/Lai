package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 4/6/2016.
 */
public class ClientModel {
    private String access_token;
    private String expires_in;

    @Override
    public String toString() {
        return "ClientModel{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public ClientModel(String access_token, String expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }
    public ClientModel() {

    }
}
