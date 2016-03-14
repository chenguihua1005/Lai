package com.softtek.lai.module.login.model;

import java.io.Serializable;

import zilla.libcore.db.Id;
import zilla.libcore.db.Table;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class User implements Serializable{

    private String token;

    private String userName;

    private String nick;
    private String age;
    private String sex;
    private String weight;
    private String height;
    private String phone;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", nick='" + nick + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
