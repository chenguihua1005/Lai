package com.softtek.lai.module.bodygame3.home.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.view.ChooseView;
import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.hyphenate.chat.EMClient;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.chat.ui.SeceltGroupSentActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactExpandableAdapter;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactMenuAdapter;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.conversation.view.GroupsActivity;
import com.softtek.lai.widgets.CustomGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_contact)
public class ContactFragment extends LazyBaseFragment implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    private static final String TAG = "ContactFragment";
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl)
    FrameLayout fl;
    @InjectView(R.id.et_search)
    TextView et_search;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_contant)
    PullToRefreshExpandableListView list_contant;
    @InjectView(R.id.choose)
    ChooseView chooseView;
    @InjectView(R.id.tv_perview)
    TextView tv_perview;

    //    @InjectView(R.id.lin_group_send)
//    LinearLayout lin_group_send;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.menu_gridview)
    CustomGridView menu_gridview;


    ContactMenuAdapter menuAdapter;
    //通讯录联系人列表
//    List<ChatContactInfoModel> list;
    List<ChatContactModel> list;

    LinearLayout ll_search;


    //通讯录联系人列表适配器
//    ChatContantAdapter adapter;
    private ContactExpandableAdapter adapter;

    public static Map<String, List<ChatContactModel>> datas = new HashMap<>();
    private List<String> groups = new ArrayList<>();


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        tv_title.setText("通讯录");

    }

    @Override
    protected void initDatas() {
        //顶上几个菜单列表
        menuAdapter = new ContactMenuAdapter(getActivity());
        menu_gridview.setAdapter(menuAdapter);


        menu_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                if (isLogin) {
                    if (0 == position) {
                        Intent intent = new Intent(getActivity(), GroupsActivity.class);
                        startActivity(intent);
                    } else if (2 == position) {
                        Intent intent = new Intent(getActivity(), SeceltGroupSentActivity.class);
                        intent.putExtra("list", (Serializable) list);
                        startActivity(intent);
                    }
                } else {
                    Util.toastMsg("会话异常，请稍后再试");
                }

            }
        });

        list = new ArrayList<ChatContactModel>();

        adapter = new ContactExpandableAdapter(getContext(), datas, groups);
//        adapter = new ChatContantAdapter(getContext(), list);
        list_contant.getRefreshableView().setAdapter(adapter);
        list_contant.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        View head= LayoutInflater.from(getContext()).inflate(R.layout.expandable_head_contact,null);
//        ll_search= (LinearLayout) head.findViewById(R.id.ll_search);
//        ll_search.setOnClickListener(this);

//        list_contant.setOnRefreshListener(this);

        chooseView.setChooseListener(new ChooseView.OnChooseListener() {

            @Override
            public void onDown() {
                tv_perview.setVisibility(View.VISIBLE);
            }

            @Override
            public void chooseView(String text, int index) {
                tv_perview.setText(text.trim());
                if (index == -10) {
                    list_contant.post(new Runnable() {
                        @Override
                        public void run() {
                            list_contant.getRefreshableView().smoothScrollByOffset(0);
                        }
                    });
                } else {
                    list_contant.getRefreshableView().setSelectedGroup(index);
                }
            }

            @Override
            public void onUp() {
                tv_perview.setVisibility(View.GONE);
            }
        });


        list_contant.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                ChatContactModel contact=datas.get(groups.get(i)).get(i1);
//                Intent intent=new Intent(ContactsActivity.this,InvitationSettingActivity.class);
//                intent.putExtra("classId",getIntent().getStringExtra("classId"));
//                intent.putExtra("inviterId",contact.getAccountId());
//                startActivity(intent);
//                return false;

                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                Log.i(TAG, "是否登錄 = " + isLogin);
                if (isLogin) {
                    ChatContactModel model = datas.get(groups.get(i)).get(i1);
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                    intent.putExtra(Constant.EXTRA_USER_ID, model.getHXAccountId().toLowerCase());
                    intent.putExtra("name", model.getUserName());
                    intent.putExtra("photo", path + model.getPhoto());
                    startActivity(intent);
                } else {
                    Util.toastMsg("会话功能开通中，请稍后再试");
                }
                return false;

            }
        });

    }

    @Override
    protected void lazyLoad() {
        Log.i("ContactFragment 加载数据");
        //加载数据
        loadingData();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (getContext() instanceof BodyGameActivity) {
            BodyGameActivity activity = (BodyGameActivity) getContext();
            activity.setAlpha(1);
        }
    }

    private void loadingData() {
        dialogShow("加载中");
        getDataAndUpdate();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.lin_group_send:
//                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
//                if (isLogin) {
//                    Intent intent = new Intent(getActivity(), SeceltGroupSentActivity.class);
//                    intent.putExtra("list", (Serializable) list);
//                    startActivity(intent);
//                } else {
//                    Util.toastMsg("会话异常，请稍后再试");
//                }
//
//                break;
        }
    }

    private void getDataAndUpdate() {
        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
        String token = UserInfoModel.getInstance().getToken();

        service.getEMChatContacts(token, 1, 99, new Callback<ResponseData<List<ChatContactModel>>>() {
            @Override
            public void success(ResponseData<List<ChatContactModel>> listResponseData, Response response) {
                int status = listResponseData.getStatus();
                dialogDissmiss();
                switch (status) {
                    case 200:
                        list.clear();
                        list = listResponseData.getData();
                        Log.i(TAG, "获取通讯录数据 = " + new Gson().toJson(list));
                        groups.clear();
                        datas.clear();
                        if (list != null) {
                            for (ChatContactModel contact : list) {
                                String groupName;
                                if (TextUtils.isEmpty(contact.getUserEn())) {
                                    groupName = "#";
                                } else {
                                    groupName = contact.getUserEn().substring(0, 1).toUpperCase();

                                }
                                if (!groups.contains(groupName)) {
                                    groups.add(groupName);
                                    List<ChatContactModel> invitatedContacts = new ArrayList<>();
                                    datas.put(groupName, invitatedContacts);
                                }
                                datas.get(groupName).add(contact);
                            }
                            if (groups.contains("#")) {
                                groups.remove(groups.indexOf("#"));
                                groups.add("#");
                            }
                        }
                        adapter.notifyDataSetChanged();
                        for (int i = 0; i < groups.size(); i++) {
                            list_contant.getRefreshableView().expandGroup(i);
                            chooseView.buildCharaset(groups.get(i));
                        }


                        break;
                    case 201:

                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
                if (list_contant != null) {
                    list_contant.onRefreshComplete();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (list_contant != null) {
                    list_contant.onRefreshComplete();
                }
                dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });

    }


//    @Override
//    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
//        getDataAndUpdate();
//    }
//
//    @Override
//    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
//        if (list_contant != null) {
//            list_contant.onRefreshComplete();
//        }
//    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        getDataAndUpdate();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
