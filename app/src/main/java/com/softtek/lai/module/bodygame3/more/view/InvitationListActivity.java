package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.softtek.lai.module.bodygame3.more.adapter.InvitatedExpandableAdapter;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.InvitatedContact;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.RetrofitError;
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
    InvitatedExpandableAdapter adapter;
    private ClassModel model;

    private int pageIndex;
    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.invitation_add));
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    @Override
    protected void initDatas() {
        adapter=new InvitatedExpandableAdapter(this,datas,groups);
        lv.getRefreshableView().setAdapter(adapter);

        lv.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
        Bundle bundle=getIntent().getBundleExtra("class");
        model=bundle.getParcelable("class");
        pageIndex=1;
        dialogShow("加载中...");
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getInvitatedContactList(
                        UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        model.getClassId(),
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<InvitatedContact>>>() {
                            @Override
                            public void success(ResponseData<List<InvitatedContact>> data, Response response) {
                                dialogDissmiss();
                                lv.onRefreshComplete();
                                if(data.getStatus()==200){
                                    onResult(data.getData());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                super.failure(error);
                                dialogDissmiss();
                            }
                        });

    }
    Map<String,List<InvitatedContact>> datas=new HashMap<>();
    private List<String> groups=new ArrayList<>();
    private void onResult(List<InvitatedContact> models){
//        InvitatedContact aa=new InvitatedContact();
//        aa.setJoinGroupName("aa");
//        aa.setInviterUserName("关谷奥匈");
//        models.add(aa);
//        InvitatedContact gg=new InvitatedContact();
//        gg.setJoinGroupName("gg");
//        gg.setInviterUserName("dsaa");
//        models.add(gg);
//        InvitatedContact bb=new InvitatedContact();
//        bb.setJoinGroupName("bb");
//        bb.setInviterUserName("asda");
//        models.add(bb);
//        InvitatedContact cc=new InvitatedContact();
//        cc.setJoinGroupName("cc");
//        cc.setInviterUserName("rffds");
//        models.add(cc);
//        InvitatedContact dd=new InvitatedContact();
//        dd.setJoinGroupName("cc");
//        dd.setInviterUserName("gfd");
//        models.add(dd);
//        InvitatedContact ee=new InvitatedContact();
//        ee.setJoinGroupName("dd");
//        ee.setInviterUserName("aa");
//        models.add(ee);
//        InvitatedContact ff=new InvitatedContact();
//        ff.setJoinGroupName("aa");
//        ff.setInviterUserName("dad");
//        models.add(ff);
        for (InvitatedContact contact:models){
            String groupName=contact.getJoinGroupName();
            if(!groups.contains(groupName)){
                groups.add(groupName);
                List<InvitatedContact> invitatedContacts=new ArrayList<>();
                datas.put(groupName,invitatedContacts);
            }
            datas.get(groupName).add(contact);
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < groups.size(); i++) {
            lv.getRefreshableView().expandGroup(i);
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
                startActivity(new Intent(this,ContactsActivity.class));
            }
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

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
                                    onResult(data.getData());
                                }
                            }
                        });
    }
}
