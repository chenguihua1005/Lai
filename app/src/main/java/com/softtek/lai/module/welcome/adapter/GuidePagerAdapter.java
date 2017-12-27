/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.welcome.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.home.view.ModifyPasswordActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.service.DaemonService;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.MD5;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by julie.zhu on 3/18/2016.
 */
public class GuidePagerAdapter extends PagerAdapter {
    private List<View> views;
    private Activity activity;

    public GuidePagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

//    @Override
//    public void destroyItem(View arg0, int arg1, Object arg2) {
//        ((ViewPager) arg0).removeView(views.get(arg1));
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }



    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            Button start_btn = (Button) arg0.findViewById(R.id.start_btn);
            start_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    creat();
                }
            });
        }
        if (arg1 == views.size() - 2) {
            Button skip_btn2 = (Button) arg0.findViewById(R.id.skip_btn2);
            skip_btn2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    creat();
                }
            });
        }
        if (arg1 == views.size() - 3) {
            Button skip_btn = (Button) arg0.findViewById(R.id.skip_btn);
            skip_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    creat();
                }
            });
        }
        return views.get(arg1);
    }

    /*
    * 开启主页
    * */
    private void creat() {
        //获取用户的帐号和密码
//        String user= SharedPreferenceService.getInstance().get(Constants.USER,"");
//        final String password=SharedPreferenceService.getInstance().get(Constants.PDW,"");
//        String token=UserInfoModel.getInstance().getToken();
//        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(user)||StringUtils.isEmpty(password)){
            activity.finish();
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
//        }else{
//            PackageManager pm= LaiApplication.getInstance().getPackageManager();
//            StringBuffer buffer=new StringBuffer();
//            if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)){
//                buffer.append("计步类型=SENSOR_STEP_COUNTER");
//            }else if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)){
//                buffer.append("计步类型=SENSOR_ACCELEROMETER");
//            }else{
//                buffer.append("计步类型=不支持");
//            }
//            //登录
//            ZillaApi.NormalRestAdapter.create(LoginService.class).doLogin(buffer.toString(),user, password, new Callback<ResponseData<UserModel>>() {
//                @Override
//                public void success(ResponseData<UserModel> userModelResponseData, Response response) {
//                    int status=userModelResponseData.getStatus();
//                    switch (status) {
//                        case 200:
//                            //JPushInterface.init(WelcomeActivity.this);
//                            //JpushSet set = new JpushSet(WelcomeActivity.this);
//                            UserModel model=userModelResponseData.getData();
//                            Log.i("token=="+model.getToken());
//                            //set.setAlias(model.getMobile());
//                            //set.setStyleBasic();
//                            UserInfoModel.getInstance().saveUserCache(model);
//                            //如果用户加入了跑团
//                            if("1".equals(model.getIsJoin())){
//
//                                stepDeal(activity,model.getUserid(), StringUtils.isEmpty(model.getTodayStepCnt())?0:Long.parseLong(model.getTodayStepCnt()));
//                            }
//                            final String token=userModelResponseData.getData().getToken();
//                            if("0".equals(model.getIsCreatInfo())&&!model.isHasGender()){
//                                //如果没有创建档案且性别不是2才算没创建档案
//                                UserInfoModel.getInstance().setToken("");
//                                Intent intent=new Intent(activity, CreatFlleActivity.class);
//                                intent.putExtra("token",token);
//                                activity.finish();
//                                activity.startActivity(intent);
//                            }else if(MD5.md5WithEncoder("000000").equals(password)){
//                                UserInfoModel.getInstance().setToken("");
//                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
//                                        .setTitle(activity.getString(R.string.login_out_title))
//                                        .setMessage("您正在使用默认密码, 为了您的账户安全, 需要设置一个新密码.")
//                                        .setPositiveButton("立即修改", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                                Intent intent=new Intent(activity, ModifyPasswordActivity.class);
//                                                intent.putExtra("type","1");
//                                                intent.putExtra("token",token);
//                                                activity.finish();
//                                                activity.startActivity(intent);
//                                            }
//                                        });
//                                Dialog dialog=dialogBuilder.create();
//                                dialog.setCancelable(false);
//                                dialog.show();
//                            }else {
//                                activity.finish();
//                                Intent start=new Intent(activity, HomeActviity.class);
//                                activity.startActivity(start);
//                            }
//                            break;
//                        default:
//                            activity.finish();
//                            Intent intent = new Intent(activity, LoginActivity.class);
//                            activity.startActivity(intent);
//                            break;
//                    }
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    activity.finish();
//                    Intent intent = new Intent(activity, LoginActivity.class);
//                    activity.startActivity(intent);
//                }
//            });
//        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }


    private void stepDeal(Context context, String userId, long step){
        //获取当天的开始时间和结束时间
        String dateStar= DateUtil.weeHours(0);
        String dateEnd=DateUtil.weeHours(1);
        List<UserStep> steps= StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
        //删除旧数据
        StepUtil.getInstance().deleteOldDate(dateStar);
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
        }
        Log.i("启动计步器");
        //启动计步器服务
        context.startService(new Intent(context.getApplicationContext(), StepService.class));
        context.startService(new Intent(context.getApplicationContext(), DaemonService.class));
    }

}

