package com.softtek.lai.module.grade.presenter;

import com.softtek.lai.module.grade.model.StudentModel;

import java.util.List;

/**
 * Created by jerry.guan on 4/28/2016.
 */
public interface StudentListCallback {

    void updataData(List<StudentModel> models);
}
