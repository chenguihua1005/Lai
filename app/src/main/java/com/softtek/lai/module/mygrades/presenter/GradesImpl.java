package com.softtek.lai.module.mygrades.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.GradesModel;
import com.softtek.lai.module.mygrades.model.HonorModel;
import com.softtek.lai.module.mygrades.net.GradesService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public class GradesImpl implements IGradesPresenter{

    private GradesService gradesService;

    public  GradesImpl(){
        gradesService= ZillaApi.NormalRestAdapter.create(GradesService.class);
    }

    @Override
    public void getStepCount() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getStepCount(token, new Callback<ResponseData<GradesModel>>() {

            @Override
            public void success(ResponseData<GradesModel> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("查询正确");
                        break;
                    case 500:
                        Util.toastMsg("查询出bug");
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
    public void getCurrentDateOrder(int RGId) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentDateOrder(token,RGId,new Callback<ResponseData<DayRankModel>>() {

            @Override
            public void success(ResponseData<DayRankModel> listResponseData, Response response) {
                 //Log.i("listResponseData",""+listResponseData);
                 EventBus.getDefault().post(listResponseData.getData());
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("查询正确");
                        break;
                    case 500:
                        Util.toastMsg("查询出bug");
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
    public void getCurrentWeekOrder() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getCurrentWeekOrder(token, new Callback<ResponseData<DayRankModel>>() {

            @Override
            public void success(ResponseData<DayRankModel> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("查询正确");
                        break;
                    case 500:
                        Util.toastMsg("查询出bug");
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
    public void getStepHonor() {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gradesService.getStepHonor(token, new Callback<ResponseData<HonorModel>>() {

            @Override
            public void success(ResponseData<HonorModel> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        Util.toastMsg("查询正确");
                        break;
                    case 500:
                        Util.toastMsg("查询出bug");
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


}
