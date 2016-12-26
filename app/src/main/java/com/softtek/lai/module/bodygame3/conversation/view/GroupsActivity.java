package com.softtek.lai.module.bodygame3.conversation.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.adapter.GroupAdapter;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 2016/11/28.
 */

@InjectLayout(R.layout.activity_groups)
public class GroupsActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "GroupsActivity";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.group_list)
    ListView group_list;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static GroupsActivity instance;
    private List<ContactClassModel> classModels;

    private GroupAdapter groupAdapter;
    private final static int LOGIN_CONFLICT = 0x0088;
    public AlertDialog.Builder builder = null;


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    Toast.makeText(GroupsActivity.this, R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
                    break;
                case LOGIN_CONFLICT:
                    if (builder != null) {
                        return;
                    }
                    builder = new AlertDialog.Builder(GroupsActivity.this)
                            .setTitle("温馨提示").setMessage("您的帐号已经在其他设备登录，请重新登录后再试。")
                            .setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    builder = null;
                                    UserInfoModel.getInstance().loginOut();
                                    LocalBroadcastManager.getInstance(LaiApplication.getInstance().getContext().get()).sendBroadcast(new Intent(StepService.STEP_CLOSE_SELF));
                                    Intent intent = new Intent(GroupsActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).setCancelable(false);
                    Dialog dialog = builder.create();
                    if (!GroupsActivity.this.isFinishing()) {
                        if (dialog != null && !dialog.isShowing()) {
                            dialog.show();
                        }
                    }

                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void initViews() {
        if (DisplayUtil.getSDKInt() > 18) {
            int status = DisplayUtil.getStatusHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = status;
            toolbar.setLayoutParams(params);
        }
        ll_left.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        tv_title.setText("群聊");
//        fl_right.setVisibility(View.VISIBLE);
//        iv_email.setBackground(ContextCompat.getDrawable(this, R.drawable.groupicon));
    }

    @Override
    protected void initDatas() {
        classModels = new ArrayList<ContactClassModel>();
        groupAdapter = new GroupAdapter(this, classModels);
        group_list.setAdapter(groupAdapter);

        getContactGroups();
        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
                // it is group chat
                intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                intent.putExtra("userId", groupAdapter.getItem(i).getHXGroupId());
                intent.putExtra("name", groupAdapter.getItem(i).getClassName());//组名

                intent.putExtra("classId", groupAdapter.getItem(i).getClassId());

                intent.putExtra("classModel", groupAdapter.getItem(i));

//                intent.putExtra("userId", groupAdapter.getItem(i).getGroupId());
//                intent.putExtra("name", groupAdapter.getItem(i).getGroupName());
                startActivityForResult(intent, 0);

            }
        });


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        //pull down to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                            handler.sendEmptyMessage(0);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        refresh();
        super.onResume();
    }

    private void refresh() {
        getContactGroups();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    public void back(View view) {
        finish();
    }


    /**
     * 获取群聊列表信息（即班级列表）
     */
    private void getContactGroups() {
        try {
            ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
            service.GetClassListByAccountId(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId() + "", new Callback<ResponseData<List<ContactClassModel>>>() {
                @Override
                public void success(ResponseData<List<ContactClassModel>> listResponseData, Response response) {
                    int status = listResponseData.getStatus();
                    if (200 == status) {
                        classModels = listResponseData.getData();
                        Log.i(TAG, "classModels = " + new Gson().toJson(classModels));
                        if (classModels != null) {
                            groupAdapter.updateData(classModels);
                        }
                        //                for (int i = 0; i < classModels.size(); i++) {
                        //                    ContactClassModel classModel = classModels.get(i);
                        //                    String HXGroupId = classModel.getHXGroupId();
                        //
                        //                    if (!TextUtils.isEmpty(HXGroupId)) {
                        //                        try {
                        //                            EMClient.getInstance().groupManager().destroyGroup(HXGroupId);
                        //                            Util.toastMsg("解散成功！");
                        //
                        //                        } catch (HyphenateException e) {
                        //                            e.printStackTrace();
                        //                            Util.toastMsg("解散失败！");
                        //                        }
                        //                    }
                        //
                        //                }

                    } else {
                        Util.toastMsg(listResponseData.getMsg());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

}
