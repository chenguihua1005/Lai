/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.view.CustomProgress;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.MD5;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private ILoginPresenter loginPresenter;

    @LifeCircleInject
    ValidateLife validateLife;

    @Required(order = 1, messageResId = R.string.phoneValidateNullLogin)
    @InjectView(R.id.et_phone)
    EditText et_phone;

    @Required(order = 2, messageResId = R.string.passwordValidateNull)
    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.tv_login)
    TextView tv_login;

    @InjectView(R.id.tv_forgetpsd)
    TextView tv_forgetpsd;

    @InjectView(R.id.tv_regist)
    TextView tv_regist;

    @InjectView(R.id.ll_visitor)
    LinearLayout ll_visitor;


    private CustomProgress progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        tintManager.setStatusBarTintResource(R.drawable.grey_white);
        progressDialog = CustomProgress.build(this,"登录中...");
        tv_login.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
        ll_visitor.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        loginPresenter = new LoginPresenterImpl(this);
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

 /*   public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public static String getSystem(){
        String SYS = null;
        try {
            Properties prop= new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null){
                SYS = SYS_MIUI;//小米
            }else if(prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    ||prop.getProperty(KEY_EMUI_VERSION, null) != null
                    ||prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null){
                SYS = SYS_EMUI;//华为
            }else if(getMeizuFlymeOSFlag().toLowerCase().contains("flyme")){
                SYS = SYS_FLYME;//魅族
            };
        } catch (IOException e){
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String)get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    *//*打开自启动管理页*//*
    public static void openStart(Context context){
        *//*if(Build.VERSION.SDK_INT < 23){
            return;
        }*//*
        String system = getSystem();
        Intent intent = new Intent();
        if(system.equals(SYS_EMUI)){//华为
            ComponentName componentName = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
            intent.setComponent(componentName);
        }else if(system.equals(SYS_MIUI)){//小米
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            intent.setComponent(componentName);
        }
        try{
            context.startActivity(intent);
        }catch (Exception e){//抛出异常就直接打开设置页面
            intent=new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                //openStart(this);
                /*Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.APP_MARKET");
                PackageManager pm = this.getPackageManager();
                List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
                int size = infos.size();
                for (int i = 0; i < size; i++) {
                    ActivityInfo activityInfo = infos.get(i).activityInfo;
                    String packageName = activityInfo.packageName;
                    Log.i("packageName : " + packageName);
                }*/
                validateLife.validate();
                break;
            case R.id.tv_forgetpsd:
                finish();
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.tv_regist:
                startActivity(new Intent(this, RegistActivity.class));
                finish();
                break;
            case R.id.ll_visitor:
                UserInfoModel.getInstance().visitorLogin();
                finish();
                startActivity(new Intent(this, HomeActviity.class));
                overridePendingTransition(R.anim.activity_enter,0);
                break;

        }
    }

    @Override
    public void onValidationSucceeded() {
        tv_login.setEnabled(false);
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        progressDialog.show();
        loginPresenter.doLogin(phone, MD5.md5WithEncoder(password), progressDialog,tv_login);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
