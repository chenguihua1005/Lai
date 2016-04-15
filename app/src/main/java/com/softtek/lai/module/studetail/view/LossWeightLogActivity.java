package com.softtek.lai.module.studetail.view;

import android.content.Intent;
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
import com.softtek.lai.module.studetail.adapter.LossWeightLogAdapter;
import com.softtek.lai.module.studetail.eventModel.LogEvent;
import com.softtek.lai.module.studetail.model.LogList;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.presenter.IMemberInfopresenter;
import com.softtek.lai.module.studetail.presenter.MemberInfoImpl;
import com.softtek.lai.widgets.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_loss_weight_log)
public class LossWeightLogActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
            ,PullToRefreshBase.OnRefreshListener2<ListView>,MemberInfoImpl.MemberInfoImplCallback{

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
    private long accountId=0;
    private int review_flag=0;
    private int pageIndex=1;

    @Override
    protected void initViews() {
        //EventBus.getDefault().register(this);
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
        accountId=getIntent().getLongExtra("accountId",0);
        review_flag=getIntent().getIntExtra("review",0);
        memberInfopresenter=new MemberInfoImpl(this,this);
        adapter=new LossWeightLogAdapter(this, logs,review_flag);
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        },200);
    }

 /*   @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }*/



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
        if(position<2){
            return;
        }

        Intent intent=new Intent(this,LogDetailActivity.class);
        intent.putExtra("log",logs.get(position-2));
        intent.putExtra("review",review_flag);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        memberInfopresenter.getLossWeigthLogList(accountId,1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        memberInfopresenter.getLossWeigthLogList(accountId,pageIndex);
    }

    @Override
    public void getLogList(LogList logs) {
        ptrlv.onRefreshComplete();
        if(logs==null){

        }
    }
}
