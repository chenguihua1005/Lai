package com.softtek.lai.module.bodygame3.more.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.widgets.CircleImageView;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_invitation_setting)
public class InvitationSettingActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_tianshi)
    TextView tv_tianshi;
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;
    @InjectView(R.id.tv_number)
    TextView tv_number;
    @InjectView(R.id.tv_create_time)
    TextView tv_create_time;
    @InjectView(R.id.tv_group_name)
    TextView tv_group_name;
    @InjectView(R.id.tv_role)
    TextView tv_role;

    @InjectView(R.id.rl_group)
    RelativeLayout rl_group;
    @InjectView(R.id.rl_role)
    RelativeLayout rl_role;


    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        ll_left.setOnClickListener(this);
        rl_group.setOnClickListener(this);
        rl_role.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
