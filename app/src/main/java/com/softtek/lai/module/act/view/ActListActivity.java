package com.softtek.lai.module.act.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
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

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    @InjectView(R.id.act_list)
    PullToRefreshListView act_list;

    ActManager actManager;
    String userId;
    int pageIndex = 1;
    int totalPage = 1;

    GroupListItemAdapter adapter;
    private List<ActlistModel> list = new ArrayList<ActlistModel>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        act_list.setMode(PullToRefreshBase.Mode.BOTH);
        act_list.setOnRefreshListener(this);
        act_list.setEmptyView(img_mo_message);
        act_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActlistModel actlistModel = list.get(position - 1);
                Intent intent = new Intent(ActListActivity.this, ActActivity.class);
                intent.putExtra("id", actlistModel.getActId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
        tv_title.setText("活动列表");
        userId = UserInfoModel.getInstance().getUser().getUserid();
        adapter = new GroupListItemAdapter(this, list);
        act_list.setAdapter(adapter);

        actManager = new ActManager(this);
        actManager.activityList(pageIndex + "", userId);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (act_list != null)
                    act_list.setRefreshing();
            }
        }, 500);
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
        try {
            if ("true".equals(type)) {
                if (act_list != null) {
                    act_list.onRefreshComplete();
                }
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
                if (act_list != null) {
                    act_list.onRefreshComplete();
                }
                if (pageIndex == 1) {
                    pageIndex = 1;
                } else {
                    pageIndex--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        act_list.onRefreshComplete();
                    }
                }, 200);
            }
        }
    }
}
