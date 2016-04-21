package com.softtek.lai.module.bodygamest.present;

import android.app.ProgressDialog;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.eventModel.PhotoListEvent;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.UploadPhotModel;
import com.softtek.lai.module.bodygamest.net.PhotoListService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;
import zilla.libzilla.dialog.LoadingDialog;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class PhotoListIml implements PhotoListPre {
    private PhotoListService service;
    private PhotoListCallback cb;

    public PhotoListIml(PhotoListCallback cb) {
        service = ZillaApi.NormalRestAdapter.create(PhotoListService.class);
        this.cb=cb;
    }

    @Override
    public void doGetDownPhoto(String AccountId, int pageIndex, final ProgressDialog loadingDialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.doGetDownPhoto(token, AccountId, pageIndex, new Callback<ResponseData<DownPhotoModel>>() {
            @Override
            public void success(ResponseData<DownPhotoModel> listResponseData, Response response) {
                loadingDialog.dismiss();
                int status = listResponseData.getStatus();
                switch (status) {

                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        Util.toastMsg("获取图片成功");
                        break;
                    case 500:
                        Util.toastMsg("获取图片失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                loadingDialog.dismiss();
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getUserPhotos(String photoName) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getUserPhotos(token, photoName, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {

                    case 200:
                        System.out.println("listResponseData:"+listResponseData);
                        EventBus.getDefault().post(listResponseData);
                        break;
                    case 500:
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {

                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    @Override
    public void getUploadPhoto(String AccountId, String pageIndex) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getUploadPhoto(token, AccountId, pageIndex, new Callback<ResponseData<DownPhotoModel>>() {
            @Override
            public void success(ResponseData<DownPhotoModel> listResponseData, Response response) {

                int status = listResponseData.getStatus();
                switch (status) {

                    case 200:
                        EventBus.getDefault().post(listResponseData.getData());
                        Util.toastMsg("获取图片成功");
                        break;
                    case 500:
                        EventBus.getDefault().post(listResponseData.getData());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }


    @Override
    public void doUploadPhoto(String AccountId, String filePath, final ProgressDialog loadingDialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.doUploadPhoto(token, AccountId, new TypedFile("image/png", new File(filePath)), new Callback<ResponseData<UploadPhotModel>>() {
            @Override
            public void success(ResponseData<UploadPhotModel> uploadPhotModelResponseData, Response response) {
                loadingDialog.dismiss();
                int status = uploadPhotModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        cb.uoploadPhotoSuccess(true,uploadPhotModelResponseData.getData().getImg());
                        Util.toastMsg("上传成功");
                        break;
                    case 500:
                        cb.uoploadPhotoSuccess(false,null);
                        Util.toastMsg("上传失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                loadingDialog.dismiss();
                cb.uoploadPhotoSuccess(false,null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();

            }
        });
    }

    public interface PhotoListCallback{
        void uoploadPhotoSuccess(boolean result,String photo);
    }
}
