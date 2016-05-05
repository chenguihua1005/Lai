/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.view;


import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
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
import com.softtek.lai.module.sport.model.CityModel;
import com.softtek.lai.module.sport.model.DxqModel;
import com.softtek.lai.module.sport.model.GroupModel;
import com.softtek.lai.module.sport.presenter.SportGroupManager;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.WheelView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 加入跑团
 */
@InjectLayout(R.layout.activity_join_group)
public class JoinGroupActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener, SportGroupManager.GetGroupListCallBack {

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

    SportGroupManager sportGroupManager;

    List<String> dq_name_list;
    List<String> dq_id_list;
    String select_dq_name = "";
    String select_dq_id = "";
    int select_dq_posion = 0;

    String select_dq_name_info = "";
    String select_dq_id_info = "";
    int select_dq_posion_info = 0;


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
        sportGroupManager = new SportGroupManager(this);
        dialogShow("加载中");
        dq_name_list = new ArrayList<String>();
        dq_id_list = new ArrayList<String>();
        sportGroupManager.getBregionList();
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
                showDaquDialog();
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

    public void showDaquDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select, null);
        final WheelView wheel = (WheelView) view.findViewById(R.id.wheel);
        select_dq_id_info = "";
        select_dq_name_info = "";
        select_dq_posion_info = 0;

        wheel.setOffset(1);
        wheel.setItems(dq_name_list);
        wheel.setSeletion(select_dq_posion);
        wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_dq_id_info = dq_id_list.get(selectedIndex - 1);
                select_dq_name_info = item;
                select_dq_posion_info = selectedIndex - 1;
            }
        });
        birdialog.setTitle("选择大区").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_dq_name_info)) {
                            select_dq_name = dq_name_list.get(0);
                            select_dq_id = dq_id_list.get(0);
                            select_dq_posion = 0;
                        } else {
                            select_dq_name = select_dq_name_info;
                            select_dq_id = select_dq_id_info;
                            select_dq_posion = select_dq_posion_info;
                        }
                        text_dq.setText(select_dq_name);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create()
                .show();
    }

    @Override
    public void getBregionList(String type, List<DxqModel> list) {
        dialogDissmiss();
        if ("success".equals(type)) {
            for (int i = 0; i < list.size(); i++) {
                dq_name_list.add(list.get(i).getRegionName());
                dq_id_list.add(list.get(i).getRegionId());
            }
        }

    }

    @Override
    public void getSregionList(String type, List<DxqModel> list) {

    }

    @Override
    public void getCityList(String type, List<CityModel> list) {

    }

    @Override
    public void getRGListByCity(String type, List<GroupModel> list) {

    }
}
