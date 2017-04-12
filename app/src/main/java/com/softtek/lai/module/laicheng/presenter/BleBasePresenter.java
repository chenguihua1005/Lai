package com.softtek.lai.module.laicheng.presenter;

import android.widget.Toast;

import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.laiClassroom.net.SearchService;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
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
//                        token = bleTokenResponse.getAccess_token();
                        getView().getTokenSuccess(bleTokenResponse);
//                        storeOrSendCalcRsData(74.2f, 288.5f, 293.8f, 27.0f, 251.3f, 244.4f, 255.2f, 260.5f, 23.4f, 216.0f, 211.0f);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
//                        Toast.makeText(getApplicationContext(), "获取token失败", Toast.LENGTH_SHORT).show();
//                        token = "";
                        getView().getTokenFailed();
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
                        getView().checkMacSuccess();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        getView().checkMacFailed();
                    }
                });
    }

    public void upLoadImpedance(UploadImpedanceModel model) {
//        ZillaApi.getCustomRESTAdapter(BASE_URL + "DataSync/UploadData", new RequestInterceptor() {
//            @Override
//            public void intercept(RequestFacade request) {
//
//            }
//        })

        ZillaApi.NormalRestAdapter
                .create(BleService.class).uploadImpedance(model, UserInfoModel.getInstance().getUserId(), 1, new RequestCallback<BleMainData>() {
            @Override
            public void success(BleMainData data, Response response) {
                getView().upLoadImpedanceSuccess(data);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
//                initUiByBleFailed();
                getView().upLoadImpedanceFailed();
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
    }
}
