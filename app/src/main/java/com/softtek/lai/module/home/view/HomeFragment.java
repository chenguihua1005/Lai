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
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.ChatUserInfoModel;
import com.hyphenate.easeui.domain.ChatUserModel;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.home.view.BodyGameActivity;
import com.softtek.lai.module.group.view.JoinGroupActivity;
import com.softtek.lai.module.home.adapter.FragementAdapter;
import com.softtek.lai.module.home.adapter.ModelAdapter;
import com.softtek.lai.module.home.eventModel.HomeEvent;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.model.UnReadMsg;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.sport2.view.LaiSportActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.RollHeaderView;
import com.umeng.analytics.MobclickAgent;

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


/**
 * Created by jerry.guan on 3/15/2016.
 * 首页
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends LazyBaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG = "HomeFragment";
    @InjectView(R.id.rhv_adv)
    RollHeaderView rhv_adv;

    @InjectView(R.id.gv_model)
    CustomGridView gv_model;

    @InjectView(R.id.pull)
    MySwipRefreshView pull;

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
        EventBus.getDefault().register(this);
        registerMessageReceiver();
        ll_left.setVisibility(View.INVISIBLE);
        UserModel userModel = UserInfoModel.getInstance().getUser();
        if (userModel == null || String.valueOf(Constants.VR).equals(userModel.getUserrole())) {
            fl_right.setVisibility(View.INVISIBLE);
        } else {
            fl_right.setVisibility(View.VISIBLE);
            iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.email));
        }
        fl_right.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        ActivityRecordFragment recordFragment = new ActivityRecordFragment();
        ProductInfoFragment productInfoFragment = new ProductInfoFragment();
        SaleInfoFragment saleInfoFragment = new SaleInfoFragment();
        fragments.add(recordFragment);
        fragments.add(productInfoFragment);
        fragments.add(saleInfoFragment);
        fragementAdapter = new FragementAdapter(getChildFragmentManager(), fragments);
        page.setAdapter(fragementAdapter);
        page.setOffscreenPageLimit(3);
        //设置tabLayout和viewpage关联
        tab.setupWithViewPager(page);
        tab.setTabMode(TabLayout.MODE_FIXED);

        pull.setProgressViewOffset(true, -20, DisplayUtil.dip2px(getContext(), 100));
        pull.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        pull.setOnRefreshListener(this);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    pull.setEnabled(true);
                } else {
                    pull.setEnabled(false);
                }
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");

    }

    private ModelAdapter modelAdapter;

    @Override
    protected void initDatas() {
        tv_title.setText("莱聚+");
        homeInfoPresenter = new HomeInfoImpl(getContext());
        modelAdapter = new ModelAdapter(getContext());
        gv_model.setAdapter(modelAdapter);
        gv_model.setOnItemClickListener(this);
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
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(mMessageReceiver);
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void lazyLoad() {
        //第一次加载自动刷新
        pull.setRefreshing(true);
        homeInfoPresenter.getHomeInfoData(pull);
    }

    @Override
    public void onResume() {
        super.onResume();
        rhv_adv.startRoll();
        model = UserInfoModel.getInstance().getUser();
        if (model == null) {
            return;
        }
        String userrole = model.getUserrole();
        if (!String.valueOf(Constants.VR).equals(userrole)) {
            ZillaApi.NormalRestAdapter.create(Message2Service.class).getMessageRead(UserInfoModel.getInstance().getToken(),
                    UserInfoModel.getInstance().getUserId(),
                    new Callback<ResponseData<UnReadMsg>>() {
                        @Override
                        public void success(ResponseData<UnReadMsg> responseData, Response response) {
                            int status = responseData.getStatus();
                            try {
                                switch (status) {
                                    case 200:
                                        if (responseData.getData().getNum() > 0) {
                                            iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.has_email));
                                        } else {
                                            iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.email));
                                        }
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
//            chatUserModel.setUserId(StringUtils.isEmpty(model.getHXAccountId()) ? "" : model.getHXAccountId());

            ChatUserInfoModel.getInstance().setUser(chatUserModel);
            String hasEmchat = model.getHasEmchat();
            if ("1".equals(hasEmchat)) {//如果有环信号
                timer = new Timer();
                TimerTask task = new TimerTask() {

                    @Override
                    public void run() {
                        // 需要做的事:发送消息
                        final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
                        Log.i(TAG, "hxid = " + hxid);
                        Log.i(TAG, "model.getHXAccountId() = " + model.getHXAccountId());


                        if (hxid.equals(model.getHXAccountId())) {
                            if (timer != null) {
                                timer.cancel();
                            }
                            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                            ChatUserModel chatUserModel = new ChatUserModel();
                            chatUserModel.setUserName(model.getNickname());
                            chatUserModel.setUserPhone(path + model.getPhoto());
                            chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                            ChatUserInfoModel.getInstance().setUser(chatUserModel);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    EMClient.getInstance().updateCurrentUserNick(model.getNickname());
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                }
                            }).start();
                        } else {
                            if ("-1".equals(hxid)) {
                                Log.i(TAG, "Constants.IS_LOGINIMG = " + Constants.IS_LOGINIMG);
                                if ("0".equals(Constants.IS_LOGINIMG)) {
                                    loginChat(progressDialog, model.getHXAccountId());
                                }
                            } else {
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

    @Override
    public void onPause() {
        super.onPause();
        rhv_adv.stopRoll();
    }

    private void HXLoginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

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
            }
        });
    }

    @Override
    public void onRefresh() {
        homeInfoPresenter.getHomeInfoData(pull);
    }

    private void loginChat(final ProgressDialog progressDialog, final String account) {
        Log.i(TAG, "account = " + account + "  HBL_SOFTTEK#321");
        Constants.IS_LOGINIMG = "1";
        EMClient.getInstance().login(account.toLowerCase(), "HBL_SOFTTEK#321", new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "登录成功............");

                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                Constants.IS_LOGINIMG = "0";
                SharedPreferenceService.getInstance().put("HXID", account.toLowerCase());
                String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                ChatUserModel chatUserModel = new ChatUserModel();
                chatUserModel.setUserName(model.getNickname());
                chatUserModel.setUserPhone(path + model.getPhoto());
                chatUserModel.setUserId(model.getHXAccountId().toLowerCase());
                ChatUserInfoModel.getInstance().setUser(chatUserModel);
                EMClient.getInstance().updateCurrentUserNick(model.getNickname());
                EMClient.getInstance().chatManager().loadAllConversations();

                //从服务器加载和该用户相关的所有群组
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                if (timer != null) {
                    timer.cancel();
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                System.out.println("progress:" + progress + "     status:" + status);
            }

            @Override
            public void onError(final int code, final String message) {
                Log.i(TAG, "登录error............" + message);
                Constants.IS_LOGINIMG = "0";
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
        ////判断当前用户是否拥有此按钮权限
        if (userInfoModel.hasPower(position)) {
            //如果有则判断更具具体角色进入相应的页面
            switch (position) {
                case Constants.BODY_GAME:
                    startActivity(new Intent(getContext(), BodyGameActivity.class));
                    MobclickAgent.onEvent(getContext(), "BodyGameEvent");
                    break;
                case Constants.LAI_YUNDONG:
                    String isJoin = userInfoModel.getUser().getIsJoin();
                    if (StringUtils.isEmpty(isJoin) || "0".equals(isJoin)) {
                        startActivity(new Intent(getContext(), JoinGroupActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), LaiSportActivity.class));
                    }
                    MobclickAgent.onEvent(getContext(), "LaiSportEvent");
                    break;
                case Constants.LAI_CLASS:
                case Constants.LAI_EXCLE:
                case Constants.LAI_SHOP:
                    new AlertDialog.Builder(getContext()).setMessage("功能开发中敬请期待").create().show();
                    break;
            }

        } else {
            //如果本身没有该按钮权限则根据不同身份提示用户，进行下一步操作
            AlertDialog.Builder information_dialog;
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
                case Constants.SR:
                case Constants.SP:
                    break;
            }
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
                    startActivity(new Intent(getContext(), Message2Activity.class));
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
                try {
                    iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.has_email));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
