package com.softtek.lai.module.community.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.community.model.TopicInfo;
import com.softtek.lai.module.community.net.CommunityService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_topic_list)
public class TopicListActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ptrlv)
    PullToRefreshListView ptrlv;

    private EasyAdapter<TopicInfo> adapter;
    private List<TopicInfo> datas;

    @Override
    protected void initViews() {
        tv_title.setText("话题");
        ptrlv.setOnRefreshListener(this);
        ptrlv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout startLabelse = ptrlv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
    }

    @Override
    protected void initDatas() {
        datas = new ArrayList<>();
        adapter = new EasyAdapter<TopicInfo>(this, datas, R.layout.item_topic_list) {
            @Override
            public void convert(ViewHolder holder, TopicInfo data, int position) {
                TextView tv_topic = holder.getView(R.id.tv_topic);
                tv_topic.setText("#" + data.getTopicName() + "#");
                TextView tv_content = holder.getView(R.id.tv_content);
                tv_content.setText(data.getTopicExplain());
                SquareImageView siv_topic = holder.getView(R.id.siv_topic);
                if (TextUtils.isEmpty(data.getTopicPhoto())) {
                    Picasso.with(TopicListActivity.this).load(R.drawable.default_icon_square)
                            .placeholder(R.drawable.default_icon_square).into(siv_topic);
                } else {
                    Picasso.with(TopicListActivity.this).load(AddressManager.get("photoHost") + data.getTopicPhoto())
                            .resize(DisplayUtil.dip2px(TopicListActivity.this, 40), DisplayUtil.dip2px(TopicListActivity.this, 40))
                            .centerCrop()
                            .error(R.drawable.default_icon_square)
                            .placeholder(R.drawable.default_icon_square)
                            .into(siv_topic);
                }

            }
        };
        ptrlv.setAdapter(adapter);
        ptrlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TopicListActivity.this, TopicDetailActivity.class);
                intent.putExtra("topicId",datas.get(position-1).getTopicType());
                startActivity(intent);
            }
        });
        ptrlv.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    ptrlv.setRefreshing();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },300);
    }

    @OnClick(R.id.ll_left)
    public void backClick() {
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        ZillaApi.NormalRestAdapter.create(CommunityService.class)
                .getTopicList(new RequestCallback<ResponseData<List<TopicInfo>>>() {
                    @Override
                    public void success(ResponseData<List<TopicInfo>> listResponseData, Response response) {
                        ptrlv.onRefreshComplete();
                        datas.clear();
                        datas.addAll(listResponseData.getData());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ptrlv.onRefreshComplete();
                        super.failure(error);
                    }
                });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
