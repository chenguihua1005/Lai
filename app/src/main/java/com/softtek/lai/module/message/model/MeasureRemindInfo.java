/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

import java.io.Serializable;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MeasureRemindInfo implements Serializable {

    private String RecAcId;
    private String Content;

    public String getRecAcId() {
        return RecAcId;
    }

    public void setRecAcId(String recAcId) {
        RecAcId = recAcId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "MeasureRemindInfo{" +
                "RecAcId='" + RecAcId + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
