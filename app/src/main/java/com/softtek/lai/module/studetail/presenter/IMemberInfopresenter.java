/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.presenter;

import android.app.ProgressDialog;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.studetail.eventModel.LogEvent;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public interface IMemberInfopresenter {

    void getMemberinfo(String classId, String userId, ProgressDialog progressDialog);

    void getLossWeightChatData(String userId,String classId);

    void getLossWeigthLogList(long accountId,int pageIndex);

    void doZan(long accountId, long logId, Callback<ResponseData<Zan>> callback);
}
