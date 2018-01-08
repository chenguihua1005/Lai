package com.softtek.lai.module.customermanagement.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.customermanagement.model.SituationOfMobileModel;
import com.softtek.lai.module.customermanagement.service.CustomerService;
import com.softtek.lai.module.login.model.IdentifyModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
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
                int status = stringResponseData.getStatus();
                if (status != 200) {
                    if (getView() != null) {
                        getView().getIdentifyCallback(false, status);
                    }

//                    Util.toastMsg(stringResponseData.getMsg());
                } else {
                    if (getView() != null) {
                        getView().getIdentifyCallback(true, 200);
                    }
                }
                SharedPreferenceService.getInstance().put("identify_customer",stringResponseData.getData().getIdentify());
                Log.i("验证码获取结果>>>>" + stringResponseData.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() != null) {
                    getView().getIdentifyCallback(false, 0);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void getSituationOfTheMobile(String mobile) {
        service.getSituationOfTheMobile(UserInfoModel.getInstance().getToken(), mobile, "", new Callback<ResponseData<SituationOfMobileModel>>() {
            @Override
            public void success(ResponseData<SituationOfMobileModel> responseData, Response response) {
                if (getView() != null) {
                    getView().dialogDissmiss();
                }
                int status = responseData.getStatus();
                if (200 == status) {
                    SituationOfMobileModel model = responseData.getData();
//                    boolean IsLocked = model.isLocked();//是否被锁定，true-是，false-否
//                    boolean IsDownline = model.isDownline();//是否是下线，true-是，false-否
                    boolean IsRegistered = model.isRegistered();//是否注册，true-是，false-否
                    boolean IsInMyClub = model.isInMyClub();//是否是本俱乐部客户，true-是，false-否
                    if (getView() != null) {
                        getView().hasInClub(IsRegistered, IsInMyClub);
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

    public interface RegisterForCustomerCallback extends BaseView {
        void getIdentifyCallback(boolean result, int statusCode);

        void hasInClub(boolean IsRegistered, boolean hasInClub);//是否已经在俱乐部
    }
}
