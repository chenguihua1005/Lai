package com.softtek.lai.module.bodygame3.home.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.view.ChooseView;
import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.chat.ui.SeceltGroupSentActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactExpandableAdapter;
import com.softtek.lai.module.bodygame3.conversation.adapter.ContactMenuAdapter;
import com.softtek.lai.module.bodygame3.conversation.database.ContactDao;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.module.bodygame3.conversation.model.ContactListModel;
import com.softtek.lai.module.bodygame3.conversation.model.CountModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.conversation.view.ContactSearchActivity;
import com.softtek.lai.module.bodygame3.conversation.view.GroupsActivity;
import com.softtek.lai.module.bodygame3.conversation.view.NewFriendActivity;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
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


    private EMConnectionListener connectionListener;
    public AlertDialog.Builder builder = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                if (builder != null) {
                    return;
                }
                builder = new AlertDialog.Builder(getActivity())
                        .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                        .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder = null;
                                UserInfoModel.getInstance().loginOut();
                                LocalBroadcastManager.getInstance(LaiApplication.getInstance().getContext().get()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).setCancelable(false);
                Dialog dialog = builder.create();
                if (!getActivity().isFinishing()) {
                    if (dialog != null && !dialog.isShowing()) {
                        dialog.show();
                    }
                }
            }
        }

    };


    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        tv_title.setText("通讯录");
        tip_search.setVisibility(View.GONE);
        search_hint.setText("请输入姓名或手机号进行搜索");

        ll_search.setOnClickListener(this);

//        connectionListener = new EMConnectionListener() {
//            @Override
//            public void onDisconnected(final int error) {
//                if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                    SharedPreferenceService.getInstance().put("HXID", "-1");
//                    if (!getActivity().isFinishing()) {
//                        EMClient.getInstance().logout(true, new EMCallBack() {
//
//                            @Override
//                            public void onSuccess() {
//                                // TODO Auto-generated method stub
//                                handler.sendEmptyMessage(0);
//                            }
//
//                            @Override
//                            public void onProgress(int progress, String status) {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onError(int code, String message) {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onConnected() {
//                // 当连接到服务器之后，这里开始检查是否有没有发送的ack回执消息，
//            }
//        };
//        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    @Override
    protected void initDatas() {
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
                    } else if (1 == position) {//新朋友
                        Intent intent = new Intent(getActivity(), NewFriendActivity.class);//群聊列表
                        startActivityForResult(intent, REFRESH_UI);
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
                    Intent intent = new Intent(getActivity(), PersonDetailActivity.class);
                    intent.putExtra("isFriend", 1);//1： 好友
                    intent.putExtra("AccountId", model.getAccountId());
                    intent.putExtra("HXAccountId", model.getHXAccountId());
                    intent.putExtra("UserName", model.getUserName());
                    intent.putExtra("AFriendId", model.getAFriendId());
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
        getFriendPendingCount();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                Intent intent = new Intent(getContext(), ContactSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getFriendPendingCount() {
        try {
            ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
            service.getFriendPendingCount(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<CountModel>>() {
                @Override
                public void success(ResponseData responseData, Response response) {
                    if (200 == responseData.getStatus()) {
                        CountModel countModel = (CountModel) responseData.getData();
                        if (countModel != null) {
                            count = countModel.getCount();
                            if (count >= 0) {
                                menuAdapter.updateCount(count);
                            }
                        }
                    } else {
                        Util.toastMsg(responseData.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ZillaApi.dealNetError(error);
                    error.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

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

                        Log.i(TAG, "数据 = " + new Gson().toJson(model));
                        if (model != null) {
                            count = model.getCount();
                            menuAdapter.updateCount(count);

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
                                ContactDao dao = new ContactDao(getContext());
                                dao.clearContactTab();
                                dao.insert(list);

                            }
                            adapter.notifyDataSetChanged();

                            Log.i(TAG, "groups = " + new Gson().toJson(groups));
                            Log.i(TAG, "datas = " + new Gson().toJson(datas));
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

}
