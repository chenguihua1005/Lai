/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.ggx.widgets.view.CustomProgress;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.jpush.JpushSet;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.home.view.ValidateCertificationActivity;
import com.softtek.lai.module.login.model.EMChatAccountModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.service.DaemonService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public class LoginPresenterImpl implements ILoginPresenter {

    private Context context;
    private LoginService service;

    public LoginPresenterImpl(Context context) {
        this.context = context;
        service = ZillaApi.NormalRestAdapter.create(LoginService.class);
    }
    @Override
    public void getEMChatAccount(final ProgressDialog dialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getEMChatAccount(token, new Callback<ResponseData<EMChatAccountModel>>() {
            @Override
            public void success(ResponseData<EMChatAccountModel> responseData, Response response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        EMChatAccountModel model = responseData.getData();
                        EventBus.getDefault().post(model);
                        break;
                    case 300:
                        EventBus.getDefault().post(null);
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void getUpdateName(String accountId, final String userName, final ProgressDialog dialog) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.getUpdateName(token, accountId, userName, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        UserModel userModel = UserInfoModel.getInstance().getUser();
                        userModel.setNickname(userName);
                        UserInfoModel.getInstance().saveUserCache(userModel);
                        ((AppCompatActivity) context).finish();
                        break;
                    default:
                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                ZillaApi.dealNetError(error);
            }
        });
    }

    @Override
    public void doLogin(final String userName, final String password, final CustomProgress dialog, final TextView tv) {
        PackageManager pm = LaiApplication.getInstance().getPackageManager();
        StringBuffer buffer = new StringBuffer();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            buffer.append("计步类型=SENSOR_STEP_COUNTER");
        } else if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {
            buffer.append("计步类型=SENSOR_ACCELEROMETER");
        } else {
            buffer.append("计步类型=不支持");
        }
        service.doLogin(buffer.toString(), userName, password, new Callback<ResponseData<UserModel>>() {
            @Override
            public void success(final ResponseData<UserModel> userResponseData, Response response) {
                if (dialog != null) dialog.dismiss();
                if (tv != null) {
                    tv.setEnabled(true);
                }
                int status = userResponseData.getStatus();
                switch (status) {
                    case 200:
                        final UserModel model = userResponseData.getData();
                        JpushSet set = new JpushSet(context);
                        set.setAlias(model.getMobile());
                        set.setStyleBasic();
                        UserInfoModel.getInstance().saveUserCache(model);
//                        SharedPreferenceService.getInstance().put(Constants.USER, userName);
//                        SharedPreferenceService.getInstance().put(Constants.PDW, password);
                        //开启登录服务
//                        context.getApplicationContext().startService(new Intent(context, HXLoginService.class));
                        //如果用户加入了跑团
                        if ("1".equals(model.getIsJoin())) {
                            stepDeal(context, model.getUserid(), StringUtils.isEmpty(model.getTodayStepCnt()) ? 0 : Long.parseLong(model.getTodayStepCnt()));
                        }
                        final String token = userResponseData.getData().getToken();
                        if ("0".equals(model.getIsCreatInfo())) {
                            //如果没有创建档案且性别不是2才算没创建档案
                            UserInfoModel.getInstance().setToken("");
                            Intent intent = new Intent(context, CreatFlleActivity.class);
                            intent.putExtra("token", token);
                            context.startActivity(intent);
                        } /*else if (MD5.md5WithEncoder("000000").equals(password)) {
                            UserInfoModel.getInstance().setToken("");
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                                    .setTitle(context.getString(R.string.login_out_title))
                                    .setMessage("您正在使用默认密码, 为了您的账户安全, 需要设置一个新密码.")
                                    .setPositiveButton("立即修改", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(context, ModifyPasswordActivity.class);
                                            intent.putExtra("type", "1");
                                            intent.putExtra("token", token);
                                            ((AppCompatActivity) context).finish();
                                            context.startActivity(intent);
                                        }
                                    });
                            Dialog dialog = dialogBuilder.create();
                            dialog.setCancelable(false);
                            dialog.show();
                        }*/ else if(TextUtils.isEmpty(model.getCertification())&&SharedPreferenceService.getInstance().get("tipCertif",true)){
                            UserInfoModel.getInstance().setToken(token);
                            SharedPreferenceService.getInstance().put("tipCertif",false);
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                                    .setTitle(context.getString(R.string.login_out_title))
                                    .setMessage("您尚未完成资格证号认证，请尽快认证")
                                    .setNegativeButton("稍后认证", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ((AppCompatActivity) context).finish();
                                            context.startActivity(new Intent(context, HomeActviity.class));
                                        }
                                    })
                                    .setPositiveButton("现在认证", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ((AppCompatActivity) context).finish();
                                            Intent intent=new Intent(context, ValidateCertificationActivity.class);
                                            context.startActivity(intent);
                                        }
                                    });
                            Dialog dialog = dialogBuilder.create();
                            dialog.setCancelable(false);
                            dialog.show();
                        }else {
                            UserInfoModel.getInstance().setToken(token);
                            ((AppCompatActivity) context).finish();
                            Intent start = new Intent(context, HomeActviity.class);
                            context.startActivity(start);
                        }
                        break;
                    default:
                        Util.toastMsg(userResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) dialog.dismiss();
                if (tv != null) {
                    tv.setEnabled(true);
                }
                ZillaApi.dealNetError(error);
            }
        });
    }


    private void stepDeal(Context context, String userId, long step) {
        //获取用户最新的步数
        int currentStep = StepUtil.getInstance().getCurrentStep(userId);
        //删除旧数据
        StepUtil.getInstance().deleteDateByPersonal(userId);
        if (step > currentStep) {
            //如果服务器上的步数大于本地
            UserStep userStep = new UserStep();
            userStep.setAccountId(Long.parseLong(userId));
            userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
            userStep.setStepCount(step);
            StepUtil.getInstance().saveStep(userStep);
        } else {
            //如果本地大于服务器的
            UserStep userStep = new UserStep();
            userStep.setAccountId(Long.parseLong(userId));
            userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
            userStep.setStepCount(currentStep);
            StepUtil.getInstance().saveStep(userStep);
        }
        //启动计步器服务
        context.startService(new Intent(context.getApplicationContext(), StepService.class).putExtra("isExit",true));
        context.startService(new Intent(context.getApplicationContext(), DaemonService.class));

    }

    @Override
    public void updateHXState(final String phoneNo, final String hxAccountId, final String state, final ProgressDialog dialog, final DialogInterface dialogs, final String type) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        service.updateHXState(token, phoneNo, hxAccountId, state, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                int status = responseData.getStatus();
                switch (status) {
                    case 200:
                        if (dialogs != null) {
                            dialogs.dismiss();
                        }
                        if ("1".equals(state)) {
                            UserModel userModel = UserInfoModel.getInstance().getUser();
                            userModel.setHasEmchat("1");
                            userModel.setHXAccountId(hxAccountId);
                            UserInfoModel.getInstance().saveUserCache(userModel);
                            if (!"isInBack".equals(type)) {
                                ((AppCompatActivity) context).finish();
                            }
                        }
                        break;
                    default:
                        if (!"isInBack".equals(type)) {
                            EventBus.getDefault().post(1);
                        } else {
                            updateHXState(phoneNo, hxAccountId, state, dialog, dialogs, type);
                        }
//                        Util.toastMsg(responseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (!"isInBack".equals(type)) {
                    EventBus.getDefault().post(1);
                } else {
                    updateHXState(phoneNo, hxAccountId, state, dialog, dialogs, type);
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
}
