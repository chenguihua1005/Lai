package com.softtek.lai.module.studetail.eventModel;

import com.softtek.lai.module.studetail.model.LossWeightLogModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John on 2016/4/6.
 */
public class LogEvent implements Serializable{

   public static final long serialVersionUID =6615613898089541797L;

    public int flag=0;
    private List<LossWeightLogModel> lossWeightLogModels;

    public LogEvent(int flag,List<LossWeightLogModel> lossWeightLogModels) {
        this.lossWeightLogModels = lossWeightLogModels;
        this.flag=flag;
    }

    public LogEvent() {
    }

    public List<LossWeightLogModel> getLossWeightLogModels() {
        return lossWeightLogModels;
    }

    public void setLossWeightLogModels(List<LossWeightLogModel> lossWeightLogModels) {
        this.lossWeightLogModels = lossWeightLogModels;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "flag=" + flag +
                ", lossWeightLogModels=" + lossWeightLogModels +
                '}';
    }
}
