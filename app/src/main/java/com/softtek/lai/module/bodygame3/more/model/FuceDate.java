package com.softtek.lai.module.bodygame3.more.model;

/**
 * 复测日模型
 *
 * @author jerry.Guan
 *         created by 2016/11/25
 */

public class FuceDate {

    private int WeekNum;
    private String MeasureDate;
    private String CreateTime;
    private String UpdateTime;
    private boolean CanEdit;

    public boolean isCanEdit() {
        return CanEdit;
    }

    public void setCanEdit(boolean canEdit) {
        CanEdit = canEdit;
    }

    public int getWeekNum() {
        return WeekNum;
    }

    public void setWeekNum(int WeekNum) {
        this.WeekNum = WeekNum;
    }

    public String getMeasureDate() {
        return MeasureDate;
    }

    public void setMeasureDate(String MeasureDate) {
        this.MeasureDate = MeasureDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }
}
