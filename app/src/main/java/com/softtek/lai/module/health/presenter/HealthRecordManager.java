package com.softtek.lai.module.health.presenter;

import android.content.Context;

import com.github.snowdream.android.util.Log;
import com.google.gson.annotations.Until;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.net.PhotoListService;
import com.softtek.lai.module.health.model.HealthCircrumModel;
import com.softtek.lai.module.health.model.HealthFatModel;
import com.softtek.lai.module.health.model.HealthHiplieModel;
import com.softtek.lai.module.health.model.HealthUpArmGirthModel;
import com.softtek.lai.module.health.model.HealthWaistlineModel;
import com.softtek.lai.module.health.model.HealthWeightModel;
import com.softtek.lai.module.health.model.HealthdoLegGirthModel;
import com.softtek.lai.module.health.model.HealthupLegGirthModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.net.HealthyService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public class HealthRecordManager {

    private String token;
    private HealthyService service;
    //private Context context;
    private HealthRecordCallBack cb;

    public HealthRecordManager(HealthRecordCallBack context) {
        //this.context = context;
        cb= context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(HealthyService.class);
    }
    public void doGetHealthPysicalRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthPysicalRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<PysicalModel>>() {
            @Override
            public void success(ResponseData<PysicalModel> pysicalModelResponseData, Response response) {
                Log.i(pysicalModelResponseData.toString());
                cb.getHealthPysicalRecords(pysicalModelResponseData.getData());
                if (pysicalModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(pysicalModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthPysicalRecords(null);
                super.failure(error);

            }
        });
    }

    public void doGetHealthWeightRecords(String Startdate,String Enddate,int i) {
        service.GetHealthWeightRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthWeightModel>>() {
            @Override
            public void success(ResponseData<HealthWeightModel> healthWeightModelResponseData, Response response) {
                Log.i(healthWeightModelResponseData.toString());
                Log.i("diaoyonng");
                cb.getHealthWeightRecords(healthWeightModelResponseData.getData());
                Log.i("diaoyonng111111");
                if (healthWeightModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthWeightModelResponseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cb.getHealthWeightRecords(null);
                super.failure(error);

            }
        });
    }
    //内脂
    public void doGetHealthfatRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthfatRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthFatModel>>() {
            @Override
            public void success(ResponseData<HealthFatModel> healthFatModelResponseData, Response response) {
                Log.i(healthFatModelResponseData.toString());
                cb.getHealthfatRecords(healthFatModelResponseData.getData());
                if (healthFatModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthFatModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthfatRecords(null);
                super.failure(error);

            }

        });
    }
    //胸围
    public void doGetHealthcircumRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthcircumRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthCircrumModel>>() {
            @Override
            public void success(ResponseData<HealthCircrumModel> healthCircrumModelResponseData, Response response) {
                Log.i(healthCircrumModelResponseData.toString());
                cb.getHealthcircumRecords(healthCircrumModelResponseData.getData());
                if (healthCircrumModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthCircrumModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthcircumRecords(null);
                super.failure(error);

            }
        });
    }
    //腰围
    public void doGetHealthwaistlineRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthwaistlineRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthWaistlineModel>>() {
            @Override
            public void success(ResponseData<HealthWaistlineModel> healthWaistlineModelResponseData, Response response) {
                Log.i(healthWaistlineModelResponseData.toString());
                cb.getHealthwaistlineRecords(healthWaistlineModelResponseData.getData());
                if (healthWaistlineModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthWaistlineModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthwaistlineRecords(null);
                super.failure(error);

            }
        });
    }
    //臀围
    public void doGetHealthhiplieRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthhiplieRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthHiplieModel>>() {
            @Override
            public void success(ResponseData<HealthHiplieModel> healthHiplieModelResponseData, Response response) {
                Log.i(healthHiplieModelResponseData.toString());
                cb.getHealthhiplieRecords(healthHiplieModelResponseData.getData());
                if (healthHiplieModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthHiplieModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthhiplieRecords(null);
                super.failure(error);

            }
        });
    }

    //上臂围
    public void doGetHealthupArmGirthRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthupArmGirthRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthUpArmGirthModel>>() {
            @Override
            public void success(ResponseData<HealthUpArmGirthModel> healthUpArmGirthModelResponseData, Response response) {
                Log.i(healthUpArmGirthModelResponseData.toString());
                cb.getHealthupArmGirthRecords(healthUpArmGirthModelResponseData.getData());
                if (healthUpArmGirthModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthUpArmGirthModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthupArmGirthRecords(null);
                super.failure(error);

            }
        });
    }
    //大腿围
    public void doGetHealthupLegGirthRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthupLegGirthRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthupLegGirthModel>>() {
            @Override
            public void success(ResponseData<HealthupLegGirthModel> healthupLegGirthModelResponseData, Response response) {
                Log.i(healthupLegGirthModelResponseData.toString());
                cb.getGetHealthupLegGirthRecords(healthupLegGirthModelResponseData.getData());
                if (healthupLegGirthModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthupLegGirthModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getGetHealthupLegGirthRecords(null);
                super.failure(error);

            }
        });
    }
    //小腿围
    public void doGetHealthdoLegGirthRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthdoLegGirthRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthdoLegGirthModel>>() {
            @Override
            public void success(ResponseData<HealthdoLegGirthModel> healthdoLegGirthModelResponseData, Response response) {
                Log.i(healthdoLegGirthModelResponseData.toString());
                cb.getHealthdoLegGirthRecords(healthdoLegGirthModelResponseData.getData());
                if (healthdoLegGirthModelResponseData.getStatus()!=200)
                {
                    Util.toastMsg(healthdoLegGirthModelResponseData.getMsg());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                cb.getHealthdoLegGirthRecords(null);
                super.failure(error);

            }
        });
    }
    public interface HealthRecordCallBack{

        void getHealthPysicalRecords(PysicalModel pysicalModel);
        void getHealthWeightRecords(HealthWeightModel healthWeightModel);
        void getHealthfatRecords(HealthFatModel healthFatModel);
        void getHealthcircumRecords(HealthCircrumModel healthCircrumModel);
        void getHealthwaistlineRecords(HealthWaistlineModel healthWaistlineModel);
        void getHealthhiplieRecords(HealthHiplieModel healthHiplieModel);
        void getHealthupArmGirthRecords(HealthUpArmGirthModel healthUpArmGirthModel);
        void getGetHealthupLegGirthRecords(HealthupLegGirthModel healthupLegGirthModel);
        void getHealthdoLegGirthRecords(HealthdoLegGirthModel healthdoLegGirthModel);
    }
}
