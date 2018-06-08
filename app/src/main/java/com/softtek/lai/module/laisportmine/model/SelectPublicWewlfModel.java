package com.softtek.lai.module.laisportmine.model;

import com.softtek.lai.module.message2.model.NoticeModel;

/**
 * Created by lareina.qiao on 5/11/2016.
 */
public class SelectPublicWewlfModel {
    private NoticeModel publicWewlfModel;
    private boolean isSelect;

    public SelectPublicWewlfModel(NoticeModel publicWewlfModel, boolean isSelect) {
        this.publicWewlfModel = publicWewlfModel;
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "SelectPublicWewlfModel{" +
                "publicWewlfModel=" + publicWewlfModel +
                ", isSelect=" + isSelect +
                '}';
    }

    public NoticeModel getPublicWewlfModel() {
        return publicWewlfModel;
    }

    public void setPublicWewlfModel(NoticeModel publicWewlfModel) {
        this.publicWewlfModel = publicWewlfModel;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
