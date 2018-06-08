package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.view.ChooseView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.home.view.BodyGameNewActivity;
import com.softtek.lai.module.bodygame3.more.adapter.ContactExpandableAdapter;
import com.softtek.lai.module.bodygame3.more.model.Contact;
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

@InjectLayout(R.layout.activity_contacts)
public class ContactsActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ExpandableListView> {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.choose)
    ChooseView chooseView;
    @InjectView(R.id.tv_perview)
    TextView tv_perview;
    @InjectView(R.id.tv_nomessage)
    TextView tv_nomessage;

    @InjectView(R.id.ll_search)
    LinearLayout ll_search;

    @InjectView(R.id.elv)
    PullToRefreshExpandableListView elv;
    private ContactExpandableAdapter adapter;

    private int pageIndex = 1;
    public static Map<String, List<Contact>> datas = new HashMap<>();
    private List<String> groups = new ArrayList<>();

    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        boolean flag = getIntent().getBooleanExtra("createClass", false);
        if (flag) {
            ll_left.setVisibility(View.INVISIBLE);
            tv_right.setText("跳过");
            fl_right.setOnClickListener(this);
//            ll_left.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(ContactsActivity.this, BodyGameActivity.class);
//                    intent.putExtra("type",4);
//                    finish();
//                    startActivity(intent);
//                }
//            });
        } else {
            ll_left.setOnClickListener(this);
        }
        elv.setOnRefreshListener(this);
        elv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        elv.setEmptyView(tv_nomessage);
        //View head= LayoutInflater.from(this).inflate(R.layout.expandable_head_contact,null);
        //ll_search= (LinearLayout) head.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        //elv.getRefreshableView().addHeaderView(head);
        adapter = new ContactExpandableAdapter(this, datas, groups);
        elv.getRefreshableView().setAdapter(adapter);
        elv.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
        elv.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Contact contact = datas.get(groups.get(i)).get(i1);
                Intent intent = new Intent(ContactsActivity.this, InvitationSettingActivity.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                intent.putExtra("inviterId", contact.getAccountId());
                intent.putExtra("inviterHXId", contact.getHXAccountId());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void initDatas() {
        chooseView.setChooseListener(new ChooseView.OnChooseListener() {

            @Override
            public void onDown() {
                tv_perview.setVisibility(View.VISIBLE);
            }

            @Override
            public void chooseView(String text, int index) {
                tv_perview.setText(text.trim());
                if (index == -10) {
                    elv.post(new Runnable() {
                        @Override
                        public void run() {
                            elv.getRefreshableView().smoothScrollByOffset(0);
                        }
                    });
                } else {
                    elv.getRefreshableView().setSelectedGroup(index);
                }
            }

            @Override
            public void onUp() {
                tv_perview.setVisibility(View.GONE);
            }
        });
//        dialogShow();
//        ZillaApi.NormalRestAdapter.create(MoreService.class)
//                .getContactsList(UserInfoModel.getInstance().getToken(),
//                        UserInfoModel.getInstance().getUserId(),
//                        20, 1,
//                        new RequestCallback<ResponseData<List<Contact>>>() {
//                            @Override
//                            public void success(ResponseData<List<Contact>> data, Response response) {
//                                dialogDissmiss();
//                                if(data.getStatus()==200){
//                                    try {
//                                        onResult(data.getData());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                dialogDissmiss();
//                                super.failure(error);
//                            }
//                        });
    }


    private void onResult(List<Contact> models) {
        if (models == null || models.isEmpty()) {
            return;
        }
        for (Contact contact : models) {
            String groupName;
            if (TextUtils.isEmpty(contact.getUserEn())) {
                groupName = "#";
            } else {
                groupName = contact.getUserEn().substring(0, 1).toUpperCase();

            }
            if (!groups.contains(groupName)) {
                groups.add(groupName);
                List<Contact> invitatedContacts = new ArrayList<>();
                datas.put(groupName, invitatedContacts);
            }
            datas.get(groupName).add(contact);
        }
        if (groups.contains("#")) {
            groups.remove(groups.indexOf("#"));
            groups.add("#");
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < groups.size(); i++) {
            elv.getRefreshableView().expandGroup(i);
            chooseView.buildCharaset(groups.get(i));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_search: {
                Intent intent = new Intent(this, SearchContactActivity.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                intent.putExtra("createClass", getIntent().getBooleanExtra("createClass", false));
                startActivity(intent);
            }
            break;
            case R.id.fl_right: {
//                Intent intent = new Intent(this, BodyGameActivity.class);
                Intent intent = new Intent(this, BodyGameNewActivity.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                intent.putExtra("type", 3);
                finish();
                startActivity(intent);
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
                .getContactsList(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<Contact>>>() {
                            @Override
                            public void success(ResponseData<List<Contact>> data, Response response) {
                                elv.onRefreshComplete();
                                if (data.getStatus() == 200) {
                                    onResult(data.getData());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                elv.onRefreshComplete();
                                super.failure(error);
                            }
                        });
    }
}
