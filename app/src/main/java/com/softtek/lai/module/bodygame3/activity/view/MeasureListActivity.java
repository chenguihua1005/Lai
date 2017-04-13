package com.softtek.lai.module.bodygame3.activity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.RetestTabAdapter;
import com.softtek.lai.module.bodygame3.activity.model.MeasureListModel;
import com.softtek.lai.module.bodygame3.activity.presenter.MesurePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 4/7/2017.
 */

@InjectLayout(R.layout.activity_fucelist)
public class MeasureListActivity extends BaseActivity<MesurePresenter> implements MesurePresenter.MesureView {
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    List<Fragment> fragments;

    String[] tabtitle = {"未复测", "已复测"};
    private String classId;

    private int unchecknum = 0;//未复测人数
    private int checkednum = 0;//已复测人数

    @Override
    protected void initViews() {
        tv_title.setText("未测量学员 (0)");
        classId = getIntent().getStringExtra("classId");

        fragments = new ArrayList<>();
        fragments.add(UnMeasureFragment.getInstance(classId));
        fragments.add(MeasuredFragment.getInstance(classId));
        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(), fragments, tabtitle));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);

        setPresenter(new MesurePresenter(this));

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATENUMBER_MEASURE));
    }

    @Override
    protected void initDatas() {
        doGetData(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()), classId, 1, 100);
    }

    //获取审核列表数据
    private void doGetData(Long accountid, String classid, final int pageIndex, int pageSize) {
        getPresenter().getMesureList(accountid, classid, pageIndex, pageSize);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void getMesureList(List<MeasureListModel> list) {
        if (list != null) {
            if (list.size() == 0) {
                tabtitle[0] = "未复测学员(" + "0" + ")";
                tabtitle[1] = "已复测学员(" + "0" + ")";
                TabLayout.Tab tab1 = tab.getTabAt(0);
                tab1.setText(tabtitle[0]);
                TabLayout.Tab tab2 = tab.getTabAt(1);
                tab2.setText(tabtitle[1]);

                tv_title.setText(tabtitle[0]);
            } else {
                unchecknum = Integer.parseInt(list.get(0).getCount());
                checkednum = Integer.parseInt(list.get(1).getCount());
                tabtitle[0] = "未复测学员(" + unchecknum + ")";
                tabtitle[1] = "已复测学员(" + checkednum + ")";
                TabLayout.Tab tab1 = tab.getTabAt(0);
                tab1.setText(tabtitle[0]);
                TabLayout.Tab tab2 = tab.getTabAt(1);
                tab2.setText(tabtitle[1]);

                tv_title.setText(tabtitle[0]);
            }
        }
    }


    public static final String UPDATENUMBER_MEASURE = "UPDATENUMBER_MEASURE";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && UPDATENUMBER_MEASURE.equalsIgnoreCase(intent.getAction())) {
                String number_uncheck = intent.getStringExtra("number_uncheck");
                String number_checked = intent.getStringExtra("number_checked");
                String str = "未复测学员(" + number_uncheck + ")";
                String str2 = "已复测学员(" + number_checked + ")";
                tv_title.setText(str);

                TabLayout.Tab tab1 = tab.getTabAt(0);
                tab1.setText(str);
                TabLayout.Tab tab2 = tab.getTabAt(1);
                tab2.setText(str2);

            }
        }
    };

    @Override
    public void hidenLoading() {

    }
}
