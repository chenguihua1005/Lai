package com.softtek.lai.module.sport.model;

/**
 * Created by John on 2016/6/2.
 * 运动数据提交
 */
public class SportData {

    private long AccountId;
    private String TimeLength;//时长
    private int total;//总步数
    private String speed;//速度
    private String Calories;//卡路里
    private String Kilometre;//公里
    private int MType;//运动类型
    private String Trajectory;//轨迹json字符串

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }

    public String getTimeLength() {
        return TimeLength;
    }

    public void setTimeLength(String timeLength) {
        TimeLength = timeLength;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getKilometre() {
        return Kilometre;
    }

    public void setKilometre(String kilometre) {
        Kilometre = kilometre;
    }

    public int getMType() {
        return MType;
    }

    public void setMType(int MType) {
        this.MType = MType;
    }

    public String getTrajectory() {
        return Trajectory;
    }

    public void setTrajectory(String trajectory) {
        Trajectory = trajectory;
    }

    @Override
    public String toString() {
        return "SportData{" +
                "AccountId=" + AccountId +
                ", TimeLength='" + TimeLength + '\'' +
                ", total=" + total +
                ", speed='" + speed + '\'' +
                ", Calories='" + Calories + '\'' +
                ", Kilometre='" + Kilometre + '\'' +
                ", MType=" + MType +
                ", Trajectory='" + Trajectory + '\'' +
                '}';
    }
}
