package com.softtek.lai.module.sportchart.presenter;

import android.app.ProgressDialog;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sportchart.model.PhotModel;
import com.softtek.lai.module.sportchart.net.ChartService;
import com.softtek.lai.utils.RequestCallback;

import java.io.File;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
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
            public void success(ResponseData<PhotModel> data, Response response) {
                loadingDialog.dismiss();
                if (data.getStatus()==200)
                {
                    if (callback!=null)
                    {
                        callback.getResult(data.getData());
                    }
                }
                else {
                    if (callback!=null)
                    {
                        callback.getResult(null);
                        Util.toastMsg(data.getMsg());
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

    public void setCallback(PhotoManagerCallback callback) {
        this.callback = callback;
    }

    public interface PhotoManagerCallback{
        void getResult(PhotModel result);
    }
}
