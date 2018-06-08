package com.softtek.lai.module.bodygame3.conversation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

public class ConatctDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "ConatctDBHelper";
    public static final String DATABASE_NAME = "contactCache.db";

    private Context mContext;

    // 第一次创建数据库
    public ConatctDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        mContext = context;
    }

    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_contactTable());//创建通讯录联系人表
//        db.execSQL(create_classTable());// 创建班级表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    private String create_contactTable() {
        String create_Conatct = "create table " + ContactTable.TABLE_NAME + "(" + ContactTable._ID
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
        Log.i(TAG, "create_Conatct()..." + create_Conatct);

        return create_Conatct;
    }


    private String create_classTable() {
        String create_Class = "create table " + ClassTable.TABLE_NAME + "(" + ClassTable._ID
                + " integer primary key autoincrement, "
                + ClassTable.ClassId + " varchar(20),"
                + ClassTable.ClassName + " varchar(20),"
                + ClassTable.ClassCode + " varchar(20),"
                + ClassTable.HXGroupId + " varchar(20))";
        return create_Class;
    }
}
