package com.softtek.lai.module.historydate.model;

/**
 * Created by jarvis.liu on 3/31/2016.
 */
public class HistoryHonorInfo {
    private String HonorType;       //奖章类型	0:减重奖章,1:复测奖章,2:月冠军,3:全国排名
    private String HonorName;       //奖章名称
    private String CreateDate;       //时间
    private String Value;       //奖章级别	   例：减重奖章:5,10,15
                                                //复测奖章:1,2,3
                                                //月冠军:1,2,3….12
                                                //全国排名:名次

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getHonorType() {
        return HonorType;
    }

    public void setHonorType(String honorType) {
        HonorType = honorType;
    }

    public String getHonorName() {
        return HonorName;
    }

    public void setHonorName(String honorName) {
        HonorName = honorName;
    }


    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "HistoryHonorInfo{" +
                "HonorType='" + HonorType + '\'' +
                ", HonorName='" + HonorName + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
