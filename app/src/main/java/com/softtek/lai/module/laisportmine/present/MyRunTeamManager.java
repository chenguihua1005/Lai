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
                        if (cb!=null) {
                            cb.getRunTeamName(runTeamModelResponseData.getData());
                        }
                        break;
                    case 100:
                        if (cb!=null) {
                            cb.getRunTeamName(null);
                        }
                        break;
                    default:
                        Log.i(runTeamModelResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (cb!=null) {
                    cb.getRunTeamName(null);
                }
                ZillaApi.dealNetError(error);
            }
        });


    }

    public interface MyRunTeamCallback{
        void getRunTeamName(RunTeamModel runTeamModel);
    }
}
