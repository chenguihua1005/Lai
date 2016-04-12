/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.CrashHandler;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.File.model.FilterModel;
import com.softtek.lai.utils.NetErrorHandler;
import retrofit.RequestInterceptor;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.DBHelper;
import zilla.libcore.file.PropertiesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zilla on 9/8/15.
 */
public class LaiApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {

    private static LaiApplication laiApplication;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        laiApplication = this;
        new Zilla().setCallBack(this).initSystem(this);
        UserInfoModel.getInstance(this);
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }

    public static LaiApplication getInstance() {
        return laiApplication;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
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

    /**
     * Config API info
     */
    private void initApi() {
        ZillaApi.NormalRestAdapter = ZillaApi.getRESTAdapter(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
//                requestFacade.addEncodedPathParam();
                requestFacade.addHeader("appid", PropertiesManager.get("appid"));
                /*Log.i("LaiApplication token="+UserInfoModel.getInstance().getToken());
                if(UserInfoModel.getInstance().getToken()!=null&&
                        !UserInfoModel.getInstance().getToken().equals("")){
                    requestFacade.addHeader("token",UserInfoModel.getInstance().getToken());
                }*/
            }
        });

        ZillaApi.setmIApiErrorHandler(new NetErrorHandler());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("onCreate(SQLiteDatabase db)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("onUpgrade(SQLiteDatabase db)");

    }
}
