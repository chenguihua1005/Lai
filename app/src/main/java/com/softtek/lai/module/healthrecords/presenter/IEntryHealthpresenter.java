package com.softtek.lai.module.healthrecords.presenter;

import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.model.LastestRecordModel;

/**
 * Created by zcy on 2016/4/18.
 */
public interface IEntryHealthpresenter {
    //手动录入健康记录
    void entryhealthrecord(HealthModel healthModel);

    //获取最新健康记录
    void doGetLastestRecord(long accountid);

}
