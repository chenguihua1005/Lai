/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.module.welcome.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RelativeLayout;

import com.forlong401.log.transaction.log.manager.LogManager;
import com.forlong401.log.transaction.utils.LogUtils;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.service.DaemonService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;

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
        //guide.setBackgroundResource(R.drawable.guide_bac2);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    @Override
    protected void initDatas() {

        LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
        new Handler().postDelayed(this,1000);


    }

    @Override
    public void run() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE);
        boolean isfirstRun = sharedPreferences.getBoolean("isfirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isfirstRun)
        {
            Intent intent1=new Intent(this,GuidePageActivity.class);
            startActivity(intent1);
            editor.putBoolean("isfirstRun", false);
            editor.commit();
            finish();
        } else {
            //获取用户的帐号和密码
            String user=SharedPreferenceService.getInstance().get(Constants.USER,"");
            String password=SharedPreferenceService.getInstance().get(Constants.PDW,"");
            if(StringUtils.isEmpty(user)||StringUtils.isEmpty(password)){
                finish();
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                //登录
                ZillaApi.NormalRestAdapter.create(LoginService.class).doLogin(user, password, new Callback<ResponseData<UserModel>>() {
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
                                    LogManager.getManager(getApplicationContext()).log("autoLogin:","This user has join Group",
                                            LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                                    stepDeal(WelcomeActivity.this,model.getUserid(), StringUtils.isEmpty(model.getTodayStepCnt())?0:Long.parseLong(model.getTodayStepCnt()));
                                }else{
                                    LogManager.getManager(getApplicationContext()).log("autoLogin:","This user has not join Group",
                                            LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
                                }
                                finish();
                                Intent start=new Intent(WelcomeActivity.this, HomeActviity.class);
                                startActivity(start);
                                break;
                            default:
                                finish();
                                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }




    }

    private void stepDeal(Context context, String userId, long step){
        //获取当天的开始时间和结束时间
        String dateStar=DateUtil.weeHours(0);
        String dateEnd=DateUtil.weeHours(1);
        List<UserStep> steps=StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
        //删除旧数据
        StepUtil.getInstance().deleteOldDate(dateStar);
        LogManager.getManager(context.getApplicationContext()).log("autoLogin:","stepDeal function was called! execute delete old step\n"+
                        "dateStar="+dateStar,
                LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        if(!steps.isEmpty()){
            UserStep stepEnd=steps.get(steps.size()-1);
            int currentStep= (int) (stepEnd.getStepCount());
            LogManager.getManager(context.getApplicationContext()).log("autoLogin:","stepDeal function was called!Service Step="+step+"\n" +
                            "query current step was not empty,current step="+currentStep,
                    LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
            if(step>currentStep){
                //如果服务器上的步数大于本地
                UserStep userStep=new UserStep();
                userStep.setAccountId(Long.parseLong(userId));
                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                userStep.setStepCount(step);
                StepUtil.getInstance().saveStep(userStep);
                LogManager.getManager(context.getApplicationContext()).log("autoLogin:","service step >current step",
                        LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
            }else{
                //如果本地大于服务器的
                UserStep userStep=new UserStep();
                userStep.setAccountId(Long.parseLong(userId));
                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                userStep.setStepCount(currentStep);
                StepUtil.getInstance().saveStep(userStep);
                LogManager.getManager(context.getApplicationContext()).log("autoLogin:","service step <= current step",
                        LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
            }
            //如果不大于则 不需要操作什么
        }else{
            //本地没有数据则写入本地
            LogManager.getManager(context.getApplicationContext()).log("autoLogin:","stepDeal function was called!Service Step="+step+"\n" +
                            "query current step was empty",
                    LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
            UserStep serverStep=new UserStep();
            serverStep.setAccountId(Long.parseLong(userId));
            serverStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
            serverStep.setStepCount(step);
            StepUtil.getInstance().saveStep(serverStep);
        }
        Log.i("启动计步器");
        //启动计步器服务
        context.startService(new Intent(context.getApplicationContext(), StepService.class));
        context.startService(new Intent(context.getApplicationContext(), DaemonService.class));
    }
}
