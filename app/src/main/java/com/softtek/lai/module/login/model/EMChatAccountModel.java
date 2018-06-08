/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-16
 */

package com.softtek.lai.module.login.model;

/**
 * Created by jarvis on 3/3/2016.
 */
public class EMChatAccountModel {

    private String HXAccountId;
    private String State;

    @Override
    public String toString() {
        return "EMChatAccountModel{" +
                "HXAccountId='" + HXAccountId + '\'' +
                ", State='" + State + '\'' +
                '}';
    }

    public String getHXAccountId() {
        return HXAccountId;
    }

    public void setHXAccountId(String HXAccountId) {
        this.HXAccountId = HXAccountId;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
