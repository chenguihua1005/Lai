package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.MeasureModel;

import java.util.List;

/**
 * Created by lareina.qiao on 4/7/2016.
 */
public class MeasureEvent {
    private List<MeasureModel> measureModels;

    @Override
    public String toString() {
        return "MeasureEvent{" +
                "measureModels=" + measureModels +
                '}';
    }

    public List<MeasureModel> getMeasureModels() {
        return measureModels;
    }

    public void setMeasureModels(List<MeasureModel> measureModels) {
        this.measureModels = measureModels;
    }
}
