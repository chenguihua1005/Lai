package com.softtek.lai.module.lossweightstory.presenter;

import android.content.Context;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.lossweightstory.model.LogStoryDetailModel;
import com.softtek.lai.module.lossweightstory.net.LossWeightLogService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/18/2016.
 */
public class LogStoryDetailManager {

    private LogStoryDetailManagerCallback cb;
    private String token;
    private LossWeightLogService service;

    public LogStoryDetailManager(Context context) {
        this.cb = (LogStoryDetailManagerCallback) context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(LossWeightLogService.class);
    }

    public void getLogDetail(long logId){
        service.getLogById(token, logId, new RequestCallback<ResponseData<LogStoryDetailModel>>() {
            @Override
            public void success(ResponseData<LogStoryDetailModel> logStoryDetailModelResponseData, Response response) {
                if(cb!=null)cb.getLogDetail(logStoryDetailModelResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if(cb!=null)cb.getLogDetail(null);
            }
        });
    }

    public interface LogStoryDetailManagerCallback{
        void getLogDetail(LogStoryDetailModel model);
    }
}
