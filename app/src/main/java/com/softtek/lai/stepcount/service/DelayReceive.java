package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

/**
 * Created by John on 2016/5/26.
 *
 */
public class DelayReceive extends BroadcastReceiver{

    private StepNetService service;

    @Override
    public void onReceive(Context context, Intent intent) {
        UserModel model=UserInfoModel.getInstance().getUser();
        if(model==null||"0".equals(model.getIsJoin())){
            return;
        }
        //做上传工作
        String userId=model.getUserid();
        service= ZillaApi.NormalRestAdapter.create(StepNetService.class);
        int todayStep=StepService.todayStep;
        StringBuilder buffer=new StringBuilder();
        buffer.append(DateUtil.getInstance().getCurrentDate());
        buffer.append(",");
        buffer.append(todayStep);
        //提交数据
        submitStep(Long.parseLong(userId),buffer.toString());
        context.startService(new Intent(context.getApplicationContext(),StepService.class));
    }

    private void submitStep(long accountId, String step){
        Log.i("步数数据开始上传.......................................");
        Log.i("步数>>"+step);
        service.synStepCount(UserInfoModel.getInstance().getToken(),accountId, step, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i("上传成功");
            }
        });
    }
}
