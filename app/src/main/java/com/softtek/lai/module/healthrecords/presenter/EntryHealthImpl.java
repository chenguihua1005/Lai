package com.softtek.lai.module.healthrecords.presenter;

import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.net.HealthRecordService;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;
import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.RetrofitError;
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
    public void entryhealthrecord(HealthModel healthModel) {
        Log.i("HealthRecordService>>>>>>>>>>>>>>" + healthRecordService);
        String token = SharedPreferenceService.getInstance().get("token", "");
//        healthRecordService.memberentry(token, healthModel, new Callback<ResponseData<HealthModel>>() {
//
//            @Override
//            public void success(ResponseData<HealthModel> listResponseData, Response response) {
//                int status = listResponseData.getStatus();
//                Log.i("listResponseData:"+listResponseData);
//                switch (status) {
//                    case 200:
//                        Util.toastMsg("录入成功");
//                        break;
//                    case 500:
//                        Util.toastMsg("录入失败");
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
    }
}
