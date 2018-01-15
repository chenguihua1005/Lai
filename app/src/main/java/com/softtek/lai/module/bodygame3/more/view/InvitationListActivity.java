package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
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
import com.softtek.lai.module.bodygame3.home.view.BodyGameActivity;
import com.softtek.lai.module.bodygame3.more.adapter.InvitatedExpandableAdapter;
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
    InvitatedExpandableAdapter adapter;
    private String classId;

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
        classId=getIntent().getStringExtra("classId");
        adapter=new InvitatedExpandableAdapter(this,datas,groups);
        lv.getRefreshableView().setAdapter(adapter);

        lv.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return groupPosition==0?true:false;
            }
        });
        pageIndex=1;

    }
    Map<String,List<InvitatedContact>> datas=new HashMap<>();
    private List<String> groups=new ArrayList<>();
    private void onResult(List<InvitatedContact> models){
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
                Intent intent=new Intent(this, BodyGameActivity.class);
                intent.putExtra("type",4);
                intent.putExtra("classId",classId);
                startActivity(intent);
                finish();
            }
                break;
            case R.id.fl_right:{
                //跳转邀请小伙伴
                Intent intent=new Intent(this,ContactsActivity.class);
                intent.putExtra("classId",classId);
                startActivity(intent);
            }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(this, BodyGameActivity.class);
            intent.putExtra("type",4);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        pageIndex=1;
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getInvitatedContactList(classId,
                        UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        classId,
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<InvitatedContact>>>() {
                            @Override
                            public void success(ResponseData<List<InvitatedContact>> data, Response response) {
                                try {
                                    lv.onRefreshComplete();
                                    if(data.getStatus()==200){
                                        groups.clear();
                                        datas.clear();
                                        onResult(data.getData());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

    @Override
    public void onResume() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (lv != null)
                    lv.setRefreshing();
            }
        },400);
        super.onResume();
    }

    //    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        InvitatedContact contact=intent.getParcelableExtra("invitater");
//        classId=intent.getStringExtra("classId");
//        String groupName=contact.getJoinGroupName();
//        if(datas.containsKey(groupName)){
//            datas.get(groupName).add(contact);
//        }else {
//            List<InvitatedContact> data=new ArrayList<>();
//            data.add(contact);
//            groups.add(groupName);
//            datas.put(groupName,data);
//        }
//        adapter.notifyDataSetChanged();
//        for (int i = 0; i < groups.size(); i++) {
//            lv.getRefreshableView().expandGroup(i);
//        }
//    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        pageIndex++;
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getInvitatedContactList(classId,
                        UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        classId,
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<InvitatedContact>>>() {
                            @Override
                            public void success(ResponseData<List<InvitatedContact>> data, Response response) {
                                try {
                                    lv.onRefreshComplete();
                                    if(data.getStatus()==200){
                                        onResult(data.getData());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }
}
