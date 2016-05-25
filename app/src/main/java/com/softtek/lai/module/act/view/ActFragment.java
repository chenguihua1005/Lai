package com.softtek.lai.module.act.view;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.act.adapter.ActZKAdapter;
import com.softtek.lai.module.act.adapter.GroupListItemAdapter;
import com.softtek.lai.module.act.model.ActDetiallistModel;
import com.softtek.lai.module.act.model.ActZKModel;
import com.softtek.lai.module.act.model.ActlistModel;
import com.softtek.lai.module.act.presenter.ActManager;
import com.softtek.lai.module.tips.adapter.AskHealthyAdapter;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.presenter.AskHealthyManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis on 4/27/2016.
 */
@InjectLayout(R.layout.fragment_act)
public class ActFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView> ,ActManager.GetActivitySituationCallBack {
    @InjectView(R.id.zk_list)
    PullToRefreshListView zk_list;
    ActManager actManager;
    int pageIndex = 1;
    int totalPage = 1;
    String userId;
    String id;
    ActZKAdapter adapter;
    private List<ActDetiallistModel> list = new ArrayList<ActDetiallistModel>();

    @Override
    protected void initViews() {
        zk_list.setMode(PullToRefreshBase.Mode.BOTH);
        zk_list.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        actManager = new ActManager(this);
        userId = UserInfoModel.getInstance().getUser().getUserid();
        id = getActivity().getIntent().getStringExtra("id");
        actManager.getActivitySituation(pageIndex+"",userId,id);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (zk_list != null)
                    zk_list.setRefreshing();
            }
        }, 500);
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
            if (zk_list != null) {
                System.out.println("pageIndex:" + pageIndex);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zk_list.onRefreshComplete();
                    }
                }, 200);
            }
        }
    }

    @Override
    public void getActivitySituation(String type, ActZKModel model) {
        if ("true".equals(type)) {
            if (zk_list != null) {
                zk_list.onRefreshComplete();
            }
            String pages = model.getPageCount();
            if (!"".equals(pages)) {
                totalPage = Integer.parseInt(pages);
            } else {
                totalPage = 1;
            }

            if (pageIndex == 1) {
                adapter = new ActZKAdapter(getContext(), list,model.getActType());
                zk_list.setAdapter(adapter);
            }else {
                list.addAll(model.getActDetiallist());
                adapter.notifyDataSetChanged();
            }
        } else {
            if (zk_list != null) {
                zk_list.onRefreshComplete();
            }
            if (pageIndex == 1) {
                pageIndex = 1;
            } else {
                pageIndex--;
            }
        }
    }
}
