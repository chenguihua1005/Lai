package com.softtek.lai.module.historydate.model;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataItemModel {

    private boolean isChecked;
    private boolean isShow;
    private HistoryDataModel dataModel;

    public HistoryDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(HistoryDataModel dataModel) {
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
