package com.softtek.lai.module.personalPK.model;

/**
 * Created by John on 2016/5/8.
 */
public class PKDetailMold {

    private long PKId;
    private int TargetType;//目标类型
    private String Target;//目标
    private String Start;
    private String End;
    private int ChipType;//筹码类型
    private String Chip;//自定义筹码内容
    private int Status;//pk状态
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
}
