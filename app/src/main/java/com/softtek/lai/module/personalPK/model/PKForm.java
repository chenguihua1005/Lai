package com.softtek.lai.module.personalPK.model;

/**
 * Created by jerry.guan on 5/9/2016.
 * 创建PK赛表单
 */
public class PKForm {

    private long Challenged;//发起挑战人id
    private long BeChallenged;//接受人id
    private String Start;//开始日期
    private String End;//结束日期
    private int ChipType;//筹码类型
    private String Chip;//自定义筹码时用，筹码内容
    private int TargetType;//挑战规则类型
    private double Target;//目标值

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
    public String toString() {
        return "PKForm{" +
                "Challenged=" + Challenged +
                ", BeChallenged=" + BeChallenged +
                ", Start='" + Start + '\'' +
                ", End='" + End + '\'' +
                ", ChipType=" + ChipType +
                ", Chip='" + Chip + '\'' +
                ", TargetType=" + TargetType +
                ", Target=" + Target +
                '}';
    }
}
