package com.softtek.lai.module.healthrecords.presenter;

import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.model.LastestRecordModel;

/**
 * Created by zcy on 2016/4/18.
 */
public interface IEntryHealthpresenter {
    //健康记录录入
    void entryhealthrecord(long accountId,HealthModel healthModel);

    void doGetLastestRecord(long accountid,LastestRecordModel lastestRecordModel);

}
