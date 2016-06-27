package com.softtek.lai.module.sport.presenter;

import android.util.Log;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.SportData;
import com.softtek.lai.module.sport.model.TotalSportModel;
import com.softtek.lai.module.sport.net.SportService;
import com.softtek.lai.module.sport.view.RunSportActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 *
 */
public class SportManager {

    private String token;
    private SportService service;
    private GetMovementListCallBack getMovementListCallBack;
    private GetHistoryTotalMovementCallBack getHistoryTotalMovementCallBack;

    public SportManager(GetMovementListCallBack getMovementListCallBack) {
        this.getMovementListCallBack = getMovementListCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }
    public SportManager(){
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public SportManager(GetHistoryTotalMovementCallBack getHistoryTotalMovementCallBack) {
        this.getHistoryTotalMovementCallBack = getHistoryTotalMovementCallBack;
        token = UserInfoModel.getInstance().getToken();
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public void getMovementList() {
        service.getMovementList(token, new RequestCallback<ResponseData<List<HistorySportModel>>>() {
            @Override
            public void success(ResponseData<List<HistorySportModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("true", listResponseData.getData());
                        }

                        break;
                    case 100:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("false", new ArrayList<HistorySportModel>());
                        }

                        break;
                    default:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("false", new ArrayList<HistorySportModel>());
                            Util.toastMsg(listResponseData.getMsg());
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getMovementListCallBack != null) {
                    getMovementListCallBack.getMovementList("false", new ArrayList<HistorySportModel>());
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getHistoryTotalMovement() {
        service.getHistoryTotalMovement(token, new RequestCallback<ResponseData<TotalSportModel>>() {
            @Override
            public void success(ResponseData<TotalSportModel> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getHistoryTotalMovementCallBack != null) {
                            getHistoryTotalMovementCallBack.getHistoryTotalMovement("true", listResponseData.getData());
                        }

                        break;
                    case 100:
                        if (getHistoryTotalMovementCallBack != null) {
                            getHistoryTotalMovementCallBack.getHistoryTotalMovement("false",null);
                        }

                        break;
                    default:
                        if (getHistoryTotalMovementCallBack != null) {
                            getHistoryTotalMovementCallBack.getHistoryTotalMovement("false",null);
                            Util.toastMsg(listResponseData.getMsg());
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getHistoryTotalMovementCallBack != null) {
                    getHistoryTotalMovementCallBack.getHistoryTotalMovement("false", null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void submitSportData(final RunSportActivity activity, SportData data){
        service.submitSportData(UserInfoModel.getInstance().getToken(),
                data,
                new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        com.github.snowdream.android.util.Log.i(responseData.toString());
                        if(responseData.getStatus()!=200){
                            Util.toastMsg(responseData.getMsg());
                        }
                        if(activity!=null) {
                            activity.doSubmitResult(responseData.getStatus());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(activity!=null) {
                            activity.doSubmitResult(-1);
                        }
                        super.failure(error);
                    }
                });
    }


    public interface GetMovementListCallBack {

        void getMovementList(String type, List<HistorySportModel> list);
    }
    public interface GetHistoryTotalMovementCallBack {

        void getHistoryTotalMovement(String type, TotalSportModel model);
    }

}
