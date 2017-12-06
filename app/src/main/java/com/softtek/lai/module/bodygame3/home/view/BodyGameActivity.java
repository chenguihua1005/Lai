package com.softtek.lai.module.bodygame3.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.conversation.database.ClassGroupUtil;
import com.softtek.lai.module.bodygame3.conversation.database.GroupTable;
import com.softtek.lai.module.bodygame3.conversation.model.ContactClassModel;
import com.softtek.lai.module.bodygame3.conversation.model.HxInviteToGroupModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.home.adapter.MainPageAdapter;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.widgets.SimpleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_bodygame3)
public class BodyGameActivity extends BaseActivity implements View.OnClickListener {

    List<ContactClassModel> classModels;

    @InjectView(R.id.btn_bodygame)
    SimpleButton btn_bodygame;
//    @InjectView(R.id.btn_chat)
//    SimpleButton btn_chat;
//    @InjectView(R.id.btn_contact)
//    SimpleButton btn_contact;
    @InjectView(R.id.btn_activity)
    SimpleButton btn_activity;
    @InjectView(R.id.btn_more)
    SimpleButton btn_more;
    @InjectView(R.id.nsvp)
    ViewPager content;
    @InjectView(R.id.tv_unread_num)
    TextView tv_umread;

    private List<Fragment> fragments;
    private FragmentPagerAdapter adapter;

    private int current = 0;
    private boolean isClick = false;


    @Override
    protected void initViews() {
        MobclickAgent.openActivityDurationTrack(false);
        btn_bodygame.setOnClickListener(this);
//        btn_chat.setOnClickListener(this);
//        btn_contact.setOnClickListener(this);
        btn_activity.setOnClickListener(this);
        btn_more.setOnClickListener(this);

        fragments = new ArrayList<>();
        fragments.add(new BodyGameFragment());
//        fragments.add(new ChatFragment());
//        fragments.add(new ContactFragment());
        fragments.add(new ActivityFragment());
        fragments.add(new MoreFragment());
        content.setOffscreenPageLimit(3);
        adapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
        content.setAdapter(adapter);

        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isClick) {
                    setChildProgress(position, 1 - positionOffset);
                    setChildProgress(position + 1, positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面切换了
                isClick = false;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                current = state;
            }
        });

        //设置第一个fragment
        int type = getIntent().getIntExtra("type", 0);
        current = type;
        restoreState();
        switch (type) {
            case 0:
                btn_bodygame.setProgress(1);
                break;
//            case 1:
//                btn_chat.setProgress(1);
//                break;
//            case 2:
//                btn_contact.setProgress(1);
//                break;
            case 1:
                btn_activity.setProgress(1);
                break;
            case 2:
                btn_more.setProgress(1);
                break;

        }
        content.setCurrentItem(current, false);


        UserModel model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        ChatUserModel chatUserModel = new ChatUserModel();
        chatUserModel.setUserName(model.getNickname());
        chatUserModel.setUserPhone(path + model.getPhoto());
        chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
        ChatUserInfoModel.getInstance().setUser(chatUserModel);

        //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
//                    for (EMGroup group : grouplist) {
//                        String groupId = group.getGroupId();
////                        EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
////                        Log.i(TAG, " 解散成功！" + groupId);
//                    }
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
////                    Log.i(TAG, " 解散失败！");
//                }
//
//            }
//        }).start();


        //检查是否有进群邀请
