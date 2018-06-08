package com.softtek.lai.module.bodygame3.more.model;

/**
 * 减重和减脂等级自定义模型
 * @author jerry.Guan
 *         created by 2016/11/28
 */

public class LevelModel {

    private int reachCount;//达标多少
    private String levelName;//级别名
    private String lossCount;//此级别减多少斤
    private int level;//级别等级

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getReachCount() {
        return reachCount;
    }

    public void setReachCount(int reachCount) {
        this.reachCount = reachCount;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLossCount() {
        return lossCount;
    }

    public void setLossCount(String lossCount) {
        this.lossCount = lossCount;
    }
}
