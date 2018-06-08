/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

import java.io.Serializable;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class SelectNoticeMsgModel implements Serializable {
    private boolean isSelect;
    private NoticeMsgModel noticeMsgModel;

    @Override
    public String toString() {
        return "SelectNoticeMsgModel{" +
                "isSelect=" + isSelect +
                ", noticeMsgModel=" + noticeMsgModel +
                '}';
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public NoticeMsgModel getNoticeMsgModel() {
        return noticeMsgModel;
    }

    public void setNoticeMsgModel(NoticeMsgModel noticeMsgModel) {
        this.noticeMsgModel = noticeMsgModel;
    }

    public SelectNoticeMsgModel(boolean isSelect, NoticeMsgModel noticeMsgModel) {

        this.isSelect = isSelect;
        this.noticeMsgModel = noticeMsgModel;
    }
}
