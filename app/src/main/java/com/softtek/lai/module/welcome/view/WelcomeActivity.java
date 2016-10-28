/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.module.welcome.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.widget.RelativeLayout;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.home.view.ModifyPasswordActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.service.DaemonService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.MD5;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_guide)
public class WelcomeActivity extends BaseActivity implements Runnable{

    @InjectView(R.id.guide)
    RelativeLayout guide;


    @Override
    protected void initViews() {
        Constants.IS_LOGINIMG="0";
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    @Override
    protected void initDatas() {
        if(isServiceStarted(getApplicationContext(),"com.softtek.lai.stepcount.service.StepService")){
            LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
        }
        new Handler(Looper.getMainLooper()).postDelayed(this,1000);

    }

    @Override
    public void run() {
        boolean isfirstRun = SharedPreferenceService.getInstance().get("isfirstRun",true);
        if (isfirstRun)
        {
            SharedPreferenceService.getInstance().put("isfirstRun",false);
            startActivity(new Intent(this,GuidePageActivity.class));
            finish();
        } else {
            //获取用户的帐号和密码
            String user=SharedPreferenceService.getInstance().get(Constants.USER,"");
            final String password=SharedPreferenceService.getInstance().get(Constants.PDW,"");
            String token=UserInfoModel.getInstance().getToken();
            if(StringUtils.isEmpty(token)||StringUtils.isEmpty(user)||StringUtils.isEmpty(password)){
                UserInfoModel.getInstance().visitorLogin();
                finish();
                Intent intent = new Intent(WelcomeActivity.this, HomeActviity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_enter,0);
            }else{
                //登录
                PackageManager pm= getPackageManager();
                StringBuffer buffer=new StringBuffer();
                if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)){
                    buffer.append("计步类型=SENSOR_STEP_COUNTER");
                }else if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)){
                    buffer.append("计步类型=SENSOR_ACCELEROMETER");
                }else{
                    buffer.append("计步类型=不支持");
                }
                ZillaApi.NormalRestAdapter.create(LoginService.class).doLogin(buffer.toString(),user, password, new Callback<ResponseData<UserModel>>() {
                    @Override
                    public void success(ResponseData<UserModel> userModelResponseData, Response response) {
                        int status=userModelResponseData.getStatus();
                        switch (status) {
                            case 200:
                                //JPushInterface.init(WelcomeActivity.this);
                                //JpushSet set = new JpushSet(WelcomeActivity.this);
                                UserModel model=userModelResponseData.getData();
                                Log.i("token=="+model.getToken());
                                //set.setAlias(model.getMobile());
                                //set.setStyleBasic();
                                UserInfoModel.getInstance().saveUserCache(model);
                                //如果用户加入了跑团
                                if("1".equals(model.getIsJoin())){
                                    stepDeal(WelcomeActivity.this,model.getUserid(), StringUtils.isEmpty(model.getTodayStepCnt())?0:Long.parseLong(model.getTodayStepCnt()));
                                }
                                final String token=userModelResponseData.getData().getToken();
                                if("0".equals(model.getIsCreatInfo())&&!model.isHasGender()){
                                    //如果没有创建档案且性别不是2才算没创建档案
                                    UserInfoModel.getInstance().setToken("");
                                    Intent intent=new Intent(WelcomeActivity.this, CreatFlleActivity.class);
                                    intent.putExtra("token",token);
                                    finish();
                                    startActivity(intent);
                                }else if(MD5.md5WithEncoder("000000").equals(password)){
                                    UserInfoModel.getInstance().setToken("");
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(WelcomeActivity.this)
                                            .setTitle(getString(R.string.login_out_title))
                                            .setMessage("您正在使用默认密码, 为了您的账户安全, 需要设置一个新密码.")
                                            .setPositiveButton("立即修改", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Intent intent=new Intent(WelcomeActivity.this, ModifyPasswordActivity.class);
                                                    intent.putExtra("type","1");
                                                    intent.putExtra("token",token);
                                                    finish();
                                                    startActivity(intent);
                                                }
                                            });
                                    Dialog dialog=dialogBuilder.create();
                                    dialog.setCancelable(false);
                                    dialog.show();
                                }else {
                                    finish();
                                    Intent start=new Intent(WelcomeActivity.this, HomeActviity.class);
                                    startActivity(start);
                                    overridePendingTransition(R.anim.activity_enter,0);
                                }
                                break;
                            default:
                                UserInfoModel.getInstance().visitorLogin();
                                finish();
                                Intent intent = new Intent(WelcomeActivity.this, HomeActviity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.activity_enter,0);
                                break;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UserInfoModel.getInstance().visitorLogin();
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, HomeActviity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_enter,0);
                    }
                });
            }
        }
    }

    private void stepDeal(Context context, String userId, long step){
        //List<UserStep> steps=StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
        //StepUtil.getInstance().deleteOldDate(dateEnd);
        //首先先关闭服务
        //isServiceStarted(context.getApplicationContext(),StepService.class.getPackage().getName());
        //LocalBroadcastManager.getInstance(LaiApplication.getInstance().getContext().get()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
        //获取用户最新的步数
        int currentStep=StepUtil.getInstance().getCurrentStep(userId);
        //删除旧数据
        StepUtil.getInstance().deleteDateByPersonal(userId);
        if(step>currentStep){
            //如果服务器上的步数大于本地
            UserStep userStep=new UserStep();
            userStep.setAccountId(Long.parseLong(userId));
            userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
            userStep.setStepCount(step);
            StepUtil.getInstance().saveStep(userStep);
        }else{
            //如果本地大于服务器的
            UserStep userStep=new UserStep();
            userStep.setAccountId(Long.parseLong(userId));
            userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
            userStep.setStepCount(currentStep);
            StepUtil.getInstance().saveStep(userStep);
        }
        //启动计步器服务
        context.startService(new Intent(context.getApplicationContext(), StepService.class));
        context.startService(new Intent(context.getApplicationContext(), DaemonService.class));

        /*if(!steps.isEmpty()){
            UserStep stepEnd=steps.get(steps.size()-1);
            int currentStep= (int) (stepEnd.getStepCount());
            if(step>currentStep){
                //如果服务器上的步数大于本地
                UserStep userStep=new UserStep();
                userStep.setAccountId(Long.parseLong(userId));
                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                userStep.setStepCount(step);
                StepUtil.getInstance().saveStep(userStep);
            }else{
                //如果本地大于服务器的
                UserStep userStep=new UserStep();
                userStep.setAccountId(Long.parseLong(userId));
                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                userStep.setStepCount(currentStep);
                StepUtil.getInstance().saveStep(userStep);
            }
            //如果不大于则 不需要操作什么
        }else{
            //本地没有数据则写入本地
            UserStep serverStep=new UserStep();
            serverStep.setAccountId(Long.parseLong(userId));
            serverStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
            serverStep.setStepCount(step);
            StepUtil.getInstance().saveStep(serverStep);
        }*/
    }
    public static  boolean isServiceStarted(Context context,String packageName){
        boolean isStarted =false;
        try{
            ActivityManager mActivityManager =
                    (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> mRunningService =
                    mActivityManager.getRunningServices(100);
            for (ActivityManager.RunningServiceInfo amService : mRunningService){
                if(amService.service.getClassName().toString().compareTo(packageName)==0){
                    isStarted = true;
                    break;
                }
            }
        }catch(SecurityException e){
            e.printStackTrace();
        }
        return isStarted;
    }
}
