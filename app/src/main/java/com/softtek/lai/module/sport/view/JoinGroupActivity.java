/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.view;


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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.softtek.lai.utils.ACache;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 加入跑团
 */
@InjectLayout(R.layout.activity_join_group)
public class JoinGroupActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.rel_dq)
    RelativeLayout rel_dq;

    @InjectView(R.id.rel_xq)
    RelativeLayout rel_xq;

    @InjectView(R.id.rel_cs)
    RelativeLayout rel_cs;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.img_dq)
    ImageView img_dq;

    @InjectView(R.id.img_xq)
    ImageView img_xq;

    @InjectView(R.id.img_cs)
    ImageView img_cs;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_dq)
    TextView text_dq;

    @InjectView(R.id.text_xq)
    TextView text_xq;

    @InjectView(R.id.text_cs)
    TextView text_cs;

    @InjectView(R.id.list_group)
    ListView list_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rel_dq.setOnClickListener(this);
        rel_xq.setOnClickListener(this);
        rel_cs.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.gamestH);
        iv_email.setImageResource(R.drawable.search);
    }

    @Override
    protected void initDatas() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.fl_right:

                break;
            case R.id.rel_dq:

                break;
            case R.id.rel_xq:

                break;
            case R.id.rel_cs:

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

}
