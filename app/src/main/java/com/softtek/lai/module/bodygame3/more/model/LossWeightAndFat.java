package com.softtek.lai.module.bodygame3.more.model;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2016/11/27
 */

public class LossWeightAndFat {


    private int WeightLevel;
    private String IsHasClass;
    private int FatLevel;
    private List<LossFatLevel> LossFatLevelList;
    private List<LossWeightLevel> LossWeightLevelList;

    public String getIsHasClass() {
        return IsHasClass;
    }

    public void setIsHasClass(String isHasClass) {
        IsHasClass = isHasClass;
    }

    public int getWeightLevel() {
        return WeightLevel;
    }

    public void setWeightLevel(int WeightLevel) {
        this.WeightLevel = WeightLevel;
    }

    public int getFatLevel() {
        return FatLevel;
    }

    public void setFatLevel(int FatLevel) {
        this.FatLevel = FatLevel;
    }

    public List<LossFatLevel> getLossFatLevelList() {
        return LossFatLevelList;
    }

    public void setLossFatLevelList(List<LossFatLevel> LossFatLevelList) {
        this.LossFatLevelList = LossFatLevelList;
    }

    public List<LossWeightLevel> getLossWeightLevelList() {
        return LossWeightLevelList;
    }

    public void setLossWeightLevelList(List<LossWeightLevel> LossWeightLevelList) {
        this.LossWeightLevelList = LossWeightLevelList;
    }

    public static class LossFatLevel {
        /**
         * FatReachCount : 0
         * Level : 1
         */

        private int FatReachCount;
        private int Level;


        public int getFatReachCount() {
            return FatReachCount;
        }

        public void setFatReachCount(int FatReachCount) {
            this.FatReachCount = FatReachCount;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }
    }

    public static class LossWeightLevel {
        /**
         * WeightReachCount : 1
         * Level : 1
         */

        private int WeightReachCount;
        private int Level;

        public int getWeightReachCount() {
            return WeightReachCount;
        }

        public void setWeightReachCount(int WeightReachCount) {
            this.WeightReachCount = WeightReachCount;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }
    }
}
