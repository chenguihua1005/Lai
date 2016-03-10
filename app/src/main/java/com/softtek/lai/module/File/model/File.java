package com.softtek.lai.module.File.model;

/**
 * Created by julie.zhu on 3/4/2016.
 */
public class File {
     private String nickname; //昵称
     private String birthday; //生日yyyy-MM-dd
     private int height;      // 身高
     private int weight;      //体重
     private int gender;      //性别 :gender(int 0女，1男)

    public File(String nickname, String birthday, int height, int weight, int gender) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirthday() {
        return birthday;
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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
        return "File{" +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", gender=" + gender +
                '}';
    }
}
