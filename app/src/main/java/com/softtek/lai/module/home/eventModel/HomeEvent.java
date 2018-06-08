package com.softtek.lai.module.home.eventModel;

import com.softtek.lai.module.home.model.HomeInfoModel;

import java.util.List;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class HomeEvent {

    private List<HomeInfoModel> infos;

    public HomeEvent(List<HomeInfoModel> infos) {
        this.infos = infos;
    }

    public List<HomeInfoModel> getInfos() {
        return infos;
    }

    public void setInfos(List<HomeInfoModel> infos) {
        this.infos = infos;
    }
}
