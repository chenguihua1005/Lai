package com.softtek.lai.module.healthyreport.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView2;
import com.softtek.lai.module.healthyreport.model.HealthyChartModel;
import com.softtek.lai.module.healthyreport.net.HealthyRecordService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jerry.guan on 4/17/2017.
 */

public class HealthyChartPresenter extends BasePresenter<HealthyChartPresenter.HealthyChartView>{

    HealthyRecordService service;

    public HealthyChartPresenter(HealthyChartView baseView) {
        super(baseView);
        service= ZillaApi.NormalRestAdapter.create(HealthyRecordService.class);
    }

    public void getLBHistory(String recordId,int type){
        if(getView()!=null){
            getView().dialogShow("加载中");
        }
        service.getLBHistory(UserInfoModel.getInstance().getToken(), recordId, type,
                new RequestCallback<ResponseData<HealthyChartModel>>() {
                    @Override
                    public void success(ResponseData<HealthyChartModel> data, Response response) {
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

    public void getLBLineChart(String accountId,int pid,int type,String lastDate,int direction){
        if(getView()!=null){
            getView().dialogShow("获取中...");
        }
        service.getLBLineChart(UserInfoModel.getInstance().getToken(),
                accountId,
                pid,
                type,
                lastDate,
                direction,
                new RequestCallback<ResponseData<HealthyChartModel.ChartBean>>() {
                    @Override
                    public void success(ResponseData<HealthyChartModel.ChartBean> data, Response response) {
                        if(getView()!=null){
                            getView().dialogDissmiss();
                        }
                        if(data.getStatus()==200){
                            if(getView()!=null){
                                getView().getData2(data.getData());
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

    public interface HealthyChartView extends BaseView2<HealthyChartModel,HealthyChartModel.ChartBean> {

    }
}
