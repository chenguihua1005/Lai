package com.softtek.lai.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.home.view.HomeFragment;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.stepcount.service.StepService;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.IApiError;
import zilla.libcore.api.IApiErrorHandler;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by Zilla on 22/1/16.
 */
public class NetErrorHandler implements IApiErrorHandler {

    private AlertDialog.Builder builder = null;

    @Override
    public boolean dealCustomError(Context context, @NonNull IApiError object) {
        boolean isCustomError = false;
        try {
            int errorCode = object.getErrorCode();
            switch (errorCode) {
                case -1:
                    isCustomError = true;
                    break;
                default:
                    break;
            }
            String msg = object.getErrorMessage();
            if (!TextUtils.isEmpty(msg)) {
                Util.toastMsg("" + msg);
            }

            Log.d(object.toString());
        } catch (Exception e) {
            Log.e(e.getMessage());
            Util.toastMsg("" + object.toString());
        }
        return isCustomError;
    }

    @Override
    public void dealNetError(RetrofitError error) {
        Log.i(error.getUrl());
        error.printStackTrace();
        switch (error.getKind()) {
            case NETWORK:
                Throwable throwable = error.getCause();
                if (throwable instanceof SocketTimeoutException) {
                    Util.toastMsg(zilla.libcore.R.string.net_error_timeout);
                } else if (throwable instanceof UnknownHostException) {
                    Util.toastMsg(zilla.libcore.R.string.net_error_unknownhost);
                } else if (throwable instanceof InterruptedIOException) {
                    Util.toastMsg(zilla.libcore.R.string.net_error_timeout);
                } else {
                    Util.toastMsg(zilla.libcore.R.string.net_error_neterror);
                }
                break;
            case HTTP:
                int statusCode = error.getResponse().getStatus();
                switch (statusCode) {
                    case 401:
                        int customCode = 0;
                        if (error.getBody() instanceof ResponseData) {
                            ResponseData data = (ResponseData) error.getBody();
                            customCode = data.getStatus();
                        }
                        Log.i("return code====="+customCode);
                        switch (customCode) {
                            case 401:
                                if (HomeFragment.timer != null) {
                                    HomeFragment.timer.cancel();
                                }
                                SharedPreferenceService.getInstance().put("HXID", "-1");
                                if (EMChat.getInstance().isLoggedIn()) {
                                    EMChatManager.getInstance().logout(true,new EMCallBack() {

                                        @Override
                                        public void onSuccess() {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onProgress(int progress, String status) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onError(int code, String message) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                                }
                                if (builder != null ||LaiApplication.getInstance().getContext()==null|| (LaiApplication.getInstance().getContext().get() instanceof LoginActivity)) {
                                    return;
                                }
                                builder = new AlertDialog.Builder(LaiApplication.getInstance().getContext().get())
                                        .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                                        .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder = null;
                                                UserInfoModel.getInstance().loginOut();
                                                LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                                Intent intent = new Intent(LaiApplication.getInstance().getContext().get(), LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                LaiApplication.getInstance().startActivity(intent);

                                            }
                                        }).setCancelable(false);
                                builder.create().show();
                                break;
                            case 4001:
                                new AlertDialog.Builder(LaiApplication.getInstance().getContext().get())
                                        .setTitle("温馨提示").setMessage("您已被管理员移出跑团, 您可以等待管理员为您重新分配跑团或选择加入新的跑团")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                                UserModel model = UserInfoModel.getInstance().getUser();
                                                model.setIsJoin("0");
                                                UserInfoModel.getInstance().saveUserCache(model);
                                                Intent intent = new Intent(LaiApplication.getInstance(), HomeActviity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                LaiApplication.getInstance().startActivity(intent);
                                            }
                                        }).setCancelable(false).create().show();
                                break;
                            case 4002:
                                new AlertDialog.Builder(LaiApplication.getInstance().getContext().get())
                                        .setTitle("温馨提示").setMessage("您所在跑团已被管理员删除, 您可以等待管理员为您重新分配跑团或选择加入新的跑团")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                                UserModel model = UserInfoModel.getInstance().getUser();
                                                model.setIsJoin("0");
                                                UserInfoModel.getInstance().saveUserCache(model);
                                                Intent intent = new Intent(LaiApplication.getInstance(), HomeActviity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                LaiApplication.getInstance().startActivity(intent);
                                            }
                                        }).setCancelable(false).create().show();
                                break;
                            case 4003:
                                new AlertDialog.Builder(LaiApplication.getInstance().getContext().get())
                                        .setTitle("温馨提示").setMessage("您已被管理员移动到新的跑团, 请重新点击莱运动以更新跑团")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ZillaApi.NormalRestAdapter.create(StepNetService.class).updateIsMove(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData>() {
                                                    @Override
                                                    public void success(ResponseData responseData, Response response) {
                                                        Intent intent = new Intent(LaiApplication.getInstance(), HomeActviity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        LaiApplication.getInstance().startActivity(intent);
                                                    }

                                                    @Override
                                                    public void failure(RetrofitError error) {
                                                        Intent intent = new Intent(LaiApplication.getInstance(), HomeActviity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        LaiApplication.getInstance().startActivity(intent);

                                                    }
                                                });

                                            }
                                        }).setCancelable(false).create().show();
                                break;
                            default:
                                break;
                        }
                        break;
                    case 403:
                        Util.toastMsg(zilla.libcore.R.string.net_http_403);
                        break;
                    case 404:
                        Util.toastMsg(zilla.libcore.R.string.net_http_404);
                        break;
                    case 500:
                        Util.toastMsg(zilla.libcore.R.string.net_http_500);
                        break;
                    case 502:
                        Util.toastMsg(zilla.libcore.R.string.net_http_502);
                        break;
                    case 503:
                        Util.toastMsg("服务不可用");
                        break;
                    default:
                        Util.toastMsg(zilla.libcore.R.string.net_http_other);
                        break;
                }
                break;
            case CONVERSION:
                Util.toastMsg(zilla.libcore.R.string.net_parse_error);
                Log.e("Parse error", error);
                break;
            case UNEXPECTED:
                Util.toastMsg(zilla.libcore.R.string.net_other);
                Log.e("UnKnuown error", error);
                break;
            default:
                break;
        }
    }
}
