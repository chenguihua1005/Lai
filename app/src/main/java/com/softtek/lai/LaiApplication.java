/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.chat.ChatHelper;
import com.softtek.lai.common.CrashHandler;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.utils.NetErrorHandler;
import com.umeng.socialize.PlatformConfig;

import java.lang.ref.WeakReference;

import im.fir.sdk.FIR;
import retrofit.RequestInterceptor;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.DBHelper;
import zilla.libcore.file.PropertiesManager;

/**
 * Created by zilla on 9/8/15.
 */
public class LaiApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {

    private static LaiApplication laiApplication;
    private WeakReference<Context>  context;

    @Override
    public void onCreate() {
        super.onCreate();
        laiApplication = this;
        new Zilla().setCallBack(this).initSystem(this);
        UserInfoModel.getInstance(this);
        FIR.init(this);//注册Fir自动更新
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
        ChatHelper.getInstance().init(getApplicationContext());
    }

    public static LaiApplication getInstance() {
        return laiApplication;
    }

    /*public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }*/

    public WeakReference<Context> getContext() {
        return context;
    }

    public void setContext(WeakReference<Context> context) {
        this.context = context;
    }

    /**
     * init
     *
     * @param context Context
     */
    @Override
    public void onInit(Context context) {
        initApi();
        DBHelper.getInstance().setDbUpgradeListener(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Config API info
     */
    private void initApi() {
        PlatformConfig.setSinaWeibo("4097250846", "f4cb916c401319e78c9fc1b73660e28a");
        PlatformConfig.setWeixin("wxdef946afe85d49a2", "8f2e4913b794a310dd6662014748c43d");
        ZillaApi.NormalRestAdapter = ZillaApi.getRESTAdapter(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
//                requestFacade.addEncodedPathParam();
                requestFacade.addHeader("appid", PropertiesManager.get("appid"));
            }
        });

        ZillaApi.setmIApiErrorHandler(new NetErrorHandler());
    }

    public static final String CREATE_STEP="create table user_step(" +
            "id text primary key," +
            "accountId text," +
            "stepCount bigint," +
            "recordTime text )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("onCreate(SQLiteDatabase db)");
        db.execSQL(CREATE_STEP);
        Log.i("表创建了");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("onUpgrade(SQLiteDatabase db)");
        switch (oldVersion){
            case 1:
                db.execSQL("drop table user_step");//删除表
                db.execSQL(CREATE_STEP);//创建新表
                break;
        }
    }
}
