/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.view;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.studetail.model.Member;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student_detail)
public class StudentDetailActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.civ_header_image)
    CircleImageView civ_header_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_totle_log)
    TextView tv_totle_log;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabContent;

    private ProgressDialog progressDialog;
    private IMemberInfopresenter memberInfopresenter;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");
    }

    @Override
    protected void initDatas() {
        EventBus.getDefault().register(this);
        memberInfopresenter = new MemberInfoImpl(this);
        tv_title.setText("学员详情");
        progressDialog.show();
        memberInfopresenter.getMemberinfo("1", "4", progressDialog);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetData(Member member) {
        Picasso.with(this).load(member.getPhoto()).error(R.drawable.default_pic).into(civ_header_image);
        Picasso.with(this).load(member.getBeforImg()).error(R.drawable.default_pic).into(iv_loss_before);
        Picasso.with(this).load(member.getAfterImg()).error(R.drawable.default_pic).into(iv_loss_after);
        tv_name.setText(member.getUserName());
        tv_phone.setText(member.getMobile());
        tv_totle_log.setText(member.getLogCount() + "篇");
        tv_totle_lw.setText(member.getLossWeight() + "kg");
        tv_loss_before.setText(member.getLossBefor() + "kg");
        tv_loss_after.setText(member.getLossAfter() + "kg");

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
