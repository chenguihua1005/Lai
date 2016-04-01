package com.softtek.lai.module.bodygamest.present;

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

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class PhotoListIml implements PhotoListPre{
    private PhotoListService service;
    public PhotoListIml(){
        service= ZillaApi.NormalRestAdapter.create(PhotoListService.class);
    }

    @Override
    public void doGetDownPhoto(String AccountId) {
        Log.i("service>>>>>>>>>>>>>>>>>>>>>>>>>>"+service);
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doGetDownPhoto(token, AccountId, new Callback<ResponseData<List<DownPhotoModel>>>() {
            @Override
            public void success(ResponseData<List<DownPhotoModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        EventBus.getDefault().post(new PhotoListEvent(listResponseData.getData()));
                        Util.toastMsg("获取图片成功");
                        break;
                    case 500:
                        Util.toastMsg("获取图片失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }

    @Override
    public void doUploadPhoto(String AccountId, String filePath) {
        String token=SharedPreferenceService.getInstance().get("token","");
        service.doUploadPhoto(token, AccountId, new TypedFile("image/png", new File(filePath)), new Callback<ResponseData<UploadPhotModel>>() {
            @Override
            public void success(ResponseData<UploadPhotModel> uploadPhotModelResponseData, Response response) {

                int status=uploadPhotModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("上传成功");
                    case 500:
                        Util.toastMsg("上传失败");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Util.toastMsg("服务器异常");
            }
        });
    }
}
