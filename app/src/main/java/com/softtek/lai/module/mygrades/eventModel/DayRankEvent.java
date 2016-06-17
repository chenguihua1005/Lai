package com.softtek.lai.module.mygrades.eventModel;

import com.softtek.lai.module.mygrades.model.DayRankModel;

import java.util.List;

/**
 * Created by julie.zhu on 5/3/2016.
 * 当日排名
 */
public class DayRankEvent {

    private List<DayRankModel> dayRankModels;

    public DayRankEvent(List<DayRankModel> dayRankModels) {
        this.dayRankModels = dayRankModels;
    }

    public List<DayRankModel> getDayRankModels() {
        return dayRankModels;
    }

    public void setDayRankModels(List<DayRankModel> dayRankModels) {
        this.dayRankModels = dayRankModels;
    }
}
