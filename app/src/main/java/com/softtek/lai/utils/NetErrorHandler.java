package com.softtek.lai.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.module.login.view.LoginActivity;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.RetrofitError;
import zilla.libcore.api.IApiError;
import zilla.libcore.api.IApiErrorHandler;
import zilla.libcore.util.Util;

/**
 * Created by Zilla on 22/1/16.
 */
public class NetErrorHandler implements IApiErrorHandler {

    private AlertDialog.Builder builder=null;

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
                        if(builder!=null){
                            return;
                        }
                        builder=new AlertDialog.Builder(LaiApplication.getInstance().getContext())
                                .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                                .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder=null;
                                        Intent intent=new Intent(LaiApplication.getInstance(), LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        LaiApplication.getInstance().startActivity(intent);
                                    }
                                }).setNegativeButton("稍候", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder=null;
                                    }
                                });

                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                builder=null;
                            }
                        });
                        builder.create().show();
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
