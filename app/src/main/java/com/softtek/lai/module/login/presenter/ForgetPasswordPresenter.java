package com.softtek.lai.module.login.presenter;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.login.model.IdentifyModel;
import com.softtek.lai.module.login.net.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/11/2017.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordPresenter.ForgetPasswordView>{

    private LoginService service;

    public ForgetPasswordPresenter(ForgetPasswordView baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    public void getIdentify(String phone, String state) {
        service.getIdentify(phone, state, new Callback<ResponseData<IdentifyModel>>() {
            @Override
            public void success(ResponseData<IdentifyModel> stringResponseData, Response response) {
                if (stringResponseData.getStatus() != 200) {
                    if(getView()!=null){
                        getView().getIdentifyCallback(false);
                    }
                    Util.toastMsg(stringResponseData.getMsg());
                } else {
                    if(getView()!=null){
                        getView().getIdentifyCallback(true);
                    }
                }
                Log.i("验证码获取结果>>>>" + stringResponseData.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()!=null){
                    getView().getIdentifyCallback(false);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    public void checkIdentify(final String phone, final String identify) {
        if(getView()!=null){
            getView().dialogShow("重置密码");
        }
        service.checkIdentify(phone, identify, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i("校验结果>>>" + responseData.toString());
                int status = responseData.getStatus();
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
                switch (status) {
                    case 200:
                        if(getView()!=null){
                            getView().checkIdentifySuccess(phone,identify);
                        }
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView()!=null){
                    getView().dialogDissmiss();
                }
            }
        });
    }

    public interface ForgetPasswordView extends BaseView{
        void getIdentifyCallback(boolean result);
        void checkIdentifySuccess(String phone,String identify);
    }
}
