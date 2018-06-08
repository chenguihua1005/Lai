/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.group.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.group.adapter.GroupAdapter;
import com.softtek.lai.module.group.model.CityModel;
import com.softtek.lai.module.group.model.DxqModel;
import com.softtek.lai.module.group.model.GroupModel;
import com.softtek.lai.module.group.presenter.SportGroupManager;
import com.softtek.lai.widgets.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 加入跑团
 */
@InjectLayout(R.layout.activity_join_group)
public class JoinGroupActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, SportGroupManager.GetGroupListCallBack {

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

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

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

    List<GroupModel> group_list = new ArrayList<>();
    GroupAdapter adapter;

    SportGroupManager sportGroupManager;

    List<String> dq_name_list;
    List<String> dq_id_list;
    List<String> dq_is_head_list;
    String select_dq_name = "";
    String select_dq_id = "";
    int select_dq_posion = 0;
    String select_is_head = "";

    String select_is_head_info = "";
    String select_dq_name_info = "";
    String select_dq_id_info = "";
    int select_dq_posion_info = 0;

    List<String> xq_name_list;
    List<String> xq_id_list;
    String select_xq_name = "";
    String select_xq_id = "";
    int select_xq_posion = 0;
    String select_xq_name_info = "";
    String select_xq_id_info = "";
    int select_xq_posion_info = 0;

