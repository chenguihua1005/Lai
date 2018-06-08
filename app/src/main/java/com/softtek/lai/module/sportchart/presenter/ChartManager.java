package com.softtek.lai.module.sportchart.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sportchart.model.StepCountModel;
import com.softtek.lai.module.sportchart.net.ChartService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by lareina.qiao on 10/19/2016.
 */
public class ChartManager {
    private ChartManagerCallback callback;
    private ChartService service;

    public ChartManager(ChartManagerCallback callback) {
        this.callback = callback;
        service = ZillaApi.NormalRestAdapter.create(ChartService.class);
    }

    public void doGetStepCount(String accountid, String start, String end) {
        service.doGetStepCount(UserInfoModel.getInstance().getToken(), accountid, start, end, new RequestCallback<ResponseData<StepCountModel>>() {
            @Override
            public void success(ResponseData<StepCountModel> stepCountModelResponseData, Response response) {
                if (stepCountModelResponseData.getStatus() == 200) {
                    if (callback != null) {
                        callback.getResu(stepCountModelResponseData.getData());
                    }
                } else {
                    if (callback != null) {
                        callback.getResu(null);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (callback != null) {
                    callback.getResu(null);
                }
            }
        });
    }


    public void setCallback(ChartManagerCallback callback) {
        this.callback = callback;
    }

    public interface ChartManagerCallback {
        void getResu(StepCountModel result);
    }
}
