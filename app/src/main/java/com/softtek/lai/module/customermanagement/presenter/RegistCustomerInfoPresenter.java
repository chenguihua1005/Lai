package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.CustomerInfoModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/24/2017.
 */

public class RegistCustomerInfoPresenter extends BasePresenter<RegistCustomerInfoPresenter.SaveCustomerCallback> {
    CustomerService service;

    public RegistCustomerInfoPresenter(SaveCustomerCallback baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void registerForCustomer(CustomerInfoModel model) {
        service.registerForCustomer(UserInfoModel.getInstance().getToken(), model, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        Util.toastMsg(responseData.getMsg());
                        if (getView() != null) {
                            getView().registerForCustomerSucsess();
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });


    }


    public interface SaveCustomerCallback extends BaseView {
        void registerForCustomerSucsess();
    }
}
