package com.softtek.lai.module.laiClassroom;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laiClassroom.adapter.CollectAdapter;
import com.softtek.lai.module.laiClassroom.adapter.SubjectDetailAdapter;
import com.softtek.lai.module.laiClassroom.model.CollectModel;
import com.softtek.lai.module.laiClassroom.model.CollectlistModel;
import com.softtek.lai.module.laiClassroom.presenter.SubjectDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.http.Query;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_subjectdetail)
public class SubjectdetailActivity extends BaseActivity<SubjectDetailPresenter> implements SubjectDetailPresenter.getSubjectdetail, PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.plv_subject)
    PullToRefreshListView plv_subject;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    private String topicId;
    private int pageindex = 1;
    private List<CollectlistModel> collectlistModels = new ArrayList<>();
    private SubjectDetailAdapter adapter;

    @Override
    protected void initViews() {

        plv_subject.setMode(PullToRefreshBase.Mode.BOTH);
        plv_subject.setOnRefreshListener(this);
        plv_subject.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_subject.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_subject.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        tv_title.setText(getIntent().getStringExtra("topictitle"));
        topicId = getIntent().getStringExtra("topicId");
        setPresenter(new SubjectDetailPresenter(this));
        adapter = new SubjectDetailAdapter(this, collectlistModels);
        plv_subject.setAdapter(adapter);
        dialogShow("正在加载");
        getPresenter().UpdateSubjectData(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), topicId, pageindex, 10,0);
    }

    @Override
    public void getSubjectData(CollectModel collectModel,int from) {
        plv_subject.onRefreshComplete();
        if (collectModel != null) {
            im_nomessage.setVisibility(View.GONE);
            if (!collectModel.getArticleList().isEmpty()) {
                if(from==0){
                    collectlistModels.clear();
                }
                collectlistModels.addAll(collectModel.getArticleList());
                adapter.notifyDataSetChanged();
            } else {
                if (pageindex == 1) {
                    im_nomessage.setVisibility(View.VISIBLE);
                } else {
                    im_nomessage.setVisibility(View.GONE);
                    pageindex--;
                }
            }
        } else {
            im_nomessage.setVisibility(View.VISIBLE);
            pageindex--;
        }
    }

    @Override
    public void dissmiss() {
        plv_subject.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex = 1;
        getPresenter().UpdateSubjectData(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), topicId, pageindex, 10,0);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageindex++;
        getPresenter().UpdateSubjectData(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), topicId, pageindex, 10,1);

    }
}
