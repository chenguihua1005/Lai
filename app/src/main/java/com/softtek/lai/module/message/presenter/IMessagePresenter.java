/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.presenter;

import android.app.ProgressDialog;
import android.widget.ImageView;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface IMessagePresenter {


    //PC接受参赛要求
    void acceptInviterToClass(String inviters, String classId, String acceptType);

    //SR接受SP邀请
    void acceptInviter(String inviters, String classId, String acceptType);

    //此班级周期内学员是否已加入班级
    void accIsJoinClass(String accountid,String classid);

}
