package com.softtek.lai.module.ranking.model;

/**
 * @author jerry.Guan
 * @date 2016/10/22
 */

public class OrderInfo {

    private String orderInfo;
    private String steps;
    private String IsPrasie;
    private String PrasieNum;
    private String AcStepGuid;

    public String getAcStepGuid() {
        return AcStepGuid;
    }

    public void setAcStepGuid(String acStepGuid) {
        AcStepGuid = acStepGuid;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
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
