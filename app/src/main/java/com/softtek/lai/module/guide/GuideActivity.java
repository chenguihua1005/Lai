/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai.module.guide;

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
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.ZillaDB;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_guide)
public class GuideActivity extends BaseActivity implements Runnable{

    private  String token=null;
    private SportGroupService service;
    private StepNetService stepNetService;
    private List<UserStep> old;
    private CountDownLatch latch=new CountDownLatch(1);
    private String current_date;

    @Override
    protected void initViews() {
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }

    @Override
    protected void initDatas() {
        current_date= DateUtil.getInstance(DateUtil.yyyy_MM_dd).getCurrentDate();
        service= ZillaApi.NormalRestAdapter.create(SportGroupService.class);
        stepNetService=ZillaApi.NormalRestAdapter.create(StepNetService.class);
        //检查是否存在token
        token= UserInfoModel.getInstance().getToken();
        Log.i("token="+token);
        new Handler().postDelayed(this,1000);
    }
    //执行token不为空的情况
    private void checks(){
        //先检查是否存在旧数据
        Log.i("开始检查是存在旧数据××××××××××××××××××××××××××××××");
        final String userId=UserInfoModel.getInstance().getUser().getUserid();
        old= StepUtil.getInstance().checkOldStep(userId);
        if(!old.isEmpty()){//如果有旧数据
            //提交旧数据
            StringBuffer buffer=new StringBuffer();
            for(UserStep step:old){
                buffer.append(step.getRecordTime());
                buffer.append(",");
                buffer.append(step.getStepCount());
                buffer.append(";");
            }
            submitStep(Long.parseLong(userId),buffer.substring(0,buffer.lastIndexOf(";")));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("开始检查是否加入跑团××××××××××××××××××××××××××××××");
        service.isJoinRunGroup(token, userId,
                new RequestCallback<ResponseData<StepResponseModel>>() {
                    @Override
                    public void success(ResponseData<StepResponseModel> data, Response response) {
                        if(data.getStatus()==200){//加入了跑团
                            long step=data.getData().getTodayStepCnt();
                            long currentStep=StepUtil.getInstance().getCurrentStep(userId);
                            if(step>currentStep){
                                //删除旧数据
                                //新增新数据
                                //ZillaDB.getInstance().update(userStep);
                            }
                            //启动计步器服务
                            startService(new Intent(GuideActivity.this, StepService.class));
                        }else{
                            //没有加入跑团，不启动几步功能直接进入首页
                            Intent intent = new Intent(GuideActivity.this, HomeActviity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //有异常返回登录也重新登录
                        UserInfoModel.getInstance().loginOut();//本地退出
                        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }


    @Override
    public void run() {
        if(StringUtils.isEmpty(token)){
            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            checks();
        }
    }

    private void submitStep(final long accountId, final String step){
        stepNetService.synStepCount(accountId, step, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                //提交成功删除除了当前天的数据
                for (UserStep userStep:old){
                    if(userStep.getRecordTime().equals(current_date)){
                        continue;
                    }
                    ZillaDB.getInstance().delete(userStep);
                }
                latch.countDown();
                Util.toastMsg(responseData.getMsg());
            }

            @Override
            public void failure(RetrofitError error) {
                //提交失败删除旧步数
                latch.countDown();
            }
        });
    }
}
