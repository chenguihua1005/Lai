/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.EditText;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.File.view.CreatFlleActivity;
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
 * Created by jerry.guan on 3/3/2016.
 */
public class RegistPresenterImpl implements IRegistPresenter {

    private LoginService service;
    private Context context;
    private IdentifyCallBack callBack;

    public RegistPresenterImpl(Context context, IdentifyCallBack callBack) {
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public void doRegist(String userName, String password, EditText et_identify) {
        String identify = et_identify.getText().toString();
        service.doRegist(userName, password, identify, new Callback<ResponseData<UserModel>>() {
            @Override
            public void success(ResponseData<UserModel> userResponseData, Response response) {
                Log.i(userResponseData.toString());
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        UserInfoModel.getInstance().saveUserCache(userResponseData.getData());
                        context.startActivity(new Intent(context, CreatFlleActivity.class));
                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("注册失败");
                ZillaApi.dealNetError(error);

            }
        });

    }

    @Override
    public void getIdentify(String phone, String state) {
        service.getIdentify(phone, state, new Callback<ResponseData<IdentifyModel>>() {
            @Override
            public void success(ResponseData<IdentifyModel> stringResponseData, Response response) {
                //SharedPreferenceService.getInstance().put("identify", stringResponseData.getData().getIdentify());
                if (stringResponseData.getStatus() != 200) {
                    callBack.getIdentifyCallback(false);
                    Util.toastMsg(stringResponseData.getMsg());
                } else {
                    callBack.getIdentifyCallback(true);
                }
                Log.i("验证码获取结果>>>>" + stringResponseData.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);
                callBack.getIdentifyCallback(false);
            }
        });
    }

    @Override
    public boolean validateGetIdentify(EditText et_phone, EditText et_password, EditText et_rePassword) {
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

    public interface IdentifyCallBack {

        void getIdentifyCallback(boolean result);
    }
}
