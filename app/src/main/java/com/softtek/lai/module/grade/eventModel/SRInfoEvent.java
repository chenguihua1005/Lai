/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.grade.eventModel;

import com.softtek.lai.module.grade.model.SRInfoModel;

import java.util.List;

/**
 * Created by jerry.guan on 3/23/2016.
 */
public class SRInfoEvent {

    private List<SRInfoModel> infos;

    public SRInfoEvent(List<SRInfoModel> infos) {
        this.infos = infos;
    }

    public List<SRInfoModel> getInfos() {
        return infos;
    }

    public void setInfos(List<SRInfoModel> infos) {
        this.infos = infos;
    }
}
