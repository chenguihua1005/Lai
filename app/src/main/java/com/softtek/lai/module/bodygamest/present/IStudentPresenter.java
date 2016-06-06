/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.present;

import android.widget.ListView;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface IStudentPresenter {

    //荣誉榜
    void getStudentHonor();


    void getStudentHonours();    //获取学员奖章个数

    //成绩单
    void getTranscrip(String userid, ListView list_student_score);

    //未复测周数
    void GetNotMeasuredRecordByPC(long accountId);
}
