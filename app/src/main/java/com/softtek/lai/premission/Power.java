package com.softtek.lai.premission;

import java.util.List;

/**
 * Created by jerry.guan on 3/31/2016.
 * 权限
 */
public class Power {

    private boolean bodyGame;
    private boolean laiYunDong;
    private boolean chat;
    private boolean laiExcel;
    private boolean laiShop;

    public boolean isBodyGame() {
        return bodyGame;
    }

    public void setBodyGame(boolean bodyGame) {
        this.bodyGame = bodyGame;
    }

    public boolean isLaiYunDong() {
        return laiYunDong;
    }

    public void setLaiYunDong(boolean laiYunDong) {
        this.laiYunDong = laiYunDong;
    }

    public boolean isChat() {
        return chat;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public boolean isLaiExcel() {
        return laiExcel;
    }

    public void setLaiExcel(boolean laiExcel) {
        this.laiExcel = laiExcel;
    }

    public boolean isLaiShop() {
        return laiShop;
    }

    public void setLaiShop(boolean laiShop) {
        this.laiShop = laiShop;
    }

    @Override
    public String toString() {
        return "Power{" +
                "bodyGame=" + bodyGame +
                ", laiYunDong=" + laiYunDong +
                ", chat=" + chat +
                ", laiExcel=" + laiExcel +
                ", laiShop=" + laiShop +
                '}';
    }
}
