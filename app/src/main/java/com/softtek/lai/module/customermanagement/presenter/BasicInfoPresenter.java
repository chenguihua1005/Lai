package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.BasicInfoModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 12/8/2017.
 */

public class BasicInfoPresenter extends BasePresenter<BasicInfoPresenter.BasicInfoCallBack> {
    private CustomerService service;

    public BasicInfoPresenter(BasicInfoCallBack baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void getCustomerBasicInfo(String mobile) {
        service.getBasicsOfCustomer(UserInfoModel.getInstance().getToken(), mobile, new Callback<ResponseData<BasicInfoModel>>() {
            @Override
            public void success(ResponseData<BasicInfoModel> responseData, Response response) {
                int status = responseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                if (200 == status) {
                    if (getView() != null) {
                        getView().getBasicInfo(responseData.getData());
                    }
                } else {
                    Util.toastMsg(responseData.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
            }
        });
    }

    public interface BasicInfoCallBack extends BaseView {
        void getBasicInfo(BasicInfoModel model);
    }


}
