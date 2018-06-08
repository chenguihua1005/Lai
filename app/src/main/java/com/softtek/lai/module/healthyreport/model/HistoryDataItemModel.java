package com.softtek.lai.module.healthyreport.model;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataItemModel {

    private boolean isChecked=false;
    private boolean isShow=false;
    private HistoryDataModel.RecordsBean dataModel;

    public HistoryDataItemModel() {
    }

    public HistoryDataItemModel(boolean isChecked, boolean isShow, HistoryDataModel.RecordsBean dataModel) {
        this.isChecked = isChecked;
        this.isShow = isShow;
        this.dataModel = dataModel;
    }

    public HistoryDataModel.RecordsBean getDataModel() {
        return dataModel;
    }

    public void setDataModel(HistoryDataModel.RecordsBean dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
