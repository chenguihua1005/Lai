package com.softtek.lai.module.mygrades.eventModel;

import com.softtek.lai.module.mygrades.model.GradesModel;

import java.util.List;

/**
 * Created by julie.zhu on 5/3/2016.
 * 我的成绩
 */
public class GradesEvent {

    private List<GradesModel>gradesModels;

    public GradesEvent(List<GradesModel>gradesModels){this.gradesModels=gradesModels;}

    public List<GradesModel> getgradesModels() {
        return gradesModels;
    }

    public void setGradesModels(List<GradesModel> gradesModels) {
        this.gradesModels = gradesModels;
    }

}
