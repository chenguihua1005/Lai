/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-25
 */

package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.BanjiStudent;

import java.util.List;

/**
 * Created by lareina.qiao on 3/24/2016.
 */
public class BanjiStudentEvent {
    private List<BanjiStudent> banjiStudents;

    public BanjiStudentEvent(List<BanjiStudent> banjiStudents) {
        this.banjiStudents = banjiStudents;
    }

    public List<BanjiStudent> getBanjiStudents() {
        return banjiStudents;
    }

    public void setBanjiStudents(List<BanjiStudent> banjiStudents) {
        this.banjiStudents = banjiStudents;
    }
}
