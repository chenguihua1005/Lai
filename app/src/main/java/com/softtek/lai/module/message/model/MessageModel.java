/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessageModel implements Serializable {

    private List<MeasureRemindInfo> MeasureRemind;
    private List<MessageDetailInfo> SPInviteSR;
    private List<MessageDetailInfo> SRandPCApply;
    private List<MessageDetailInfo> PCJoin;

    public List<MeasureRemindInfo> getMeasureRemind() {
        return MeasureRemind;
    }

    public void setMeasureRemind(List<MeasureRemindInfo> measureRemind) {
        MeasureRemind = measureRemind;
    }

    public List<MessageDetailInfo> getSPInviteSR() {
        return SPInviteSR;
    }

    public void setSPInviteSR(List<MessageDetailInfo> SPInviteSR) {
        this.SPInviteSR = SPInviteSR;
    }

    public List<MessageDetailInfo> getSRandPCApply() {
        return SRandPCApply;
    }

    public void setSRandPCApply(List<MessageDetailInfo> SRandPCApply) {
        this.SRandPCApply = SRandPCApply;
    }

    public List<MessageDetailInfo> getPCJoin() {
        return PCJoin;
    }

    public void setPCJoin(List<MessageDetailInfo> PCJoin) {
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
