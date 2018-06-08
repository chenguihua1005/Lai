/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActZKP1Model implements Serializable {
    private String ActDImg;
    private String ActDName;
    private String ActDOrder;
    private String ActDTotal;

    @Override
    public String toString() {
        return "ActZKP1Model{" +
                "ActDImg='" + ActDImg + '\'' +
                ", ActDName='" + ActDName + '\'' +
                ", ActDOrder='" + ActDOrder + '\'' +
                ", ActDTotal='" + ActDTotal + '\'' +
                '}';
    }

    public String getActDImg() {
        return ActDImg;
    }

    public void setActDImg(String actDImg) {
        ActDImg = actDImg;
    }

    public String getActDName() {
        return ActDName;
    }

    public void setActDName(String actDName) {
        ActDName = actDName;
    }

    public String getActDOrder() {
        return ActDOrder;
    }

    public void setActDOrder(String actDOrder) {
        ActDOrder = actDOrder;
    }

    public String getActDTotal() {
        return ActDTotal;
    }

    public void setActDTotal(String actDTotal) {
        ActDTotal = actDTotal;
    }
}
