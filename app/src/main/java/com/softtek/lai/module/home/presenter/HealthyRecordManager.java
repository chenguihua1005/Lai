package com.softtek.lai.module.home.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.retest.model.LaichModel;
import com.softtek.lai.module.retest.net.RestService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/27/2016.
 */
public class HealthyRecordManager {

    private RestService service;
    private HealthyRecordCallback cb;

    public HealthyRecordManager(HealthyRecordCallback cb) {
        this.cb=cb;
        service=ZillaApi.NormalRestAdapter.create(RestService.class);
    }

    public void GetUserMeasuredInfo(String phone) {
        String token= UserInfoModel.getInstance().getToken();
        service.GetUserMeasuredInfo(token, phone, new Callback<ResponseData<LaichModel>>() {
            @Override
            public void success(ResponseData<LaichModel> laichModelResponseData, Response response) {
                int status=laichModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        LaichModel laichModel= laichModelResponseData.getData();
                       cb.getModel(laichModel);
                        break;
                    default:
                        cb.getModel(null);
                        Util.toastMsg(laichModelResponseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                cb.getModel(null);
                ZillaApi.dealNetError(error);
            }
        });

    }

    public interface HealthyRecordCallback{
        void getModel(LaichModel model);
    }
}
