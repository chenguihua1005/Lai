package com.softtek.lai.module.bodygame3.home.event;

import com.softtek.lai.module.bodygame3.more.model.FuceDate;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2016/12/9
 */

public class UpdateFuce {

    private String classId;
    private List<FuceDate> fuceDate;

    public UpdateFuce(String classId, List<FuceDate> fuceDate) {
        this.classId = classId;
        this.fuceDate = fuceDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List<FuceDate> getFuceDate() {
        return fuceDate;
    }

    public void setFuceDate(List<FuceDate> fuceDate) {
        this.fuceDate = fuceDate;
    }
}
