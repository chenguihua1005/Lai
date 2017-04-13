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
import com.softtek.lai.common.NetErrorHandler;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.database.ClassMemberTable;
import com.softtek.lai.module.bodygame3.conversation.database.ContactTable;
import com.softtek.lai.module.bodygame3.conversation.database.GroupTable;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DisplayUtil;
import com.umeng.socialize.PlatformConfig;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;
import retrofit.RequestInterceptor;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.DBHelper;
import zilla.libcore.file.PropertiesManager;

/**
 *
 * Created by zilla on 9/8/15.
 */
public class LaiApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {

    private static LaiApplication laiApplication;
    private WeakReference<Context> context;

    @Override
    public void onCreate() {
        super.onCreate();
        laiApplication = this;
        new Zilla().setCallBack(this).initSystem(this);
        UserInfoModel.getInstance(this);
        JPushInterface.init(this);
        ChatHelper.getInstance().init(getApplicationContext());
//        CrashHandler.getInstance().init(this);

    }


    public static LaiApplication getInstance() {
        return laiApplication;
    }


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
                requestFacade.addHeader("appid", PropertiesManager.get("appid"));
                requestFacade.addHeader("vision_name", DisplayUtil.getAppVersionName(LaiApplication.laiApplication));
                UserModel info = UserInfoModel.getInstance().getUser();
                if (info != null) {
                    requestFacade.addHeader("cilent_mobile", info.getMobile());
                } else {
                    requestFacade.addHeader("cilent_mobile", "");
                }
            }
        });

        ZillaApi.setmIApiErrorHandler(new NetErrorHandler());
    }

    public static final String CREATE_STEP = "create table user_step(" +
            "id text primary key," +
            "accountId text," +
            "stepCount bigint," +
            "recordTime text )";
    public static final String CREATE_SPORT_DATA = "create table sport_data(" +
            "id text primary key," +
            "user_id text," +
            "longitude text," +
            "latitude text," +
            "speed text," +//速度
            "step integer," +//当前步数
            "currentkm text," +//当前公里数
            "kilometre integer," +//是否是一公里
            "hasProblem integer," +//是否是问题坐标
            "kilometre_time integer," +//一公里配速
            "index_count text," +//索引字段
            "time_consuming integer)";//耗时


    //通讯录联系人表
    public static final String CREATE_CONATCT = "create table " + ContactTable.TABLE_NAME + "(" + ContactTable._ID
            + " integer primary key autoincrement, "
            + ContactTable.Mobile + " varchar(20),"
            + ContactTable.UserName + " varchar(20),"
            + ContactTable.UserEn + " varchar(20),"
            + ContactTable.Gender + " varchar(20),"
            + ContactTable.Photo + " varchar(20),"
            + ContactTable.UserRole + "  varchar(20),"
            + ContactTable.HXAccountId + "  varchar(20),"

            + ContactTable.Certification + " varchar(20),"
            + ContactTable.AccountId + " varchar(20),"
            + ContactTable.AFriendId + " varchar(20),"
            + ContactTable.AccpetTime + " varchar(20))";


    // 班级联系人表
    public static final String CREATE_CLASS_MEMBERS = "create table " + ClassMemberTable.TABLE_NAME + "(" + ClassMemberTable._ID
            + " integer primary key autoincrement, "
            + ClassMemberTable.HXAccountId + " varchar(20),"
            + ClassMemberTable.AccountId + " varchar(20),"
            + ClassMemberTable.UserName + " varchar(20),"
            + ClassMemberTable.Mobile + " varchar(20),"
            + ClassMemberTable.Photo + " varchar(20))";



    // 班级群聊表
    public static final String CREATE_CLASS_GROUP = "create table " + GroupTable.TABLE_NAME + "(" + GroupTable._ID
            + " integer primary key autoincrement, "
            + GroupTable.ClassId + " varchar(20),"
            + GroupTable.ClassName + " varchar(20),"
            + GroupTable.ClassCode + " varchar(20),"
            + GroupTable.HXGroupId + " varchar(20))";


    //用户帐号表
    public static final String CREATE_USER_INFO="create table user_info (" +
            "userId text primary key," +
            "userRole integer," +
            "roleName text," +
            "nickName text," +
            "gender text," +
            "weight text," +
            "height text," +
            "photo text," +
            "certification text," +
            "certTime text," +
            "mobile text," +
            "birthday text," +
            "isJoin text," +
            "todayStepCnt text," +
            "isCreatInfo text," +
            "HXAccountId text," +
            "HasEmchat text," +
            "HasThClass text," +
            "doingClass text," +
            "update_time text"+
            "exit text)";

    //添加用户表字段
    public static final String ADD_UPDATE_TIME_FOR_USER="ALTER TABLE user_info ADD COLUMN update_time text;";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STEP);
        db.execSQL(CREATE_SPORT_DATA);
        db.execSQL(CREATE_CONATCT);
        db.execSQL(CREATE_CLASS_MEMBERS);
        db.execSQL(CREATE_CLASS_GROUP);
        db.execSQL(CREATE_USER_INFO);
        Log.i("表创建了");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("onUpgrade(SQLiteDatabase db)");
        switch (oldVersion) {
            case 1:
                Log.i("版本1 更新 。。。。。。。。。。。。。。");
                db.execSQL("drop table user_step");//删除表
                db.execSQL(CREATE_STEP);//创建新表
            case 2:
                Log.i("版本2 更新 。。。。。。。。。。。。。。");
                db.execSQL(CREATE_CONATCT);
            case 3:
                Log.i("old版本3 更新 。。。。。。。。。。。。。。");
                db.execSQL(CREATE_CLASS_MEMBERS);
                db.execSQL(CREATE_CLASS_GROUP);
            case 4:
                Log.i("创建用户信息表");
                db.execSQL(CREATE_USER_INFO);
                break;
        }


    }
}
