package com.softtek.lai.module.healthrecords.presenter;

import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.model.LastestRecordModel;
import com.softtek.lai.module.healthrecords.net.HealthRecordService;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by zcy on 2016/4/18.
 */
public class EntryHealthImpl implements IEntryHealthpresenter {

    private HealthRecordService healthRecordService;
    private Context context;

    public EntryHealthImpl(HealthEntryActivity healthEntryActivity) {
        healthRecordService = ZillaApi.NormalRestAdapter.create(HealthRecordService.class);
        context = healthEntryActivity;
    }

    @Override
    public void entryhealthrecord(long accountId, HealthModel healthModel) {
        Log.i("HealthRecordService>>>>>>>>>>>>>>" + healthRecordService);
        String token = SharedPreferenceService.getInstance().get("token", "");

        healthRecordService.entryhealthrecord(token,accountId,healthModel,new Callback<ResponseData<HealthModel>>() {

            @Override
            public void success(ResponseData<HealthModel> healthModelResponseData,Response response) {
                int status = healthModelResponseData.getStatus();
                Log.i("healthModelResponseData:"+healthModelResponseData);
                switch (status) {
                    case 200:
                        Util.toastMsg("健康记录录入成功");
                        break;
                    case 201:
                        Util.toastMsg("健康记录保存异常");
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
    public void doGetLastestRecord(long accountid, LastestRecordModel lastestRecordModel) {
        Log.i("HealthRecordService>>>>>>>>>>>>>>" + healthRecordService);
        String token = SharedPreferenceService.getInstance().get("token", "");
        healthRecordService.doGetLastestRecord(token, accountid, new Callback<ResponseData<LastestRecordModel>>() {
            @Override
            public void success(ResponseData<LastestRecordModel> lastestRecordModelResponseData, Response response) {
                int status = lastestRecordModelResponseData.getStatus();
                Log.i("lastestRecordModelResponseData:"+lastestRecordModelResponseData);
                switch (status) {
                    case 200:
                        Util.toastMsg("获取最新健康记录成功");
                        break;
                    case 100:
                        Util.toastMsg("暂无健康记录数据");
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
