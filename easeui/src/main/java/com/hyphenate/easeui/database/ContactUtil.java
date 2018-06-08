package com.hyphenate.easeui.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.db.DBHelper;

import static com.hyphenate.easeui.database.ContactTable.AccpetTime;

/**
 * Created by jessica.zhang on 1/20/2017.
 */

public class ContactUtil {
    private static ContactUtil util;
    private DBHelper dbHelper;

    public void clearContactTab() {//清空当前表
        String sql = "delete from " + ContactTable.TABLE_NAME + " ;";
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
            String sql = "select count(*) as c from sqlite_master  where type ='table' and name ='" + ContactTable.TABLE_NAME + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {

        }
        return result;
    }

    public void insert(List<ChatContactModel> list) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                for (int i = 0; i < list.size(); i++) {
                    ChatContactModel model = list.get(i);
                    ContentValues values = new ContentValues();
                    values.put(ContactTable.Mobile, model.getMobile());
                    values.put(ContactTable.UserName, model.getUserName());

                    values.put(ContactTable.UserEn, model.getUserEn());
                    values.put(ContactTable.Gender, model.getGender());
                    values.put(ContactTable.Photo, model.getPhoto());

                    values.put(ContactTable.UserRole, model.getUserRole());
                    values.put(ContactTable.HXAccountId, model.getHXAccountId());

                    values.put(ContactTable.Certification, model.getCertification());
                    values.put(ContactTable.AccountId, model.getAccountId());
                    values.put(ContactTable.AFriendId, model.getAFriendId());
                    values.put(AccpetTime, model.getAccpetTime());
                    db.insert(ContactTable.TABLE_NAME, null, values);
                }
                db.close();
            }

//            Util.toastMsg("数据存入成功@！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<ChatContactModel> getAllConatct() {
        List<ChatContactModel> lists = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(ContactTable.TABLE_NAME, null, null, null, null, null,
                    null);
            lists = new ArrayList<ChatContactModel>();
            while (cursor.moveToNext()) {
                String Mobile = cursor.getString(cursor.getColumnIndex(ContactTable.Mobile));
                String UserName = cursor.getString(cursor.getColumnIndex(ContactTable.UserName));

                String UserEn = cursor.getString(cursor.getColumnIndex(ContactTable.UserEn));
                String Gender = cursor.getString(cursor.getColumnIndex(ContactTable.Gender));

                String Photo = cursor.getString(cursor.getColumnIndex(ContactTable.Photo));
                String UserRole = cursor.getString(cursor.getColumnIndex(ContactTable.UserRole));

                String HXAccountId = cursor.getString(cursor.getColumnIndex(ContactTable.HXAccountId));


                String Certification = cursor.getString(cursor.getColumnIndex(AccpetTime));
                String AccountId = cursor.getString(cursor.getColumnIndex(ContactTable.AccountId));
                String AFriendId = cursor.getString(cursor.getColumnIndex(ContactTable.AFriendId));
                String AccpetTime = cursor.getString(cursor.getColumnIndex(ContactTable.AccpetTime));
//(String mobile, String userName, String userEn, String gender, String photo, String userRole, String HXAccountId, String accpetTime, String certification, long accountId, String AFriendId) {

                ChatContactModel model = new ChatContactModel(Mobile, UserName, UserEn, Integer.parseInt(Gender), Photo, Integer.parseInt(UserRole), HXAccountId, AccpetTime, Certification, Long.parseLong(AccountId), AFriendId);
                lists.add(model);
            }
            cursor.close();
            db.close();
        }
        return lists;
    }


    //
    public ChatContactModel findContact(String hxaccountid) {
        ChatContactModel model = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(ContactTable.TABLE_NAME, null, ContactTable.HXAccountId + " =?",
                    new String[]{hxaccountid}, null, null, null);
            if (cursor.moveToFirst()) {
                String Mobile = cursor.getString(cursor.getColumnIndex(ContactTable.Mobile));
                String UserName = cursor.getString(cursor.getColumnIndex(ContactTable.UserName));

                String UserEn = cursor.getString(cursor.getColumnIndex(ContactTable.UserEn));
                String Gender = cursor.getString(cursor.getColumnIndex(ContactTable.Gender));

                String Photo = cursor.getString(cursor.getColumnIndex(ContactTable.Photo));
                String UserRole = cursor.getString(cursor.getColumnIndex(ContactTable.UserRole));

                String HXAccountId = cursor.getString(cursor.getColumnIndex(ContactTable.HXAccountId));


                String Certification = cursor.getString(cursor.getColumnIndex(AccpetTime));
                String AccountId = cursor.getString(cursor.getColumnIndex(ContactTable.AccountId));
                String AFriendId = cursor.getString(cursor.getColumnIndex(ContactTable.AFriendId));
                String AccpetTime = cursor.getString(cursor.getColumnIndex(ContactTable.AccpetTime));
//(String mobile, String userName, String userEn, String gender, String photo, String userRole, String HXAccountId, String accpetTime, String certification, long accountId, String AFriendId) {

                model = new ChatContactModel(Mobile, UserName, UserEn, Integer.parseInt(Gender), Photo, Integer.parseInt(UserRole), HXAccountId, AccpetTime, Certification, Long.parseLong(AccountId), AFriendId);

            }
            cursor.close();
            db.close();
        }
        com.github.snowdream.android.util.Log.i("model = " + model);
        return model;
    }


    private ContactUtil() {
        dbHelper = DBHelper.getInstance();
    }

    public static ContactUtil getInstance() {
        if (util == null) {
            util = new ContactUtil();
        }
        return util;
    }
}
