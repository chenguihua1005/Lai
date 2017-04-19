package com.softtek.lai.premission;

import java.util.List;

/**
 * Created by jerry.guan on 3/31/2016.
 * 权限
 */
public class Power {

    private boolean bodyGame;
    private boolean laiYunDong;
    private boolean laiClass;
    private boolean laiChen;
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

    public boolean isLaiClass() {
        return laiClass;
    }

    public void setLaiClass(boolean laiClass) {
        this.laiClass = laiClass;
    }

    public boolean isLaiChen() {
        return laiChen;
    }

    public void setLaiChen(boolean laiChen) {
        this.laiChen = laiChen;
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
                ", laiClass=" + laiClass +
                ", laiChen=" + laiChen +
                ", laiShop=" + laiShop +
                '}';
    }
}
