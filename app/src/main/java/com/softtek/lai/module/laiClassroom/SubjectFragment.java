package com.softtek.lai.module.laiClassroom;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.adapter.HeaderFooterReAdapter;
import com.softtek.lai.module.laiClassroom.model.ArticleTopicModel;
import com.softtek.lai.module.laiClassroom.model.RecommendModel;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.presenter.SubjectPresenter;
import com.softtek.lai.widgets.RollHeaderViewT;
import com.squareup.picasso.Picasso;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/16/2017.
 */
@InjectLayout(R.layout.fragment_subjectnew)
public class SubjectFragment extends LazyBaseFragment<SubjectPresenter> implements SubjectPresenter.getSubject,
        PullLoadMoreRecyclerView.PullLoadMoreListener{


    @InjectView(R.id.recyclerView)
    PullLoadMoreRecyclerView recyclerView;
    HeaderFooterReAdapter mAdapter;

    private View headerView;
    private RollHeaderViewT rhv_adv;

    List<RecommendModel> recommendModels = new ArrayList<>();
    List<ArticleTopicModel> articleTopicModels = new ArrayList<>();

    int pageindex = 1;

    @Override
    protected void lazyLoad() {
        recyclerView.post(new Runnable(){
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                onRefresh();//执行数据加载操作
            }
        });
    }

    @Override
    protected void initViews() {
        GridLayoutManager glm=new GridLayoutManager(getContext(),2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position==0?2:1;
            }
        });
        recyclerView.getRecyclerView().setLayoutManager(glm);
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.view_header_layout, (ViewGroup) recyclerView.getParent(), false);
        rhv_adv= (RollHeaderViewT) headerView.findViewById(R.id.rhv_adv);
        mAdapter = new HeaderFooterReAdapter(headerView,getContext(), articleTopicModels);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnPullLoadMoreListener(this);
    }

    @Override
    protected void initDatas() {
        setPresenter(new SubjectPresenter(this));
    }

    @Override
    public void getSubjectart(SubjectModel subjectModel,int upOrLoad) {

        if(upOrLoad==0){
            if(subjectModel.getRecommendTopicList()!=null&&!subjectModel.getRecommendTopicList().isEmpty()){
                recommendModels.clear();
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                rhv_adv.setImgUrlData(recommendModels);
            }
            if (!subjectModel.getArticleTopicList().isEmpty()) {
                articleTopicModels.clear();
                articleTopicModels.addAll(subjectModel.getArticleTopicList());
                mAdapter.notifyDataSetChanged();
            }
        }else {
            if (!subjectModel.getArticleTopicList().isEmpty()) {
                articleTopicModels.addAll(subjectModel.getArticleTopicList());
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onRefreshCompleted() {
//        recyclerView.setRefreshing(false);
        recyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMoreCompleted() {
        recyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onRefresh() {
        pageindex = 1;
        getPresenter().getSubjectData(pageindex, 10,0);
    }

    @Override
    public void onLoadMore() {
        pageindex++;
        getPresenter().getSubjectData(pageindex, 10,1);
    }


}
