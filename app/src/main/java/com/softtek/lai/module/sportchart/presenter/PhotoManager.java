package com.softtek.lai.module.sportchart.presenter;

import android.app.ProgressDialog;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.model.UploadPhotModel;
import com.softtek.lai.module.sportchart.model.PhotModel;
import com.softtek.lai.module.sportchart.model.StepCountModel;
import com.softtek.lai.module.sportchart.net.ChartService;
import com.softtek.lai.utils.RequestCallback;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Header;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 10/19/2016.
 */
public class PhotoManager {
    private PhotoManagerCallback callback;
    private ChartService service;

    public PhotoManager(PhotoManagerCallback callback) {
        this.callback = callback;
        service= ZillaApi.NormalRestAdapter.create(ChartService.class);
    }
    public void doUploadPhoto(String ClassId, String type,String filePath, final ProgressDialog loadingDialog){
        service.doUploadPhoto(UserInfoModel.getInstance().getToken(), ClassId, type,new TypedFile("image/png", new File(filePath)), new RequestCallback<ResponseData<PhotModel>>() {
            @Override
            public void success(ResponseData<PhotModel> uploadPhotModelResponseData, Response response) {
                loadingDialog.dismiss();
                if (uploadPhotModelResponseData.getStatus()==200)
                {
                    if (callback!=null)
                    {
                        callback.getResult(uploadPhotModelResponseData.getData());
                    }
                }
                else {
                    if (callback!=null)
                    {
                        callback.getResult(null);
                        Util.toastMsg(uploadPhotModelResponseData.getMsg());
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                loadingDialog.dismiss();
                callback.getResult(null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });

    }
//    public void doUploadPhoto(String AccountId, String filePath, final ProgressDialog loadingDialog) {
//        String token = SharedPreferenceService.getInstance().get("token", "");
//        service.doUploadPhoto(token, AccountId, new TypedFile("image/png", new File(filePath)), new Callback<ResponseData<UploadPhotModel>>() {
//            @Override
//            public void success(ResponseData<UploadPhotModel> uploadPhotModelResponseData, Response response) {
//                loadingDialog.dismiss();
//                if (uploadPhotModelResponseData.getStatus()==200)
//                {
//                    if (callback!=null)
//                    {
//                        callback.getResult(uploadPhotModelResponseData.getData());
//                    }
//                }
//                else {
//                    if (callback!=null)
//                    {
//                        callback.getResult(null);
//                        Util.toastMsg(uploadPhotModelResponseData.getMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                loadingDialog.dismiss();
//                callback.getResult(null);
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }

    public void setCallback(PhotoManagerCallback callback) {
        this.callback = callback;
    }

    public interface PhotoManagerCallback{
        void getResult(PhotModel result);
    }
}
