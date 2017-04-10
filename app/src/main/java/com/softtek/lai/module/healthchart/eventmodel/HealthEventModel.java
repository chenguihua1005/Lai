package com.softtek.lai.module.healthchart.eventmodel;

import com.softtek.lai.module.healthchart.model.HealthDateModel;

import java.util.List;

/**
 * Created by lareina.qiao on 4/15/2016.
 */
public class HealthEventModel {
    private List<HealthDateModel>healthDateModels;

    @Override
    public String toString() {
        return "HealthEventModel{" +
                "healthDateModels=" + healthDateModels +
                '}';
    }

    public List<HealthDateModel> getHealthDateModels() {
        return healthDateModels;
    }

    public void setHealthDateModels(List<HealthDateModel> healthDateModels) {
        this.healthDateModels = healthDateModels;
    }
}
