package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by John on 2016/5/26.
 *
 */
public class DelayReceive extends BroadcastReceiver{

    private StepNetService service;

    @Override
    public void onReceive(Context context, Intent intent) {
        //做上传工作
        if(UserInfoModel.getInstance().getUser()==null){
            return;
        }
        Log.i("步数数据开始上传.......................................");
        String userId=UserInfoModel.getInstance().getUser().getUserid();
        service= ZillaApi.NormalRestAdapter.create(StepNetService.class);
         /*
        处理上传任务
         */
        String dateStar=DateUtil.weeHours(0);
        String dateEnd=DateUtil.weeHours(1);
        List<UserStep> steps= StepUtil.getInstance().getCurrentData(userId,dateStar,dateEnd);
        if(!steps.isEmpty()){//if have datas
            UserStep stepStart=steps.get(0);
            UserStep stepEnd=steps.get(steps.size()-1);
            StringBuilder buffer=new StringBuilder();
            buffer.append(stepEnd.getRecordTime().split(" ")[0]);
            buffer.append(",");
            long step=stepEnd.getStepCount()-stepStart.getStepCount();
            buffer.append(step);
            //提交数据
            submitStep(Long.parseLong(userId),buffer.toString());
        }
        context.startService(new Intent(context,StepService.class));
    }

    private void submitStep(final long accountId, String step){
        Log.i("步数>>"+step);
        service.synStepCount(UserInfoModel.getInstance().getToken(),accountId, step, new RequestCallback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                Log.i(responseData.getMsg());
            }
        });
    }
}