    List<String> city_name_list;
    List<String> city_id_list;
    String select_city_name = "";
    String select_city_id = "";
    int select_city_posion = 0;
    String select_city_name_info = "";
    String select_city_id_info = "";
    int select_city_posion_info = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        rel_dq.setOnClickListener(this);
        rel_xq.setOnClickListener(this);
        rel_cs.setOnClickListener(this);
        list_group.setVisibility(View.GONE);
        list_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupModel groupModel = group_list.get(position);
                if ("1".equals(groupModel.getIsHasSonRG())) {
                    Intent intent = new Intent(JoinGroupActivity.this, GroupSecActivity.class);
                    intent.putExtra("type", "0");
                    intent.putExtra("select_name", select_city_name + " > " + groupModel.getRGName());
                    intent.putExtra("parent_name", groupModel.getRGName());
                    intent.putExtra("id", groupModel.getRGId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initViews() {
        tv_title.setText(R.string.gamestH);
        iv_email.setImageResource(R.drawable.img_join_group_search);
    }

    @Override
    protected void initDatas() {
        sportGroupManager = new SportGroupManager(this);
        dialogShow("加载中");
        dq_name_list = new ArrayList<>();
        dq_id_list = new ArrayList<>();
        dq_is_head_list = new ArrayList<>();
        sportGroupManager.getBregionList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.fl_right:
                startActivity(new Intent(this, GroupSearchActivity.class));
                break;
            case R.id.rel_dq:
                if (dq_name_list.size() != 0) {
                    showDaQuDialog();
                }
                break;
            case R.id.rel_xq:
                if ("选择大区".equals(text_dq.getText())) {

                } else {
                    if (xq_name_list.size() != 0) {
                        showXiaoQuDialog();
                    }
                }
                break;
            case R.id.rel_cs:
                if ("选择小区".equals(text_xq.getText())) {

                } else {
                    if (city_name_list.size() != 0) {
                        showCityDialog();
                    }
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void resetCity() {
        text_cs.setTextColor(ContextCompat.getColor(this,R.color.word));
        text_cs.setText("选择城市");
        img_cs.setImageResource(R.drawable.img_join_group_select);

        city_name_list = new ArrayList<>();
        city_id_list = new ArrayList<>();
        select_city_name = "";
        select_city_id = "";
        select_city_posion = 0;
        select_city_name_info = "";
        select_city_id_info = "";
        select_city_posion_info = 0;

        img_mo_message.setVisibility(View.GONE);
        list_group.setVisibility(View.GONE);

    }

    private void resetXQCity() {
        text_xq.setTextColor(ContextCompat.getColor(this,R.color.word));
        text_xq.setText("选择小区");
        img_xq.setImageResource(R.drawable.img_join_group_select);
        text_cs.setTextColor(ContextCompat.getColor(this,R.color.word12));
        text_cs.setText("选择城市");
        img_cs.setImageResource(R.drawable.img_join_group_selected);

        xq_name_list = new ArrayList<String>();
        xq_id_list = new ArrayList<String>();
        select_xq_name = "";
        select_xq_id = "";
        select_xq_posion = 0;
        select_xq_name_info = "";
        select_xq_id_info = "";
        select_xq_posion_info = 0;

        city_name_list = new ArrayList<String>();
        city_id_list = new ArrayList<String>();
        select_city_name = "";
        select_city_id = "";
        select_city_posion = 0;
        select_city_name_info = "";
        select_city_id_info = "";
        select_city_posion_info = 0;

        img_mo_message.setVisibility(View.GONE);
        list_group.setVisibility(View.GONE);

        rel_xq.setVisibility(View.VISIBLE);
        rel_cs.setVisibility(View.VISIBLE);

    }

    public void showDaQuDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select, null);
        final WheelView wheel = (WheelView) view.findViewById(R.id.wheel);
        select_dq_id_info = "";
        select_dq_name_info = "";
        select_is_head_info = "";
        select_dq_posion_info = 0;
        wheel.setOffset(1);
        wheel.setItems(dq_name_list);
        wheel.setSeletion(select_dq_posion);
        wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_dq_id_info = dq_id_list.get(selectedIndex - 1);
                select_is_head_info = dq_is_head_list.get(selectedIndex - 1);
                select_dq_name_info = item;
                select_dq_posion_info = selectedIndex - 1;
            }
        });
        birdialog.setTitle("选择大区").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_dq_name_info) && "选择大区".equals(text_dq.getText().toString())) {
                            select_dq_name = dq_name_list.get(0);
                            select_dq_id = dq_id_list.get(0);
                            select_is_head = dq_is_head_list.get(0);
                            select_dq_posion = 0;
                            text_dq.setText(select_dq_name);
                            resetXQCity();
                            dialogShow("加载中");
                            // sportGroupManager.getSregionList(select_dq_id);
                            if ("1".equals(select_is_head)) {
                                sportGroupManager.getHQRGlist(select_dq_id);
                                rel_xq.setVisibility(View.GONE);
                                rel_cs.setVisibility(View.GONE);
                            } else {
                                sportGroupManager.getSregionList(select_dq_id);
                            }
                            return;
                        }
                        if (!"".equals(select_dq_name_info)) {
                            select_dq_name = select_dq_name_info;
                            select_dq_id = select_dq_id_info;
                            select_is_head = select_is_head_info;
                            select_dq_posion = select_dq_posion_info;
                            text_dq.setText(select_dq_name);
                            resetXQCity();
                            dialogShow("加载中");
                            if ("1".equals(select_is_head)) {
                                sportGroupManager.getHQRGlist(select_dq_id);
                                rel_xq.setVisibility(View.GONE);
                                rel_cs.setVisibility(View.GONE);
                            } else {
                                sportGroupManager.getSregionList(select_dq_id);
                            }
                        }else {
                            text_dq.setText(select_dq_name);
                            resetXQCity();
                            dialogShow("加载中");
                            if ("1".equals(select_is_head)) {
                                sportGroupManager.getHQRGlist(select_dq_id);
                                rel_xq.setVisibility(View.GONE);
                                rel_cs.setVisibility(View.GONE);
                            } else {
                                sportGroupManager.getSregionList(select_dq_id);
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create()
                .show();
    }

    public void showXiaoQuDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select, null);
        final WheelView wheel = (WheelView) view.findViewById(R.id.wheel);
        select_xq_id_info = "";
        select_xq_name_info = "";
        select_xq_posion_info = 0;
        wheel.setOffset(1);
        wheel.setItems(xq_name_list);
        wheel.setSeletion(select_xq_posion);
        wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_xq_id_info = xq_id_list.get(selectedIndex - 1);
                select_xq_name_info = item;
                select_xq_posion_info = selectedIndex - 1;
            }
        });
        birdialog.setTitle("选择小区").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_xq_name_info) && "选择小区".equals(text_xq.getText().toString())) {
                            select_xq_name = xq_name_list.get(0);
                            select_xq_id = xq_id_list.get(0);
                            select_xq_posion = 0;
                            text_xq.setText(select_xq_name);
                            resetCity();
                            dialogShow("加载中");
                            sportGroupManager.getCityList(select_xq_id);
                        }
                        if (!"".equals(select_xq_name_info)) {
                            select_xq_name = select_xq_name_info;
                            select_xq_id = select_xq_id_info;
                            select_xq_posion = select_xq_posion_info;
                            text_xq.setText(select_xq_name);
                            resetCity();
                            dialogShow("加载中");
                            sportGroupManager.getCityList(select_xq_id);
                        }else {
                            text_xq.setText(select_xq_name);
                            resetCity();
                            dialogShow("加载中");
                            sportGroupManager.getCityList(select_xq_id);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create()
                .show();
    }

    public void showCityDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select, null);
        final WheelView wheel = (WheelView) view.findViewById(R.id.wheel);
        select_city_id_info = "";
        select_city_name_info = "";
        select_city_posion_info = 0;
        wheel.setOffset(1);
        wheel.setItems(city_name_list);
        wheel.setSeletion(select_city_posion);
        wheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_city_id_info = city_id_list.get(selectedIndex - 1);
                select_city_name_info = item;
                select_city_posion_info = selectedIndex - 1;
            }
        });
        birdialog.setTitle("选择城市").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_city_name_info) && "选择城市".equals(text_cs.getText().toString())) {
                            select_city_name = city_name_list.get(0);
                            select_city_id = city_id_list.get(0);
                            select_city_posion = 0;
                            text_cs.setText(select_city_name);
                            dialogShow("加载中");
                            sportGroupManager.getRGListByCity(select_city_id);
                        }
                        if (!"".equals(select_city_name_info)) {
                            select_city_name = select_city_name_info;
                            select_city_id = select_city_id_info;
                            select_city_posion = select_city_posion_info;
                            text_cs.setText(select_city_name);
                            dialogShow("加载中");
                            sportGroupManager.getRGListByCity(select_city_id);
                        }else {
                            text_cs.setText(select_city_name);
                            dialogShow("加载中");
                            sportGroupManager.getRGListByCity(select_city_id);
                        }
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
        try {
            if ("success".equals(type)) {
                for (int i = 0; i < list.size(); i++) {
                    dq_name_list.add(list.get(i).getRegionName());
                    dq_id_list.add(list.get(i).getRegionId());
                    dq_is_head_list.add(list.get(i).getIsHeadOffice());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getSregionList(String type, List<DxqModel> list) {
        dialogDissmiss();
        try {
            if ("success".equals(type)) {
                for (int i = 0; i < list.size(); i++) {
                    xq_name_list.add(list.get(i).getRegionName());
                    xq_id_list.add(list.get(i).getRegionId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCityList(String type, List<CityModel> list) {
        dialogDissmiss();
        try {
            if ("success".equals(type)) {
                for (int i = 0; i < list.size(); i++) {
                    city_name_list.add(list.get(i).getCityName());
                    city_id_list.add(list.get(i).getCityId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRGListByCity(String type, List<GroupModel> list) {
        dialogDissmiss();
        try {
            if ("success".equals(type)) {
                group_list = list;
                if (group_list.size() > 0) {
                    img_mo_message.setVisibility(View.GONE);
                } else {
                    img_mo_message.setVisibility(View.VISIBLE);
                }
                list_group.setVisibility(View.VISIBLE);
                adapter = new GroupAdapter(this, group_list);
                list_group.setAdapter(adapter);
            } else {
                group_list = new ArrayList<GroupModel>();
                list_group.setVisibility(View.GONE);
                img_mo_message.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getHQRGlist(String type, List<GroupModel> list) {
        dialogDissmiss();
        try {
            if ("success".equals(type)) {
                group_list = list;
                if (group_list.size() > 0) {
                    img_mo_message.setVisibility(View.GONE);
                } else {
                    img_mo_message.setVisibility(View.VISIBLE);
                }
                list_group.setVisibility(View.VISIBLE);
                adapter = new GroupAdapter(this, group_list);
                list_group.setAdapter(adapter);
            } else {
                group_list = new ArrayList<GroupModel>();
                list_group.setVisibility(View.GONE);
                img_mo_message.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
