package com.softtek.lai.module.bodygame3.more.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 邀请人列表模型
 * @author jerry.Guan
 *         created by 2016/11/21
 */

public class InvitatedContact implements Parcelable{


    private String MessageId;
    private long InviterId;
    private String InviterPhoto;
    private String InviterUserName;
    private String InviterMobile;
    private String InviterCertification;
    private String JoinGroupName;
    private String JoinGroupId;
    private int ClassRole;
    private int InviterStatus;
    private int Target;

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public InvitatedContact() {
    }

    protected InvitatedContact(Parcel in) {
        MessageId = in.readString();
        InviterId = in.readLong();
        InviterPhoto = in.readString();
        InviterUserName = in.readString();
        InviterMobile = in.readString();
        InviterCertification = in.readString();
        JoinGroupName = in.readString();
        JoinGroupId = in.readString();
        ClassRole = in.readInt();
        InviterStatus = in.readInt();
    }

    public static final Creator<InvitatedContact> CREATOR = new Creator<InvitatedContact>() {
        @Override
        public InvitatedContact createFromParcel(Parcel in) {
            return new InvitatedContact(in);
        }

        @Override
        public InvitatedContact[] newArray(int size) {
            return new InvitatedContact[size];
        }
    };

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String MessageId) {
        this.MessageId = MessageId;
    }

    public long getInviterId() {
        return InviterId;
    }

    public void setInviterId(long InviterId) {
        this.InviterId = InviterId;
    }

    public String getInviterPhoto() {
        return InviterPhoto;
    }

    public void setInviterPhoto(String InviterPhoto) {
        this.InviterPhoto = InviterPhoto;
    }

    public String getInviterUserName() {
        return InviterUserName;
    }

    public void setInviterUserName(String InviterUserName) {
        this.InviterUserName = InviterUserName;
    }

    public String getInviterMobile() {
        return InviterMobile;
    }

    public void setInviterMobile(String InviterMobile) {
        this.InviterMobile = InviterMobile;
    }

    public String getInviterCertification() {
        return InviterCertification;
    }

    public void setInviterCertification(String InviterCertification) {
        this.InviterCertification = InviterCertification;
    }

    public String getJoinGroupName() {
        return JoinGroupName;
    }

    public void setJoinGroupName(String JoinGroupName) {
        this.JoinGroupName = JoinGroupName;
    }

    public String getJoinGroupId() {
        return JoinGroupId;
    }

    public void setJoinGroupId(String JoinGroupId) {
        this.JoinGroupId = JoinGroupId;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int ClassRole) {
        this.ClassRole = ClassRole;
    }

    public int getInviterStatus() {
        return InviterStatus;
    }

    public void setInviterStatus(int InviterStatus) {
        this.InviterStatus = InviterStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(MessageId);
        parcel.writeLong(InviterId);
        parcel.writeString(InviterPhoto);
        parcel.writeString(InviterUserName);
        parcel.writeString(InviterMobile);
        parcel.writeString(InviterCertification);
        parcel.writeString(JoinGroupName);
        parcel.writeString(JoinGroupId);
        parcel.writeInt(ClassRole);
        parcel.writeInt(InviterStatus);
    }
}
