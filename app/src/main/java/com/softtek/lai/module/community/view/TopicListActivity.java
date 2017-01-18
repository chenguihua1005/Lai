package com.softtek.lai.module.community.view;

import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.community.model.TopicListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_topic_list)
public class TopicListActivity extends BaseActivity {

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private EasyAdapter<TopicListModel> adapter;
    private List<TopicListModel> datas=new ArrayList<>();
    @Override
    protected void initViews() {
        tv_title.setText("话题");
    }

    @Override
    protected void initDatas() {
        adapter=new EasyAdapter<TopicListModel>(this,datas,R.layout.item_topic_list) {
            @Override
            public void convert(ViewHolder holder, TopicListModel data, int position) {

            }
        };
        ptrlv.setAdapter(adapter);

    }
    @OnClick(R.id.ll_left)
    public void backClick(){
        finish();
    }

}
