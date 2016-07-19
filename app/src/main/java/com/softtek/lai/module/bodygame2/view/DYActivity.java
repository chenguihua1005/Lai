package com.softtek.lai.module.bodygame2.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame2.adapter.DyAdapter;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_dy)
public class DYActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2, GradeImpl.GradeCalllback {
    @InjectView(R.id.lv_dynamic)
    PullToRefreshListView lv_dynamic;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    private int pageIndex;
    private IGrade grade;
    private DyAdapter adapter;
    List<DynamicInfoModel> dynamicInfos = new ArrayList<>();
    String classId;

    @Override
    protected void initViews() {
        tv_title.setText("班级动态");
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
        try {
            lv_dynamic.onRefreshComplete();
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
