package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jarvis.liu on 3/31/2016.
 */
public class StudentHonorInfo {
    private String HonorType;       //奖章类型	0:减重奖章,1:复测奖章,2:月冠军,3:全国排名
    private String HonorName;       //奖章名称
    private String CreateDate;       //时间
    private String HonorStatus;       //是否点亮	True:点亮，False:未点亮
    private String Value;       //奖章级别	   例：减重奖章:5,10,15
                                                //复测奖章:1,2,3
                                                //月冠军:1,2,3….12
                                                //全国排名:名次


    public StudentHonorInfo(String honorType, String honorName, String honorStatus, String value) {
        HonorType = honorType;
        HonorName = honorName;
        HonorStatus = honorStatus;
        Value = value;
    }

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

    public String getHonorStatus() {
        return HonorStatus;
    }

    public void setHonorStatus(String honorStatus) {
        HonorStatus = honorStatus;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public StudentHonorInfo(String honorType, String honorName, String createDate, String honorStatus, String value) {
        HonorType = honorType;
        HonorName = honorName;
        CreateDate = createDate;
        HonorStatus = honorStatus;
        Value = value;
    }

    @Override
    public String toString() {
        return "StudentHonorInfo{" +
                "HonorType='" + HonorType + '\'' +
                ", HonorName='" + HonorName + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", HonorStatus='" + HonorStatus + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }

    public StudentHonorInfo(String honorType, String value) {
        HonorType = honorType;
        Value = value;
    }

}
