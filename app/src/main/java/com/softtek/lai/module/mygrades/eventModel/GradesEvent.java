package com.softtek.lai.module.mygrades.eventModel;

import com.softtek.lai.module.mygrades.model.DayRankModel;

import java.util.List;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public class GradesEvent {
    private List<DayRankModel> dayRankModels;

    public GradesEvent(List<DayRankModel> dayRankModels) {
        this.dayRankModels = dayRankModels;
    }

    public List<DayRankModel> getDayRankModels() {
        return dayRankModels;
    }

    public void setDayRankModels(List<DayRankModel> dayRankModels) {
        this.dayRankModels = dayRankModels;
    }
}
