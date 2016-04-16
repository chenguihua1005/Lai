/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.presenter;

import android.app.ProgressDialog;

import com.softtek.lai.module.studetail.eventModel.LogEvent;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public interface IMemberInfopresenter {

    void getMemberinfo(String classId, String userId, ProgressDialog progressDialog);

    void getLossWeightChatData(String userId,String classId);

    void getLossWeigthLogList(long accountId,int pageIndex);

    @Deprecated
    LogEvent loadLogListCache();

    void doZan(long accountId,long logId);
}
