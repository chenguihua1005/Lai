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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_contactTable());//创建通讯录联系人表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
                + ContactTable.AccpetTime + " varchar(20))";
        Log.i(TAG, "create_Conatct()..." + create_Conatct);

        return create_Conatct;
    }
}
