/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.domain.ChatUserInfoModel;
import com.easemob.easeui.domain.ChatUserModel;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity;
import com.softtek.lai.module.bodygame2pcnoclass.view.BodyGamePCNoClassActivity;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRActivity;
import com.softtek.lai.module.bodygame2vr.BodyGameVRActivity;
import com.softtek.lai.module.bodygamest.model.HasClass;
import com.softtek.lai.module.bodygamest.net.StudentService;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.group.view.GroupMainActivity;
import com.softtek.lai.module.group.view.JoinGroupActivity;
import com.softtek.lai.module.home.adapter.FragementAdapter;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.eventModel.HomeEvent;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.login.model.EMChatAccountModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message.net.MessageService;
import com.softtek.lai.module.message.view.MessageActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.RollHeaderView;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/15/2016.
 * 首页
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {

    @InjectView(R.id.rhv_adv)
    RollHeaderView rhv_adv;

    @InjectView(R.id.gv_model)
    CustomGridView gv_model;

    @InjectView(R.id.pull)
    SwipeRefreshLayout pull;

    @InjectView(R.id.page)
    ViewPager page;

    @InjectView(R.id.appbar)
    AppBarLayout appBar;

    @InjectView(R.id.tabs)
    TabLayout tab;

    @InjectView(R.id.tv_title)
    TextView tv_title;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.img_red)
    ImageView img_red;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;


    private IHomeInfoPresenter homeInfoPresenter;
    private ILoginPresenter loginPresenter;
    private StudentImpl studentImpl;

    private List<String> advList = new ArrayList<>();
    private List<HomeInfoModel> records = new ArrayList<>();
    private List<HomeInfoModel> products = new ArrayList<>();
    private List<HomeInfoModel> sales = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    FragementAdapter fragementAdapter;
    private MessageReceiver mMessageReceiver;
    UserModel model;
    private ProgressDialog progressDialog;
    public static Timer timer;

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.INVISIBLE);
        String userroles = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.VR).equals(userroles)) {
            fl_right.setVisibility(View.INVISIBLE);
        } else {
            fl_right.setVisibility(View.VISIBLE);
        }
        iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.email));
        fl_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        ActivityRecordFragment recordFragment = new ActivityRecordFragment();
        ProductInfoFragment productInfoFragment = new ProductInfoFragment();
        SaleInfoFragment saleInfoFragment = new SaleInfoFragment();
        fragments.add(recordFragment);
        fragments.add(productInfoFragment);
        fragments.add(saleInfoFragment);
        fragementAdapter = new FragementAdapter(getFragmentManager(), fragments);
        page.setAdapter(fragementAdapter);
        page.setOffscreenPageLimit(3);
        //设置tabLayout和viewpage关联
        tab.setupWithViewPager(page);
        tab.setTabMode(TabLayout.MODE_FIXED);
        appBar.addOnOffsetChangedListener(this);
        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(), 100));
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        pull.setOnRefreshListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");

    }

    private ModelAdapter modelAdapter;

    @Override
    protected void initDatas() {
        tv_title.setText("莱聚+");

        //载入缓存数据
        homeInfoPresenter.loadCacheData();
        modelAdapter = new ModelAdapter(getContext());
        gv_model.setAdapter(modelAdapter);
        gv_model.setOnItemClickListener(this);
        //第一次加载自动刷新
        pull.post(new Runnable() {
            @Override
            public void run() {
                pull.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Subscribe
    public void onEvent(EMChatAccountModel model) {
//        if (model != null) {
//            String state = model.getState();
//            if ("0".equals(state)) {
//                Util.toastMsg("您的会话权限开通中，请稍候再试");
//            } else if ("-1".equals(state)) {
//                Util.toastMsg("开通会话功能需要身份认证");
//            } else {
//                Util.toastMsg("会话异常，请稍候再试");
//                UserModel userModel = UserInfoModel.getInstance().getUser();
//                userModel.setHasEmchat("1");
//                userModel.setHXAccountId(model.getHXAccountId());
//                UserInfoModel.getInstance().saveUserCache(userModel);
//            }
//        } else {
//            Util.toastMsg("会话异常，请稍候再试");
//        }
    }

    @Subscribe
    public void onEventRefresh(HomeEvent event) {
        advList.clear();
        records.clear();
        products.clear();
        sales.clear();
        for (HomeInfoModel info : event.getInfos()) {
            switch (info.getImg_Type()) {
                case "0":
                    advList.add(info.getImg_Addr());
                    break;
                case "1":
                    records.add(info);
                    break;
                case "2":
                    products.add(info);
                    break;
                case "6":
                    sales.add(info);
                    break;
            }
        }
        rhv_adv.setImgUrlData(advList);
        ((ActivityRecordFragment) fragments.get(0)).updateInfo(records);
        ProductInfoFragment productInfoFragment = ((ProductInfoFragment) fragments.get(1));
        productInfoFragment.updateInfo(products);
        SaleInfoFragment saleInfoFragment = ((SaleInfoFragment) fragments.get(2));
        saleInfoFragment.updateInfo(sales);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        homeInfoPresenter = new HomeInfoImpl(getContext());
        loginPresenter = new LoginPresenterImpl(getContext());
        studentImpl = new StudentImpl(getContext());
        registerMessageReceiver();
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mMessageReceiver);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }

        String userrole = model.getUserrole();
        if (String.valueOf(Constants.VR).equals(userrole)) {

        } else {
            ZillaApi.NormalRestAdapter.create(MessageService.class).getMessageRead(UserInfoModel.getInstance().getToken(),
                    new Callback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                            int status = responseData.getStatus();
                            try {
                                switch (status) {
                                    case 200:
                                        iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.has_email));
                                        break;
                                    default:
                                        iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.email));
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                        }
                    });
            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
            ChatUserModel chatUserModel = new ChatUserModel();
            chatUserModel.setUserName(model.getNickname());
            chatUserModel.setUserPhone(path + model.getPhoto());
            chatUserModel.setUserId(StringUtils.isEmpty(model.getHXAccountId()) ? "" : model.getHXAccountId().toLowerCase());
            ChatUserInfoModel.getInstance().setUser(chatUserModel);
            String hasEmchat = model.getHasEmchat();
            System.out.println("hasEmchat:" + hasEmchat);
            if ("1".equals(hasEmchat)) {
                timer = new Timer();
                TimerTask task = new TimerTask() {

                    @Override
                    public void run() {
                        // 需要做的事:发送消息
                        final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
                        if (hxid.equals(model.getHXAccountId())) {
                            System.out.println("111111111");
                            if (timer != null) {
                                timer.cancel();
                            }
                        } else {
                            System.out.println("2222222222222");
                            if ("-1".equals(hxid)) {
                                System.out.println("333333333333");
                                loginChat(progressDialog, model.getHXAccountId());
                            } else {
                                System.out.println("4444444444");
                                new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                HXLoginOut();
                                            }
                                        }
                                ).start();
                            }
                        }
                    }
                };
                timer.schedule(task, 0, 10000);
            }
        }


    }

    private void HXLoginOut() {
        EMChatManager.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                SharedPreferenceService.getInstance().put("HXID", "-1");
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                HXLoginOut();
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //float num = Math.abs(1f * Math.abs(verticalOffset) / 1000);
        //toolbar.setAlpha(num);
        /*if(verticalOffset<0){
            toolbar.setVisibility(View.VISIBLE);
        }

        if(num<=0){
            toolbar.setVisibility(View.GONE);
        }*/
        Fragment fragment = fragments.get(tab.getSelectedTabPosition());
        if (fragment != null) {
            if (fragment instanceof ActivityRecordFragment) {
                ActivityRecordFragment recordFragment = (ActivityRecordFragment) fragment;
                if (recordFragment.isRecycleFirst() && verticalOffset >= 0) {
                    pull.setEnabled(true);
                } else {
                    pull.setEnabled(false);
                }
            } else if (fragment instanceof ProductInfoFragment) {
                ProductInfoFragment recordFragment = (ProductInfoFragment) fragment;
                if (recordFragment.isRecycleFirst() && verticalOffset >= 0) {
                    pull.setEnabled(true);
                } else {
                    pull.setEnabled(false);
                }
            } else if (fragment instanceof SaleInfoFragment) {
                SaleInfoFragment recordFragment = (SaleInfoFragment) fragment;
                if (recordFragment.isRecycleFirst() && verticalOffset >= 0) {
                    pull.setEnabled(true);
                } else {
                    pull.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        homeInfoPresenter.getHomeInfoData(pull);
    }

    private void loginChat(final ProgressDialog progressDialog, final String account) {
        EMChatManager.getInstance().login(account.toLowerCase(), "HBL_SOFTTEK#321", new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                SharedPreferenceService.getInstance().put("HXID", account.toLowerCase());
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                ChatUserModel chatUserModel = new ChatUserModel();
                chatUserModel.setUserName(model.getNickname());
                chatUserModel.setUserPhone(path + model.getPhoto());
                chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                ChatUserInfoModel.getInstance().setUser(chatUserModel);
                EMChatManager.getInstance().updateCurrentUserNick(model.getNickname());
                EMChatManager.getInstance().loadAllConversations();

                if (timer != null) {
                    timer.cancel();
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 功能模块按钮
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        int role = Integer.parseInt(userInfoModel.getUser().getUserrole());
        ////判断当前用户是否拥有此按钮权限
        if (userInfoModel.hasPower(position)) {
            //如果有则判断更具具体角色进入相应的页面
            switch (position) {
                case Constants.BODY_GAME:
                    intoBodyGamePage(role);
                    break;
                case Constants.LAI_YUNDONG:
                    String isJoin = userInfoModel.getUser().getIsJoin();
                    if (StringUtils.isEmpty(isJoin) || "0".equals(isJoin)) {
                        startActivity(new Intent(getContext(), JoinGroupActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), GroupMainActivity.class));
                    }
                    break;
                case Constants.CHAT:
//                    boolean isLogin = EMChat.getInstance().isLoggedIn();
//                    if (isLogin) {
//                        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
//                        ChatUserModel chatUserModel = new ChatUserModel();
//                        chatUserModel.setUserName(model.getNickname());
//                        chatUserModel.setUserPhone(path + model.getPhoto());
//                        chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
//                        ChatUserInfoModel.getInstance().setUser(chatUserModel);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                EMChatManager.getInstance().updateCurrentUserNick(model.getNickname());
//                                EMChatManager.getInstance().loadAllConversations();
//                            }
//                        }).start();
//                        // 进入主页面
//                        Intent intent = new Intent(getActivity(), ConversationListActivity.class);
//                        startActivity(intent);
//                    } else {
//                        loginPresenter.getEMChatAccount(progressDialog);
//                    }
                case Constants.LAI_EXCLE:
                case Constants.LAI_SHOP:
                    new AlertDialog.Builder(getContext()).setMessage("功能开发中敬请期待").create().show();
                    break;
            }

        } else {
            //如果本身没有该按钮权限则根据不同身份提示用户，进行下一步操作
            AlertDialog.Builder information_dialog = null;
            switch (Integer.parseInt(userInfoModel.getUser().getUserrole())) {
                case Constants.VR:
                    //游客若没有此功能，可能是未登录，提示请先登录
                    information_dialog = new AlertDialog.Builder(getContext());
                    information_dialog.setTitle("您当前是游客身份，请登录后再试").setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent login = new Intent(getContext(), LoginActivity.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(login);
                        }
                    }).setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                    break;
                case Constants.NC:
                case Constants.INC:
                case Constants.PC:
                    if (position == Constants.CHAT) {
                        information_dialog = new AlertDialog.Builder(getContext());
                        information_dialog.setTitle("开通会话功能需要身份认证").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    } else {
                        information_dialog = new AlertDialog.Builder(getContext());
                        information_dialog.setTitle("请先进行身份认证后再试").setPositiveButton("认证", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转到身份认证界面
                                startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                            }
                        }).setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    }
                    break;
                case Constants.SR:
                    break;
                case Constants.SP:
                    break;
            }
        }

    }

    /**
     * 根据角色进入相应的体管赛页面
     *
     * @param role
     * @return
     */
    private void intoBodyGamePage(int role) {
        //受邀未认证成功就是普通用户，认证成功就是高级用户
        AlertDialog.Builder information_dialog = null;
        if (role == Constants.VR) {
            //提示用户让他注册或者直接进入2个功能的踢馆赛模块
            information_dialog = new AlertDialog.Builder(getContext());
            information_dialog.setTitle("您当前是游客身份，请登录后再试").setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent login = new Intent(getContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(login);
                }
            }).setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getContext(), BodyGameVRActivity.class));
                    //startActivity(new Intent(getContext(), BodygameYkActivity.class));
                }
            }).create().show();
        } else if (role == Constants.NC) {
            //提示用户让他进行身份认证否则进入2个功能的踢馆赛模块
            information_dialog = new AlertDialog.Builder(getContext());
            information_dialog.setTitle("您还没有认证身份，如果您想更多了解莱聚+，请先认证身份").setPositiveButton("先去认证", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getContext(), ValidateCertificationActivity.class));
                }
            }).setNegativeButton("先进去逛逛", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getContext(), BodyGameVRActivity.class));
                    //startActivity(new Intent(getContext(), BodygameYkActivity.class));
                }
            }).create().show();
        } else if (role == Constants.INC) {
            //提示用户让他进行身份认证否则进入2个功能的踢馆赛模块
            studentImpl.pcIsJoinClass(UserInfoModel.getInstance().getUser().getUserid(), new RequestCallback<ResponseData<HasClass>>() {
                @Override
                public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                    Log.i(hasClassResponseData.toString());
                    if (hasClassResponseData.getStatus() == 200) {
                        if ("1".equals(hasClassResponseData.getData().getIsHave())) {
                            startActivity(new Intent(getContext(), BodyGamePCActivity.class));
                            //startActivity(new Intent(getContext(), com.softtek.lai.module.bodygamest.view.BodyGamePCActivity.class));
                        } else {
                            startActivity(new Intent(getContext(), BodyGameVRActivity.class));
                            //startActivity(new Intent(getContext(), BodygameYkActivity.class));
                        }
                    } else {
                        Util.toastMsg(hasClassResponseData.getMsg());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                }
            });
        } else if (role == Constants.PC) {
            //直接进入踢馆赛学员版
            progressDialog.show();
            ZillaApi.NormalRestAdapter.create(StudentService.class).hasClass2(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<HasClass>>() {
                @Override
                public void success(ResponseData<HasClass> hasClassResponseData, Response response) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    HasClass hasClass = hasClassResponseData.getData();
                    if ("0".equals(hasClass.getIsHave()) || "4".equals(hasClass.getIsHave())
                            || "1".equals(hasClass.getIsHave()) || "5".equals(hasClass.getIsHave())) {
                        //从未加入过班级或者旧班级已经结束还没有加入新班级
                        Intent noClass = new Intent(getContext(), BodyGamePCNoClassActivity.class);
                        noClass.putExtra("class_status", Integer.parseInt(hasClass.getIsHave()));
                        startActivity(noClass);
                    } else if ("2".equals(hasClass.getIsHave()) || "3".equals(hasClass.getIsHave())) {
                        //只要班级进行中就可以
                        startActivity(new Intent(getContext(), BodyGamePCActivity.class));
                    }
                }
            });
            //startActivity(new Intent(getContext(), com.softtek.lai.module.bodygamest.view.BodyGamePCActivity.class));
        } else if (role == Constants.SR) {
            //进入踢馆赛助教版
            startActivity(new Intent(getContext(), BodyGameSRActivity.class));
            //startActivity(new Intent(getContext(), BodygameSRActivity.class));
        } else if (role == Constants.SP) {
            //进入踢馆赛顾问版
            startActivity(new Intent(getContext(), BodyGameSPActivity.class));

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_right:
            case R.id.iv_email:
                String userroles = UserInfoModel.getInstance().getUser().getUserrole();
                if (String.valueOf(Constants.VR).equals(userroles)) {
                    //提示用户让他登录或者直接进入2个功能的踢馆赛模块
                    AlertDialog.Builder information_dialog = null;
                    information_dialog = new AlertDialog.Builder(getContext());
                    information_dialog.setTitle("您当前是游客身份，请登录后再试").setPositiveButton("现在登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent login = new Intent(getContext(), LoginActivity.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(login);
                        }
                    }).setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                } else {
                    startActivity(new Intent(getContext(), MessageActivity.class));
                }
                break;
        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constants.MESSAGE_RECEIVED_ACTION);
        getContext().registerReceiver(mMessageReceiver, filter);

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.has_email));
            }
        }
    }

}
