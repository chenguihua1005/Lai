package com.softtek.lai.module.bodygame3.activity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.adapter.RetestTabAdapter;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.activity.presenter.InitAuditPresenter;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by Terry on 2016/11/29.
 */
@InjectLayout(R.layout.activity_honorranking)
public class InitAuditListActivity extends BaseActivity<InitAuditPresenter> implements InitAuditPresenter.InitAuditView {

    @InjectView(R.id.tab)
    TabLayout tab;

    @InjectView(R.id.content)
    ViewPager content;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    List<Fragment> fragments;
    FuceSevice fuceSevice;
    String[] tabtitle = {"未录入", "未审核", "已审核"};

    //    String[] tabtitle = {"未审核", "已审核"};
    String classId;
    int unInputNum = 0;//未录入
    int Auditnum = 0;
    int Auditednum = 0;

    private String typeDate;

    @Override
    protected void initViews() {
        tv_title.setText("初始数据审核");
        classId = getIntent().getStringExtra("classId");
        typeDate = getIntent().getStringExtra("typeDate");

        fragments = new ArrayList<>();
        fragments.add(UnInputFragment.getInstance(classId, typeDate)); //未录入
        fragments.add(InitAuditFragment.getInstance(classId, typeDate));//未审核
        fragments.add(InitAuditedFragment.getInstance(classId, typeDate));//已审核
        content.setAdapter(new RetestTabAdapter(getSupportFragmentManager(), fragments, tabtitle));
        tab.setupWithViewPager(content);
        tab.setTabMode(TabLayout.MODE_FIXED);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Auditnum", Auditnum);
                setResult(RESULT_OK, intent);
                LocalBroadcastManager.getInstance(InitAuditListActivity.this).sendBroadcast(new Intent(FuceForStuActivity.EVENT_TAG));
                finish();
            }
        });


        setPresenter(new InitAuditPresenter(this));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATENUMBER_CHUSHICHECK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDatas() {
        fuceSevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);

//        doGetData(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()), classId, 1, 100);

        getPresenter().getInitAuditList(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()), classId, 1, 100);
    }


    public void update() {
//        tabtitle[0] = "未录入(" + unInputNum + ")";
//        tabtitle[1] = "待审核(" + (--Auditnum) + ")";
//        tabtitle[2] = "已审核(" + (++Auditednum) + ")";
//        TabLayout.Tab tab1 = tab.getTabAt(0);
//        tab1.setText(tabtitle[0]);
//        TabLayout.Tab tab2 = tab.getTabAt(1);
//        tab2.setText(tabtitle[1]);
//        TabLayout.Tab tab3 = tab.getTabAt(2);
//        tab3.setText(tabtitle[2]);

        getPresenter().getInitAuditList(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()), classId, 1, 100);

    }

//    public void updates(int Auditnu) {
//        Auditnum = Auditnu;
//        tabtitle[0] = "未录入(" + unInputNum + ")";
//        tabtitle[1] = "待审核(" + Auditnum + ")";
//        tabtitle[2] = "已审核(" + Auditednum + ")";
//        TabLayout.Tab tab1 = tab.getTabAt(0);
//        tab1.setText(tabtitle[0]);
//        TabLayout.Tab tab2 = tab.getTabAt(1);
//        tab2.setText(tabtitle[1]);
//        TabLayout.Tab tab3 = tab.getTabAt(2);
//        tab3.setText(tabtitle[2]);
//    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent = new Intent();
            intent.putExtra("Auditnum", Auditnum);
            setResult(RESULT_OK, intent);
            LocalBroadcastManager.getInstance(InitAuditListActivity.this).sendBroadcast(new Intent(FuceForStuActivity.EVENT_TAG));
//            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void getInitAuditList(List<AuditListModel> list) {
        if (list != null) {
            if (list.size() == 0) {
                tabtitle[0] = "未录入(" + "0" + ")";
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
                        Auditnum = Integer.parseInt(model.getCount());
                    } else if (1 == model.getStatus()) {
                        Auditednum = Integer.parseInt(model.getCount());
                    } else if (-1 == model.getStatus()) {
                        unInputNum = Integer.parseInt(model.getCount());
                    }
                }

                tabtitle[0] = "未录入(" + unInputNum + ")";
                tabtitle[1] = "待审核(" + Auditnum + ")";
                tabtitle[2] = "已审核(" + Auditednum + ")";
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

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    public static final String UPDATENUMBER_CHUSHICHECK = "UPDATENUMBER_CHUSHICHECK";
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && UPDATENUMBER_CHUSHICHECK.equalsIgnoreCase(intent.getAction())) {
                unInputNum = intent.getIntExtra("unFuce_num", 0);
                Auditnum = intent.getIntExtra("uncheck_num", 0);
                Auditednum = intent.getIntExtra("checked_num", 0);

                tabtitle[0] = "未录入(" + unInputNum + ")";
                tabtitle[1] = "待审核(" + Auditnum + ")";
                tabtitle[2] = "已审核(" + Auditednum + ")";

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
