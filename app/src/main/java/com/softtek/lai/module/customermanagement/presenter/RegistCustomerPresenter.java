package com.softtek.lai.module.customermanagement.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.module.login.model.IdentifyModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/24/2017.
 */

public class RegistCustomerPresenter extends BasePresenter<RegistCustomerPresenter.RegisterForCustomerCallback> {
    CustomerService service;

    public RegistCustomerPresenter(RegisterForCustomerCallback baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void getIdentify(String phone, String state) {
        service.getIdentify(phone, state, new Callback<ResponseData<IdentifyModel>>() {
            @Override
            public void success(ResponseData<IdentifyModel> stringResponseData, Response response) {
                if (stringResponseData.getStatus() != 200) {
                    if (getView() != null) {
                        getView().getIdentifyCallback(false);
                    }
                    Util.toastMsg(stringResponseData.getMsg());
                } else {
                    if (getView() != null) {
                        getView().getIdentifyCallback(true);
                    }
                }
                Log.i("验证码获取结果>>>>" + stringResponseData.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() != null) {
                    getView().getIdentifyCallback(false);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public interface RegisterForCustomerCallback extends BaseView {
        void getIdentifyCallback(boolean result);
    }
}
