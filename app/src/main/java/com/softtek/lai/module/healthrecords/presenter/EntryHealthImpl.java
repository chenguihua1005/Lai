package com.softtek.lai.module.healthrecords.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthrecords.EventModel.RecordEvent;
import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.model.LastestRecordModel;
import com.softtek.lai.module.healthrecords.net.HealthRecordService;
import com.softtek.lai.module.healthrecords.view.HealthEntryActivity;
import com.softtek.lai.module.home.view.HealthyFragment;
import com.softtek.lai.module.home.view.HealthyRecordFragment;


import org.greenrobot.eventbus.EventBus;

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
    private EntryHealthyCallback  cb;
    private String token;

    public EntryHealthImpl(EntryHealthyCallback cb) {
        healthRecordService = ZillaApi.NormalRestAdapter.create(HealthRecordService.class);
        this.cb=cb;
        token= UserInfoModel.getInstance().getToken();
    }

    //手动录入健康记录
    @Override
    public void entryhealthrecord(HealthModel healthModel) {

        healthRecordService.entryhealthrecord(token,healthModel,new Callback<ResponseData>() {
            @Override
            public void success(ResponseData healthModelResponseData,Response response) {
                int status = healthModelResponseData.getStatus();
                Log.i("healthModelResponseData:"+healthModelResponseData);
                switch (status) {
                    case 200:
                       if(cb!=null)cb.saveSuccess(true);
                        break;
                    case 201:
                        if(cb!=null)cb.saveSuccess(false);
                        Util.toastMsg("健康记录保存失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(cb!=null)cb.saveSuccess(false);
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }

    //获取最新健康记录
    @Override
    public void doGetLastestRecord(long accountid) {
        healthRecordService.doGetLastestRecord(token,accountid, new Callback<ResponseData<LastestRecordModel>>() {
            @Override
            public void success(ResponseData<LastestRecordModel> lastestRecordModelResponseData, Response response) {
                int status = lastestRecordModelResponseData.getStatus();
                switch (status) {
                    case 200:
                        EventBus.getDefault().post(new RecordEvent(lastestRecordModelResponseData.getData()));
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

    public interface EntryHealthyCallback{

        void saveSuccess(boolean res);
    }

}
