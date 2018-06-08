package com.softtek.lai.module.personalPK.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class PKListModel implements Parcelable{

    private long PKId;
    //发起挑战者
    private int ChP;
    private long Challenged;
    private String Mobile;
    private String UserName;
    private String Photo;
    private int PraiseStatus;
    //接受挑战者
    private long BeChallenged;
    private String BUserName;
    private String BMobile;
    private String BPhoto;
    private int BChp;
    private int BPraiseStatus;

    private int ChipType;
    private int TStatus;
    private String Start;
    private String End;
    private String WinnerId;


    public String getWinnerId() {
        return WinnerId;
    }

    public void setWinnerId(String winnerId) {
        WinnerId = winnerId;
    }

    public PKListModel() {
    }

    protected PKListModel(Parcel in) {
        PKId = in.readLong();
        ChP = in.readInt();
        Challenged = in.readLong();
        Mobile = in.readString();
        UserName = in.readString();
        Photo = in.readString();
        BeChallenged = in.readLong();
        BUserName = in.readString();
        BMobile = in.readString();
        BPhoto = in.readString();
        BChp = in.readInt();
        ChipType = in.readInt();
        TStatus = in.readInt();
        Start = in.readString();
        End = in.readString();
        WinnerId = in.readString();
        BPraiseStatus=in.readInt();
        PraiseStatus=in.readInt();
    }

    public static final Creator<PKListModel> CREATOR = new Creator<PKListModel>() {
        @Override
        public PKListModel createFromParcel(Parcel in) {
            return new PKListModel(in);
        }

        @Override
        public PKListModel[] newArray(int size) {
            return new PKListModel[size];
        }
    };

    public int getTStatus() {
        return TStatus;
    }

    public void setTStatus(int TStatus) {
        this.TStatus = TStatus;
    }

    public long getPKId() {
        return PKId;
    }

    public void setPKId(long PKId) {
        this.PKId = PKId;
    }

    public int getChP() {
        return ChP;
    }

    public void setChP(int chP) {
        ChP = chP;
    }

    public long getChallenged() {
        return Challenged;
    }

    public void setChallenged(long challenged) {
        Challenged = challenged;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public long getBeChallenged() {
        return BeChallenged;
    }

    public void setBeChallenged(long beChallenged) {
        BeChallenged = beChallenged;
    }

    public String getBUserName() {
        return BUserName;
    }

    public void setBUserName(String BUserName) {
        this.BUserName = BUserName;
    }

    public String getBMobile() {
        return BMobile;
    }

    public void setBMobile(String BMobile) {
        this.BMobile = BMobile;
    }

    public String getBPhoto() {
        return BPhoto;
    }

    public void setBPhoto(String BPhoto) {
        this.BPhoto = BPhoto;
    }

    public int getBChp() {
        return BChp;
    }

    public void setBChp(int BChp) {
        this.BChp = BChp;
    }

    public int getChipType() {
        return ChipType;
    }

    public void setChipType(int chipType) {
        ChipType = chipType;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public int getPraiseStatus() {
        return PraiseStatus;
    }

    public void setPraiseStatus(int praiseStatus) {
        PraiseStatus = praiseStatus;
    }

    public int getBPraiseStatus() {
        return BPraiseStatus;
    }

    public void setBPraiseStatus(int BPraiseStatus) {
        this.BPraiseStatus = BPraiseStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(PKId);
        dest.writeInt(ChP);
        dest.writeLong(Challenged);
        dest.writeString(Mobile);
        dest.writeString(UserName);
        dest.writeString(Photo);
        dest.writeLong(BeChallenged);
        dest.writeString(BUserName);
        dest.writeString(BMobile);
        dest.writeString(BPhoto);
        dest.writeInt(BChp);
        dest.writeInt(ChipType);
        dest.writeInt(TStatus);
        dest.writeString(Start);
        dest.writeString(End);
        dest.writeString(WinnerId);
        dest.writeInt(BPraiseStatus);
        dest.writeInt(PraiseStatus);
    }
}
