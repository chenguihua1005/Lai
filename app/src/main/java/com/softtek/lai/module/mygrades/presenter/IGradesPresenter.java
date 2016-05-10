package com.softtek.lai.module.mygrades.presenter;

import org.joda.time.DateTime;
import org.joda.time.field.ScaledDurationField;

import java.util.Date;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public interface IGradesPresenter {
    //2.19.1	我的成绩
    void getStepCount(DateTime start,DateTime end);

//    //2.19.2	勋章详情页
//    void getStepHonor();
}
