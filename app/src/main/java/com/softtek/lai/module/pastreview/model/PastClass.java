package com.softtek.lai.module.pastreview.model;

import java.util.List;

/**
 * Created by jerry.guan on 6/30/2016.
 * 往期班级
 */
public class PastClass {

    private PastBaseData BaseData;
    private List<Honor> Honor;
    private LossStory LossLog;
    private List<Photo> ImgBook;

    public PastBaseData getBaseData() {
        return BaseData;
    }

    public void setBaseData(PastBaseData baseData) {
        BaseData = baseData;
    }

    public List<com.softtek.lai.module.pastreview.model.Honor> getHonor() {
        return Honor;
    }

    public void setHonor(List<com.softtek.lai.module.pastreview.model.Honor> honor) {
        Honor = honor;
    }

    public LossStory getLossLog() {
        return LossLog;
    }

    public void setLossLog(LossStory lossLog) {
        LossLog = lossLog;
    }

    public List<Photo> getImgBook() {
        return ImgBook;
    }

    public void setImgBook(List<Photo> imgBook) {
        ImgBook = imgBook;
    }
}
