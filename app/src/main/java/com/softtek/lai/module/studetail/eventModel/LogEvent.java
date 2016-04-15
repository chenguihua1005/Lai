package com.softtek.lai.module.studetail.eventModel;

import com.softtek.lai.module.studetail.model.LogList;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John on 2016/4/6.
 */
public class LogEvent implements Serializable{

   public static final long serialVersionUID =6615613898089541797L;

    public int flag=0;
    private LogList logList;

    public LogEvent() {
    }

    public LogEvent(int flag, LogList logList) {
        this.flag = flag;
        this.logList = logList;
    }

    public LogList getLogList() {
        return logList;
    }

    public void setLogList(LogList logList) {
        this.logList = logList;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "flag=" + flag +
                ", logList=" + logList +
                '}';
    }
}
