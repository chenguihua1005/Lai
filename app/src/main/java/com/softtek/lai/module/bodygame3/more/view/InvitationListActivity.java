package com.softtek.lai.module.bodygame3.more.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.adapter.MyExpandableAdapter;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.InvitatedContact;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * 邀请列表界面
 */
@InjectLayout(R.layout.activity_invitation_list)
public class InvitationListActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ExpandableListView>{

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.lv)
    PullToRefreshExpandableListView lv;
    MyExpandableAdapter adapter;
    private ClassModel model;
    Map<String,List<String>> datas=new HashMap<>();
    private List<String> groups=new ArrayList<>();
    private int pageIndex;
    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.invitation_add));
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    protected void initDatas() {
        //adapter=new MyExpandableAdapter(this,datas,parentList);
        lv.getRefreshableView().setAdapter(adapter);
//        for (int i = 0; i < parentList.length; i++) {
//            lv.getRefreshableView().expandGroup(i);
//        }
        lv.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
        Bundle bundle=getIntent().getBundleExtra("class");
        model=bundle.getParcelable("class");
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                lv.setRefreshing();
            }
        },300);

    }

    private void onResult(List<InvitatedContact> models,boolean isRefresh){
        if(isRefresh){
            groups.clear();
            datas.clear();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:{
                finish();
            }
                break;
            case R.id.fl_right:{
                //跳转邀请小伙伴
            }
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        pageIndex=1;
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getInvitatedContactList(
                        UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        model.getClassId(),
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<InvitatedContact>>>() {
                            @Override
                            public void success(ResponseData<List<InvitatedContact>> data, Response response) {
                                lv.onRefreshComplete();
                                if(data.getStatus()==200){
                                   // onResult(data.getData());
                                }
                            }
                        });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        pageIndex++;
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getInvitatedContactList(
                        UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        model.getClassId(),
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<InvitatedContact>>>() {
                            @Override
                            public void success(ResponseData<List<InvitatedContact>> data, Response response) {
                                lv.onRefreshComplete();
                                if(data.getStatus()==200){
                                    //onResult(data.getData());
                                }
                            }
                        });
    }
}
