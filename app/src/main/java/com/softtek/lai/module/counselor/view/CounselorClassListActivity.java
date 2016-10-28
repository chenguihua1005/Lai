/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.presenter.CounselorClassImpl;
import com.softtek.lai.module.counselor.presenter.ICounselorClassPresenter;
import com.softtek.lai.module.grade.view.GradeHomeActivity;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 体管赛，班级列表
 */
@InjectLayout(R.layout.activity_counselor_classlist)
public class CounselorClassListActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.expand_list)
    ListView expand_list;

    @InjectView(R.id.lin_create_class)
    LinearLayout lin_create_class;

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;


    private ICounselorClassPresenter counselorClassPresenter;
    //private CounselorClassAdapter adapter;
    /*List<ClassInfoModel> list;
    List<String> time_month_list = new ArrayList<String>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        lin_create_class.setOnClickListener(this);
        expand_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CounselorClassListActivity.this, GradeHomeActivity.class);
                ClassInfoModel classInfo = (ClassInfoModel) expand_list.getAdapter().getItem(position);
                SharedPreferenceService.getInstance().put("classId", classInfo.getClassId());
                intent.putExtra("classId", classInfo.getClassId());
                intent.putExtra("review", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        tv_title.setText("班级列表");

    }

    @Override
    public void onResume() {
        super.onResume();
        dialogShow("加载中");
        counselorClassPresenter = new CounselorClassImpl(this);
        counselorClassPresenter.getClassList("0", expand_list, lin_create_class, img_mo_message);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_create_class:        //我要开班
                startActivity(new Intent(this, CreateCounselorClassActivity.class));
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }


}
