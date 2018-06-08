package com.softtek.lai.module.sport2.model;

/**
 * Created by jerry.guan on 10/17/2016.
 */
public class SportMineModel {

    private String Username;
    private String Photo;
    private int MoveCnt;
    private int TodayStepCnt;
    private int TodayKaluliCnt;
    private String TodayStepOdr;
    private String WeekStepOrder;
    private int DonatenNum;
    private int MedalCnt;
    private int Unreadmsg;
    private String RGName;


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getMoveCnt() {
        return MoveCnt;
    }

    public void setMoveCnt(int moveCnt) {
        MoveCnt = moveCnt;
    }

    public int getTodayStepCnt() {
        return TodayStepCnt;
    }

    public void setTodayStepCnt(int todayStepCnt) {
        TodayStepCnt = todayStepCnt;
    }

    public int getTodayKaluliCnt() {
        return TodayKaluliCnt;
    }

    public void setTodayKaluliCnt(int todayKaluliCnt) {
        TodayKaluliCnt = todayKaluliCnt;
    }

    public String getTodayStepOdr() {
        return TodayStepOdr;
    }

    public void setTodayStepOdr(String todayStepOdr) {
        TodayStepOdr = todayStepOdr;
    }

    public String getWeekStepOrder() {
        return WeekStepOrder;
    }

    public void setWeekStepOrder(String weekStepOrder) {
        WeekStepOrder = weekStepOrder;
    }

    public int getDonatenNum() {
        return DonatenNum;
    }

    public void setDonatenNum(int donatenNum) {
        DonatenNum = donatenNum;
    }

    public int getMedalCnt() {
        return MedalCnt;
    }

    public void setMedalCnt(int medalCnt) {
        MedalCnt = medalCnt;
    }

    public int getUnreadmsg() {
        return Unreadmsg;
    }

    public void setUnreadmsg(int unreadmsg) {
        Unreadmsg = unreadmsg;
    }

    public String getRGName() {
        return RGName;
    }

    public void setRGName(String RGName) {
        this.RGName = RGName;
    }

    @Override
    public String toString() {
        return "SportMineModel{" +
                "Username='" + Username + '\'' +
                ", Photo='" + Photo + '\'' +
                ", MoveCnt=" + MoveCnt +
                ", TodayStepCnt=" + TodayStepCnt +
                ", TodayKaluliCnt=" + TodayKaluliCnt +
                ", TodayStepOdr='" + TodayStepOdr + '\'' +
                ", WeekStepOrder='" + WeekStepOrder + '\'' +
                ", DonatenNum=" + DonatenNum +
                ", MedalCnt=" + MedalCnt +
                ", Unreadmsg=" + Unreadmsg +
                ", RGName='" + RGName + '\'' +
                '}';
    }
}
