/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface IStudentPresenter {

    //发送邀请
    void sendInviterMsg(String inviters, String classId, ImageView img_invite);


}
