package com.softtek.lai.module.bodygame3.more.view;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.adapter.MemberExpandableAdapter;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup3;
import com.softtek.lai.module.bodygame3.more.model.Member;
import com.softtek.lai.module.bodygame3.more.net.MoreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_class_member)
public class ClassMemberActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ExpandableListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    PullToRefreshExpandableListView lv;

    //EasyAdapter<Member> adapter;
    MemberExpandableAdapter adapter;
    private Map<String,List<Member>> members =new HashMap<>();
    private List<String> parents=new ArrayList<>();
    private List<ClassGroup> groups=new ArrayList<>();
    private String classId;
    private String classHxId;


    @Override
    protected void initViews() {
        tv_title.setText("人员管理");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initDatas() {
        classId = getIntent().getStringExtra("classId");
        classHxId = getIntent().getStringExtra("classHxId");
        lv.setOnRefreshListener(this);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        adapter=new MemberExpandableAdapter(this,members,parents,classId,classHxId,groups,tv_title);
        lv.getRefreshableView().setAdapter(adapter);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(lv!=null){
                    lv.setRefreshing();
                }
            }
        },400);
    }


    @Override
    public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getClassesMembers(UserInfoModel.getInstance().getToken(),
                        classId,
                        new Callback<ResponseData<List<ClassGroup3>>>() {
                            @Override
                            public void success(ResponseData<List<ClassGroup3>> data, Response response) {
                                lv.onRefreshComplete();
                                if (data.getStatus() == 200) {
                                    try {
                                        List<ClassGroup> temp=new ArrayList<>();
                                        for (ClassGroup3 group3:data.getData()){
                                            ClassGroup group=new ClassGroup();
                                            group.setCGId(group3.ClassGroupId);
                                            group.setCGName(group3.ClassGroupName);
                                            temp.add(group);
                                            if(!parents.contains(group3.ClassGroupName)){
                                                parents.add(group3.ClassGroupName);
                                            }
                                            if(!members.containsKey(group3.ClassGroupName)){
                                                members.put(group3.ClassGroupName,group3.GroupMembers);
                                            }else {
                                                List<Member> member=members.get(group3.ClassGroupName);
                                                member.clear();
                                                member.addAll(group3.GroupMembers);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                        groups.clear();
                                        groups.addAll(temp);
                                        for (int i = 0; i < parents.size(); i++) {
                                            lv.getRefreshableView().expandGroup(i);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Util.toastMsg(data.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                lv.onRefreshComplete();
                                ZillaApi.dealNetError(error);
                            }
                        });
    }
}
