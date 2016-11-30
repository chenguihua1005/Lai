package com.softtek.lai.module.bodygame3.conversation.view;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 2016/11/29.
 */

@InjectLayout(R.layout.activity_classdetail)
public class ClassDetailActivity extends BaseActivity {
    @InjectView(R.id.coach_img)
    ImageView coach_img;
    @InjectView(R.id.coach_name)
    TextView coach_name;

    @InjectView(R.id.tv_classname)
    TextView tv_classname;

    @InjectView(R.id.tv_classNo)
    TextView tv_classNo;

    @InjectView(R.id.tv_classStart_time)
    TextView tv_classStart_time;

    @InjectView(R.id.tv_members_accout)
    TextView tv_members_accout;
    @InjectView(R.id.btn_dismissclass)
    Button btn_dismissclass;


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }
}
