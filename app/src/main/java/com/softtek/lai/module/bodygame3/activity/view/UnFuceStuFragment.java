package com.softtek.lai.module.bodygame3.activity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.model.MemberListModel;
import com.softtek.lai.module.bodygame3.activity.presenter.FuceCheckListPresenter;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 11/24/2016.
 */
@InjectLayout(R.layout.fragment_retest)
public class UnFuceStuFragment extends LazyBaseFragment<FuceCheckListPresenter> implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, FuceCheckListPresenter.FuceCheckListView {
    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;
    @InjectView(R.id.ll_nomessage)
    RelativeLayout im_nomessage;

    int pageIndex = 1;
    Long userid;
    private static String classid;
    private static String typedata;
    private static int resetdatestatus = 1;
    EasyAdapter<MemberListModel> adapter;
    private List<MemberListModel> memberListModels = new ArrayList<>();

    public static Fragment getInstance(String classId, String typeDate, int type) {
        UnFuceStuFragment fragment = new UnFuceStuFragment();
        Bundle data = new Bundle();
        classid = classId;
        typedata = typeDate;
        resetdatestatus = type;
        fragment.setArguments(data);
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(UPDATE_UI_FCCHECK_TABLIST));
    }

    @Override
    protected void initDatas() {
        setPresenter(new FuceCheckListPresenter(this));
        userid = UserInfoModel.getInstance().getUserId();
        adapter = new EasyAdapter<MemberListModel>(getContext(), memberListModels, R.layout.retest_list_audit_item) {
            @Override
            public void convert(ViewHolder holder, MemberListModel data, int position) {
                TextView username = holder.getView(R.id.tv_username);
                TextView tv_group = holder.getView(R.id.tv_group);
                CircleImageView cir_headim = holder.getView(R.id.cir_headim);
                tv_group.setText("(" + data.getGroupName() + ")");

                TextView tv_tip = holder.getView(R.id.tv_tip);
                tv_tip.setText("为其测量");
                username.setText(data.getUserName());
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
        Intent FcAudit = new Intent(getContext(), InitDataUnInputActivity2.class);
        FcAudit.putExtra("ACMId", memberListModels.get(i - 1).getAcmId());
        FcAudit.putExtra("classId", classid);
        int isAudit = 0;
        FcAudit.putExtra("Audited", isAudit);
        FcAudit.putExtra("AccountId", Long.parseLong(memberListModels.get(i - 1).getUserId()));
        FcAudit.putExtra("resetdatestatus", resetdatestatus);

        FcAudit.putExtra("typeDate", typedata);
        FcAudit.putExtra("type", 2);
        FcAudit.putExtra("typeforwhich", 1);

        FcAudit.putExtra("guangboname", UPDATE_UI_FCCHECK_TABLIST);//广播
        startActivity(FcAudit);

    }


    public static final String UPDATE_UI_FCCHECK_TABLIST = "UPDATE_UI_FCCHECK_TABLIST";

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && UPDATE_UI_FCCHECK_TABLIST.equalsIgnoreCase(intent.getAction())) {
                memberListModels.clear();
                getPresenter().getMeasureReviewedList(classid, typedata, 1, Constants.PAGESIZE_FUCE);
            }
        }
    };

    @Override
    public void onDestroyView() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroyView();
    }


    //下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        memberListModels.clear();
        pageIndex = 1;
        getPresenter().getMeasureReviewedList(classid, typedata, pageIndex, Constants.PAGESIZE_FUCE);

    }

    //上拉加载
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        ++pageIndex;
        getPresenter().getMeasureReviewedList(classid, typedata, pageIndex, Constants.PAGESIZE_FUCE);
    }


    @Override
    public void getMeasureReviewedList(List<AuditListModel> list) {
        if (list != null && list.size() == 3) {
            int unFuce_num = 0;
            int uncheck_num = 0;
            int checked_num = 0;
            for (int i = 0; i < list.size(); i++) {
                AuditListModel model = list.get(i);
                if (0 == model.getStatus()) {
                    uncheck_num = Integer.parseInt(model.getCount());
                } else if (1 == model.getStatus()) {
                    checked_num = Integer.parseInt(model.getCount());
                } else if (-1 == model.getStatus()) {
                    unFuce_num = Integer.parseInt(model.getCount());
                    memberListModels.addAll(model.getMemberList());
                }
            }

            adapter.notifyDataSetChanged();
            Intent intent = new Intent(FcAuditListActivity.UPDATENUMBER_FUCUCHECK);
            intent.putExtra("unFuce_num", unFuce_num);
            intent.putExtra("uncheck_num", uncheck_num);
            intent.putExtra("checked_num", checked_num);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

        }
    }

    @Override
    public void hidenLoading() {
        plv_audit.onRefreshComplete();
    }
}
