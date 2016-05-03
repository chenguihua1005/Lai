package com.softtek.lai.module.mygrades.presenter;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public interface IGradesPresenter {
    //2.19.1	我的成绩
    void getStepCount();

    //2.19.3	当日排名
    void getCurrentDateOrder(int RGId);

    //2.19.4	当周排名
    void getCurrentWeekOrder();  //int RGId

    //2.19.2	勋章详情页
    void getStepHonor();
}
