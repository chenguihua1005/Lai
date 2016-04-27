package com.softtek.lai.module.bodygamest.present;

import android.app.ProgressDialog;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public interface PhotoListPre {
    void doGetDownPhoto(String AccountId,int pageIndex, ProgressDialog loadingDialog);
    void doUploadPhoto(String AccountId,String filePath, ProgressDialog loadingDialog);
    void getUploadPhoto(String AccountId,String pageIndex);
    void getUserPhotos(String photoName);
    void getLossData(String accountId);
}
