package com.softtek.lai.module.bodygame2.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.adapter.DyAdapter;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.widgets.NoSlidingViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_dy)
public class DYActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2, GradeImpl.GradeCalllback {
    @InjectView(R.id.lv_dynamic)
    PullToRefreshListView lv_dynamic;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    private int pageIndex;
    private IGrade grade;
    private DyAdapter adapter;
    List<DynamicInfoModel> dynamicInfos = new ArrayList<>();
    String classId;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        lv_dynamic.setOnRefreshListener(this);
        lv_dynamic.setMode(PullToRefreshBase.Mode.BOTH);
    }


    @Override
    protected void initDatas() {
        grade = new GradeImpl(this,"1");
        adapter = new DyAdapter(this, dynamicInfos);
        lv_dynamic.setAdapter(adapter);
        classId = getIntent().getStringExtra("classId");
        grade.getClassDynamic(Long.parseLong(classId), 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex = 1;
        grade.getClassDynamic(Long.parseLong(classId),0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        grade.getClassDynamic(Long.parseLong(classId), pageIndex);
    }

    @Override
    public void getDynamicCallback(List<DynamicInfoModel> dynamicInfoModels) {
        lv_dynamic.onRefreshComplete();
        try {
            if (dynamicInfoModels == null) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (dynamicInfoModels.isEmpty()) {
                pageIndex = --pageIndex < 1 ? 1 : pageIndex;
                return;
            }
            if (pageIndex == 1) {
                dynamicInfos.clear();
            }
            dynamicInfos.addAll(dynamicInfoModels);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
