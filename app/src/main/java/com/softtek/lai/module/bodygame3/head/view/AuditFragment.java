package com.softtek.lai.module.bodygame3.head.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.model.MemberListModel;
import com.softtek.lai.module.bodygame3.activity.model.UseredModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.module.ranking.persenter.RankManager;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 11/24/2016.
 */
@InjectLayout(R.layout.fragment_retest)
public class AuditFragment extends LazyBaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;
    FuceSevice fuceSevice;
    AuditListModel auditListModel;
    MemberListModel memberListModel;
    EasyAdapter<AuditListModel> adapter;
    private List<AuditListModel> auditListModels = new ArrayList<AuditListModel>();
    private List<MemberListModel> memberListModels = new ArrayList<MemberListModel>();
    private List<MemberListModel> memberListModels1 = new ArrayList<MemberListModel>();
    public static AuditFragment getInstance(){
        AuditFragment fragment=new AuditFragment();
        Bundle data=new Bundle();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        plv_audit.setOnItemClickListener(this);
        plv_audit.setMode(PullToRefreshBase.Mode.BOTH);
        plv_audit.setOnRefreshListener(this);
        plv_audit.setEmptyView(im_nomessage);
        ILoadingLayout startLabelse = plv_audit.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("正在刷新数据");// 刷新时
        startLabelse.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabelsr = plv_audit.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("正在加载数据");
        endLabelsr.setReleaseLabel("松开立即加载");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void initDatas() {
        fuceSevice= ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        doGetData(Long.parseLong("4"),"C4E8E179-FD99-4955-8BF9-CF470898788B","2016-10-22",1,10);
        adapter=new EasyAdapter<AuditListModel>(getContext(),auditListModels,R.layout.retest_list_audit_item) {
            @Override
            public void convert(ViewHolder holder, AuditListModel data, int position) {
                TextView username=holder.getView(R.id.tv_username);
            }
        };
        plv_audit.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
    //获取审核列表数据
    private void doGetData(Long accountid, String classid, String typedate, int pageIndex, int pageSize) {
        fuceSevice.dogetAuditList(UserInfoModel.getInstance().getToken(), accountid, classid, typedate, pageIndex, pageSize, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
//                        if (listResponseData)
                        memberListModels=listResponseData.getData().get(1).getMemberList();
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }
        });
    }
}
