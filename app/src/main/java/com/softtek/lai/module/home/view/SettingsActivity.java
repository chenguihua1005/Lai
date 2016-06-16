/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.login.presenter.IPasswordPresenter;
import com.softtek.lai.module.login.presenter.PasswordPresnter;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;

import org.json.JSONObject;

import butterknife.InjectView;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_setting)
public class SettingsActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.lin_about)
    LinearLayout lin_about;
    @InjectView(R.id.tv_version)
    TextView tv_version;

    @InjectView(R.id.ll_check)
    LinearLayout ll_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        lin_about.setOnClickListener(this);
        ll_check.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_title.setText("系统设置");
        tv_version.setText("V "+  DisplayUtil.getAppVersionName(this));
        iv_email.setVisibility(View.GONE);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.lin_about:
                startActivity(new Intent(this,AboutMeActivity.class));
                break;
            case R.id.ll_check:
                initVersionCode();
                checkUpdate();
                break;

        }
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    //**********************************************************

    /**
     * 获取网上软件版本号
     * 检查本地版本号
     **/

    int version1;
    int build;
    String information;
    String installUrl;

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public void initVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version1 = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUpdate() {
        initVersionCode();
        FIR.checkForUpdateInFIR("b405d60358cdcb42b9c9d06f6e1d7918", new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {

                Log.i( "check from fir.im success! " + "\n" + versionJson);

                try {
                    JSONObject jsonObject = new JSONObject(versionJson);
                    String name = jsonObject.getString("name");
                    String version = jsonObject.getString("version");
                    String changelog = jsonObject.getString("changelog");
                    String updated_at = jsonObject.getString("updated_at");
                    String versionShort = jsonObject.getString("versionShort");
                    build = jsonObject.getInt("build");
                    installUrl = jsonObject.getString("installUrl");
                    information = "名称：" + name + "\n" + "更新内容:" + changelog + "\n" + "版本号：" + versionShort;
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }

            @Override
            public void onFail(Exception exception) {
                exception.printStackTrace();
            }

            @Override
            public void onStart() {}

            @Override
            public void onFinish() {
                if (build > version1) {
                    initDialog();
                }else if(build==version1){
                    new AlertDialog.Builder(SettingsActivity.this).setMessage("当前版本已最新").create().show();
                }
            }
        });
    }

    /**
     * 初始化dialog
     */
    public void initDialog() {
        /**
         * 状态选择
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新版提示");
        builder.setMessage(information);
        builder.setPositiveButton("前往下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(installUrl));
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });
        builder.show();
    }

}
