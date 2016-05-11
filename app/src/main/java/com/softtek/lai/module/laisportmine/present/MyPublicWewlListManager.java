package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/11/2016.
 */
public class MyPublicWewlListManager {
    private MineService service;
    private MyPublicWewlListCallback cb;

    public MyPublicWewlListManager(MyPublicWewlListCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doGetNowRgName(long accountid) {
        String token= UserInfoModel.getInstance().getToken();
        service.GetDonateMsg(token, accountid, new Callback<ResponseData<PublicWewlfModel>>() {
            @Override
            public void success(ResponseData<PublicWewlfModel> publicWewlfModelResponseData, Response response) {
                int status=publicWewlfModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("成功读取"+publicWewlfModelResponseData.getData());
                        cb.getMyPublicWewlList(publicWewlfModelResponseData.getData());
                        break;
                    default:
                        Log.i("读取失败"+publicWewlfModelResponseData.getData());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getMyPublicWewlList(null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface MyPublicWewlListCallback{
        void getMyPublicWewlList(PublicWewlfModel publicWewlfModel);
    }
}
