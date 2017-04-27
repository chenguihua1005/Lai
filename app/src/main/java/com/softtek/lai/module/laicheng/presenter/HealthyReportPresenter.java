package com.softtek.lai.module.laicheng.presenter;

import android.os.SystemClock;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.healthyreport.model.HealthyReport;
import com.softtek.lai.module.healthyreport.model.HealthyShareData;
import com.softtek.lai.module.healthyreport.net.HealthyRecordService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/7/2017.
 */

public class HealthyReportPresenter extends BasePresenter<HealthyReportPresenter.HealthyReportView>{

    private HealthyRecordService service;
    public HealthyReportPresenter(HealthyReportView baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(HealthyRecordService.class);
    }


    public void healthyReport(String reportId){
        if(getView()!=null){
            getView().dialogShow("获取健康报告");
        }
        service.getHealthyReport(UserInfoModel.getInstance().getToken(), reportId, new RequestCallback<ResponseData<HealthyReport>>() {
            @Override
            public void success(ResponseData<HealthyReport> data, Response response) {
                if(data.getStatus()==200){
                    if(getView()!=null){
                        getView().getData(data.getData());
                    }
                }
                if(getView()!=null){
                    getView().dialogDissmiss();
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

    public void getShareData(String recordId){
        if(getView()!=null){
            getView().dialogShow("处理分享数据中...");
        }
        service.getShareData(UserInfoModel.getInstance().getToken(), recordId,
                new RequestCallback<ResponseData<HealthyShareData>>() {
                    @Override
                    public void success(ResponseData<HealthyShareData> data, Response response) {
                        if (data.getStatus()==200){
                            if(getView()!=null){
                                getView().getShareData(data.getData());
                            }
                        }
                        if(getView()!=null){
                            getView().dialogDissmiss();
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
        void getShareData(HealthyShareData data);
    }
}
