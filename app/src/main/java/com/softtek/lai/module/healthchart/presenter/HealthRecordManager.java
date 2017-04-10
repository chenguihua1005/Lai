package com.softtek.lai.module.healthchart.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.healthchart.model.HealthCircrumModel;
import com.softtek.lai.module.healthchart.model.HealthFatModel;
import com.softtek.lai.module.healthchart.model.HealthHiplieModel;
import com.softtek.lai.module.healthchart.model.HealthUpArmGirthModel;
import com.softtek.lai.module.healthchart.model.HealthWaistlineModel;
import com.softtek.lai.module.healthchart.model.HealthWeightModel;
import com.softtek.lai.module.healthchart.model.HealthdoLegGirthModel;
import com.softtek.lai.module.healthchart.model.HealthupLegGirthModel;
import com.softtek.lai.module.healthchart.model.PysicalModel;
import com.softtek.lai.module.healthchart.net.HealthyService;
import com.softtek.lai.utils.RequestCallback;

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
    private HealthRecordCallBack cb;

    public HealthRecordManager(HealthRecordCallBack context) {
        cb= context;
        token= UserInfoModel.getInstance().getToken();
        service= ZillaApi.NormalRestAdapter.create(HealthyService.class);
    }
    //获取体脂数据
    public void doGetHealthPysicalRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthPysicalRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<PysicalModel>>() {
            @Override
            public void success(ResponseData<PysicalModel> pysicalModelResponseData, Response response) {
                Log.i(pysicalModelResponseData.toString());
                if (pysicalModelResponseData.getStatus()==200) {
                    cb.getHealthPysicalRecords(pysicalModelResponseData.getData());
                }else {
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
    //获取体重数据
    public void doGetHealthWeightRecords(String Startdate,String Enddate,int i) {
        service.GetHealthWeightRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthWeightModel>>() {
            @Override
            public void success(ResponseData<HealthWeightModel> healthWeightModelResponseData, Response response) {
                Log.i(healthWeightModelResponseData.toString());
                if (healthWeightModelResponseData.getStatus()==200) {
                    cb.getHealthWeightRecords(healthWeightModelResponseData.getData());
                }else {
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
    //获取内脂数据
    public void doGetHealthfatRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthfatRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthFatModel>>() {
            @Override
            public void success(ResponseData<HealthFatModel> healthFatModelResponseData, Response response) {
                Log.i(healthFatModelResponseData.toString());
                if (healthFatModelResponseData.getStatus()==200) {
                    cb.getHealthfatRecords(healthFatModelResponseData.getData());
                }else {
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
    //获取胸围数据
    public void doGetHealthcircumRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthcircumRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthCircrumModel>>() {
            @Override
            public void success(ResponseData<HealthCircrumModel> healthCircrumModelResponseData, Response response) {
                Log.i(healthCircrumModelResponseData.toString());
                if (healthCircrumModelResponseData.getStatus()==200) {
                    cb.getHealthcircumRecords(healthCircrumModelResponseData.getData());
                }else {
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
    //获取腰围数据
    public void doGetHealthwaistlineRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthwaistlineRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthWaistlineModel>>() {
            @Override
            public void success(ResponseData<HealthWaistlineModel> healthWaistlineModelResponseData, Response response) {
                Log.i(healthWaistlineModelResponseData.toString());
                if (healthWaistlineModelResponseData.getStatus()==200) {
                    cb.getHealthwaistlineRecords(healthWaistlineModelResponseData.getData());
                }else {
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
    //获取臀围数据
    public void doGetHealthhiplieRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthhiplieRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthHiplieModel>>() {
            @Override
            public void success(ResponseData<HealthHiplieModel> healthHiplieModelResponseData, Response response) {
                Log.i(healthHiplieModelResponseData.toString());
                if (healthHiplieModelResponseData.getStatus()==200) {
                    cb.getHealthhiplieRecords(healthHiplieModelResponseData.getData());
                }else {
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

    //获取上臂围数据
    public void doGetHealthupArmGirthRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthupArmGirthRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthUpArmGirthModel>>() {
            @Override
            public void success(ResponseData<HealthUpArmGirthModel> healthUpArmGirthModelResponseData, Response response) {
                Log.i(healthUpArmGirthModelResponseData.toString());
                if (healthUpArmGirthModelResponseData.getStatus()==200) {
                    cb.getHealthupArmGirthRecords(healthUpArmGirthModelResponseData.getData());
                }else {
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
    //获取大腿围数据
    public void doGetHealthupLegGirthRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthupLegGirthRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthupLegGirthModel>>() {
            @Override
            public void success(ResponseData<HealthupLegGirthModel> healthupLegGirthModelResponseData, Response response) {
                Log.i(healthupLegGirthModelResponseData.toString());
                if (healthupLegGirthModelResponseData.getStatus()==200) {
                    cb.getGetHealthupLegGirthRecords(healthupLegGirthModelResponseData.getData());
                }else {
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
    //获取小腿围数据
    public void doGetHealthdoLegGirthRecords(String Startdate,String Enddate,int i) {
        service.doGetHealthdoLegGirthRecords(token, Startdate, Enddate, i, new RequestCallback<ResponseData<HealthdoLegGirthModel>>() {
            @Override
            public void success(ResponseData<HealthdoLegGirthModel> healthdoLegGirthModelResponseData, Response response) {
                Log.i(healthdoLegGirthModelResponseData.toString());
                if (healthdoLegGirthModelResponseData.getStatus()==200)
                {
                    cb.getHealthdoLegGirthRecords(healthdoLegGirthModelResponseData.getData());
                }else {
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
