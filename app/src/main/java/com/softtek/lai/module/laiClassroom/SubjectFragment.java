package com.softtek.lai.module.laiClassroom;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.laiClassroom.model.ArticleTopicModel;
import com.softtek.lai.module.laiClassroom.model.SubjectModel;
import com.softtek.lai.module.laiClassroom.model.TopicModel;
import com.softtek.lai.module.laiClassroom.presenter.SubjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
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
    private List<String> advList = new ArrayList<>();

    int pageindex=1;
//    RelativeLayout re_photowall;

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

        adapter=new EasyAdapter<ArticleTopicModel>(getContext(),topicListModels,R.layout.frag_girdlist_subject_item) {
            @Override
            public void convert(ViewHolder holder, ArticleTopicModel data, int position) {
                TextView tv_clickhot=holder.getView(R.id.tv_clickhot);
                tv_clickhot.setText(data.getClicks()+"");
                advList.add(data.getTopicImg());
            }
        };
        ple_list.setAdapter( adapter);

    }

    @Override
    protected void initDatas() {
      setPresenter(new SubjectPresenter(this));



    }


    @Override
    public void getSubjectart(SubjectModel subjectModel) {
        topicListModels.addAll(subjectModel.getArticleTopicList());
        if (topicListModels.size()!=0||topicListModels!=null)
        {TopicModel topicModel;
            topicModel=new TopicModel();
            if (topicListModels.size()%2==0)
            {   int j=0;
                for (int i=0;i<=topicListModels.size()/2;i=i+2)
                {

//                    topicModel.setTopicName();
//                    topicModels.set(j).getTopicName().set(i,topicListModels.get(i).getTopicName());
//                    topicModels.get(j).getTopicName().set(i+1,topicListModels.get(i+1).getTopicName());
//                    j++;
                }
            }
            else {
                int j=0;
                for (int i=1;i<=topicListModels.size();i=i+2) {
                    if (i==topicListModels.size())
                    {
                        topicModels.get(j).getTopicName().set(i-1,topicListModels.get(i-1).getTopicName());
                    }
                    else {
                        topicModels.get(j).getTopicName().set(i-1,topicListModels.get(i-1).getTopicName());
                        topicModels.get(j).getTopicName().set(i,topicListModels.get(i).getTopicName());
                        j++;
                    }

                }
            }
            Log.i("3333",new Gson().toJson(topicModels));

        }
        ple_list.setAdapter(adapter);
        ple_list.onRefreshComplete();

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
