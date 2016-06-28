package com.softtek.lai.module.pastreview.view;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.pastreview.adapter.StoryAdapter;
import com.softtek.lai.module.pastreview.model.StoryList;
import com.softtek.lai.module.pastreview.model.StoryModel;
import com.softtek.lai.module.pastreview.presenter.PastReviewManager;
import com.softtek.lai.module.studetail.view.LogDetailActivity;
import com.softtek.lai.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * 学员往期减重故事
 */
@InjectLayout(R.layout.activity_story)
public class StoryActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView>
        ,AdapterView.OnItemClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private List<StoryModel> logs=new ArrayList<>();
    private PastReviewManager manager;
    private StoryAdapter adapter;
    private int pageIndex;
    private int totalPage;
    private long userId;
    private long classId;
    @Override
    protected void initViews() {
        tv_title.setText("我的减重故事");
        ll_left.setOnClickListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlv.setOnItemClickListener(this);
        ptrlv.setOnRefreshListener(this);
        userId= StringUtil.getLong(UserInfoModel.getInstance().getUser().getUserid());
        classId=getIntent().getLongExtra("classId",0);
    }

    @Override
    protected void initDatas() {
        manager=new PastReviewManager();
        adapter=new StoryAdapter(this,logs);
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
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        manager.getStoryList(this,userId,classId,pageIndex);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if(pageIndex<=totalPage){
            pageIndex++;
            manager.getStoryList(this,userId,classId,pageIndex);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrlv.onRefreshComplete();
                }
            },200);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,LogDetailActivity.class);
        intent.putExtra("logId",logs.get(position-1).getLossLogId());
        startActivity(intent);
    }

    public void getLogList(StoryList logs) {
        ptrlv.onRefreshComplete();
        if(logs==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        totalPage=Integer.parseInt(logs.getPageCount());
        List<StoryModel> models=logs.getLogList();
        if(models==null||models.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
            this.logs.clear();
        }
        this.logs.addAll(models);
        if(adapter!=null)
            adapter.notifyDataSetChanged();

    }

}
