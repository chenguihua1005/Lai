/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.presenter;

import com.softtek.lai.module.newmemberentry.view.model.NewstudentsModel;

/**
 * Created by julie.zhu on 3/21/2016.
 */
public interface INewStudentpresenter {
    //新学员录入信息
    void input(NewstudentsModel newstudentsModel);

    void upload(String filePath);
}
