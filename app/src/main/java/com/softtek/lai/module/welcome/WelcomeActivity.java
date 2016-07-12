/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.module.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.github.snowdream.android.util.Log;
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

    private  String token=null;
    @InjectView(R.id.guide)
    RelativeLayout guide;


    @Override
    protected void initViews() {
        guide.setBackgroundResource(R.drawable.guide_bac);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    @Override
    protected void initDatas() {
        //检查是否存在token
        token= UserInfoModel.getInstance().getToken();
        Log.i("token="+token);
        new Handler().postDelayed(this,1000);
    }
    //执行token不为空的情况
    /*private void checks(){
        final String userId=UserInfoModel.getInstance().getUser().getUserid();
        //删除旧数据
        String currentDate=DateUtil.weeHours(0);
        StepUtil.getInstance().deleteOldDate(currentDate,userId);
        //检查组别
        service.isJoinRunGroup(token, userId,
                new RequestCallback<ResponseData<StepResponseModel>>() {
                    @Override
                    public void success(ResponseData<StepResponseModel> data, Response response) {
                        if(data.getStatus()==200){//加入了跑团
                            long step=data.getData().getTodayStepCnt();
                            Log.i("服务器上的步数为="+step);
                            //获取本地最新步数
                            String dateStar=DateUtil.weeHours(0);
                            String dateEnd=DateUtil.weeHours(1);
                            List<UserStep> steps=StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
                            if(!steps.isEmpty()){
                                UserStep stepEnd=steps.get(steps.size()-1);
                                int currentStep= (int) (stepEnd.getStepCount());
                                if(step>currentStep){
                                    //如果服务器上的步数大于本地
                                    UserStep userStep=new UserStep();
                                    userStep.setAccountId(Long.parseLong(userId));
                                    userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                                    userStep.setStepCount(step);
                                    StepUtil.getInstance().saveStep(userStep);
                                }
                                //如果不大于则 不需要操作什么
                            }else{
                                //本地没有数据,则直接把服务器上的步数写入数据库
                                UserStep userStep=new UserStep();
                                userStep.setAccountId(Long.parseLong(userId));
                                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                                userStep.setStepCount(step);
                                StepUtil.getInstance().saveStep(userStep);
                            }
                            //启动计步器服务
                            startService(new Intent(getApplicationContext(), StepService.class));
                        }
                        //进入首页
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, HomeActviity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //有异常返回登录也重新登录
                        UserInfoModel.getInstance().loginOut();//本地退出
                        finish();
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

    }*/


    @Override
    public void run() {
        if(StringUtils.isEmpty(token)){
            finish();
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
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
                                //set.setAlias(model.getMobile());
                                //set.setStyleBasic();
                                UserInfoModel.getInstance().saveUserCache(model);
                                //如果用户加入了跑团
                                if("1".equals(model.getIsJoin())){
                                    stepDeal(WelcomeActivity.this,model.getUserid(), StringUtils.isEmpty(model.getTodayStepCnt())?0:Long.parseLong(model.getTodayStepCnt()));
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
        String dateStar=DateUtil.weeHours(0);
        String dateEnd=DateUtil.weeHours(1);
        List<UserStep> steps=StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
        if(!steps.isEmpty()){
            UserStep stepEnd=steps.get(steps.size()-1);
            int currentStep= (int) (stepEnd.getStepCount());
            if(step>currentStep){
                //如果服务器上的步数大于本地
                UserStep userStep=new UserStep();
                userStep.setAccountId(Long.parseLong(userId));
                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                userStep.setStepCount(step);
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
        }
        //删除旧数据
        StepUtil.getInstance().deleteOldDate(dateStar,userId);
        //启动计步器服务
        context.startService(new Intent(context.getApplicationContext(), StepService.class));
    }
}
