/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.softtek.lai.module.login.model.UserModel;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 邀请学员列表
 */
@InjectLayout(R.layout.activity_invite_student_list)
public class InviteStudentActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_student)
    ListView list_student;

    @InjectView(R.id.but_contant)
    Button but_contant;


    private IStudentPresenter studentPresenter;
    private UserModel userModel;

    @Override
    protected void initViews() {
        tv_title.setText(R.string.joinGame);
        ll_left.setOnClickListener(this);
        but_contant.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        studentPresenter = new StudentImpl(this);
        userModel=UserInfoModel.getInstance().getUser();
        String id = userModel.getUserid();
        String classId = SharedPreferenceService.getInstance().get("classId", "");
        dialogShow("加载中");
        studentPresenter.getNotInvitePC(classId, id, list_student);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.but_contant:
                startActivity(new Intent(this, InviteContantActivity.class));
                break;
        }
    }

}
