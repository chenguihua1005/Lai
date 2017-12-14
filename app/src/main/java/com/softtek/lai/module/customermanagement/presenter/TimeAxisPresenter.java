package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.TimeAxisModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 12/11/2017.
 */

public class TimeAxisPresenter extends BasePresenter<TimeAxisPresenter.TimeAxisCallBack> {
    CustomerService service;

    public TimeAxisPresenter(TimeAxisCallBack baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }


    public void getTimeAxisOfCustomer(String cludId, String mobile, int index, int size) {
        service.getTimeAxisOfCustomer(UserInfoModel.getInstance().getToken(), cludId, mobile, index, size, new Callback<ResponseData<TimeAxisModel>>() {
            @Override
            public void success(ResponseData<TimeAxisModel> responseData, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                int status = responseData.getStatus();
                if (200 == status) {
                    if (getView() != null) {
                        getView().getTimeAxisOfCustomer(responseData.getData());
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
                ZillaApi.dealNetError(error);
            }
        });

    }


    public interface TimeAxisCallBack extends BaseView {
        void getTimeAxisOfCustomer(TimeAxisModel model);
    }
}
