package com.softtek.lai.module.healthyreport.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.healthyreport.model.HealthModel;
import com.softtek.lai.module.healthyreport.model.LastestRecordModel;
import com.softtek.lai.module.healthyreport.net.HealthyRecordService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 *
 * Created by jerry.guan on 4/10/2017.
 */
public class HealthyEntryPresenter extends BasePresenter<HealthyEntryPresenter.HealthyEntryView>{

    private HealthyRecordService healthyRecordService;
    private String token;

    public HealthyEntryPresenter(HealthyEntryView baseView) {
        super(baseView);
        healthyRecordService = ZillaApi.NormalRestAdapter.create(HealthyRecordService.class);
        token= UserInfoModel.getInstance().getToken();
    }

    //手动录入健康记录
    public void entryhealthrecord(HealthModel healthModel) {
        if (getView()!=null){
            getView().dialogShow("正在提交");
        }
        healthyRecordService.entryhealthrecord(token,healthModel,new Callback<ResponseData>() {
            @Override
            public void success(ResponseData healthModelResponseData,Response response) {
                int status = healthModelResponseData.getStatus();
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                switch (status) {
                    case 200:
                        if(getView()!=null){
                            getView().commitSuccess();
                        }
                        break;
                    case 201:
                        Util.toastMsg("健康记录保存失败");
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    //获取最新健康记录
    public void doGetLastestRecord(long accountid) {
        if (getView()!=null){
            getView().dialogShow("获取数据中...");
        }
        healthyRecordService.doGetLastestRecord(token,accountid, new Callback<ResponseData<LastestRecordModel>>() {
            @Override
            public void success(ResponseData<LastestRecordModel> data, Response response) {
                int status = data.getStatus();
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                switch (status) {
                    case 200:
                        if(getView()!=null){
                            getView().getData(data.getData());
                        }
                        break;
                    case 100:
                        Util.toastMsg("暂无健康记录数据");
                        break;
                }
            }
            @Override
            public void failure(RetrofitError error) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface HealthyEntryView extends BaseView1<LastestRecordModel> {
        void commitSuccess();
    }
}
