package com.softtek.lai.module.bodygame3.conversation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;

import java.util.ArrayList;
import java.util.List;

import static com.softtek.lai.module.bodygame3.conversation.database.ContactTable.AccpetTime;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

public class ContactDao {
    private ConatctDBHelper dbOpenHelper;

    public ContactDao(Context context) {
        this.dbOpenHelper = new ConatctDBHelper(context);
    }

    public void clearContactTab() {//清空当前表
        String sql = "delete from " + ContactTable.TABLE_NAME + " ;";
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL(sql);
        }

    }


    public void insert(List<ChatContactModel> list) {
        try {
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
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
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
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


                String Certification = cursor.getString(cursor.getColumnIndex(ContactTable.AccpetTime));
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


}
