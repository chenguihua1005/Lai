package com.softtek.lai.module.studetail.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.studetail.adapter.LossWeightLogAdapter;
import com.softtek.lai.module.studetail.eventModel.LogEvent;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_loss_weight_log)
public class LossWeightLogActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
            ,PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    //列表内容
    private ImageView log_banner;
    private TextView tv_name;
    private CircleImageView cir_header_image;

    private IMemberInfopresenter memberInfopresenter;
    private List<LossWeightLogModel> logs=new ArrayList<>();
    private LossWeightLogAdapter adapter;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        tv_title.setText("减重日志");
        ll_left.setOnClickListener(this);
        View view=getLayoutInflater().inflate(R.layout.loss_weight_log_header,null,false);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        cir_header_image= (CircleImageView) view.findViewById(R.id.civ_header_image);
        log_banner= (ImageView) view.findViewById(R.id.log_header_image);
        ptrlv.getRefreshableView().addHeaderView(view);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);

    }

    @Override
    protected void initDatas() {
        memberInfopresenter=new MemberInfoImpl(this);
        LogEvent logEvent=memberInfopresenter.loadLogListCache();
        if(logEvent!=null){
            if(!logEvent.getLossWeightLogModels().isEmpty()){
                LossWeightLogModel model=logEvent.getLossWeightLogModels().get(0);
                Picasso.with(this).load(model.getAcBanner()).placeholder(R.drawable.default_pic)
                        .error(R.drawable.default_pic).into(log_banner);
                
            }
            logs.addAll(logEvent.getLossWeightLogModels());
        }
        adapter=new LossWeightLogAdapter(this, logs);
        ptrlv.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadCompleted(LogEvent event){
        ptrlv.onRefreshComplete();
        if(event.getLossWeightLogModels()==null){
            return;
        }
        if(event.flag== Constants.REFRESH){
            logs.clear();
        }
        logs.addAll(event.getLossWeightLogModels());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("点击了一个item="+position);
        if(position<2){
            return;
        }
        Log.i("日志集合》》》"+logs.size());
        Intent intent=new Intent(this,LogDetailActivity.class);
        intent.putExtra("log",logs.get(position-2));
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        memberInfopresenter.getLossWeigthLogList(Constants.REFRESH,3);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        memberInfopresenter.getLossWeigthLogList(Constants.LOADING, 3);
    }
}
