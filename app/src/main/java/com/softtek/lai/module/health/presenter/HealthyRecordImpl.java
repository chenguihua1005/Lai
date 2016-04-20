package com.softtek.lai.module.health.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.eventmodel.HealthEventModel;
import com.softtek.lai.module.health.model.HealthDateModel;
import com.softtek.lai.module.health.model.HealthyRecordModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.net.HealthyService;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/12/2016.
 */
public class HealthyRecordImpl implements IHealthyRecord  {

    private HealthyService serveice;


    public HealthyRecordImpl() {
        serveice= ZillaApi.NormalRestAdapter.create(HealthyService.class);
    }






    @Override
    public void doGetHealthPysicalRecords(String Startdate, String Enddate, int i) {
        String token= UserInfoModel.getInstance().getToken();
        serveice.doGetHealthPysicalRecords(token, Startdate, Enddate, i, new Callback<ResponseData<PysicalModel>>() {
            @Override
            public void success(ResponseData<PysicalModel> pysicalModelResponseData, Response response) {
                int states=pysicalModelResponseData.getStatus();
                switch (states)
                {
                    case 200:
                        Util.toastMsg(pysicalModelResponseData.getMsg());
                        default:
                            Util.toastMsg(pysicalModelResponseData.getMsg());
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
