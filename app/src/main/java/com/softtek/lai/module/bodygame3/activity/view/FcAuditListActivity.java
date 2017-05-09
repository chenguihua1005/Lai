package com.softtek.lai.module.bodygame3.activity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.activity.adapter.RetestTabAdapter;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.presenter.FuceCheckListPresenter;
import com.softtek.lai.module.laicheng.util.BleManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by Terry on 2016/11/29.
 */
@InjectLayout(R.layout.activity_honorranking)
public class FcAuditListActivity extends BaseActivity<FuceCheckListPresenter> implements FuceCheckListPresenter.FuceCheckListView {
    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    List<Fragment> fragments;
    String[] tabtitle = {"未复测", "待审核", "已审核"};

    String classId;
    String typeDate;
    int resetdatestatus;

    int unFuce_num = 0;
    int uncheck_num = 0;
    int checked_num = 0;

    @Override
    protected void initViews() {
        tv_title.setText("复测审核");
        classId = getIntent().getStringExtra("classId");
        typeDate = getIntent().getStringExtra("typeDate");
        resetdatestatus = getIntent().getIntExtra("resetdatestatus", 0);//接收复测日状态//复测日状态  1:已过去 2：进行中 3：未开始
        fragments = new ArrayList<>();
        fragments.add(UnFuceStuFragment.getInstance(classId, typeDate, resetdatestatus));//
        fragments.add(FcAuditFragment.getInstance(classId, typeDate, resetdatestatus)); //未审核
        fragments.add(FcAuditedFragment.getInstance(classId, typeDate));//已审核
        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(), fragments, tabtitle));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Auditnum", uncheck_num);
                setResult(RESULT_OK, intent);
                BleManager.getInstance().disconnectBluetooth();
                finish();
            }
        });


        setPresenter(new FuceCheckListPresenter(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATENUMBER_FUCUCHECK));

    }

    @Override
    protected void initDatas() {
        getPresenter().getMeasureReviewedList(classId, typeDate, 1, 10);

    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }



    // 审核通过更新数据
    public void update() {
        getPresenter().getMeasureReviewedList(classId, typeDate, 1, 10);
    }

    //刷新列表时调用刷新title数字
    public void updates(int Auditnu) {
        uncheck_num = Auditnu;
        tabtitle[0] = "未复测(" + unFuce_num + ")";
        tabtitle[1] = "待审核(" + uncheck_num + ")";
        tabtitle[2] = "已审核(" + (++checked_num) + ")";
        TabLayout.Tab tab1 = tab.getTabAt(0);
        tab1.setText(tabtitle[0]);
        TabLayout.Tab tab2 = tab.getTabAt(1);
        tab2.setText(tabtitle[1]);
        TabLayout.Tab tab3 = tab.getTabAt(2);
        tab3.setText(tabtitle[2]);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("Auditnum", uncheck_num);
            setResult(RESULT_OK, intent);
            BleManager.getInstance().disconnectBluetooth();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getMeasureReviewedList(List<AuditListModel> list) {
        if (list != null) {
            if (list.size() == 0) {
                tabtitle[0] = "未复测(" + "0" + ")";
                tabtitle[1] = "待审核(" + "0" + ")";
                tabtitle[2] = "已审核(" + "0" + ")";
                TabLayout.Tab tab1 = tab.getTabAt(0);
                tab1.setText(tabtitle[0]);
                TabLayout.Tab tab2 = tab.getTabAt(1);
                tab2.setText(tabtitle[1]);
                TabLayout.Tab tab3 = tab.getTabAt(2);
                tab3.setText(tabtitle[2]);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    AuditListModel model = list.get(i);
                    if (0 == model.getStatus()) {
                        uncheck_num = Integer.parseInt(model.getCount());
                    } else if (1 == model.getStatus()) {
                        checked_num = Integer.parseInt(model.getCount());
                    } else if (-1 == model.getStatus()) {
                        unFuce_num = Integer.parseInt(model.getCount());
                    }
                }

                tabtitle[0] = "未复测(" + unFuce_num + ")";
                tabtitle[1] = "待审核(" + uncheck_num + ")";
                tabtitle[2] = "已审核(" + checked_num + ")";
                TabLayout.Tab tab1 = tab.getTabAt(0);
                tab1.setText(tabtitle[0]);
                TabLayout.Tab tab2 = tab.getTabAt(1);
                tab2.setText(tabtitle[1]);
                TabLayout.Tab tab3 = tab.getTabAt(2);
                tab3.setText(tabtitle[2]);
            }
        }
    }

    @Override
    public void hidenLoading() {

    }

    public static final String UPDATENUMBER_FUCUCHECK = "UPDATENUMBER_FUCUCHECK";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && UPDATENUMBER_FUCUCHECK.equalsIgnoreCase(intent.getAction())) {
                unFuce_num = intent.getIntExtra("unFuce_num", 0);
                uncheck_num = intent.getIntExtra("uncheck_num", 0);
                checked_num = intent.getIntExtra("checked_num", 0);

                tabtitle[0] = "未复测(" + unFuce_num + ")";
                tabtitle[1] = "待审核(" + uncheck_num + ")";
                tabtitle[2] = "已审核(" + checked_num + ")";

                TabLayout.Tab tab1 = tab.getTabAt(0);
                tab1.setText(tabtitle[0]);
                TabLayout.Tab tab2 = tab.getTabAt(1);
                tab2.setText(tabtitle[1]);
                TabLayout.Tab tab3 = tab.getTabAt(2);
                tab3.setText(tabtitle[2]);
            }
        }
    };
}
