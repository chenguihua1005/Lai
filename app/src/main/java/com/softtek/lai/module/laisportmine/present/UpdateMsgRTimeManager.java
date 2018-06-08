package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class UpdateMsgRTimeManager {
    private MineService service;
    private UpdateMsgRTimeCallback cb;

    public UpdateMsgRTimeManager(UpdateMsgRTimeCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doUpdateMsgRTime(String accountid,String type) {
        String token= UserInfoModel.getInstance().getToken();
        service.doUpdateMsgRTime(token, accountid, type, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status=responseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("成功"+responseData.getMsg());
                        break;
                    case 100:
                        Log.i("成功"+responseData.getMsg());
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

    public interface UpdateMsgRTimeCallback{

    }
}
