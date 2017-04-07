package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureListModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.activity.presenter.MesurePresenter;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 4/7/2017.
 */

@InjectLayout(R.layout.fragment_retest)
public class MeasuredFragment extends LazyBaseFragment<MesurePresenter> implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, MesurePresenter.MesureView {
    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;
    int pageIndex = 1;
    private static String classid;

    private List<MeasureModel> memberModels = new ArrayList<MeasureModel>();
    private EasyAdapter<MeasureModel> adapter;

    public static Fragment getInstance(String Classid) {
        MeasuredFragment fragment = new MeasuredFragment();
        Bundle bundle = new Bundle();
        classid = Classid;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                plv_audit.setRefreshing();
            }
        }, 300);
    }

    @Override
    protected void initViews() {
        plv_audit.setOnItemClickListener(this);
        plv_audit.setMode(PullToRefreshBase.Mode.BOTH);
        plv_audit.setOnRefreshListener(this);
        plv_audit.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_audit.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_audit.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示

        setPresenter(new MesurePresenter(this));
    }

    @Override
    protected void initDatas() {
        adapter = new EasyAdapter<MeasureModel>(getContext(), memberModels, R.layout.mesure_item) {
            @Override
            public void convert(ViewHolder holder, MeasureModel data, int position) {
                TextView username = holder.getView(R.id.tv_username);
                username.setText(data.getUserName());

                TextView tv_tip = holder.getView(R.id.tv_tip);
                tv_tip.setText("重新测量");

                CircleImageView cir_headim = holder.getView(R.id.cir_headim);
                if (!TextUtils.isEmpty(data.getUserIconUrl())) {
                    Picasso.with(getContext()).load(AddressManager.get("photoHost") + data.getUserIconUrl()).fit().into(cir_headim);
                } else {
                    Picasso.with(getContext()).load(R.drawable.img_default).fit().into(cir_headim);
                }
            }
        };

        plv_audit.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        memberModels.clear();
        pageIndex = 1;
        doGetData(UserInfoModel.getInstance().getUserId(), classid, pageIndex, 10);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        doGetData(UserInfoModel.getInstance().getUserId(), classid, ++pageIndex, 10);
    }

    //获取审核列表数据
    private void doGetData(Long accountid, String classid, final int pageIndex, int pageSize) {
        getPresenter().getMesureList(accountid, classid, pageIndex, pageSize);
    }

    @Override
    public void getMesureList(List<MeasureListModel> list) {
        if (list.size() != 0) {
            memberModels.addAll(list.get(1).getMemberList());
            adapter.notifyDataSetChanged();

            Intent intent = new Intent(MeasureListActivity.UPDATENUMBER_MEASURE);
            intent.putExtra("number_uncheck", list.get(0).getCount());
            intent.putExtra("number_checked", list.get(1).getCount());//来自未复测
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }
    }

    @Override
    public void hidenLoading() {
        plv_audit.onRefreshComplete();
    }
}
