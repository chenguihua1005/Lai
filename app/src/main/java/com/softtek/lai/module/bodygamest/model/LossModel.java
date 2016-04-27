/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.model;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class LossModel {
    private String content;

    @Override
    public String toString() {
        return "LossModel{" +
                "content='" + content + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
