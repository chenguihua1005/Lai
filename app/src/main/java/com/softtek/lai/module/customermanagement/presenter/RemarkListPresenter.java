package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.RemarkModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 12/11/2017.
 */

public class RemarkListPresenter extends BasePresenter<RemarkListPresenter.RemarkListCallback> {
    CustomerService service;

    public RemarkListPresenter(RemarkListCallback baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }

    public void getRemarkList(String cludId, String mobile, int index, int size) {
        service.getRemarkOfCustomer(UserInfoModel.getInstance().getToken(), cludId, mobile, index, size, new Callback<ResponseData<RemarkModel>>() {
            @Override
            public void success(ResponseData<RemarkModel> responseData, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                int status = responseData.getStatus();
                if (200 == status) {
                    if (getView() != null) {
                        getView().getRemarkList(responseData.getData());
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

    public interface RemarkListCallback extends BaseView {
        void getRemarkList(RemarkModel model);
    }
}
