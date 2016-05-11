/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.present;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.softtek.lai.module.retest.model.RetestAuditModel;
import com.softtek.lai.module.retest.model.RetestWriteModel;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public interface RetestPre {
    void doGetRetestclass(long id);

    void doGetqueryResult(String str, String accountId, ProgressDialog dialog,Context context);

    void doGetBanjiStudent(long classId,long id);

    void doGetAudit(long accountId, long classId, String typeDate);

    void doPostWrite(long accountId, long loginId, RetestWriteModel retestWriteModel,Context context,ProgressDialog progressDialog);

    void doPostAudit(String loginId, String accountId, String typeDate, RetestAuditModel retestAuditModel,Context context);

    void goGetPicture(String filePath);

    void GetUserMeasuredInfo(String phone);

}
