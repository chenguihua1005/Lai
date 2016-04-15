/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessageModel implements Serializable {

    private ArrayList<MeasureRemindInfo> MeasureRemind;
    private ArrayList<MessageDetailInfo> SPInviteSR;
    private ArrayList<MessageDetailInfo> SRandPCApply;
    private ArrayList<MessageDetailInfo> PCJoin;

    public ArrayList<MeasureRemindInfo> getMeasureRemind() {
        return MeasureRemind;
    }

    public void setMeasureRemind(ArrayList<MeasureRemindInfo> measureRemind) {
        MeasureRemind = measureRemind;
    }

    public ArrayList<MessageDetailInfo> getSPInviteSR() {
        return SPInviteSR;
    }

    public void setSPInviteSR(ArrayList<MessageDetailInfo> SPInviteSR) {
        this.SPInviteSR = SPInviteSR;
    }

    public ArrayList<MessageDetailInfo> getSRandPCApply() {
        return SRandPCApply;
    }

    public void setSRandPCApply(ArrayList<MessageDetailInfo> SRandPCApply) {
        this.SRandPCApply = SRandPCApply;
    }

    public ArrayList<MessageDetailInfo> getPCJoin() {
        return PCJoin;
    }

    public void setPCJoin(ArrayList<MessageDetailInfo> PCJoin) {
        this.PCJoin = PCJoin;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "MeasureRemind=" + MeasureRemind +
                ", SPInviteSR=" + SPInviteSR +
                ", SRandPCApply=" + SRandPCApply +
                ", PCJoin=" + PCJoin +
                '}';
    }
}
