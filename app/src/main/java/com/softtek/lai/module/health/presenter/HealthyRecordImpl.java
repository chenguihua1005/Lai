package com.softtek.lai.module.health.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.net.HealthyService;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

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

//    @Override
//    public void GetHealthWeightRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.GetHealthWeightRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthWeightModel>>() {
//            @Override
//            public void success(ResponseData<HealthWeightModel> healthWeightModelResponseData, Response response) {
//                int states=healthWeightModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthWeightModel healthWeightModel=(HealthWeightModel) healthWeightModelResponseData.getData();
//                        EventBus.getDefault().post(healthWeightModel);
//                        Util.toastMsg(healthWeightModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthWeightModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void doGetHealthfatRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthfatRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthFatModel>>() {
//            @Override
//            public void success(ResponseData<HealthFatModel> healthFatModelResponseData, Response response) {
//                int states=healthFatModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthFatModel healthFatModel=(HealthFatModel) healthFatModelResponseData.getData();
//                        EventBus.getDefault().post(healthFatModel);
//                        Util.toastMsg(healthFatModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthFatModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//
//
//        });
//    }
//
//    @Override
//    public void doGetHealthcircumRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthcircumRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthCircrumModel>>() {
//            @Override
//            public void success(ResponseData<HealthCircrumModel> healthCircrumModelResponseData, Response response) {
//                int states=healthCircrumModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthCircrumModel healthCircrumModel=(HealthCircrumModel) healthCircrumModelResponseData.getData();
//                        EventBus.getDefault().post(healthCircrumModel);
//                        Util.toastMsg(healthCircrumModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthCircrumModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void doGetHealthwaistlineRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthwaistlineRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthWaistlineModel>>() {
//            @Override
//            public void success(ResponseData<HealthWaistlineModel> healthWaistlineModelResponseData, Response response) {
//                int states=healthWaistlineModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthWaistlineModel healthWaistlineModel=(HealthWaistlineModel) healthWaistlineModelResponseData.getData();
//                        EventBus.getDefault().post(healthWaistlineModel);
//                        Util.toastMsg(healthWaistlineModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthWaistlineModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void doGetHealthhiplieRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthhiplieRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthHiplieModel>>() {
//            @Override
//            public void success(ResponseData<HealthHiplieModel> healthHiplieModelResponseData, Response response) {
//                int states=healthHiplieModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthHiplieModel healthHiplieModel=(HealthHiplieModel) healthHiplieModelResponseData.getData();
//                        EventBus.getDefault().post(healthHiplieModel);
//                        Util.toastMsg(healthHiplieModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthHiplieModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void dodoGetHealthupArmGirthRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthupArmGirthRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthUpArmGirthModel>>() {
//            @Override
//            public void success(ResponseData<HealthUpArmGirthModel> healthUpArmGirthModelResponseData, Response response) {
//                int states=healthUpArmGirthModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthUpArmGirthModel healthUpArmGirthModel=(HealthUpArmGirthModel) healthUpArmGirthModelResponseData.getData();
//                        EventBus.getDefault().post(healthUpArmGirthModel);
//                        Util.toastMsg(healthUpArmGirthModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthUpArmGirthModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void doGetHealthupLegGirthRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthupLegGirthRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthupLegGirthModel>>() {
//            @Override
//            public void success(ResponseData<HealthupLegGirthModel> healthupLegGirthModelResponseData, Response response) {
//                int states=healthupLegGirthModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthupLegGirthModel healthupLegGirthModel=(HealthupLegGirthModel) healthupLegGirthModelResponseData.getData();
//                        EventBus.getDefault().post(healthupLegGirthModel);
//                        Util.toastMsg(healthupLegGirthModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthupLegGirthModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void doGetHealthdoLegGirthRecords(String Startdate, String Enddate, int i) {
//        String token= UserInfoModel.getInstance().getToken();
//        serveice.doGetHealthdoLegGirthRecords(token, Startdate, Enddate, i, new Callback<ResponseData<HealthdoLegGirthModel>>() {
//            @Override
//            public void success(ResponseData<HealthdoLegGirthModel> healthdoLegGirthModelResponseData, Response response) {
//                int states=healthdoLegGirthModelResponseData.getStatus();
//                switch (states)
//                {
//                    case 200:
//                        HealthdoLegGirthModel healthdoLegGirthModel=(HealthdoLegGirthModel) healthdoLegGirthModelResponseData.getData();
//                        EventBus.getDefault().post(healthdoLegGirthModel);
//                        Util.toastMsg(healthdoLegGirthModelResponseData.getMsg());
//                        break;
//                    default:
//                        Util.toastMsg(healthdoLegGirthModelResponseData.getMsg());
//                        break;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ZillaApi.dealNetError(error);
//                error.printStackTrace();
//            }
//        });
//    }


}
