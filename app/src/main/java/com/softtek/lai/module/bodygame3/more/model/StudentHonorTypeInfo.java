package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jarvis.liu on 3/31/2016.
 */
public class StudentHonorTypeInfo {
/*    第一次加入班级，但班级未开始，1
    第一次加入班级，班级进行中，2
    旧班级已经结束，新班级进行中，3
    旧班级已经结束，但未加入新班级，4
    旧班级已经结束，但加入了未开班的班级，5*/

    private String IsHave;

    @Override
    public String toString() {
        return "StudentHonorTypeInfo{" +
                "IsHave='" + IsHave + '\'' +
                '}';
    }

    public String getIsHave() {
        return IsHave;
    }

    public void setIsHave(String isHave) {
        IsHave = isHave;
    }
}
