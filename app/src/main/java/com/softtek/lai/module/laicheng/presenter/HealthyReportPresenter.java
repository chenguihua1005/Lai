package com.softtek.lai.module.laicheng.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.healthyreport.model.HealthyReport;
import com.softtek.lai.module.healthyreport.net.HealthRecordService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/7/2017.
 */

public class HealthyReportPresenter extends BasePresenter<HealthyReportPresenter.HealthyReportView>{

    private HealthRecordService service;
    public HealthyReportPresenter(HealthyReportView baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(HealthRecordService.class);
    }


    public void healthyReport(String reportId){
        if(getView()!=null){
            getView().dialogShow("获取健康报告");
        }
        service.getHealthyReport(UserInfoModel.getInstance().getToken(), reportId, new RequestCallback<ResponseData<HealthyReport>>() {
            @Override
            public void success(ResponseData<HealthyReport> data, Response response) {
                if(getView()!=null){
                    getView().dialogDissmiss();
                }
                if(data.getStatus()==200){
                    if(getView()!=null){
                        getView().getData(data.getData());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()!=null){
                    getView().dialogDissmiss();
                }
                super.failure(error);
            }
        });
    }

    public interface HealthyReportView extends BaseView1<HealthyReport> {

    }
}
