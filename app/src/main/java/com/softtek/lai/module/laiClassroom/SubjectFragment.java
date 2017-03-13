package com.softtek.lai.module.laiClassroom;


import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laiClassroom.model.MonographicListModel;
import com.softtek.lai.module.laiClassroom.model.TopicListModel;
import com.softtek.lai.module.laiClassroom.net.LaiClassroomService;
import com.softtek.lai.widgets.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

//专题页面
@InjectLayout(R.layout.fragment_subject)
public class SubjectFragment extends LazyBaseFragment implements PullToRefreshBase.OnRefreshListener2<ScrollView>{

    @InjectView(R.id.prsro)
    PullToRefreshScrollView prsro;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;
    @InjectView(R.id.hor_list)
    HorizontalListView hor_list;
    @InjectView(R.id.grid)
            GridView grid;
    @InjectView(R.id.layout)
    LinearLayout layout;
    EasyAdapter<TopicListModel>adapter;
    List<TopicListModel>topicListModels= new ArrayList<>();
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
                prsro.setRefreshing();
            }

        }, 300);

    }

    @Override
    protected void initViews() {
        hor_list.setFocusable(true);
        hor_list.setFocusableInTouchMode(true);
        hor_list.requestFocus();
        prsro.setOnRefreshListener(this);

//        prsro.setEmptyView(im_nomessage);
        prsro.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = prsro.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = prsro.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
//        View headView = View.inflate(getContext(), R.layout.bodygame3_head, null);
//        re_photowall = (RelativeLayout) headView.findViewById(R.id.re_photowall);
//        ptrgv.getRefreshableView().
//        ptrgv.getRefreshableView().addView(headView);
//
////        ptrgv.getRefreshableView().addView(headView);
        adapter=new EasyAdapter<TopicListModel>(getContext(),topicListModels,R.layout.frag_girdlist_subject_item) {
            @Override
            public void convert(ViewHolder holder, TopicListModel data, int position) {
                TextView tv_clickhot=holder.getView(R.id.tv_clickhot);
                tv_clickhot.setText(data.getClicks());
            }
        };
        hor_list.setAdapter(adapter);
        grid.setAdapter(adapter);
//        ptrgv.setAdapter(adapter);

    }

    @Override
    protected void initDatas() {



    }

//    @Override
//    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
//
//    }
//
//    @Override
//    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
//
//    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        LaiClassroomService service= ZillaApi.NormalRestAdapter.create(LaiClassroomService.class);
        service.doGetArticleTopic(UserInfoModel.getInstance().getToken(), 1, 10, new Callback<ResponseData<MonographicListModel>>() {
            @Override
            public void success(ResponseData<MonographicListModel> monographicListModelResponseData, Response response) {
                int status=monographicListModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        topicListModels.addAll(monographicListModelResponseData.getData().getArticleTopicList());
                        adapter.notifyDataSetChanged();
                        prsro.onRefreshComplete();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }
}
