/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NoticeModel implements Parcelable{
    private String Msgtype;
    private String Msgid;
    private String MsgContent;
    private String SendTime;
    private String IsRead;
    private boolean isSelected;

    public NoticeModel() {
    }

    protected NoticeModel(Parcel in) {
        Msgtype = in.readString();
        Msgid = in.readString();
        MsgContent = in.readString();
        SendTime = in.readString();
        IsRead = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<NoticeModel> CREATOR = new Creator<NoticeModel>() {
        @Override
        public NoticeModel createFromParcel(Parcel in) {
            return new NoticeModel(in);
        }

        @Override
        public NoticeModel[] newArray(int size) {
            return new NoticeModel[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean select) {
        isSelected = select;
    }

    public String getMsgtype() {
        return Msgtype;
    }

    public void setMsgtype(String msgtype) {
        Msgtype = msgtype;
    }

    public String getMsgid() {
        return Msgid;
    }

    public void setMsgid(String msgid) {
        Msgid = msgid;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Msgtype);
        parcel.writeString(Msgid);
        parcel.writeString(MsgContent);
        parcel.writeString(SendTime);
        parcel.writeString(IsRead);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
