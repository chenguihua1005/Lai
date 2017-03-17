package com.softtek.lai.module.laiClassroom;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.adapter.HeaderFooterReAdapter;
import com.softtek.lai.module.laiClassroom.model.ArticleTopicModel;
import com.softtek.lai.module.laiClassroom.model.RecommendModel;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.presenter.SubjectPresenter;
import com.squareup.picasso.Picasso;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 3/16/2017.
 */
@InjectLayout(R.layout.fragment_subjectnew)
public class SubjectNewFragment extends LazyBaseFragment<SubjectPresenter>implements SubjectPresenter.getSubject, SwipeRefreshLayout.OnRefreshListener, SuperRecyclerView.LoadingListener, SuperBaseAdapter.OnItemClickListener {
   @InjectView(R.id.ple_list)
   SuperRecyclerView ple_list;
   HeaderFooterReAdapter mAdapter;

   private View headerView;
   ViewPager viewPager;
   List<RecommendModel> recommendModels = new ArrayList<>();
   List<ArticleTopicModel>articleTopicModels=new ArrayList<>();
   int pageindex=1;
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

     LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
     layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
     layoutManager=new GridLayoutManager(getContext(),1);
     ple_list.setLayoutManager(layoutManager);
     ple_list.setRefreshEnabled(true);
     ple_list.setLoadMoreEnabled(true);
     ple_list.setLoadingListener(this);
     headerView = LayoutInflater.from(getContext()).inflate(R.layout.view_header_layout, null);

     viewPager = (ViewPager) headerView.findViewById(R.id.viewpager);
    }

    @Override
    protected void initDatas() {
     setPresenter(new SubjectPresenter(this));
     initAdapter();
    }
    @Override
    public void getSubjectart(SubjectModel subjectModel) {
     if (subjectModel != null) {
      if (!subjectModel.getRecommendTopicList().isEmpty()) {
       viewPager.setOffscreenPageLimit(subjectModel.getRecommendTopicList().size());
       recommendModels.clear();
       viewPager.removeAllViews();
       recommendModels.addAll(subjectModel.getRecommendTopicList());
       adapterData();
      }
      if (!subjectModel.getArticleTopicList().isEmpty())
      {
       articleTopicModels.addAll(subjectModel.getArticleTopicList());
      }
     }
    }

 @Override
 public void onLoadMore() {
  new Handler().postDelayed(new Runnable() {
   @Override
   public void run() {
    pageindex++;
    getPresenter().getSubjectData(pageindex,10);
    ple_list.completeLoadMore();

   }
  },2000);
 }

 @Override
 public void onRefresh() {
  new Handler().postDelayed(new Runnable() {
   @Override
   public void run() {
    pageindex=1;
    getPresenter().getSubjectData(pageindex,10);
    articleTopicModels.clear();
    ple_list.completeRefresh();

   }
  },2000);
 }
 private void initAdapter(){
  mAdapter = new HeaderFooterReAdapter(getContext(),articleTopicModels);
  mAdapter.addHeaderView(headerView);
  mAdapter.setOnItemClickListener(this);
  ple_list.setAdapter(mAdapter);
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
 @Override
 public void onItemClick(View view, Object item, int position) {
  Intent intent = new Intent(getContext(), SubjectdetailActivity.class);
  intent.putExtra("topictitle", articleTopicModels.get(position).getTopicName());
  intent.putExtra("topicId", articleTopicModels.get(position).getTopicId());
  startActivity(intent);
 }

}
