/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.InviteContantAdapter;
import com.softtek.lai.module.counselor.model.ContactListInfoModel;
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 邀请通讯录学员
 */
@InjectLayout(R.layout.activity_invite_contant_list)
public class InviteContantActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_contant)
    ListView list_contant;


    private IStudentPresenter studentPresenter;
    private ACache aCache;
    private UserModel userModel;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    private String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};


    private ArrayList<ContactListInfoModel> contactListValue = new ArrayList<ContactListInfoModel>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getResources().getString(zilla.libcore.R.string.dialog_loading));
        progressDialog.setMessage("正在加载内容...");
        progressDialog.show();
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());

        asyncQueryHandler.startQuery(0, null, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

        InviteContantAdapter adapter = new InviteContantAdapter(this, contactListValue);
        list_contant.setAdapter(adapter);


    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("参赛邀请");

    }

    @Override
    protected void initDatas() {
        studentPresenter = new StudentImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);


    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst(); // 游标移动到第一项
                //得到手机号码

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
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

                    }

                    ContactListInfoModel contactListInfo = new ContactListInfoModel(contactPhoto, name, number);
                    contactListValue.add(contactListInfo);

                }
                System.out.println("contactListValue:" + contactListValue);
                progressDialog.dismiss();
                super.onQueryComplete(token, cookie, cursor);
            } else {
                progressDialog.dismiss();
            }
        }

    }

}