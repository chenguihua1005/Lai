package com.softtek.lai.module.laiClassroom;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.adapter.FilterAdapter;
import com.softtek.lai.module.laiClassroom.adapter.WholeAdapter;
import com.softtek.lai.module.laiClassroom.model.Artical;
import com.softtek.lai.module.laiClassroom.model.ArticalList;
import com.softtek.lai.module.laiClassroom.model.FilteData;
import com.softtek.lai.module.laiClassroom.presenter.WholePresenter;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

//全部页面
@InjectLayout(R.layout.fragment_whole)
public class WholeFragment extends LazyBaseFragment<WholePresenter> implements WholePresenter.WholeView ,PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.rv_sort)
    RecyclerView rv_sort;
    @InjectView(R.id.rv_type)
    RecyclerView rv_type;
    @InjectView(R.id.rv_subject)
    RecyclerView rv_subject;

    @InjectView(R.id.ll_panel)
    LinearLayout ll_panel;
    @InjectView(R.id.tv_arrow)
    TextView tv_arrow;

    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private WholeAdapter adapter;
    private List<Artical> articals;
    private int pageIndex=1;


    public WholeFragment() {

    }


    @Override
    protected void lazyLoad() {
        getPresenter().getFilterData();
        getPresenter().getArticleList("0","0","1",1,0);
    }

    @Override
    protected void initViews() {
        ll_panel.setY(DisplayUtil.dip2px(getContext(),-150f));
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
//        ptrlv.setEmptyView(empty);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrlv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        setPresenter(new WholePresenter(this));
        rv_sort.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_type.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_subject.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        articals=new ArrayList<>();
        adapter=new WholeAdapter(getContext(),articals);
        ptrlv.setAdapter(adapter);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(tv_arrow.getCompoundDrawables()[2], "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv_arrow.setText(!isExpand?"完成":"筛选");
            }
        });
        animator.start();
    }

    boolean doAnimat;
    boolean isExpand;
    @OnClick(R.id.rl_expand)
    public void doExpand(){
        if(doAnimat){
           return;
        }
        if(!isExpand){
            //展开
            ll_panel.animate()
                    .setInterpolator(new DecelerateInterpolator(2))
                    .setDuration(500)
                    .yBy(DisplayUtil.dip2px(getContext(),150f))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isExpand=true;
                            doAnimat=false;
                            ll_panel.setTranslationY(0);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            doAnimat=true;
                        }
                    })
                    .start();
            animateArrow(!isExpand);

        }else {
            //收起
            ll_panel.animate()
                    .setInterpolator(new DecelerateInterpolator(2))
                    .setDuration(500)
                    .yBy(DisplayUtil.dip2px(getContext(),-150f))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isExpand=false;
                            doAnimat=false;
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            doAnimat=true;
                        }
                    })
                    .start();
            animateArrow(!isExpand);
        }
    }

    @Override
    public void getData(FilteData data) {
        rv_sort.setAdapter(new FilterAdapter(getContext(),data.getOrderByList(),FilterAdapter.SINGLE));
        rv_type.setAdapter(new FilterAdapter(getContext(),data.getMediaTypeList(),FilterAdapter.MULTI));
        rv_subject.setAdapter(new FilterAdapter(getContext(),data.getCategoryList(),FilterAdapter.MULTI));
    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex=1;
        getPresenter().getArticleList("0","0","1",pageIndex,0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex++;
        getPresenter().getArticleList("0","0","1",pageIndex,1);
    }


    @Override
    public void getArticles(ArticalList data, int upOrDown) {
        if(upOrDown==0){
            //刷新
            articals.clear();
        }
        articals.addAll(data.getArticleList());
        adapter.notifyDataSetChanged();
    }
}