//        getMsgHxInviteToGroup();


    }


    private void setChildProgress(int position, float progress) {
        switch (position) {
            case 0:
                btn_bodygame.setProgress(progress);
                break;
//            case 1:
//                btn_chat.setProgress(progress);
//                break;
//            case 2:
//                btn_contact.setProgress(progress);
//                break;
            case 1:
                btn_activity.setProgress(progress);
                break;
            case 2:
                btn_more.setProgress(progress);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type", 0);
        current = type;
        Log.i("消息中心发来通知");
        if (content != null) {
            restoreState();
            switch (type) {
                case 0:
                    btn_bodygame.setProgress(1);
                    break;
//                case 1:
//                    btn_chat.setProgress(1);
//                    break;
//                case 2:
//                    btn_contact.setProgress(1);
//                    break;
                case 1:
                    btn_activity.setProgress(1);
                    break;
                case 2:
                    btn_more.setProgress(1);
                    break;

            }
            content.setCurrentItem(current, false);
        }
    }


    @Override
    protected void initDatas() {
        registerMessageReceiver();

        //此处获取群组信息，并缓存在本地
//        getContactGroups();

    }


    //伙计班级群信息，并存入列表
//    private void getContactGroups() {
//        try {
//            ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
//            service.GetClassListByAccountId(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId() + "", new Callback<ResponseData<List<ContactClassModel>>>() {
//                @Override
//                public void success(ResponseData<List<ContactClassModel>> listResponseData, Response response) {
//                    int status = listResponseData.getStatus();
//                    if (200 == status) {
//                        classModels = listResponseData.getData();
//                        if (classModels != null) {
//
//                            //存入数据库
//                            com.github.snowdream.android.util.Log.i(TAG, "判断表明是否存在......");
//                            if (ClassGroupUtil.getInstance().tableIsExist(GroupTable.TABLE_NAME)) {
//                                com.github.snowdream.android.util.Log.i(TAG, "存在。。。。。");
//                                ClassGroupUtil.getInstance().insert(classModels);
//                            } else {
//                                com.github.snowdream.android.util.Log.i(TAG, "不存在。。。。。");
//                            }
//                        }
//
//                    } else {
//                        Util.toastMsg(listResponseData.getMsg());
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
//    }

    @Override
    public void onResume() {
        super.onResume();
//        if (EMClient.getInstance().isLoggedInBefore()) {
//            int unreadNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
//            Log.i("onResume 获取还信未读消息=" + unreadNum);
//            updateMessage(unreadNum);
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        //filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_CHAT_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getClass().getCanonicalName() + "接收到还信的消息");
            if (Constants.MESSAGE_CHAT_ACTION.equals(intent.getAction())) {
                int unreadNum = intent.getIntExtra("count", 0);
                Log.i(TAG, "收到未读消息数= " + unreadNum);
                //更新小红点
                updateMessage(unreadNum);
            }
        }
    }

    @Override
    public void onClick(View v) {
        restoreState();
        switch (v.getId()) {
            case R.id.btn_bodygame:
                btn_bodygame.setProgress(1);
                current = 0;
                break;
//            case btn_chat:
//                btn_chat.setProgress(1);
//                current = 1;
//                break;
//            case btn_contact:
//                btn_contact.setProgress(1);
//                current = 2;
//                break;
            case R.id.btn_activity:
                btn_activity.setProgress(1);
                current = 1;
                break;
            case R.id.btn_more:
                btn_more.setProgress(1);
                current = 2;
                break;
        }
        content.setCurrentItem(current, false);

    }

    private void restoreState() {
        btn_bodygame.setProgress(0);
//        btn_chat.setProgress(0);
//        btn_contact.setProgress(0);
        btn_activity.setProgress(0);
        btn_more.setProgress(0);

    }

    public void setAlpha(float alpha) {
        tintManager.setStatusBarAlpha(alpha);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
    }

    public void updateMessage(int num) {
        //显示
        if (num <= 0) {
            tv_umread.setVisibility(View.GONE);
        } else {
            String read = num >= 100 ? "99+" : num + "";
            tv_umread.setText(read);
            tv_umread.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, HomeActviity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    private List<HxInviteToGroupModel> needGroupList = new ArrayList<HxInviteToGroupModel>();

    //    查看学员是否有加入环信群的消息
//    private void getMsgHxInviteToGroup() {
//        Log.i(TAG, " 查看学员是否有加入环信群的消息......");
//        needGroupList.clear();
//        final ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
//        service.getMsgHxInviteToGroup(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new Callback<ResponseData<List<HxInviteToGroupModel>>>() {
//            @Override
//            public void success(ResponseData<List<HxInviteToGroupModel>> listResponseData, Response response) {
//                int status = listResponseData.getStatus();
//                if (200 == status) {
//                    needGroupList = listResponseData.getData();
//                    if (needGroupList != null && needGroupList.size() > 0) {
//                        for (int i = 0; i < needGroupList.size(); i++) {
//                            final HxInviteToGroupModel model = needGroupList.get(i);
//                            if (model != null) {
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
////                                        EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(show.getClassHxGroupId()), String.valueOf(show.getClassMasterHxId()));
//                                            EMClient.getInstance().groupManager().acceptInvitation(String.valueOf(model.getClassGroupHxId()), String.valueOf(model.getCoachHxId()));
//
//                                            //环迅同意进群之后，告知后台
//                                            service.completeJoinHx(UserInfoModel.getInstance().getToken(), model.getClassId(), model.getMessageId(), new Callback<ResponseData>() {
//                                                @Override
//                                                public void success(ResponseData responseData, Response response) {
//                                                    if (200 == responseData.getStatus()) {
//
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void failure(RetrofitError error) {
//                                                    error.printStackTrace();
//                                                    ZillaApi.dealNetError(error);
//                                                }
//                                            });
//
//
//                                        } catch (HyphenateException e) {
//                                            e.printStackTrace();
////                                            runOnUiThread(new Runnable() {
////                                                @Override
////                                                public void run() {
////                                                    dialogDissmiss();
////                                                    Util.toastMsg("环信异常");
////                                                }
////                                            });
//                                        }
//
//                                    }
//                                }).start();
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//
//
//    }


}
