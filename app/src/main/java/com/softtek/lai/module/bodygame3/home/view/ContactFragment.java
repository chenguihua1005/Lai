package com.softtek.lai.module.bodygame3.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.view.ChooseView;
import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.hyphenate.chat.EMClient;
import com.softtek.lai.R;
import com.softtek.lai.chat.ui.SeceltGroupSentActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactExpandableAdapter;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactMenuAdapter;
import com.softtek.lai.module.bodygame3.conversation.database.ContactTable;
import com.softtek.lai.module.bodygame3.conversation.database.ContactUtil;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactListModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.conversation.view.ContactSearchActivity;
import com.softtek.lai.module.bodygame3.conversation.view.GroupsActivity;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;
import com.softtek.lai.module.bodygame3.more.view.SearchFriendActivity;
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
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_contact)
public class ContactFragment extends LazyBaseFragment implements View.OnClickListener {
    private static final String TAG = "ContactFragment";
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_contant)
    PullToRefreshExpandableListView list_contant;
    @InjectView(R.id.choose)
    ChooseView chooseView;
    @InjectView(R.id.tv_perview)
    TextView tv_perview;

    @InjectView(R.id.tip_search)
    TextView tip_search;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.menu_gridview)
    CustomGridView menu_gridview;

    @InjectView(R.id.ll_search)
    LinearLayout ll_search;

    @InjectView(R.id.search_hint)
    TextView search_hint;


    ContactMenuAdapter menuAdapter;
    //通讯录联系人列表
    List<ChatContactModel> list;


    //通讯录联系人列表适配器
    private ContactExpandableAdapter adapter;

    public static Map<String, List<ChatContactModel>> datas = new HashMap<>();
    private List<String> groups = new ArrayList<>();

    public static final int REFRESH_UI = 0x001;
    public int count = 0;

    public static final String UPDATE_CONTACT_MSG = "MESSAGE_UPDATE_CONTACT";//用于删除好友后，更新联系人列表
    public MessageUpdateReceiver messageUpdateReceiver;


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        fl_right.setOnClickListener(this);
        tv_right.setText("添加");
        tv_title.setText("通讯录");
        tip_search.setVisibility(View.GONE);
        search_hint.setText("请输入姓名或手机号进行搜索");

        ll_search.setOnClickListener(this);

    }

    public void registerMessageReceiver() {
        messageUpdateReceiver = new MessageUpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ContactFragment.UPDATE_CONTACT_MSG);
        getActivity().registerReceiver(messageUpdateReceiver, filter);
    }

    @Override
    protected void initDatas() {
        registerMessageReceiver();
        //顶上几个菜单列表
        menuAdapter = new ContactMenuAdapter(getActivity(), count);
        menu_gridview.setAdapter(menuAdapter);
        menu_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                if (isLogin) {
                    if (0 == position) {
                        Intent intent = new Intent(getActivity(), GroupsActivity.class);//群聊列表
                        startActivity(intent);
                    }
//                    else if (1 == position) {//新朋友
//                        Intent intent = new Intent(getActivity(), NewFriendActivity.class);//群聊列表
//                        startActivityForResult(intent, REFRESH_UI);
//                    } else

                    if (1 == position) {
                        Intent intent = new Intent(getActivity(), SeceltGroupSentActivity.class);
                        intent.putExtra("list", (Serializable) list);
                        startActivity(intent);
                    }
                } else {
                    Util.toastMsg("会话异常，请稍后再试");
                }

            }
        });

        list = new ArrayList<>();

        adapter = new ContactExpandableAdapter(getContext(), datas, groups);
        list_contant.getRefreshableView().setAdapter(adapter);
        list_contant.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        list_contant.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                Log.i(TAG, "刷新......");
                getDataAndUpdate();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });

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
                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                Log.i(TAG, "是否登錄 = " + isLogin);
                if (isLogin) {
                    ChatContactModel model = datas.get(groups.get(i)).get(i1);
                    Intent intent = new Intent(getActivity(), PersonDetailActivity2.class);
                    intent.putExtra("isFriend", 1);//1： 好友
                    intent.putExtra("AccountId", model.getAccountId());
                    intent.putExtra("HXAccountId", model.getHXAccountId());
                    intent.putExtra("UserName", model.getUserName());
                    intent.putExtra("AFriendId", model.getAFriendId());
                    intent.putExtra("comeFrom", Constants.FROM_CONTACT);
                    startActivity(intent);
                } else {
                    Util.toastMsg("网络异常，请重新登录后再试");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH_UI) {
            loadingData();
        }
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
    public void onResume() {
        super.onResume();
//        getFriendPendingCount();
        //查看学员是否有加入环信群的消息
//        getMsgHxInviteToGroup();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                Intent intent = new Intent(getContext(), ContactSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_right:
                startActivity(new Intent(getContext(), SearchFriendActivity.class));
                break;
        }
    }

//    private void getFriendPendingCount() {
//        try {
//            ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
//            service.getFriendPendingCount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<CountModel>>() {
//                @Override
//                public void success(ResponseData responseData, Response response) {
//                    if (200 == responseData.getStatus()) {
//                        CountModel countModel = (CountModel) responseData.getData();
//                        if (countModel != null) {
//                            count = countModel.getCount();
//                            if (count >= 0) {
//                                menuAdapter.updateCount(count);
//                            }
//                        }
//                    } else {
//                        Util.toastMsg(responseData.getMsg());
//                    }
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    ZillaApi.dealNetError(error);
//                    error.printStackTrace();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void getDataAndUpdate() {
        try {
            ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
            String token = UserInfoModel.getInstance().getToken();
            service.getEMChatContacts(token, 1, 99, new Callback<ResponseData<ContactListModel>>() {
                @Override
                public void success(ResponseData<ContactListModel> contactListModelResponseData, Response response) {
                    int status = contactListModelResponseData.getStatus();
                    dialogDissmiss();
                    if (200 == status) {
                        ContactListModel model = contactListModelResponseData.getData();

                        if (model != null) {
//                            count = model.getCount();
//                            menuAdapter.updateCount(count);

                            list.clear();
                            list.addAll(model.getContacts());
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

                                //存入数据库,之前先清除之前的数据
//                                ContactDao dao = new ContactDao(getContext());
//                                dao.clearContactTab();
//                                dao.insert(list);
                                Log.i(TAG, "判断表明是否存在......");
                                if (ContactUtil.getInstance().tableIsExist(ContactTable.TABLE_NAME)) {
                                    Log.i(TAG, "存在。。。。。");
                                    ContactUtil.getInstance().clearContactTab();
                                    ContactUtil.getInstance().insert(list);
                                } else {
                                    Log.i(TAG, "不存在。。。。。");
                                }

                            }
                            adapter.notifyDataSetChanged();

                            chooseView.clear();//清除之前的记录
                            for (int i = 0; i < groups.size(); i++) {
                                list_contant.getRefreshableView().expandGroup(i);
                                chooseView.buildCharaset(groups.get(i));
                            }
                        }
                    } else {
                        Util.toastMsg(contactListModelResponseData.getMsg());
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
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(messageUpdateReceiver);
    }

    //收到广播后刷新页面
    public class MessageUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ContactFragment.UPDATE_CONTACT_MSG.equals(intent.getAction())) {
                getDataAndUpdate();
            }

        }
    }




}
