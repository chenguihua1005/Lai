package com.hyphenate.easeui.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import zilla.libcore.db.DBHelper;


/**
 * Created by jessica.zhang on 1/22/2017.
 */

public class ClassMemberUtil {
    private static final String TAG = "ClassMemberUtil";
    private static ClassMemberUtil util;
    private DBHelper dbHelper;

    public void clearContactTab() {//清空当前表
        String sql = "delete from " + ClassMemberTable.TABLE_NAME + " ;";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL(sql);
        }
    }

    /**
     * 判断某张表是否存在
     *
     * @return
     */
    public boolean tableIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String sql = "select count(*) as c from sqlite_master  where type ='table' and name ='" + ClassMemberTable.TABLE_NAME + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    public boolean isExistedThisItem(String hXAccountId) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(ClassMemberTable.TABLE_NAME, null, ClassMemberTable.HXAccountId + " = ? ",
                    new String[]{hXAccountId}, null, null, null);
            if (cursor.moveToFirst()) {
                flag = true;
            }
            cursor.close();
            db.close();
        }
        return flag;
    }

    /**
     * 删除一条记录
     */
    public void deleteItem(String hXAccountI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(ClassMemberTable.TABLE_NAME, ClassMemberTable.HXAccountId + " = ? ",
                    new String[]{hXAccountI});
            Log.i(TAG, "删除条目 hXAccountI = " + hXAccountI);
            db.close();
        }
    }


    public void insert(List<ClassMemberModel> list) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                for (int i = 0; i < list.size(); i++) {
                    ClassMemberModel model = list.get(i);
                    String hxId = model.getHXAccountId();
                    if (isExistedThisItem(hxId)) {//存在相同条目,删除旧的
                        deleteItem(hxId);
                    }
                    Log.i(TAG, "存入 HXAccountId = " + hxId);

                    ContentValues values = new ContentValues();
                    values.put(ClassMemberTable.HXAccountId, model.getHXAccountId());
                    values.put(ClassMemberTable.AccountId, model.getAccountId());
                    values.put(ClassMemberTable.UserName, model.getUserName());
                    values.put(ClassMemberTable.Mobile, model.getMobile());
                    values.put(ClassMemberTable.Photo, model.getPhoto());

//                    values.put(ClassMemberTable.CGId, model.getCGId());
//                    values.put(ClassMemberTable.CGName, model.getCGName());

                    db.insert(ClassMemberTable.TABLE_NAME, null, values);
                }
                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ClassMemberModel findClassMember(String hxaccountid) {
        ClassMemberModel model = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(ClassMemberTable.TABLE_NAME, null, ClassMemberTable.HXAccountId + " =?",
                    new String[]{hxaccountid}, null, null, null);
            if (cursor.moveToFirst()) {
                String HXAccountId = cursor.getString(cursor.getColumnIndex(ClassMemberTable.HXAccountId));
                String AccountId = cursor.getString(cursor.getColumnIndex(ClassMemberTable.AccountId));
                String UserName = cursor.getString(cursor.getColumnIndex(ClassMemberTable.UserName));
                String Mobile = cursor.getString(cursor.getColumnIndex(ClassMemberTable.Mobile));
                String Photo = cursor.getString(cursor.getColumnIndex(ClassMemberTable.Photo));

//                String CGId = cursor.getString(cursor.getColumnIndex(ClassMemberTable.CGId));
//                String CGName = cursor.getString(cursor.getColumnIndex(ClassMemberTable.CGName));
                model = new ClassMemberModel();
                model.setHXAccountId(HXAccountId);
                model.setAccountId(Long.parseLong(AccountId));
                model.setUserName(UserName);
                model.setMobile(Mobile);
                model.setPhoto(Photo);
//                model.setCGId(CGId);
//                model.setCGName(CGName);
            }
            cursor.close();
            db.close();
        }
        com.github.snowdream.android.util.Log.i("班级成员查询 = " + model);
        return model;
    }

    private ClassMemberUtil() {
        dbHelper = DBHelper.getInstance();
    }

    public static ClassMemberUtil getInstance() {
        if (util == null) {
            util = new ClassMemberUtil();
        }
        return util;
    }


}
