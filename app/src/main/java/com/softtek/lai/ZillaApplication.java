/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.softtek.lai;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.common.CrashHandler;
import com.softtek.lai.module.File.model.Filter;
import com.softtek.lai.utils.NetErrorHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit.RequestInterceptor;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.DBHelper;
import zilla.libcore.file.PropertiesManager;

/**
 * Created by zilla on 9/8/15.
 */
public class ZillaApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {

    private List<Filter> filterList=new ArrayList<>();


    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    private static ZillaApplication zillaApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        zillaApplication=this;
        String[] datas=getResources().getStringArray(R.array.SensitiveWord);
        for(int i=0;i<datas.length;i++){
            filterList.add(new Filter(datas[i]));
        }
        new Zilla().setCallBack(this).initSystem(this);
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }

    public static ZillaApplication getInstance(){
        return zillaApplication;
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
                requestFacade.addHeader("appid",PropertiesManager.get("appid"));
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
