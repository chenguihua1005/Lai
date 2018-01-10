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
}
