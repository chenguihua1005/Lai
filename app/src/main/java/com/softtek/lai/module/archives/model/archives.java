package com.softtek.lai.module.archives.model;

/**
 * Created by julie.zhu on 3/10/2016.
 */
public class archives {
    private String nickname;    //昵称
    private String brithday;    //生日yyyy-MM-dd
    private int height;         // 身高
    private int weight;         //体重
    private int gender;        //性别 :gender(int 0女，1男)

//    //可空
//    double circum;//胸围:
//    double waistline;//腰围
//    double hiplie;//臀围
//    double armgirth;//上臂围
//    double uparmgirth;//
//    double upleggirth;//大腿围
//    double doleggirth;//小腿围


    public archives(int gender, String nickname, String brithday, int height, int weight) {
        this.gender = gender;
        this.nickname = nickname;
        this.brithday = brithday;
        this.height = height;
        this.weight = weight;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBrithday() {
        return brithday;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getGender() {
        return gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "archives{" +
                "nickname='" + nickname + '\'' +
                ", brithday='" + brithday + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", gender=" + gender +
                '}';
    }
}
