package com.softtek.lai.module.personalPK.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 2016/5/8.
 */
public class PKDetailMold implements Parcelable{

    private long PKId;
    private int TargetType;//目标类型
    private String Target;//目标
    private String Start;
    private String End;
    private int ChipType;//筹码类型
    private String Chip;//自定义筹码内容
    private int Status;//pk参与者状态
    //发起挑战者
    private long Challenged;
    private String UserName;
    private String Mobile;
    private String Photo;
    private int chpcou;//赞点数
    private long chaTotal;//总步数
    //被挑战者
    private long BeChallenged;
    private String BUserName;
    private String BMobile;
    private String BPhoto;
    private int Bchpcou;
    private long BchaTotal;
    //是否可以被点咱
    //0可以1不可以
    private int BPraiseStatus;
    private int PraiseStatus;
    private int TStatus;
    private String WinnerId;

    public int getTStatus() {
        return TStatus;
    }

    public void setTStatus(int TStatus) {
        this.TStatus = TStatus;
    }

    public String getWinnerId() {
        return WinnerId;
    }

    public void setWinnerId(String winnerId) {
        WinnerId = winnerId;
    }

    public PKDetailMold() {
    }

    protected PKDetailMold(Parcel in) {
        PKId = in.readLong();
        TargetType = in.readInt();
        Target = in.readString();
        Start = in.readString();
        End = in.readString();
        ChipType = in.readInt();
        Chip = in.readString();
        Status = in.readInt();
        Challenged = in.readLong();
        UserName = in.readString();
        Mobile = in.readString();
        Photo = in.readString();
        chpcou = in.readInt();
        chaTotal = in.readLong();
        BeChallenged = in.readLong();
        BUserName = in.readString();
        BMobile = in.readString();
        BPhoto = in.readString();
        Bchpcou = in.readInt();
        BchaTotal = in.readLong();
        BPraiseStatus=in.readInt();
        PraiseStatus=in.readInt();
    }

    public static final Creator<PKDetailMold> CREATOR = new Creator<PKDetailMold>() {
        @Override
        public PKDetailMold createFromParcel(Parcel in) {
            return new PKDetailMold(in);
        }

        @Override
        public PKDetailMold[] newArray(int size) {
            return new PKDetailMold[size];
        }
    };

    public long getPKId() {
        return PKId;
    }

    public void setPKId(long PKId) {
        this.PKId = PKId;
    }

    public int getTargetType() {
        return TargetType;
    }

    public void setTargetType(int targetType) {
        TargetType = targetType;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public long getChallenged() {
        return Challenged;
    }

    public void setChallenged(long challenged) {
        Challenged = challenged;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getChpcou() {
        return chpcou;
    }

    public void setChpcou(int chpcou) {
        this.chpcou = chpcou;
    }

    public long getChaTotal() {
        return chaTotal;
    }

    public void setChaTotal(long chaTotal) {
        this.chaTotal = chaTotal;
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

    public int getBchpcou() {
        return Bchpcou;
    }

    public void setBchpcou(int bchpcou) {
        Bchpcou = bchpcou;
    }

    public long getBchaTotal() {
        return BchaTotal;
    }

    public void setBchaTotal(long bchaTotal) {
        BchaTotal = bchaTotal;
    }

    public int getBPraiseStatus() {
        return BPraiseStatus;
    }

    public void setBPraiseStatus(int BPraiseStatus) {
        this.BPraiseStatus = BPraiseStatus;
    }

    public int getPraiseStatus() {
        return PraiseStatus;
    }

    public void setPraiseStatus(int praiseStatus) {
        PraiseStatus = praiseStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(PKId);
        dest.writeInt(TargetType);
        dest.writeString(Target);
        dest.writeString(Start);
        dest.writeString(End);
        dest.writeInt(ChipType);
        dest.writeString(Chip);
        dest.writeInt(Status);
        dest.writeLong(Challenged);
        dest.writeString(UserName);
        dest.writeString(Mobile);
        dest.writeString(Photo);
        dest.writeInt(chpcou);
        dest.writeLong(chaTotal);
        dest.writeLong(BeChallenged);
        dest.writeString(BUserName);
        dest.writeString(BMobile);
        dest.writeString(BPhoto);
        dest.writeInt(Bchpcou);
        dest.writeLong(BchaTotal);
        dest.writeInt(BPraiseStatus);
        dest.writeInt(PraiseStatus);
    }

    @Override
    public String toString() {
        return "PKDetailMold{" +
                "PKId=" + PKId +
                ", TargetType=" + TargetType +
                ", Target='" + Target + '\'' +
                ", Start='" + Start + '\'' +
                ", End='" + End + '\'' +
                ", ChipType=" + ChipType +
                ", Chip='" + Chip + '\'' +
                ", Status=" + Status +
                ", Challenged=" + Challenged +
                ", UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Photo='" + Photo + '\'' +
                ", chpcou=" + chpcou +
                ", chaTotal=" + chaTotal +
                ", BeChallenged=" + BeChallenged +
                ", BUserName='" + BUserName + '\'' +
                ", BMobile='" + BMobile + '\'' +
                ", BPhoto='" + BPhoto + '\'' +
                ", Bchpcou=" + Bchpcou +
                ", BchaTotal=" + BchaTotal +
                ", BPraiseStatus=" + BPraiseStatus +
                ", PraiseStatus=" + PraiseStatus +
                ", TStatus=" + TStatus +
                ", WinnerId='" + WinnerId + '\'' +
                '}';
    }
}
