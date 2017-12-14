package com.softtek.lai.module.customermanagement.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;

import com.ggx.widgets.view.CustomProgress;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.RequestCallback;
import com.umeng.analytics.MobclickAgent;

import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jia.lu on 12/1/2017.
 */

public abstract class MakiBaseActivity extends FragmentActivity{
    private CustomProgress progressDialog;
    private AlertDialog builder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //有盟统计
        MobclickAgent.setDebugMode(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        Log.i("当前界面名称=" + getClass().getCanonicalName());
        LaiApplication.getInstance().setContext(new WeakReference<Context>(this));
    }

    /**
     * 通用progressDialog
     *
     * @param value
     */
    protected void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            if (!isFinishing()) {
                progressDialog = CustomProgress.build(this, value);
                progressDialog.show();
            }
        }
    }

    /**
     * 通用progressDialog
     */
    protected void dialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void dealNetError(RetrofitError error,Context context) {
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
                        String customData = "";
                        if (error.getBody() instanceof ResponseData) {
                            ResponseData data = (ResponseData) error.getBody();
                            customCode = data.getStatus();
                            customData = data.getMsg();
                        }
                        Log.i("return code=====" + customCode);
                        switch (customCode) {
                            case 401:
                                LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                if (builder == null || !builder.isShowing()) {
                                    builder = new AlertDialog.Builder(context)
                                            .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                                            .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //builder = null;
                                                    UserInfoModel.getInstance().loginOut();
                                                    Intent intent = new Intent(LaiApplication.getInstance(), LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    LaiApplication.getInstance().startActivity(intent);
                                                }
                                            }).setCancelable(false).create();
                                    //builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                                    builder.show();

                                }
                                break;
                            case 403:
                                if (builder == null || !builder.isShowing()) {
                                    UserInfoModel.getInstance().clearClassSave();
                                    builder = new AlertDialog.Builder(context)
                                            .setTitle("温馨提示").setMessage(customData)
                                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(LaiApplication.getInstance(), HomeActviity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    LaiApplication.getInstance().startActivity(intent);
                                                }
                                            }).setCancelable(false).create();
                                    builder.show();
                                }
                                break;
                            case 4001:
                                AlertDialog dialog = new AlertDialog.Builder(context)
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
                                        }).setCancelable(false).create();
                                //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                                dialog.show();
                                break;
                            case 4002:
                                AlertDialog dialog1 = new AlertDialog.Builder(context)
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
                                        }).setCancelable(false).create();
                                //dialog1.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                                dialog1.show();
                                break;
                            case 4003:
                                AlertDialog dialog2 = new AlertDialog.Builder(context)
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
                                        }).setCancelable(false).create();
                                //dialog2.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                                dialog2.show();
                                break;
                            case 4004:
                                AlertDialog dialog3 = new AlertDialog.Builder(context)
                                        .setTitle("服务器维护公告").setMessage(customData)
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                UserInfoModel.getInstance().loginOut();
                                                Intent intent = new Intent(LaiApplication.getInstance(), LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                LaiApplication.getInstance().startActivity(intent);
                                            }
                                        }).setCancelable(false).create();
                                //dialog2.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                                dialog3.show();
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
