/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.grade.adapter.TutorAdapter;
import com.softtek.lai.module.grade.eventModel.SRInfoEvent;
import com.softtek.lai.module.grade.model.SRInfo;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import zilla.libcore.ui.InjectLayout;

import java.util.ArrayList;
import java.util.List;

@InjectLayout(R.layout.activity_tutor)
public class TutorActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ListView>, OnClickListener {

    @InjectView(R.id.ptrlv)
    PullToRefreshListView prlv;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;


    private IGrade grade;
    private TutorAdapter adapter;
    List<SRInfo> infos = new ArrayList<>();

    @Override
    protected void initViews() {
        prlv.setOnRefreshListener(this);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_title.setText("助教列表");
        tv_right.setText("邀请助教");
        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        grade = new GradeImpl();
        adapter = new TutorAdapter(this, infos);
        prlv.setAdapter(adapter);
        //第一次加载自动刷新
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                prlv.setRefreshing();
            }
        }, 500);
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        grade.getTutorList(1, prlv);

    }

    @Subscribe
    public void onRefreshTutor(SRInfoEvent event) {
        infos.clear();
        infos.addAll(event.getInfos());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                break;
        }
    }
}
