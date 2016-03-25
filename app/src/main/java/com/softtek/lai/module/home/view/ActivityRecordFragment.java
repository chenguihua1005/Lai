package com.softtek.lai.module.home.view;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.home.adapter.DemoLoadMoreView;
import com.softtek.lai.module.home.adapter.DividerItemDecoration;
import com.softtek.lai.module.home.eventModel.ActivityEvent;
import com.softtek.lai.module.home.eventModel.RefreshEvent;
import com.softtek.lai.module.home.model.HomeInfo;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.widgets.SuperSwipeRefreshLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.base_fragment1)
public class ActivityRecordFragment extends BaseFragment {

    @InjectView(R.id.ptrrv)
    PullToRefreshRecyclerView ptrrv;

    private List<HomeInfo> infos=new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page=0;

    @Override
    protected void initViews() {
        ptrrv.setSwipeEnable(false);
        DemoLoadMoreView loadMoreView=new DemoLoadMoreView(getContext(),ptrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("正在加载...");
        loadMoreView.setLoadMorePadding(100);
        ptrrv.setLayoutManager(new LinearLayoutManager(getContext()));
        ptrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                handler.sendEmptyMessageDelayed(1,2000);
            }
        });
        ptrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        ptrrv.setLoadMoreFooter(loadMoreView);
        ptrrv.onFinishLoading(true, false);
    }

Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(msg.what==1){
            ptrrv.onFinishLoading(true,false);
        }
    }
};

    @Override
    protected void initDatas() {
        for(int i=0;i<5;i++){
            HomeInfo info=new HomeInfo();
            info.setImg_Title("item"+i);
            infos.add(info);
        }
        homeInfoPresenter=new HomeInfoImpl(getContext());
        ptrrv.setAdapter(new RecyclerViewAdapter(getContext()));
        //homeInfoPresenter.getContentByPage(0,1, 1);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshView(ActivityEvent activity){
        if(activity.flag==0){
            infos.clear();
            infos.addAll(activity.activitys);
        }else {
            infos.addAll(activity.activitys);
        }
        //ptrrv.getAdapter().notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshResult(RefreshEvent event){

    }





    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private Context mContext;

        public RecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
            //绑定数据
            HomeInfo info=infos.get(position);
            Picasso.with(mContext).load(info.getImg_Addr()).error(R.drawable.froyo).into(holder.iv_image);
            holder.tv_title.setText(info.getImg_Title());

        }

        @Override
        public int getItemCount() {
            return infos.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView iv_image;
            public TextView tv_title;
            public ViewHolder(View view) {
                super(view);
                iv_image= (ImageView) view.findViewById(R.id.iv_image);
                tv_title= (TextView) view.findViewById(R.id.tv_title);
            }
        }
    }
}
