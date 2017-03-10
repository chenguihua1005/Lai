package com.softtek.lai.module.laiClassroom;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laiClassroom.model.MonographicListModel;
import com.softtek.lai.module.laiClassroom.model.TopicListModel;
import com.softtek.lai.module.laiClassroom.net.LaiClassroomService;

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
public class SubjectFragment extends LazyBaseFragment implements PullToRefreshBase.OnRefreshListener2<GridView>{

    @InjectView(R.id.ptrgv)
    PullToRefreshGridView ptrgv;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;
    EasyAdapter<TopicListModel>adapter;
    List<TopicListModel>topicListModels= new ArrayList<>();
    int pageindex=1;

    public SubjectFragment() {
        // Required empty public constructor
    }
    

    @Override
    protected void lazyLoad() {



    }

    @Override
    protected void initViews() {
        ptrgv.setOnRefreshListener(this);

//        ptrgv.setEmptyView(im_nomessage);
        ptrgv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = ptrgv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = ptrgv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在刷新数据");
        endLabelsr.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
//        View headView = View.inflate(getContext(), R.layout.bodygame3_head, null);
        adapter=new EasyAdapter<TopicListModel>(getContext(),topicListModels,R.layout.frag_girdlist_subject_item) {
            @Override
            public void convert(ViewHolder holder, TopicListModel data, int position) {
                TextView tv_clickhot=holder.getView(R.id.tv_clickhot);
                tv_clickhot.setText(data.getClicks());
            }
        };
        ptrgv.setAdapter(adapter);

    }

    @Override
    protected void initDatas() {



    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
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
                        ptrgv.onRefreshComplete();
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
    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

    }
}
