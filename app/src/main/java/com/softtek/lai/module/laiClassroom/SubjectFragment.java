package com.softtek.lai.module.laiClassroom;


import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.adapter.SubjectAdapter;
import com.softtek.lai.module.laiClassroom.model.ArticleTopicModel;
import com.softtek.lai.module.laiClassroom.model.RecommendModel;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.model.TopicModel;
import com.softtek.lai.module.laiClassroom.presenter.SubjectPresenter;
import com.softtek.lai.widgets.MyRelative;
import com.softtek.lai.widgets.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

//专题页面
@InjectLayout(R.layout.fragment_subject)
public class SubjectFragment extends LazyBaseFragment<SubjectPresenter> implements SubjectPresenter.getSubject, PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.ple_list)
    PullToRefreshListView ple_list;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;

    EasyAdapter<ArticleTopicModel>adapter;
    List<ArticleTopicModel> topicListModels= new ArrayList<>();
    List<TopicModel> topicModels=new ArrayList<>();
    List<RecommendModel>recommendModels=new ArrayList<>();
    private List<String> advList = new ArrayList<>();

    int pageindex=1;
    SubjectAdapter subjectAdapter;
    ViewPager viewPager;
    MyRelative rl_container;


    public SubjectFragment() {
        // Required empty public constructor
    }
    

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                ple_list.setRefreshing();
            }

        }, 300);

    }

    @Override
    protected void initViews() {
        ple_list.setFocusable(true);
        ple_list.setFocusableInTouchMode(true);
        ple_list.requestFocus();
        ple_list.setOnRefreshListener(this);

//        prsro.setEmptyView(im_nomessage);
        ple_list.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = ple_list.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ple_list.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        View headView = View.inflate(getContext(), R.layout.expandlist_subject_group, null);
        viewPager = (ViewPager) headView.findViewById(R.id.viewpager);
        rl_container = (MyRelative) headView.findViewById(R.id.rl_container);
        ple_list.getRefreshableView().addHeaderView(headView);
        subjectAdapter=new SubjectAdapter(getContext(),topicModels);

//        adapter=new EasyAdapter<ArticleTopicModel>(getContext(),topicListModels,R.layout.frag_girdlist_subject_item) {
//            @Override
//            public void convert(ViewHolder holder, ArticleTopicModel data, int position) {
//                TextView tv_clickhot=holder.getView(R.id.tv_clickhot);
//                tv_clickhot.setText(data.getClicks()+"");
//                advList.add(data.getTopicImg());
//            }
//        };
        ple_list.setAdapter( subjectAdapter);

    }

    @Override
    protected void initDatas() {
      setPresenter(new SubjectPresenter(this));

    }


    @Override
    public void getSubjectart(SubjectModel subjectModel) {
        topicListModels.addAll(subjectModel.getArticleTopicList());
        ArticleTopicModel articleTopicModel=new ArticleTopicModel();
//        articleTopicModel=topicListModels.get(0);
//        topicListModels.add(articleTopicModel);
        topicModels.clear();
        if (topicListModels.size()!=0||topicListModels!=null)
        {TopicModel topicModel;
            topicModel=new TopicModel();
            if (topicListModels.size()%2==0)
            {
                for (int i=0;i<topicListModels.size();i=i+2)
                {
                    List<String>topname=new ArrayList<>();
                    List<String>topimage=new ArrayList<>();
                    List<Integer>topclick=new ArrayList<>();
                    List<String>topid=new ArrayList<>();
                    topname.add(0,topicListModels.get(i).getTopicName());
                    topname.add(1,topicListModels.get(i+1).getTopicName());
                    topimage.add(0,topicListModels.get(i).getTopicImg());
                    topimage.add(1,topicListModels.get(i+1).getTopicImg());
                    topclick.add(0,topicListModels.get(i).getClicks());
                    topclick.add(1,topicListModels.get(i+1).getClicks());
                    topid.add(0,topicListModels.get(i).getTopicId());
                    topid.add(1,topicListModels.get(i+1).getTopicId());
                    topicModel.setTopicName(topname);
                    topicModel.setTopicImg(topimage);
                    topicModel.setTopicId(topid);
                    topicModel.setClicks(topclick);
                    topicModels.add(topicModel);
                }
            }
            else {
                for (int i=1;i<=topicListModels.size();i=i+2) {
                    if (i!=topicListModels.size())
                    {   topicModel=new TopicModel();
                        List<String>topname=new ArrayList<>();
                        List<String>topimage=new ArrayList<>();
                        List<Integer>topclick=new ArrayList<>();
                        List<String>topid=new ArrayList<>();
                        topname.add(0,topicListModels.get(i-1).getTopicName());
                        topname.add(1,topicListModels.get(i).getTopicName());
                        topimage.add(0,topicListModels.get(i-1).getTopicImg());
                        topimage.add(1,topicListModels.get(i).getTopicImg());
                        topclick.add(0,topicListModels.get(i-1).getClicks());
                        topclick.add(1,topicListModels.get(i).getClicks());
                        topid.add(0,topicListModels.get(i-1).getTopicId());
                        topid.add(1,topicListModels.get(i).getTopicId());
                        topicModel.setTopicName(topname);
                        topicModel.setTopicImg(topimage);
                        topicModel.setTopicId(topid);
                        topicModel.setClicks(topclick);
                        topicModels.add(topicModel);

                    }
                    else {
                        topicModel=new TopicModel();
                        List<String>topname=new ArrayList<>();
                        List<String>topimage=new ArrayList<>();
                        List<Integer>topclick=new ArrayList<>();
                        List<String>topid=new ArrayList<>();
                        topname.add(0,topicListModels.get(i-1).getTopicName());
                        topimage.add(0,topicListModels.get(i-1).getTopicImg());
                        topclick.add(0,topicListModels.get(i-1).getClicks());
                        topid.add(0,topicListModels.get(i-1).getTopicId());
                        topicModel.setTopicName(topname);
                        topicModel.setClicks(topclick);
                        topicModel.setTopicId(topid);
                        topicModel.setTopicImg(topimage);
                        topicModels.add(topicModel);
                    }

                }
            }

            Log.i("3333",new Gson().toJson(topicModels));

        }
        ple_list.setAdapter(subjectAdapter);
        ple_list.onRefreshComplete();
        if (subjectModel != null) {
            if (!subjectModel.getRecommendTopicList().isEmpty()) {
                recommendModels.clear();
                viewPager.removeAllViews();
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                recommendModels.addAll(subjectModel.getRecommendTopicList());
                adapterData();
            }


        }

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
            public Object instantiateItem(ViewGroup container, int position) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.list_subjectremend_item, container, false);
                container.addView(view);
                TextView tv_clickhot = (TextView) container.findViewById(R.id.tv_clickhot);
                tv_clickhot.setText(recommendModels.get(position).getClicks() + "");
                ImageView im_photo = (ImageView) container.findViewById(R.id.im_photo);
                Picasso.with(getContext()).load(AddressManager.get("photoHost") + recommendModels.get(position).getTopicImg()).fit().error(R.drawable.default_icon_square)
                        .placeholder(R.drawable.default_icon_square).into(im_photo);

                return view;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public float getPageWidth(int position) {
                return super.getPageWidth(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };

        viewPager.setAdapter(pageradapter);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        getPresenter().getSubjectData(1,10);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        getPresenter().getSubjectData(1,10);
    }
}
