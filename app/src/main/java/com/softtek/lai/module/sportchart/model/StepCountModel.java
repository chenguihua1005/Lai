package com.softtek.lai.module.sportchart.model;

import java.util.List;

/**
 * Created by lareina.qiao on 10/19/2016.
 */
public class StepCountModel {
    String Username;//用户名
    String Photo;//用户头像
    String AcBanner;//跑团banner
    String TotalStep;//总步数
    List<StepListModel> StepList;
    String IsFocus;//未关注

    @Override
    public String toString() {
        return "StepCountModel{" +
                "Username='" + Username + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AcBanner='" + AcBanner + '\'' +
                ", TotalStep='" + TotalStep + '\'' +
                ", StepList=" + StepList +
                ", IsFocus='" + IsFocus + '\'' +
                '}';
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getAcBanner() {
        return AcBanner;
    }

    public void setAcBanner(String acBanner) {
        AcBanner = acBanner;
    }

    public String getTotalStep() {
        return TotalStep;
    }

    public void setTotalStep(String totalStep) {
        TotalStep = totalStep;
    }

    public List<StepListModel> getStepList() {
        return StepList;
    }

    public void setStepList(List<StepListModel> stepList) {
        StepList = stepList;
    }

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
    }
}
