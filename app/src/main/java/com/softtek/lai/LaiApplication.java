/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */
package com.softtek.lai;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.github.snowdream.android.util.Log;
import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNResultCallback;
import com.kitnew.ble.utils.QNLog;
import com.softtek.lai.chat.ChatHelper;
import com.softtek.lai.common.CrashHandler;
import com.softtek.lai.common.ImageDownLoader;
import com.softtek.lai.common.MyOkClient;
import com.softtek.lai.common.NetErrorHandler;
import com.softtek.lai.common.UnlimitDiskUsage;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.database.ClassMemberTable;
import com.softtek.lai.module.bodygame3.conversation.database.ContactTable;
import com.softtek.lai.module.bodygame3.conversation.database.GroupTable;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.MD5;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.db.DBHelper;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.PropertiesManager;

/**
 * Created by zilla on 9/8/15.
 */
public class LaiApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {

    private static LaiApplication laiApplication;
    private WeakReference<Context> context;


//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        laiApplication = this;
        QNApiManager.getApi(getApplicationContext()).initSDK("123456789", new QNResultCallback() {
            @Override
            public void onCompete(int i) {
                android.util.Log.d("maki","执行结果校验" + i);
            }
        });
        QNLog.DEBUG = true;
        new Zilla().setCallBack(this).initSystem(this);
        UserInfoModel.getInstance(this);
        JPushInterface.init(this);
        ChatHelper.getInstance().init(getApplicationContext());
        Picasso.setSingletonInstance(new Picasso.Builder(this).
                downloader(new ImageDownLoader(new OkHttpClient.Builder()))
                .build());
        LeakCanary.install(this);
//        refWatcher=LeakCanary.install(this);
//        CrashHandler.getInstance().init(this);
    }
//
//    public static  RefWatcher getWatch(Context context){
//        LaiApplication application= (LaiApplication) context.getApplicationContext();
//            return application.refWatcher;
//    }
    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        LaiApplication app = (LaiApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        File video_dir=new File(Environment.getExternalStorageDirectory(),"lai_video");
        if(!video_dir.exists()){
            video_dir.mkdir();
        }
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(video_dir)
                .fileNameGenerator(new FileNameGenerator() {
                    @Override
                    public String generate(String s) {
                        String subStr=s.substring(0,s.lastIndexOf("."));
                        String videoId=MD5.md5WithEncoder(subStr);
                        return videoId +s.substring(s.lastIndexOf("."),s.length());
                    }
                })
                .diskUsage(new UnlimitDiskUsage()).build();
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
        ZillaApi.NormalRestAdapter = getRESTAdapter(new RequestInterceptor() {
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

    public static RestAdapter getRESTAdapter(RequestInterceptor requestInterceptor) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(AddressManager.getHost())
                .setRequestInterceptor(requestInterceptor)
                .setClient(new MyOkClient())
                .build();
        return setLog(restAdapter);
    }

    public static RestAdapter getRESTAdapter(String host) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(host)
                .setClient(new MyOkClient())
                .build();
        return setLog(restAdapter);
    }
    private static RestAdapter setLog(RestAdapter restAdapter) {
        if (Boolean.parseBoolean(PropertiesManager.get("log"))) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            restAdapter.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        return restAdapter;
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
    public static final String CREATE_USER_INFO = "create table user_info (" +
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
            "update_time text" +
            "exit text)";

    //添加用户表字段
    public static final String ADD_UPDATE_TIME_FOR_USER = "ALTER TABLE user_info ADD COLUMN update_time text;";

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
