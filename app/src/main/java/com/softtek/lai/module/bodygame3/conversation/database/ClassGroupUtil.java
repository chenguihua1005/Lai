package com.softtek.lai.module.bodygame3.conversation.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.softtek.lai.module.bodygame3.conversation.model.ClassMemberModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;

import java.util.List;

import zilla.libcore.db.DBHelper;

/**
 * Created by jessica.zhang on 1/23/2017.
 */

public class ClassGroupUtil {
    private static final String TAG = "ClassGroupUtil";

    private static ClassGroupUtil util;
    private DBHelper dbHelper;


    private ClassGroupUtil() {
        dbHelper = DBHelper.getInstance();
    }

    public static ClassGroupUtil getInstance() {
        if (util == null) {
            util = new ClassGroupUtil();
        }
        return util;
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
            String sql = "select count(*) as c from sqlite_master  where type ='table' and name ='" + GroupTable.TABLE_NAME + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();

        }
        return result;
    }


    public boolean isExistedThisItem(String HXGroupId) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            try {
                Cursor cursor = db.query(GroupTable.TABLE_NAME, null, GroupTable.HXGroupId + " = ? ",
                        new String[]{HXGroupId}, null, null, null);
                if (cursor.moveToFirst()) {
                    flag = true;
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                if (db != null)
//                    db.close();
            }
        }
        return flag;
    }


    /**
     * 删除一条记录
     */
    public void deleteItem(String HXGroupId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(GroupTable.TABLE_NAME, GroupTable.HXGroupId + " = ? ",
                    new String[]{HXGroupId});
            Log.i(TAG, "删除条目 hXAccountI = " + HXGroupId);
//            db.close();
        }
    }


    public void insert(List<ContactClassModel> list) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                for (int i = 0; i < list.size(); i++) {
                    ContactClassModel model = list.get(i);
                    String hxId = model.getHXGroupId();
                    if (isExistedThisItem(hxId)) {//存在相同条目,删除旧的
                        deleteItem(hxId);
                    }
                    Log.i(TAG, "存入 HXGroupId = " + hxId);

                    ContentValues values = new ContentValues();
                    values.put(GroupTable.ClassId, model.getClassId());
                    values.put(GroupTable.ClassName, model.getClassName());
                    values.put(GroupTable.ClassCode, model.getClassCode());
                    values.put(GroupTable.HXGroupId, model.getHXGroupId());

                    db.insert(GroupTable.TABLE_NAME, null, values);
                }
                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public GroupModel findGroup(String hXGroupId) {
        GroupModel model = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(GroupTable.TABLE_NAME, null, GroupTable.HXGroupId + " =?",
                    new String[]{hXGroupId}, null, null, null);
            if (cursor.moveToFirst()) {
                String ClassId = cursor.getString(cursor.getColumnIndex(GroupTable.ClassId));
                String ClassName = cursor.getString(cursor.getColumnIndex(GroupTable.ClassName));
                String ClassCode = cursor.getString(cursor.getColumnIndex(GroupTable.ClassCode));
                String HXGroupId = cursor.getString(cursor.getColumnIndex(GroupTable.HXGroupId));

                model = new GroupModel(ClassId, ClassName, ClassCode, HXGroupId);

            }
            cursor.close();
            db.close();
        }
        return model;
    }


}
