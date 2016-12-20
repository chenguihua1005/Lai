package com.softtek.lai.module.bodygame3.head.model;

/**
 * Created by shelly.xu on 12/20/2016.
 */

public class ClassdataModel {
    //    "HasClass": 1   ,--0：没有班级，大于0有班级
//    “DoingClass”:0 ,--0没有进行中的班级,大于0则有进行中的班级
    private int HasClass;
    private int DoingClass;

    public int getHasClass() {
        return HasClass;
    }

    public void setHasClass(int hasClass) {
        HasClass = hasClass;
    }

    public int getDoingClass() {
        return DoingClass;
    }

    public void setDoingClass(int doingClass) {
        DoingClass = doingClass;
    }
}
