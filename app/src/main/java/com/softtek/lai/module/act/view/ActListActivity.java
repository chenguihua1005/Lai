package com.softtek.lai.module.act.view;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.adapter.ActFragmentAdapter;
import com.softtek.lai.module.act.adapter.GroupListItemAdapter;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.act.model.ActlistModel;
import com.softtek.lai.module.act.presenter.ActManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_act_list)
public class ActListActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, ActManager.GetactivityListCallBack, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.act_list)
    PullToRefreshListView act_list;

    ActManager actManager;
    int pageIndex = 1;
    String userId;
    int totalPage=1;

    GroupListItemAdapter adapter;
    private List<ActlistModel> list = new ArrayList<ActlistModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        userId = UserInfoModel.getInstance().getUser().getUserid();
        tv_title.setText("活动列表");
        adapter = new GroupListItemAdapter(this, list);
        act_list.setAdapter(adapter);

        actManager = new ActManager(this);
        dialogShow("加载中");
        actManager.activityList(pageIndex + "", userId);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void activityList(String type, ActivityModel model) {
        if ("true".equals(type)) {
            if (act_list != null) {
                act_list.onRefreshComplete();
            }
            dialogDissmiss();
            String pages = model.getPageCount();
            if (!"".equals(pages)) {
                totalPage = Integer.parseInt(pages);
            } else {
                totalPage = 1;
            }

            if (pageIndex == 1) {
                list.clear();
            }
            list.addAll(model.getActlist());
            adapter.notifyDataSetChanged();
        } else {
            if (pageIndex == 1) {
                pageIndex = 1;
            } else {
                pageIndex--;
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        actManager.activityList(pageIndex + "", userId);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            actManager.activityList(pageIndex + "", userId);
        } else {
            pageIndex--;
            if (act_list != null) {
                act_list.onRefreshComplete();
            }
        }
    }
}
