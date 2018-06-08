package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 5/13/2016.
 */
public class DelNoticeOrMeasureManager {
    private MineService service;
    private DelNoticeOrMeasureCallback cb;

    public DelNoticeOrMeasureManager(DelNoticeOrMeasureCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doDelNoticeOrMeasureMsg(String MessageId,String type) {
        String token= UserInfoModel.getInstance().getToken();
        service.doDelNoticeOrMeasureMsg(token, MessageId,type, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg(responseData.getMsg());
                        Log.i("活动列表"+responseData.getMsg());
                        break;
                    default:
                        Log.i("活动列表"+responseData.getMsg());
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

    public interface DelNoticeOrMeasureCallback{

    }
}
