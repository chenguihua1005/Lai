/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.module.welcome;

import android.content.Intent;
import android.os.Handler;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.group.model.StepResponseModel;
import com.softtek.lai.module.group.net.SportGroupService;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_guide)
public class WelcomeActivity extends BaseActivity implements Runnable{

    private  String token=null;
    private SportGroupService service;


    @Override
    protected void initViews() {
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(SportGroupService.class);
        //检查是否存在token
        token= UserInfoModel.getInstance().getToken();
        Log.i("token="+token);
        new Handler().postDelayed(this,1000);
    }
    //执行token不为空的情况
    private void checks(){
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
                            int step=data.getData().getTodayStepCnt();
                            Log.i("服务器上的步数为="+step);
                            String dateStar=DateUtil.weeHours(0);
                            String dateEnd=DateUtil.weeHours(1);
                            List<UserStep> steps=StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
                            if(!steps.isEmpty()){
                                UserStep stepStart=steps.get(0);
                                UserStep stepEnd=steps.get(steps.size()-1);
                                int currentStep= (int) (stepEnd.getStepCount()-stepStart.getStepCount());
                                if(step>currentStep){
                                    //如果服务器上的步数大于本地
                                    UserStep userStep=new UserStep();
                                    userStep.setAccountId(Long.parseLong(userId));
                                    userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                                    userStep.setStepCount(step+stepStart.getStepCount());
                                    StepUtil.getInstance().saveStep(userStep);
                                }
                                //如果不大于则 不需要操作什么
                            }else{
                                //本地没有数据
                                //需要给本地插入一条 数据
                                UserStep userStep=new UserStep();
                                userStep.setAccountId(Long.parseLong(userId));
                                userStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                                userStep.setStepCount(0);
                                StepUtil.getInstance().saveStep(userStep);
                                if(step>0){
                                    //如果服务器上的数据大于0则写入本地
                                    UserStep serverStep=new UserStep();
                                    serverStep.setAccountId(Long.parseLong(userId));
                                    serverStep.setRecordTime(DateUtil.getInstance().getCurrentDate());
                                    serverStep.setStepCount(step);
                                    StepUtil.getInstance().saveStep(serverStep);
                                }

                            }
                            //删除旧数据
                            String currentDate=DateUtil.weeHours(0);
                            StepUtil.getInstance().deleteOldDate(currentDate,userId);
                            //启动计步器服务
                            startService(new Intent(getApplicationContext(), StepService.class));

                        }
                        //进入首页
                        Intent intent = new Intent(WelcomeActivity.this, HomeActviity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //有异常返回登录也重新登录
                        UserInfoModel.getInstance().loginOut();//本地退出
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }


    @Override
    public void run() {
        if(StringUtils.isEmpty(token)){
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            checks();
            /*UserModel model=UserInfoModel.getInstance().getUser();
            if(model==null){
                UserInfoModel.getInstance().loginOut();//本地退出
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                //进入首页
                Intent intent = new Intent(this, HomeActviity.class);
                startActivity(intent);
                finish();
            }*/
        }
    }
}
