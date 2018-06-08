package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 5/25/2016.
 */
public class MyPkDelPKMsgManager {
    private MineService service;
    private MyPkDelPKMsgCallback cb;

    public MyPkDelPKMsgManager(MyPkDelPKMsgCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doDelPKMsg(String MessageId) {
        String token= UserInfoModel.getInstance().getToken();
        service.doDelPKMsg(token, MessageId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("删除成功");
                        Log.i("pk列表删除"+responseData.getMsg());
                        break;
                    default:
                        Log.i("pk列表删除"+responseData.getMsg());
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

    public interface MyPkDelPKMsgCallback{

    }
}
