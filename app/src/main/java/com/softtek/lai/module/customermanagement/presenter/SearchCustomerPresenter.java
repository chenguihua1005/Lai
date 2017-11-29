package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.SearchCustomerOuterModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/29/2017.
 */

public class SearchCustomerPresenter extends BasePresenter<SearchCustomerPresenter.SearchCustomerCallBack> {
    CustomerService service;

    public SearchCustomerPresenter(SearchCustomerCallBack baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void searchCustomer(String input, int pageIndex, int pageSize) {
        service.findCustomers(UserInfoModel.getInstance().getToken(), input, pageIndex, pageSize, new RequestCallback<ResponseData<SearchCustomerOuterModel>>() {
            @Override
            public void success(ResponseData<SearchCustomerOuterModel> data, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                if (data.getStatus() == 200) {
                    if (getView() != null) {
                        getView().disMissProgressBar();
                        getView().searchCustomerCallBack(data.getData());
                    }
                } else {
                    Util.toastMsg(data.getMsg());
                    getView().disMissProgressBar();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                getView().disMissProgressBar();
            }
        });


    }

    public interface SearchCustomerCallBack extends BaseView {
        void searchCustomerCallBack(SearchCustomerOuterModel model);
        void disMissProgressBar();

        void hidenLoading();
    }
}
