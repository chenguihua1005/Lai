package com.softtek.lai.module.bodygame3.activity.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.model.MemberListModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 11/24/2016.
 */
@InjectLayout(R.layout.fragment_retest)
public class FcAuditFragment extends LazyBaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.plv_audit)
    PullToRefreshListView plv_audit;
    @InjectView(R.id.im_nomessage)
    ImageView im_nomessage;
    FuceSevice fuceSevice;
    int pageIndex=1;
    Long userid;
    String classid,typedata;
    EasyAdapter<MemberListModel> adapter;
    private List<MemberListModel> memberListModels = new ArrayList<MemberListModel>();
    public static Fragment getInstance(String classId,String typeDate) {
        FcAuditFragment fragment=new FcAuditFragment();
        Bundle data=new Bundle();
        data.putString("classid",classId);
        data.putString("typedata",typeDate);
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
        userid=UserInfoModel.getInstance().getUserId();
        adapter=new EasyAdapter<MemberListModel>(getContext(),memberListModels,R.layout.retest_list_audit_item) {
            @Override
            public void convert(ViewHolder holder, MemberListModel data, int position) {
                TextView username=holder.getView(R.id.tv_username);
                TextView tv_group=holder.getView(R.id.tv_group);
                TextView tv_weight=holder.getView(R.id.tv_weight);
                CircleImageView cir_headim=holder.getView(R.id.cir_headim);
                tv_group.setText(data.getGroupName());
                tv_weight.setText("("+data.getWeight()+")");
                username.setText(data.getUserName());
                if (!TextUtils.isEmpty(data.getUserIconUrl()))
                {
                    Picasso.with(getContext()).load(AddressManager.get("photoHost")+data.getUserIconUrl()).fit().into(cir_headim);
                }
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
    //下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            memberListModels.clear();
            pageIndex = 1;
            doGetData();
    }
    //下拉加载
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        doGetData();
    }
    //获取审核列表数据
    private void doGetData() {
        fuceSevice.dogetAuditList(UserInfoModel.getInstance().getToken(), userid, classid,"2016-12-14", pageIndex, 10, new RequestCallback<ResponseData<List<AuditListModel>>>() {
            @Override
            public void success(ResponseData<List<AuditListModel>> listResponseData, Response response) {
                plv_audit.onRefreshComplete();
                int status=listResponseData.getStatus();
                if (pageIndex==1)
                {
                    listResponseData.getData().get(0).getCount();
                }
                switch (status)
                {
                    case 200:
                        memberListModels.addAll(listResponseData.getData().get(0).getMemberList());
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
