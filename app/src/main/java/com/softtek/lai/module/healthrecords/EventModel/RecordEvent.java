package com.softtek.lai.module.healthrecords.EventModel;

import com.softtek.lai.module.healthrecords.model.LastestRecordModel;

/**
 * Created by julie.zhu on 4/19/2016.
 */
public class RecordEvent {
    private LastestRecordModel lastestRecordModel;

    public LastestRecordModel lastestRecordModel() {
        return lastestRecordModel;
    }

    public RecordEvent(LastestRecordModel lastestRecordModel) {
        this.lastestRecordModel = lastestRecordModel;
    }
    public void setLastestRecordModel(LastestRecordModel lastestRecordModel) {
        this.lastestRecordModel = lastestRecordModel;
    }
}
