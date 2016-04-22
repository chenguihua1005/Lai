package com.softtek.lai.module.health.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.eventmodel.HealthEventModel;
import com.softtek.lai.module.health.model.HealthDateModel;
import com.softtek.lai.module.health.model.HealthWeightModel;
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
        serveice.doGetHealthPysicalRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<PysicalModel>>() {
            @Override
            public void success(ResponseData<PysicalModel> pysicalModelResponseData, Response response) {
                int states=pysicalModelResponseData.getStatus();
                switch (states)
                {
                    case 200:
                        PysicalModel pysicalModel=(PysicalModel) pysicalModelResponseData.getData();
                        EventBus.getDefault().post(pysicalModel);
                        Util.toastMsg(pysicalModelResponseData.getMsg());
                        break;
                        default:
                            Util.toastMsg(pysicalModelResponseData.getMsg());
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
    public void GetHealthWeightRecords(String Startdate, String Enddate, int i) {
        String token= UserInfoModel.getInstance().getToken();
        serveice.GetHealthWeightRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthWeightModel>>() {
            @Override
            public void success(ResponseData<HealthWeightModel> healthWeightModelResponseData, Response response) {
                int states=healthWeightModelResponseData.getStatus();
                switch (states)
                {
                    case 200:
                        HealthWeightModel healthWeightModel=(HealthWeightModel) healthWeightModelResponseData.getData();
                        EventBus.getDefault().post(healthWeightModel);
                        Util.toastMsg(healthWeightModelResponseData.getMsg());
                        break;
                    default:
                        Util.toastMsg(healthWeightModelResponseData.getMsg());
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
