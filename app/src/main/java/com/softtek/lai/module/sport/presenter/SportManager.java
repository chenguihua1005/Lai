package com.softtek.lai.module.sport.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.SportData;
import com.softtek.lai.module.sport.model.TotalSportModel;
import com.softtek.lai.module.sport.net.SportService;
import com.softtek.lai.module.sport.util.SportUtil;
import com.softtek.lai.module.sport.view.RunSportActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 4/8/2016.
 */
public class SportManager {

    private SportService service;
    private GetMovementListCallBack getMovementListCallBack;
    private GetHistoryTotalMovementCallBack getHistoryTotalMovementCallBack;

    public SportManager(GetMovementListCallBack getMovementListCallBack) {
        this.getMovementListCallBack = getMovementListCallBack;
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public SportManager() {
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }

    public SportManager(GetHistoryTotalMovementCallBack getHistoryTotalMovementCallBack) {
        this.getHistoryTotalMovementCallBack = getHistoryTotalMovementCallBack;
        service = ZillaApi.NormalRestAdapter.create(SportService.class);
    }
    public void getMovementList(String pageIndex) {
        service.getMovementList(UserInfoModel.getInstance().getToken(),pageIndex, new RequestCallback<ResponseData<List<HistorySportModel>>>() {
            @Override
            public void success(ResponseData<List<HistorySportModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("true",listResponseData);
                        }

                        break;
                    case 100:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("false", null);
                        }

                        break;
                    default:
                        if (getMovementListCallBack != null) {
                            getMovementListCallBack.getMovementList("false", null);
                            Util.toastMsg(listResponseData.getMsg());
                        }
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getMovementListCallBack != null) {
                    getMovementListCallBack.getMovementList("false",null);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getHistoryTotalMovement() {
        service.getHistoryTotalMovement(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<TotalSportModel>>() {
            @Override
            public void success(ResponseData<TotalSportModel> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (getHistoryTotalMovementCallBack != null) {
                            getHistoryTotalMovementCallBack.getHistoryTotalMovement("true", listResponseData.getData());
                        }

                        break;
                    case 100:
                        if (getHistoryTotalMovementCallBack != null) {
                            getHistoryTotalMovementCallBack.getHistoryTotalMovement("false", null);
                        }

                        break;
                    default:
                        if (getHistoryTotalMovementCallBack != null) {
                            getHistoryTotalMovementCallBack.getHistoryTotalMovement("false", null);
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

    public void submitSportData(final RunSportActivity activity, SportData data) {
        service.submitSportData(UserInfoModel.getInstance().getToken(),
                data,
                new RequestCallback<ResponseData>() {
                    @Override
                    public void success(ResponseData responseData, Response response) {
                        com.github.snowdream.android.util.Log.i(responseData.toString());
                        if (responseData.getStatus() != 200) {
                            Util.toastMsg(responseData.getMsg());
                        }else if(responseData.getStatus() == 200){
                            SportUtil.getInstance().deleteSport();
                        }
                        if (activity != null) {
                            activity.doSubmitResult(responseData.getStatus());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (activity != null) {
                            activity.doSubmitResult(-1);
                        }
                        super.failure(error);
                    }
                });
    }


    public interface GetMovementListCallBack {

        void getMovementList(String type,ResponseData<List<HistorySportModel>> listResponseData);
    }

    public interface GetHistoryTotalMovementCallBack {

        void getHistoryTotalMovement(String type, TotalSportModel model);
    }

}
