package com.softtek.lai.module.bodygame3.conversation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;

import java.util.List;

import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 2016/12/16.
 */

public class ClassDao {
    private ConatctDBHelper dbOpenHelper;

    public ClassDao(Context context) {
        this.dbOpenHelper = new ConatctDBHelper(context);
    }

    public void clearClassTab() {//清空当前表
        String sql = "delete from " + ClassTable.TABLE_NAME + " ;";
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL(sql);
        }
    }


    public void insert(List<ContactClassModel> list) {
        try {
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            if (db.isOpen()) {
                for (int i = 0; i < list.size(); i++) {
                    ContactClassModel model = list.get(i);
                    ContentValues values = new ContentValues();
                    values.put(ClassTable.ClassId, model.getClassId());
                    values.put(ClassTable.ClassName, model.getClassName());
                    values.put(ClassTable.ClassCode, model.getClassCode());
                    values.put(ClassTable.HXGroupId, model.getHXGroupId());
                    db.insert(ClassTable.TABLE_NAME, null, values);
                }
                db.close();
            }

            Util.toastMsg("数据存入成功@！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClassModelDB getClassInfoByHXGroupId(String HXGroupId) {
        ClassModelDB modelDB = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        if (db.isOpen()) {
            Cursor cursor = db.query(ClassTable.TABLE_NAME, null, ClassTable.HXGroupId + " = ?", new String[]{HXGroupId}, null, null, null);
            if (cursor.moveToFirst()) {
                String ClassId = cursor.getString(cursor.getColumnIndex(ClassTable.ClassId));
                String ClassName = cursor.getString(cursor.getColumnIndex(ClassTable.ClassName));

                String ClassCode = cursor.getString(cursor.getColumnIndex(ClassTable.ClassCode));
                String hxGroupId = cursor.getString(cursor.getColumnIndex(ClassTable.HXGroupId));

//                 ClassModelDB(String classId, String className, String classCode, String HXGroupId) {
                modelDB = new ClassModelDB(ClassId, ClassName, ClassCode, hxGroupId);
            }
            cursor.close();
            db.close();
        }


        return modelDB;
    }
}
