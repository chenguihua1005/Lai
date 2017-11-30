package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.CustomerInfoModel;
import com.softtek.lai.module.customermanagement.model.FindCustomerModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/24/2017.
 */

public class SaveCustomerPresenter extends BasePresenter<SaveCustomerPresenter.SaveCustomerCallback> {
    CustomerService service;

    public SaveCustomerPresenter(SaveCustomerCallback baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(CustomerService.class);
    }


    public void getDetailOfCustomer(String mobile) {
        service.getDetailOfCustomer(UserInfoModel.getInstance().getToken(), mobile, new RequestCallback<ResponseData<FindCustomerModel>>() {
            @Override
            public void success(ResponseData<FindCustomerModel> responseData, Response response) {
                int status = responseData.getStatus();
                if (200 == status) {
                    if (getView() != null) {
                        getView().getDetailOfCustomer(responseData.getData());
                    }
                } else {
                    Util.toastMsg(responseData.getMsg());
                }
                if (getView() != null) {
                    getView().disMissLoadingDialog();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                if (getView() != null) {
                    getView().disMissLoadingDialog();
                }
            }
        });


    }

    public void saveCustomerInfo(CustomerInfoModel model) {
        service.saveCustomer(UserInfoModel.getInstance().getToken(), model, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        Util.toastMsg(responseData.getMsg());
                        if (getView() != null) {
                            getView().SaveCustomerSucsess();
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
        void SaveCustomerSucsess();

        void getDetailOfCustomer(FindCustomerModel model);

        void disMissLoadingDialog();
    }
}
