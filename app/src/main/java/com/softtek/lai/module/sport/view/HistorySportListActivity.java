package com.softtek.lai.module.sport.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.sport.adapter.HistorySportAdapter;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.presenter.SportManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_sport_list)
public class HistorySportListActivity extends BaseActivity implements View.OnClickListener, SportManager.GetMovementListCallBack , PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.sport_list)
    PullToRefreshListView sport_list;

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    SportManager sportManager;

    HistorySportAdapter adapter;
    private List<HistorySportModel> list = new ArrayList<>();
    int pageIndex = 1;
    int totalPage = 1;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        sport_list.setMode(PullToRefreshBase.Mode.BOTH);
        sport_list.setOnRefreshListener(this);
        sport_list.setEmptyView(img_mo_message);
        ILoadingLayout startLabelse = sport_list.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = sport_list.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
        adapter = new HistorySportAdapter(this, this.list);
        sport_list.setAdapter(adapter);
        sport_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(HistorySportListActivity.this,HistorySportActivity.class);
                intent.putExtra("history",list.get(position-1));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initDatas() {
        tv_title.setText("我的运动");
        sportManager=new SportManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sport_list != null)
                    sport_list.setRefreshing();
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
    public void getMovementList(String type,ResponseData<List<HistorySportModel>> listResponseData) {
        try {
            if ("true".equals(type)) {
                if (sport_list != null) {
                    sport_list.onRefreshComplete();
                }
                String pages = listResponseData.getPageCount()+"";
                if (!"".equals(pages)) {
                    totalPage = Integer.parseInt(pages);
                } else {
                    totalPage = 1;
                }

                if (pageIndex == 1) {
                    list.clear();
                }
                list.addAll(listResponseData.getData());
                adapter.notifyDataSetChanged();
            } else {
                if (sport_list != null) {
                    sport_list.onRefreshComplete();
                }
                if (pageIndex == 1) {
                    pageIndex = 1;
                } else {
                    pageIndex--;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = 1;
        sportManager.getMovementList(pageIndex + "");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        if (pageIndex <= totalPage) {
            sportManager.getMovementList(pageIndex + "");
        } else {
            pageIndex--;
            if (sport_list != null) {
                System.out.println("pageIndex:" + pageIndex);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sport_list.onRefreshComplete();
                    }
                }, 200);
            }
        }
    }
}
