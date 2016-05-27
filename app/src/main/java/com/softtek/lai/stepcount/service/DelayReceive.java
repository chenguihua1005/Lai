package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.stepcount.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.ZillaDB;

/**
 * Created by John on 2016/5/26.
 *
 */
public class DelayReceive extends BroadcastReceiver{

    private List<UserStep> oldStep=new ArrayList<>();
    private StepNetService service;

    @Override
    public void onReceive(Context context, Intent intent) {
        //做上传工作
        service= ZillaApi.NormalRestAdapter.create(StepNetService.class);
         /*
        处理上传任务，获取迄今为止的所有步数数据
         */
        oldStep.clear();
        String userId= UserInfoModel.getInstance().getUser().getUserid();
        List<UserStep> steps= StepUtil.getInstance().checkOldStep(userId);
        if(!steps.isEmpty()){//如果有旧数据
            //提交旧数据需要做优化
            List<UserStep> result=new ArrayList<>();
            StringBuilder buffer=new StringBuilder();
            String currentDate= DateUtil.getInstance("yyyy-MM-dd").getCurrentDate();
            //筛选分组
            for(UserStep step:steps){
                int i=currentDate.compareTo(step.getRecordTime());
                if(i==0||i==1){
                    oldStep.add(step);
                }

                buffer.append(step.getRecordTime());
                buffer.append(",");
                buffer.append(step.getStepCount());
                buffer.append(";");
            }

            //提交数据
            submitStep(Long.parseLong(userId),buffer.substring(0,buffer.lastIndexOf(";")));
        }
    }

    private void submitStep(final long accountId, String step){
        Log.i("步数>>"+step);
        service.synStepCount(UserInfoModel.getInstance().getToken(),accountId, step, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                //提交成功删除除了当前天的数据
                for (UserStep userStep:oldStep){
                    ZillaDB.getInstance().delete(userStep);
                }
            }
        });
    }
}
