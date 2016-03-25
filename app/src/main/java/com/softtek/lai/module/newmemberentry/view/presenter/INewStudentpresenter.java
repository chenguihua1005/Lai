package com.softtek.lai.module.newmemberentry.view.presenter;

import com.softtek.lai.module.newmemberentry.view.model.Newstudents;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public interface INewStudentpresenter {
    //新学员录入信息
    void input(Newstudents newstudents);

    void upload(String filePath);
}
