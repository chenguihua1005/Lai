package com.softtek.lai.module.historydate.model;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataItemModel {

    private boolean isChecked=false;
    private boolean isShow=false;
    private HistoryData dataModel;

    public HistoryDataItemModel() {
    }

    public HistoryDataItemModel(boolean isChecked, boolean isShow, HistoryData dataModel) {
        this.isChecked = isChecked;
        this.isShow = isShow;
        this.dataModel = dataModel;
    }

    public HistoryData getDataModel() {
        return dataModel;
    }

    public void setDataModel(HistoryData dataModel) {
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
