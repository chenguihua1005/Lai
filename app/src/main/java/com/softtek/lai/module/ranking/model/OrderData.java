package com.softtek.lai.module.ranking.model;

/**
 * Created by jerry.guan on 10/18/2016.
 */
public class OrderData {

    private String AcStepGuid;
    private String AccountId;
    private String Photo;
    private String _order;
    private String mobile;
    private String stepCount;
    private String userName;
    private String IsPrasie;//--当前用户是否点赞		0：未点赞
    private String PrasieNum;//--点赞数

    public String getAcStepGuid() {
        return AcStepGuid;
    }

    public void setAcStepGuid(String acStepGuid) {
        AcStepGuid = acStepGuid;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String get_order() {
        return _order;
    }

    public void set_order(String _order) {
        this._order = _order;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStepCount() {
        return stepCount;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsPrasie() {
        return IsPrasie;
    }

    public void setIsPrasie(String isPrasie) {
        IsPrasie = isPrasie;
    }

    public String getPrasieNum() {
        return PrasieNum;
    }

    public void setPrasieNum(String prasieNum) {
        PrasieNum = prasieNum;
    }
}
