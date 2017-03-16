package com.softtek.lai.module.laiClassroom;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.presenter.SubjectPresenter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/16/2017.
 */
@InjectLayout(R.layout.fragment_subjectnew)
public class SubjectNewFragment extends LazyBaseFragment<SubjectPresenter>implements SubjectPresenter.getSubject, SwipeRefreshLayout.OnRefreshListener {
   @InjectView(R.id.ple_list)
   SuperRecyclerView ple_list;


    @Override
    protected void lazyLoad() {

     new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

      @Override
      public void run() {
       ple_list.setRefreshing(true);
      }

     }, 300);

    }

    @Override
    protected void initViews() {
     ple_list.setRefreshListener(this);
     ple_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
       super.onScrollStateChanged(recyclerView, newState);
      }
     });
//     ple_list.dr();
     ple_list.isLoadingMore();
     ple_list.setBackground(getResources().getDrawable(R.drawable.refresh));
     ple_list.setupMoreListener(new OnMoreListener() {
      @Override
      public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
       getPresenter().getSubjectData(1,10);
      }
     },10);
     ple_list.setupSwipeToDismiss(new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
      @Override
      public boolean canDismiss(int position) {
       return true;
      }

      @Override
      public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {

      }
     });
     ple_list.setRefreshingColor(R.color.green,R.color.green1,R.color.blue,R.color.blue);

//        prsro.setEmptyView(im_nomessage);
//     ple_list.setMode(PullToRefreshBase.Mode.BOTH);
//     ILoadingLayout startLabelse = ple_list.getLoadingLayoutProxy(true, false);
//     startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
//     startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
//     startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
//     ILoadingLayout endLabelsr = ple_list.getLoadingLayoutProxy(false, true);
//     endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
//     endLabelsr.setRefreshingLabel("正在刷新数据");
//     endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
//     View headView = View.inflate(getContext(), R.layout.expandlist_subject_group, null);
//     viewPager = (ViewPager) headView.findViewById(R.id.viewpager);
//     ple_list.getRefreshableView().addHeaderView(headView);
    }

    @Override
    protected void initDatas() {

     setPresenter(new SubjectPresenter(this));

    }
    @Override
    public void getSubjectart(SubjectModel subjectModel) {
//     ple_list.showProgress();
     ple_list.hideProgress();

    }

 @Override
 public void onRefresh() {
  getPresenter().getSubjectData(1,10);
 }
}
