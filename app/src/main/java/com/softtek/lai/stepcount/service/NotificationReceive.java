package com.softtek.lai.stepcount.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.module.sport.view.RunSportActivity;
import com.softtek.lai.module.sport2.view.LaiSportActivity;

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
                Intent groupMain=new Intent(context,LaiSportActivity.class);
                groupMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                groupMain.putExtra("type",3);
                context.startActivity(groupMain);
            }
        }else{
            Intent groupMain=new Intent(context,LaiSportActivity.class);
            groupMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            groupMain.putExtra("type",3);
            context.startActivity(groupMain);
        }
    }
}
