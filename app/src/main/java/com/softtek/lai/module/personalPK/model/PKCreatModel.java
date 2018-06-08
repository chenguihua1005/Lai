package com.softtek.lai.module.personalPK.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 5/9/2016.
 * 创建PK赛表单
 */
public class PKCreatModel implements Parcelable{

    private long Challenged;//发起挑战人id
    private long BeChallenged;//接受人id
    private String Start;//开始日期
    private String End;//结束日期
    private int ChipType;//筹码类型
    private String Chip;//自定义筹码时用，筹码内容
    private int TargetType;//挑战规则类型
    private double Target;//目标值
    private String userName;
    private String beUserName;
    private String userPhoto;
    private String beUserPhoto;

    public PKCreatModel() {
    }

    protected PKCreatModel(Parcel in) {
        Challenged = in.readLong();
        BeChallenged = in.readLong();
        Start = in.readString();
        End = in.readString();
        ChipType = in.readInt();
        Chip = in.readString();
        TargetType = in.readInt();
        Target = in.readDouble();
        userName = in.readString();
        beUserName = in.readString();
        userPhoto = in.readString();
        beUserPhoto = in.readString();
    }

    public static final Creator<PKCreatModel> CREATOR = new Creator<PKCreatModel>() {
        @Override
        public PKCreatModel createFromParcel(Parcel in) {
            return new PKCreatModel(in);
        }

        @Override
        public PKCreatModel[] newArray(int size) {
            return new PKCreatModel[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBeUserName() {
        return beUserName;
    }

    public void setBeUserName(String beUserName) {
        this.beUserName = beUserName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getBeUserPhoto() {
        return beUserPhoto;
    }

    public void setBeUserPhoto(String beUserPhoto) {
        this.beUserPhoto = beUserPhoto;
    }

    public long getChallenged() {
        return Challenged;
    }

    public void setChallenged(long challenged) {
        Challenged = challenged;
    }

    public long getBeChallenged() {
        return BeChallenged;
    }

    public void setBeChallenged(long beChallenged) {
        BeChallenged = beChallenged;
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

    public int getChipType() {
        return ChipType;
    }

    public void setChipType(int chipType) {
        ChipType = chipType;
    }

    public String getChip() {
        return Chip;
    }

    public void setChip(String chip) {
        Chip = chip;
    }

    public int getTargetType() {
        return TargetType;
    }

    public void setTargetType(int targetType) {
        TargetType = targetType;
    }

    public double getTarget() {
        return Target;
    }

    public void setTarget(double target) {
        Target = target;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Challenged);
        dest.writeLong(BeChallenged);
        dest.writeString(Start);
        dest.writeString(End);
        dest.writeInt(ChipType);
        dest.writeString(Chip);
        dest.writeInt(TargetType);
        dest.writeDouble(Target);
        dest.writeString(userName);
        dest.writeString(beUserName);
        dest.writeString(userPhoto);
        dest.writeString(beUserPhoto);
    }

    @Override
    public String toString() {
        return "PKCreatModel{" +
                "Challenged=" + Challenged +
                ", BeChallenged=" + BeChallenged +
                ", Start='" + Start + '\'' +
                ", End='" + End + '\'' +
                ", ChipType=" + ChipType +
                ", Chip='" + Chip + '\'' +
                ", TargetType=" + TargetType +
                ", Target=" + Target +
                ", userName='" + userName + '\'' +
                ", beUserName='" + beUserName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", beUserPhoto='" + beUserPhoto + '\'' +
                '}';
    }
}
