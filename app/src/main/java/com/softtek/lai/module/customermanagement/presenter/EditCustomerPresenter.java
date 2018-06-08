package com.softtek.lai.module.customermanagement.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.BasicInfoModel;
import com.softtek.lai.module.customermanagement.model.CustomerInfoModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 11/24/2017.
 */

public class EditCustomerPresenter extends BasePresenter<EditCustomerPresenter.UpdateCustomerCallback> {
    CustomerService service;

    public EditCustomerPresenter(UpdateCustomerCallback baseView) {
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


    public void updateCustomerInfo(CustomerInfoModel model) {
        service.updateCustomer(UserInfoModel.getInstance().getToken(), model, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                switch (status) {
                    case 200:
                        if (getView() != null) {
                            getView().UpdateCustomerSucsess();
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                super.failure(error);
            }
        });
    }

    public void removeCustomer(String mobile) {
        service.removeCustomer(UserInfoModel.getInstance().getToken(), mobile, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                switch (status) {
                    case 200:
                        Util.toastMsg(responseData.getMsg());
                        if (getView() != null) {
                            getView().removeCustomerSuccess();
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
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


    public interface UpdateCustomerCallback extends BaseView {
        void getBasicInfo(BasicInfoModel model);

        void UpdateCustomerSucsess();

        void removeCustomerSuccess();
    }
}
