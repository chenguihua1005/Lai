package com.softtek.lai.module.community.view;

import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.community.model.TopicListModel;
import com.softtek.lai.widgets.SquareImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_topic_list)
public class TopicListActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private EasyAdapter<TopicListModel> adapter;
    private List<TopicListModel> datas;
    @Override
    protected void initViews() {
        tv_title.setText("话题");
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
    }

    @Override
    protected void initDatas() {
        datas=new ArrayList<>();
        adapter=new EasyAdapter<TopicListModel>(this,datas,R.layout.item_topic_list) {
            @Override
            public void convert(ViewHolder holder, TopicListModel data, int position) {
                TextView tv_topic=holder.getView(R.id.tv_topic);
                TextView tv_content=holder.getView(R.id.tv_content);
                SquareImageView siv_topic=holder.getView(R.id.siv_topic);
            }
        };
        ptrlv.setAdapter(adapter);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        }, 300);
    }
    @OnClick(R.id.ll_left)
    public void backClick(){
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
