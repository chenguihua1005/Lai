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

    private List<FilterModel> filterList = new ArrayList<>();


    public List<FilterModel> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<FilterModel> filterList) {
        this.filterList = filterList;
    }

    private static LaiApplication laiApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        laiApplication = this;
        String[] datas = getResources().getStringArray(R.array.SensitiveWord);
        for (int i = 0; i < datas.length; i++) {
            filterList.add(new FilterModel(datas[i]));
        }
        new Zilla().setCallBack(this).initSystem(this);
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }

    public static LaiApplication getInstance() {
        return laiApplication;
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
                //requestFacade.addHeader("token",PropertiesManager.get("token"));
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
