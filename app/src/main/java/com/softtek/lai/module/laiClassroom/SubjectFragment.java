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
import com.softtek.lai.module.laiClassroom.adapter.SubjectAdapter;
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
    SubjectAdapter subjectAdapter;

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
        topicListModels.clear();
        if (topicListModels.size()!=0||topicListModels!=null)
        {TopicModel topicModel;
            topicModel=new TopicModel();
            if (topicListModels.size()%2==0)
            {
                for (int i=0;i<=topicListModels.size()/2;i=i+2)
                {
                    List<String>topname=new ArrayList<>();
                    List<String>topimage=new ArrayList<>();
                    List<Integer>topclick=new ArrayList<>();
                    List<String>topid=new ArrayList<>();
                    topname.add(i,topicListModels.get(i).getTopicName());
                    topname.add(i+1,topicListModels.get(i+1).getTopicName());
                    topimage.add(i,topicListModels.get(i).getTopicImg());
                    topimage.add(i+1,topicListModels.get(i+1).getTopicImg());
                    topclick.add(i,topicListModels.get(i).getClicks());
                    topclick.add(i+1,topicListModels.get(i+1).getClicks());
                    topid.add(i,topicListModels.get(i).getTopicId());
                    topid.add(i+1,topicListModels.get(i+1).getTopicId());
                    topicModel.setTopicName(topname);
                    topicModel.setTopicImg(topimage);
                    topicModel.setTopicId(topid);
                    topicModel.setClicks(topclick);
                    topicModels.add(topicModel);
                }
            }
            else {
                for (int i=1;i<=topicListModels.size();i=i+2) {
                    if (i==topicListModels.size())
                    {

                        List<String>topname=new ArrayList<>();
                        List<String>topimage=new ArrayList<>();
                        List<Integer>topclick=new ArrayList<>();
                        List<String>topid=new ArrayList<>();
                        topname.add(i-1,topicListModels.get(i-1).getTopicName());
                        topimage.add(i-1,topicListModels.get(i-1).getTopicImg());
                        topclick.add(i-1,topicListModels.get(i-1).getClicks());
                        topid.add(i-1,topicListModels.get(i-1).getTopicId());
                        topicModel.setTopicName(topname);
                        topicModel.setClicks(topclick);
                        topicModel.setTopicId(topid);
                        topicModel.setTopicImg(topimage);
                        topicModels.add(topicModel);
                    }
                    else {
                        List<String>topname=new ArrayList<>();
                        List<String>topimage=new ArrayList<>();
                        List<Integer>topclick=new ArrayList<>();
                        List<String>topid=new ArrayList<>();
                        topname.add(i-1,topicListModels.get(i-1).getTopicName());
                        topname.add(i,topicListModels.get(i).getTopicName());
                        topimage.add(i-1,topicListModels.get(i-1).getTopicImg());
                        topimage.add(i,topicListModels.get(i).getTopicName());
                        topclick.add(i-1,topicListModels.get(i-1).getClicks());
                        topclick.add(i,topicListModels.get(i).getClicks());
                        topid.add(i-1,topicListModels.get(i-1).getTopicId());
                        topid.add(i,topicListModels.get(i).getTopicId());
                        topicModel.setTopicName(topname);
                        topicModel.setTopicImg(topimage);
                        topicModel.setTopicId(topid);
                        topicModel.setClicks(topclick);
                        topicModels.add(topicModel);
                    }

                }
            }

            Log.i("3333",new Gson().toJson(topicModels));

        }
        ple_list.setAdapter(subjectAdapter);
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
