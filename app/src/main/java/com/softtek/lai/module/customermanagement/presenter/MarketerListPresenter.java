package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.CustomerListModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/23/2017.
 */

public class MarketerListPresenter extends BasePresenter<MarketerListPresenter.MarketingStaffCallback> {
    CustomerService service;

    public MarketerListPresenter(MarketingStaffCallback baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void getMarketingStaffList(int pageIndex, int pageSize) {
        service.getMarketingStaffList(UserInfoModel.getInstance().getToken(), pageIndex, pageSize, new RequestCallback<ResponseData<CustomerListModel>>() {
            @Override
            public void success(ResponseData<CustomerListModel> data, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
                if (data.getStatus() == 200) {
                    if (getView() != null) {
                        getView().getMarketingStaffList(data.getData());
                    }
                } else {
                    Util.toastMsg(data.getMsg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().dialogDissmiss();
                    getView().hidenLoading();
                }
            }
        });


    }

    public interface MarketingStaffCallback extends BaseView {
        void getMarketingStaffList(CustomerListModel list);

        void hidenLoading();
    }
}
