package com.softtek.lai.module.assistant.view;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.assistant.adapter.SimpleFragmentPagerAdapter;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_assistant)
public class TestActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;


    private IAssistantPresenter assistantPresenter;
    private ACache aCache;

    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    /**
     * 联系人显示名称
     **/

    private static final int PHONES_DISPLAY_NAME_INDEX = 0;


    /**
     * 电话号码
     **/

    private static final int PHONES_NUMBER_INDEX = 1;


    /**
     * 头像ID
     **/

    private static final int PHONES_PHOTO_ID_INDEX = 2;


    /**
     * 联系人的ID
     **/

    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * 联系人名称
     **/

    private ArrayList<String> mContactsName = new ArrayList<String>();


    /**
     * 联系人头像
     **/

    private ArrayList<String> mContactsNumber = new ArrayList<String>();


    /**
     * 联系人头像
     **/

    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        getPhoneContacts();
        getSIMContacts();


    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("创建班级");

    }

    @Override
    protected void initDatas() {
        assistantPresenter = new AssistantImpl(this);
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
     * 得到手机通讯录联系人信息
     **/

    private void getPhoneContacts() {

        ContentResolver resolver =getContentResolver();


        // 获取手机联系人

        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);


        if (phoneCursor != null) {

            while (phoneCursor.moveToNext()) {


                //得到手机号码

                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

                //当手机号码为空的或者为空字段 跳过当前循环

                if (TextUtils.isEmpty(phoneNumber))

                    continue;


                //得到联系人名称

                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                System.out.println("contactName:"+contactName);


                //得到联系人ID

                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);


                //得到联系人头像ID

                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);


                //得到联系人头像Bitamp

                Bitmap contactPhoto = null;


                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的

                if (photoid > 0) {

                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);

                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);

                    contactPhoto = BitmapFactory.decodeStream(input);

                } else {

                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.img_default);

                }


                mContactsName.add(contactName);

                mContactsNumber.add(phoneNumber);

                mContactsPhonto.add(contactPhoto);
                System.out.println("mContactsName:" + mContactsName);
                System.out.println("mContactsNumber:" + mContactsNumber);
                System.out.println("mContactsPhonto:"+mContactsPhonto);

            }


            phoneCursor.close();

        }

    }


    /**
     * 得到手机SIM卡联系人人信息
     **/

    private void getSIMContacts() {

        ContentResolver resolver =getContentResolver();

        // 获取Sims卡联系人

        Uri uri = Uri.parse("content://icc/adn");

        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,

                null);


        if (phoneCursor != null) {

            while (phoneCursor.moveToNext()) {


                // 得到手机号码

                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

                // 当手机号码为空的或者为空字段 跳过当前循环

                if (TextUtils.isEmpty(phoneNumber))

                    continue;

                // 得到联系人名称

                String contactName = phoneCursor

                        .getString(PHONES_DISPLAY_NAME_INDEX);


                //Sim卡中没有联系人头像


                mContactsName.add(contactName);

                mContactsNumber.add(phoneNumber);

            }


            phoneCursor.close();

        }

    }


}
