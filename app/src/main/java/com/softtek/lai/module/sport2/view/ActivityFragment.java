package com.softtek.lai.module.sport2.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.adapter.GroupListItemAdapter;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.module.act.model.ActlistModel;
import com.softtek.lai.module.act.presenter.ActManager;
import com.softtek.lai.module.act.view.ActActivity;
import com.softtek.lai.module.home.view.HomeActviity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_activity)
public class ActivityFragment extends LazyBaseFragment implements View.OnClickListener, ActManager.GetactivityListCallBack, PullToRefreshBase.OnRefreshListener2<ListView>{


    public ActivityFragment() {
        // Required empty public constructor
    }


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
    private List<ActlistModel> list = new ArrayList<>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        act_list.setMode(PullToRefreshBase.Mode.BOTH);
        act_list.setOnRefreshListener(this);
        act_list.setEmptyView(img_mo_message);
        ILoadingLayout startLabelse = act_list.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = act_list.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
        act_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActlistModel actlistModel = list.get(position - 1);
                Intent intent = new Intent(getContext(), ActActivity.class);
                intent.putExtra("id", actlistModel.getActId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
        tv_title.setText("活动");
        userId = UserInfoModel.getInstance().getUser().getUserid();
        adapter = new GroupListItemAdapter(getContext(), list);
        act_list.setAdapter(adapter);
        actManager = new ActManager(this);

    }

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
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
                getActivity().startActivity(new Intent(getActivity(), HomeActviity.class));
                break;
        }

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
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        act_list.onRefreshComplete();
                    }
                }, 200);
            }
        }
    }
}
