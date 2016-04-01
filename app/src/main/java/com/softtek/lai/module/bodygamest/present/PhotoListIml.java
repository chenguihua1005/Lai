package com.softtek.lai.module.bodygamest.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.eventModel.PhotoListEvent;
import com.softtek.lai.module.bodygamest.model.DownPhoto;
import com.softtek.lai.module.bodygamest.net.PhotoListService;
import com.softtek.lai.module.retest.eventModel.BanjiStudentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
        service.doGetDownPhoto(token, AccountId, new Callback<ResponseData<List<DownPhoto>>>() {
            @Override
            public void success(ResponseData<List<DownPhoto>> listResponseData, Response response) {
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

            }
        });
    }
}
