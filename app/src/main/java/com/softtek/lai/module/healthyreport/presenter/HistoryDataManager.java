package com.softtek.lai.module.healthyreport.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.module.healthyreport.net.HistoryDataService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 *
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryDataManager extends BasePresenter<HistoryDataManager.HistoryDataManagerCallback> {

    private HistoryDataService service;
    private String token;

    public HistoryDataManager(HistoryDataManagerCallback cb) {
        super(cb);
        service = ZillaApi.NormalRestAdapter.create(HistoryDataService.class);
        token = UserInfoModel.getInstance().getToken();
    }


    //获取历史数据
    public void getHistoryDataList(int type,int pageIndex, final boolean isPull) {
        if (!isPull&&getView()!=null){
            getView().dialogShow("加载中");
        }
        service.getHistoryDataList(token, type, UserInfoModel.getInstance().getUserId(),pageIndex,
                new RequestCallback<ResponseData<HistoryDataModel>>() {
                    @Override
                    public void success(ResponseData<HistoryDataModel> historyDataModelResponseData, Response response) {
                        if (!isPull) {
                            if (getView() != null) {
                                getView().dialogDissmiss();
                            }
                        } else {
                            if (getView() != null) {
                                getView().hidenPull();
                            }
                        }
                        if (historyDataModelResponseData.getStatus() == 200) {
                            if (getView() != null) {
                                getView().historyDataCallback(historyDataModelResponseData.getData());
                            }
                        }else {
                            Util.toastMsg(historyDataModelResponseData.getMsg());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (!isPull) {
                            if (getView() != null) {
                                getView().dialogDissmiss();
                            }
                        } else {
                            if (getView() != null) {
                                getView().hidenPull();
                            }
                        }
                        super.failure(error);

                    }
                });
    }

    //删除历史数据
    public void deleteHistoryData(int type,String ids) {
        if (getView() != null) {
            getView().dialogShow("删除中");
        }
        service.deleteHistoryData(token, type,ids, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i(responseData.toString());
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                if (responseData.getStatus() == 200) {
                    if (getView() != null) {
                        getView().deleteResult();
                    }
                } else {
                    Util.toastMsg(responseData.getMsg());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                super.failure(error);
            }
        });
    }


    public interface HistoryDataManagerCallback extends BaseView {
        void historyDataCallback(HistoryDataModel model);

        void deleteResult();

        void hidenPull();
    }
}
