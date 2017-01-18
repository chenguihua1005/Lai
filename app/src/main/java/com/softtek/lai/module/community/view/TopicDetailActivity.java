package com.softtek.lai.module.community.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_topic_detail)
public class TopicDetailActivity extends BaseActivity {

    @InjectView(R.id.ctl)
    CollapsingToolbarLayout ctl;


    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            tintManager.setStatusBarTintResource(android.R.color.transparent);
        }
        ctl.setTitle("#精彩踢馆赛#");
        //通过CollapsingToolbarLayout修改字体颜色
        ctl.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        ctl.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
    }

    @Override
    protected void initDatas() {

    }
}
