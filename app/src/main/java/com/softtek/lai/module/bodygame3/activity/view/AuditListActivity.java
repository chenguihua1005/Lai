package com.softtek.lai.module.bodygame3.activity.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.head.adapter.RetestTabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by Terry on 2016/11/29.
 */
@InjectLayout(R.layout.activity_honorranking)
public class AuditListActivity extends BaseActivity{
    public static final int AUDITED=0;//已审核
    public static final int AUDIT=1;//未审核

    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    List<Fragment> fragments;
    @Override
    protected void initViews() {
        tv_title.setText("复测审核");
        String[] tabtitle={"未审核（4）","已审核（10）"};
        fragments=new ArrayList<>();
        fragments.add(AuditFragment.getInstance());
        fragments.add(AuditFragment.getInstance());
        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(),fragments,tabtitle));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {

    }

}
