package com.softtek.lai.module.login.model;

import java.io.Serializable;

import zilla.libcore.db.Id;
import zilla.libcore.db.Table;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class User implements Serializable{

    private String token;

    private String userid;
    private String userrole;
    private String nickname;
    private String gender;
    private String weight;
    private String height;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                ", userrole='" + userrole + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
