package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/11/2016.
 */
public class SelectPublicWewlfModel {
    private PublicWewlfModel publicWewlfModel;
    private boolean isSelect;

    public SelectPublicWewlfModel(PublicWewlfModel publicWewlfModel, boolean isSelect) {
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

    public PublicWewlfModel getPublicWewlfModel() {
        return publicWewlfModel;
    }

    public void setPublicWewlfModel(PublicWewlfModel publicWewlfModel) {
        this.publicWewlfModel = publicWewlfModel;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
