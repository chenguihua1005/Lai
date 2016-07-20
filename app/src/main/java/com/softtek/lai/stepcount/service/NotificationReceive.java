package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.module.group.view.GroupMainActivity;
import com.softtek.lai.module.sport.view.RunSportActivity;

import java.lang.ref.WeakReference;

/**
 * Created by jerry.guan on 7/19/2016.
 */
public class NotificationReceive extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        WeakReference<Context> appContext=LaiApplication.getInstance().getContext();
        if(appContext!=null){
            Context activityContext=appContext.get();
            if(activityContext instanceof RunSportActivity){
                Intent runIntent=new Intent(context,RunSportActivity.class);
                runIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(runIntent);
            }else{
                Intent groupMain=new Intent(context,GroupMainActivity.class);
                groupMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(groupMain);
            }
        }else{
            Intent groupMain=new Intent(context,GroupMainActivity.class);
            groupMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(groupMain);
        }
    }
}
