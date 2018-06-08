package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.text.Html;
import android.widget.EditText;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.login.model.IdentifyModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.RegexUtil;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 4/11/2017.
 */

public class RegistPresenter extends BasePresenter<RegistPresenter.RegistView>{

    private LoginService service;
    public RegistPresenter(RegistView baseView) {
        super(baseView);
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }

    public void doRegist(final String userName, String password, String HxAccountId, String identify) {
        if(getView()!=null){
            getView().dialogShow("注册中");
        }
        service.doRegist(userName, password, HxAccountId, identify, new Callback<ResponseData<UserModel>>() {
            @Override
            public void success(ResponseData<UserModel> userResponseData, Response response) {
                Log.i(userResponseData.toString());
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        if(getView()!=null){
                            getView().registSuccess(userResponseData.getData());
                        }

                        break;
                    default:
                        if(getView()!=null){
                            getView().registFail();
                        }
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (getView()!=null){
                    getView().registFail();
                }
                ZillaApi.dealNetError(error);

            }
        });

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

    public boolean validateGetIdentify(Context context, EditText et_phone, EditText et_password, EditText et_rePassword) {
        String phone = et_phone.getText().toString();

        if ("".equals(phone)) {
            et_phone.requestFocus();
            et_phone.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.phoneValidateNull) + "</font>"));
            return false;
        }
        if (!RegexUtil.match(context.getString(R.string.phonePattern), phone)) {
            et_phone.requestFocus();
            et_phone.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.phoneValidate) + "</font>"));
            return false;
        }
        String password = et_password.getText().toString();
        if ("".equals(password)) {
            et_password.requestFocus();
            et_password.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.passwordValidateNull) + "</font>"));
            return false;
        }
        if (!RegexUtil.match(context.getString(R.string.psdReg), password)) {
            et_password.requestFocus();
            et_password.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.passwordValidate) + "</font>"));
            return false;
        }
        String rePassword = et_rePassword.getText().toString();
        if ("".equals(rePassword)) {
            et_rePassword.requestFocus();
            et_rePassword.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.confirmPasswordNull) + "</font>"));
            return false;
        }
        if (!rePassword.equals(password)) {
            et_rePassword.requestFocus();
            et_rePassword.setError(Html.fromHtml("<font color=#FFFFFF>" + context.getString(R.string.confirmPassword) + "</font>"));
            return false;
        }
        return true;
    }


    public interface RegistView extends BaseView{
        void registSuccess(UserModel model);
        void registFail();
        void getIdentifyCallback(boolean result);
    }
}
