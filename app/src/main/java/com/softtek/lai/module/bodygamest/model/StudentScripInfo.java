package com.softtek.lai.module.bodygamest.model;

/**
 * Created by jarvis.liu on 3/31/2016.
 */
public class StudentScripInfo {
    private String RowNumber;
    private String UserName;
    private String BeforeWeight;
    private String AfterWeight;
    private String Loss;
    private String Change;
    private String Photo;

    public String getRowNumber() {
        return RowNumber;
    }

    public void setRowNumber(String rowNumber) {
        RowNumber = rowNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBeforeWeight() {
        return BeforeWeight;
    }

    public void setBeforeWeight(String beforeWeight) {
        BeforeWeight = beforeWeight;
    }

    public String getAfterWeight() {
        return AfterWeight;
    }

    public void setAfterWeight(String afterWeight) {
        AfterWeight = afterWeight;
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String loss) {
        Loss = loss;
    }

    public String getChange() {
        return Change;
    }

    public void setChange(String change) {
        Change = change;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    @Override
    public String toString() {
        return "StudentScripInfo{" +
                "RowNumber='" + RowNumber + '\'' +
                ", UserName='" + UserName + '\'' +
                ", BeforeWeight='" + BeforeWeight + '\'' +
                ", AfterWeight='" + AfterWeight + '\'' +
                ", Loss='" + Loss + '\'' +
                ", Change='" + Change + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }
}
