package com.softtek.lai.module.bodygamest.present;

import android.app.ProgressDialog;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public interface PhotoListPre {
    void doGetDownPhoto(String AccountId, ProgressDialog loadingDialog);
    void doUploadPhoto(String AccountId,String filePath, ProgressDialog loadingDialog);
}
