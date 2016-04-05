package com.softtek.lai.module.studetail.eventModel;

import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;

import java.util.List;

/**
 * Created by jerry.guan on 4/5/2016.
 */
public class LineChartEvent {

    private List<StudentLinChartInfoModel> models;

    public LineChartEvent(List<StudentLinChartInfoModel> models) {
        this.models = models;
    }

    public List<StudentLinChartInfoModel> getModels() {
        return models;
    }

    public void setModels(List<StudentLinChartInfoModel> models) {
        this.models = models;
    }
}
