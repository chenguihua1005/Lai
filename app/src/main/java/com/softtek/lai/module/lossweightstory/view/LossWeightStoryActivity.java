package com.softtek.lai.module.lossweightstory.view;

import android.os.Handler;
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
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.lossweightstory.adapter.LossWeightStoryAdapter;
import com.softtek.lai.module.lossweightstory.model.LossWeightStoryModel;
import com.softtek.lai.module.lossweightstory.presenter.LossWeightStoryManager;
import com.softtek.lai.widgets.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_loss_weight_story)
public class LossWeightStoryActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
,PullToRefreshBase.OnRefreshListener2<ListView>,LossWeightStoryManager.StoryManagerCallBack{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    //列表内容
    private ImageView log_banner;
    private TextView tv_name;
    private CircleImageView cir_header_image;

    private LossWeightStoryManager lossWeightStoryManager;
    private List<LossWeightStoryModel> lossWeightStoryModels=new ArrayList<>();
    private LossWeightStoryAdapter adapter;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("减重故事");
        tv_right.setText("新故事");
        tv_right.setOnClickListener(this);
        View view=getLayoutInflater().inflate(R.layout.loss_weight_log_header,null,false);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        cir_header_image= (CircleImageView) view.findViewById(R.id.civ_header_image);
        log_banner= (ImageView) view.findViewById(R.id.log_header_image);
        ptrlv.getRefreshableView().addHeaderView(view);
        //ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
    }

    @Override
    protected void initDatas() {
        lossWeightStoryManager=new LossWeightStoryManager(this);
        adapter=new LossWeightStoryAdapter(this,lossWeightStoryModels);
        ptrlv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrlv.setRefreshing();
            }
        },200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                //跳转新故事
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        String userId=UserInfoModel.getInstance().getUser().getUserid();
        lossWeightStoryManager.getLossWeightLogForClass(13/*Long.parseLong(userId)*/);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void getStroyList(List<LossWeightStoryModel> models) {
        ptrlv.onRefreshComplete();
        if(models==null){
            return;
        }
        Log.i(models.toString());
        if(!models.isEmpty()){
            lossWeightStoryModels.clear();
            lossWeightStoryModels.addAll(models);
            adapter.notifyDataSetChanged();
        }
    }
}
