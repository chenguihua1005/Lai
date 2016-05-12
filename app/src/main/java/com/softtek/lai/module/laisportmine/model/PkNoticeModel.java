package com.softtek.lai.module.laisportmine.model;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class PkNoticeModel {
    private String BChallenged;
    private String Challenged;
    private String PKId;

    @Override
    public String toString() {
        return "PkNoticeModel{" +
                "BChallenged='" + BChallenged + '\'' +
                ", Challenged='" + Challenged + '\'' +
                ", PKId='" + PKId + '\'' +
                '}';
    }

    public String getBChallenged() {
        return BChallenged;
    }

    public void setBChallenged(String BChallenged) {
        this.BChallenged = BChallenged;
    }

    public String getChallenged() {
        return Challenged;
    }

    public void setChallenged(String challenged) {
        Challenged = challenged;
    }

    public String getPKId() {
        return PKId;
    }

    public void setPKId(String PKId) {
        this.PKId = PKId;
    }
}
