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
    private ViewPager viewPager;

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
        viewPager = (ViewPager) headerView.findViewById(R.id.viewpager);
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
        recyclerView.setPullLoadMoreCompleted();
        if(upOrLoad==0){
            if(subjectModel.getRecommendTopicList()!=null&&!subjectModel.getRecommendTopicList().isEmpty()){
                recommendModels.clear();
                viewPager.removeAllViews();
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                adapterData();
            }
            if (!subjectModel.getArticleTopicList().isEmpty()) {
                articleTopicModels.clear();
                articleTopicModels.addAll(subjectModel.getArticleTopicList());
            }
        }else {
            if (!subjectModel.getArticleTopicList().isEmpty()) {
                articleTopicModels.addAll(subjectModel.getArticleTopicList());
            }
        }
        mAdapter.notifyDataSetChanged();
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

    private PagerAdapter pageradapter;

    private void adapterData() {

        pageradapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return recommendModels.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.list_subjectremend_item, container, false);
                TextView tv_clickhot = (TextView) view.findViewById(R.id.tv_clickhot);
                tv_clickhot.setText(String.valueOf(recommendModels.get(position).getClicks()));
                ImageView im_photo = (ImageView) view.findViewById(R.id.im_photo);
                Picasso.with(getContext()).load(AddressManager.get("photoHost") + recommendModels.get(position).getTopicImg()).fit().error(R.drawable.default_icon_square)
                        .placeholder(R.drawable.default_icon_square).into(im_photo);
                RelativeLayout re_remend = (RelativeLayout) view.findViewById(R.id.re_remend);
                re_remend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), SubjectdetailActivity.class);
                        intent.putExtra("topictitle", recommendModels.get(position).getTopicName());
                        intent.putExtra("topicId", recommendModels.get(position).getTopicId());
                        startActivity(intent);
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pageradapter);
    }
}
