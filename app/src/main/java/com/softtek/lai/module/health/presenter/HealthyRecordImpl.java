package com.softtek.lai.module.health.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.model.HealthyRecordModel;
import com.softtek.lai.module.health.net.HealthyService;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/12/2016.
 */
public class HealthyRecordImpl {

    private HealthyService serveice;
    private HealthyRecordCallback cb;

    public HealthyRecordImpl(HealthyRecordCallback cb) {
        this.cb=cb;
        serveice= ZillaApi.NormalRestAdapter.create(HealthyService.class);
    }

    public void getCurveData(){
        String token= UserInfoModel.getInstance().getToken();
        serveice.doGetHealth(token, new RequestCallback<ResponseData<List<HealthyRecordModel>>>() {
            @Override
            public void success(ResponseData<List<HealthyRecordModel>> listResponseData, Response response) {

            }
        });

    }


    public interface HealthyRecordCallback{


    }
}
