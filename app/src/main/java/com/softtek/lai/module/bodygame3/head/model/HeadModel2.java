package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 11/21/2016.
 */

public class HeadModel2 {
//    "TotalPerson": 12,
//            "TotalLossWeight": 107,
//            "ThemePic": "tgs_banner.png"

    private int TotalPerson;//总人数
    private int TotalLossWeight;//总减重
    private String ThemePic;//背景图

    public HeadModel2(int totalPerson, int totalLossWeight, String themePic) {
        TotalPerson = totalPerson;
        TotalLossWeight = totalLossWeight;
        ThemePic = themePic;
    }

    public int getTotalPerson() {
        return TotalPerson;
    }

    public void setTotalPerson(int totalPerson) {
        TotalPerson = totalPerson;
    }

    public int getTotalLossWeight() {
        return TotalLossWeight;
    }

    public void setTotalLossWeight(int totalLossWeight) {
        TotalLossWeight = totalLossWeight;
    }

    public String getThemePic() {
        return ThemePic;
    }

    public void setThemePic(String themePic) {
        ThemePic = themePic;
    }
}
