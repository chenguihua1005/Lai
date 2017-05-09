package com.softtek.lai.module.laicheng.presenter;

import android.util.Log;
import android.widget.Toast;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.laiClassroom.net.SearchService;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.UploadImpedanceModel;
import com.softtek.lai.module.laicheng.net.BleService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 2017/4/10.
 */

public class BleBasePresenter extends BasePresenter<BleBasePresenter.BleBaseView> {
    private String BASE_URL = "http://qa-api.yunyingyang.com/";
//    private String BASE_URL = "https://api.yunyingyang.com/";

    public BleBasePresenter(BleBasePresenter.BleBaseView baseView) {
        super(baseView);
    }

    public void getToken() {
        ZillaApi.getCustomRESTAdapter(BASE_URL + "oauth/token/", new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        }).create(BleService.class)
                .getBleToken("client_credentials", "shhcieurjfn734js", "qieow8572jkcv", new RequestCallback<BleTokenResponse>() {
                    @Override
                    public void success(BleTokenResponse bleTokenResponse, Response response) {
                        if (getView() != null) {
                            getView().getTokenSuccess(bleTokenResponse);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if (getView() != null) {
                            getView().getTokenFailed();
                        }
                    }
                });
    }

    public void checkMac(String token, String mac) {
        ZillaApi.getCustomRESTAdapter(BASE_URL + "DataSync/ExistEquipment/", new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        }).create(BleService.class)
                .checkMac(token, mac, new RequestCallback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        if (getView() != null) {
                            getView().checkMacSuccess();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if (getView() != null) {
                            getView().checkMacFailed();
                        }
                    }
                });
    }

    public void upLoadImpedance(UploadImpedanceModel model,long accountId,int type,String classId) {
        ZillaApi.NormalRestAdapter.create(BleService.class).
                uploadImpedance(UserInfoModel.getInstance().getToken(),model,accountId, type,classId, new RequestCallback<ResponseData<BleMainData>>() {
            @Override
            public void success(ResponseData<BleMainData> data, Response response) {
                if (data.getStatus() == 200) {
                    if (getView() != null) {
                        getView().upLoadImpedanceSuccess(data.getData());
                    }
                    Log.d("BleMainData--------", data.toString());
                }else {
                    if (getView() != null) {
                        getView().upLoadImpedanceFailed();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().upLoadImpedanceFailed();
                }
            }
        });
    }

    public void getLastData(int type){
        ZillaApi.NormalRestAdapter.create(BleService.class)
                .getLastData(UserInfoModel.getInstance().getToken(),type, new RequestCallback<ResponseData<LastInfoData>>() {
                    @Override
                    public void success(ResponseData<LastInfoData> lastInfoData, Response response) {
                        if (getView() != null) {
                            getView().refreshLastSuccess(lastInfoData.getData());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        if (getView() != null) {
                            getView().refreshLastFailed();
                        }
                    }
                });
    }


    public interface BleBaseView extends BaseView {
        void checkMacSuccess();

        void checkMacFailed();

        void getTokenSuccess(BleTokenResponse response);

        void getTokenFailed();

        void upLoadImpedanceSuccess(BleMainData data);

        void upLoadImpedanceFailed();

        void refreshLastSuccess(LastInfoData lastData);

        void refreshLastFailed();
    }
}
