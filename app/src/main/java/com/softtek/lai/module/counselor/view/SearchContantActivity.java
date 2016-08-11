/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.HanziToPinyin;
import com.softtek.lai.utils.SoftInputUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 邀请通讯录学员
 */
@InjectLayout(R.layout.activity_search_contant)
public class SearchContantActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.et_search)
    EditText et_search;

    @InjectView(R.id.list_contant)
    ListView list_contant;


    private IStudentPresenter studentPresenter;
    private UserModel userModel;
    InviteContantAdapter adapter;


    private List<ContactListInfoModel> contactListValue = new ArrayList<ContactListInfoModel>();
    private List<ContactListInfoModel> contactValue = new ArrayList<ContactListInfoModel>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setMessage(getResources().getString(zilla.libcore.R.string.dialog_loading));
//        progressDialog.setMessage("正在加载内容...");
//        progressDialog.show();
        contactListValue = (ArrayList<ContactListInfoModel>) getIntent().getSerializableExtra("list");

        adapter = new InviteContantAdapter(this, contactValue);
        list_contant.setAdapter(adapter);

//        String str="我萨达DSSDSss";
//        String pin = getPinYin(str);
//        String s="w";
//        System.out.println("ssssss:"+pin.contains(s)+"    pin:"+pin);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contactValue.clear();
                String str = s.toString();
                if (str.length() != 0) {
                    for (int i = 0; i < contactListValue.size(); i++) {
                        ContactListInfoModel contactListInfoModel = contactListValue.get(i);
                        String py = getPinYin(contactListInfoModel.getUserName());
                        if (py.contains(str)) {
                            if (contactValue.contains(contactListInfoModel)) {

                            } else {
                                contactValue.add(contactListInfoModel);
                            }
                        }
                        if (contactListInfoModel.getUserName().contains(str)) {
                            if (contactValue.contains(contactListInfoModel)) {

                            } else {
                                contactValue.add(contactListInfoModel);
                            }
                        }
                        if (contactListInfoModel.getMobile().contains(str)) {
                            if (contactValue.contains(contactListInfoModel)) {

                            } else {
                                contactValue.add(contactListInfoModel);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

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
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("搜索联系人");

    }

    @Override
    protected void initDatas() {
        studentPresenter = new StudentImpl(this);
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
        et_search.findFocus();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

        }
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
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


}
