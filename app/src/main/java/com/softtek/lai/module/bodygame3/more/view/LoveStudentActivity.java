package com.softtek.lai.module.bodygame3.more.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.love.CurrentClassFrament;
import com.softtek.lai.module.bodygame3.love.HistoryListFrament;
import com.softtek.lai.module.bodygame3.love.adapter.LoverAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 1/5/2017.
 */

@InjectLayout(R.layout.activity_love_student)
public class LoveStudentActivity extends BaseActivity {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.container)
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    LoverAdapter adapter;

    @Override
    protected void initViews() {
        tv_title.setText("爱心学员");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        long accountId = getIntent().getLongExtra("accountId", 0);
        String classId = getIntent().getStringExtra("classId");

        fragments.add(CurrentClassFrament.getInstance(accountId, classId));
        fragments.add(HistoryListFrament.getInstance(accountId, classId));
        adapter = new LoverAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        tab.setupWithViewPager(container);

    }

    @Override
    protected void initDatas() {

    }
}
