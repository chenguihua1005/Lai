/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.adapter.InviteContantAdapter;
import com.softtek.lai.module.counselor.model.ContactListInfoModel;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.HanziToPinyin;

import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jjerry.guan on 3/22/2016.
 * 邀请通讯录学员
 */
@InjectLayout(R.layout.activity_invite_contant_list)
public class InviteContantActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl)
    FrameLayout fl;
    @InjectView(R.id.et_search)
    TextView et_search;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_contant)
    ListView list_contant;

    InviteContantAdapter adapter;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象

    private String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};


    private ArrayList<ContactListInfoModel> contactListValue = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                loadContants();

            }
        }
    }

    private void loadContants() {
        progressDialog.show();
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
        asyncQueryHandler.startQuery(0, null, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

        adapter = new InviteContantAdapter(this, contactListValue);
        list_contant.setAdapter(adapter);
    }

    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.contactList);
        ll_left.setOnClickListener(this);
        fl.setOnClickListener(this);
        et_search.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载通讯录...");
    }

    @Override
    protected void initDatas() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
            }
        } else {
            loadContants();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.et_search:
            case R.id.fl:
                ACache.get(this).put("contactList",contactListValue);
                Intent intent = new Intent(this, SearchContantActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * @author jarvis
     */
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            new DealTask().execute(cursor);
        }

    }

    private void getFuzzyQueryByName(String key) {

        StringBuilder sb = new StringBuilder();
        ContentResolver cr = getContentResolver();
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.Contacts.DISPLAY_NAME + " like " + "'%" + key + "%'",
                null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            sb.append(name + " (").append(number + ")").append("\r\n");
        }
        cursor.close();

        if (!sb.toString().isEmpty()) {
        }
    }

    class DealTask extends AsyncTask<Cursor,Integer,Void>{

        @Override
        protected Void doInBackground(Cursor... params) {
            Cursor cursor=params[0];
            if (cursor != null && cursor.moveToFirst()) {
                //产生一个原型对象
                ContactListInfoModel prototype = new ContactListInfoModel();
                do{
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                   /* String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);
                    Bitmap contactPhoto = null;
                    //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                    if (photoId > 0) {
                        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
                        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), uri);
                        contactPhoto = BitmapFactory.decodeStream(input);
                    } else {
                        contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.img_default);
                    }*/
                    number = number.trim();
                    if (number.contains("-")) {
                        number = number.replace("-", "");
                    }
                    if (number.contains(" ")) {
                        number = number.replace(" ", "");
                    }
                    if (number.contains("+86")) {
                        number = number.replace("+86", "");
                    }
                    ContactListInfoModel contactListInfo =prototype.clone();
                    contactListInfo.setUserName(name);
                    contactListInfo.setMobile(number);
                    contactListValue.add(contactListInfo);

                }while (cursor.moveToNext());

            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
        }
    }

}
