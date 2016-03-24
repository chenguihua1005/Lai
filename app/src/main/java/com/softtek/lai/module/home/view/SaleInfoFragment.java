package com.softtek.lai.module.home.view;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.home.eventModel.SaleEvent;
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

@InjectLayout(R.layout.base_fragment)
public class SaleInfoFragment extends BaseFragment implements SuperSwipeRefreshLayout.OnPushLoadMoreListener{


    @InjectView(R.id.rl)
    RecyclerView rl;

    @InjectView(R.id.refresh)
    SuperSwipeRefreshLayout refresh;
    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private List<HomeInfo> infos=new ArrayList<>();

    private IHomeInfoPresenter homeInfoPresenter;

    int page=1;

    @Override
    protected void initViews() {
        refresh.setFooterView(createFooterView());
        refresh.setTargetScrollWithLayout(true);
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(refresh.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    @Override
    protected void initDatas() {
        homeInfoPresenter=new HomeInfoImpl(getContext());
        rl.setLayoutManager(new LinearLayoutManager(rl.getContext()));
        rl.setAdapter(new RecyclerViewAdapter(getActivity()));
        refresh.setOnPushLoadMoreListener(this);
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
    public void onRefreshView(SaleEvent sale){
        if(sale.flag==0){
            infos.clear();
            infos.addAll(sale.sales);
        }else {
            infos.addAll(sale.sales);
        }
        rl.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        footerTextView.setText("正在加载...");
        footerImageView.setVisibility(View.GONE);
        footerProgressBar.setVisibility(View.VISIBLE);
        homeInfoPresenter.getContentByPage(++page,1,refresh,footerProgressBar,footerImageView);
    }

    @Override
    public void onPushDistance(int distance) {

    }

    @Override
    public void onPushEnable(boolean enable) {
        footerTextView.setText(enable ? "松开加载" : "上拉加载");
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setRotation(enable ? 0 : 180);
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
            Picasso.with(mContext).load(info.getImg_Addr()).into(holder.iv_image);
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
