package com.softtek.lai.module.laisportmine.present;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;
import com.softtek.lai.module.laisportmine.net.MineService;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.module.retest.net.RestService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public class MyRunTeamManager {
    private MineService service;
    private MyRunTeamCallback cb;

    public MyRunTeamManager(MyRunTeamCallback cb) {
        this.cb=cb;
        service= ZillaApi.NormalRestAdapter.create(MineService.class);
    }

    public void doGetNowRgName(long accountid) {
        String token= UserInfoModel.getInstance().getToken();
        service.doGetNowRgName(token,accountid, new Callback<ResponseData<RunTeamModel>>() {


            @Override
            public void success(ResponseData<RunTeamModel> runTeamModelResponseData, Response response) {
                int status=runTeamModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Log.i("成功"+runTeamModelResponseData.getData());
                        cb.getRunTeamName(runTeamModelResponseData.getData().getRgName(),runTeamModelResponseData.getData().getIsHasMsg());
                        break;
                    case 100:
                        cb.getRunTeamName(runTeamModelResponseData.getData().getRgName(),"");
                        break;
                    default:
                        Log.i(runTeamModelResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getRunTeamName(null,null);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });


    }

    public interface MyRunTeamCallback{
        void getRunTeamName(String data,String flag);
    }
}