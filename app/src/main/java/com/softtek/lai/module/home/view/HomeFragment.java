/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.softtek.lai.LaiApplication;
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
import com.softtek.lai.module.home.model.ModelName;
import com.softtek.lai.module.home.model.UnReadMsg;
import com.softtek.lai.module.home.model.Version;
import com.softtek.lai.module.home.net.HomeService;
import com.softtek.lai.module.home.presenter.HomeInfoImpl;
import com.softtek.lai.module.home.presenter.IHomeInfoPresenter;
import com.softtek.lai.module.home.service.UpdateService;
import com.softtek.lai.module.laiClassroom.ClassroomActivity;
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng_new.util.Contacts;
import com.softtek.lai.module.laicheng_new.view.NewLaiBalanceActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.module.message2.net.Message2Service;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.sport2.view.LaiSportActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.MySwipRefreshView;
import com.softtek.lai.widgets.RollHeaderView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;


/**
 * Created by jerry.guan on 3/15/2016.
 * 首页
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends LazyBaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {

    @InjectView(R.id.rhv_adv)
    RollHeaderView rhv_adv;

    @InjectView(R.id.gv_model)
    GridView gv_model;

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
    private List<ModelName> models = new ArrayList<>();
    FragementAdapter fragementAdapter;
    private MessageReceiver mMessageReceiver;
    UserModel user;

    private AlertDialog mDialog;
    private SharedPreferences mSharedPreferences;

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

    }

    private ModelAdapter modelAdapter;

    @Override
    protected void initDatas() {
        tv_title.setText("莱聚+");
        homeInfoPresenter = new HomeInfoImpl(getContext());
        models.add(new ModelName("体管赛", 0));
        models.add(new ModelName("莱运动", 0));
        models.add(new ModelName("莱课堂", 0));
        models.add(new ModelName("莱秤", 0));
        models.add(new ModelName("开发中", 0));
        modelAdapter = new ModelAdapter(getContext(), models);
        gv_model.setAdapter(modelAdapter);
        gv_model.setOnItemClickListener(this);
        mSharedPreferences = getActivity().getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);

    }


    private String apkUrl;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UpdateService.startUpdate(getContext().getApplicationContext(), apkUrl);
            }
        }
    }

    private void show(final Version version) {
        int v_code = DisplayUtil.getAppVersionCode(getContext());
        if (v_code < version.getAppVisionCode()) {
            String str = "莱聚+ v " + version.getAppVisionNum() + "版本\n最新的版本！请前去下载。\n更新于：" + version.getUpdateTime();
            new AlertDialog.Builder(getContext())
                    .setTitle("版本有更新")
                    .setMessage(str)
                    .setNegativeButton("稍后更新", null)
                    .setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            apkUrl = version.getAppFileUrl();
                            if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                //启动更新服务
                                UpdateService.startUpdate(getContext().getApplicationContext(), version.getAppFileUrl());
                            }
                        }
                    }).create().show();
        }
    }

    private boolean hasPermission(String permission) {
        if (ActivityCompat.checkSelfPermission(getContext().getApplicationContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission}, 100);
            return false;
        }
        return true;
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
        //检查新版本更新
        ZillaApi.NormalRestAdapter.create(HomeService.class)
                .checkNew(new RequestCallback<ResponseData<Version>>() {
                    @Override
                    public void success(ResponseData<Version> versionResponseData, Response response) {
                        dialogDissmiss();
                        if (versionResponseData.getStatus() == 200) {
                            Version version = versionResponseData.getData();
                            try {
                                show(version);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        super.failure(error);
                    }
                });
        //第一次加载自动刷新
        pull.setRefreshing(true);
        homeInfoPresenter.getHomeInfoData(pull);

    }

    private int laiNum;
    private int tiNum;
    private int chartNum;

    @Override
    public void onResume() {
        super.onResume();
        rhv_adv.startRoll();
        user = UserInfoModel.getInstance().getUser();
        if (user == null) {
            return;
        }
        String userrole = user.getUserrole();
        if (!String.valueOf(Constants.VR).equals(userrole)) {
            ZillaApi.NormalRestAdapter.create(Message2Service.class).getMessageRead(UserInfoModel.getInstance().getToken(),
                    UserInfoModel.getInstance().getUserId(),
                    new Callback<ResponseData<UnReadMsg>>() {
                        @Override
                        public void success(ResponseData<UnReadMsg> data, Response response) {
                            int status = data.getStatus();
                            try {
                                switch (status) {
                                    case 200:
                                        if (data.getData().Num > 0) {
                                            iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.has_email));
                                        } else {
                                            iv_email.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.email));
                                        }
                                        laiNum = data.getData().LaiNum;
                                        tiNum = data.getData().TiNum;
                                        ModelName tiModel = models.get(0);
                                        tiModel.unreadNum = tiNum + chartNum;
                                        ModelName laiModel = models.get(1);
                                        laiModel.unreadNum = laiNum;
                                        modelAdapter.notifyDataSetChanged();
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
            if (EMClient.getInstance().isLoggedInBefore()) {
                EMClient.getInstance().chatManager().addMessageListener(messageListener);
                int unreadNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                updateMessage(unreadNum);
            }
        }
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // 提示新消息
            int unreadNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
            Intent msgIntent = new Intent(Constants.MESSAGE_CHAT_ACTION);
            msgIntent.putExtra("count", unreadNum);
            LaiApplication.getInstance().sendBroadcast(msgIntent);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        rhv_adv.stopRoll();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);

    }


    @Override
    public void onRefresh() {
        homeInfoPresenter.getHomeInfoData(pull);
    }

    /**
     * 功能模块按钮
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                    if (TextUtils.isEmpty(isJoin) || "0".equals(isJoin)) {
                        startActivity(new Intent(getContext(), JoinGroupActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), LaiSportActivity.class));
                    }
                    MobclickAgent.onEvent(getContext(), "LaiSportEvent");
                    break;
                case Constants.LAI_CLASS:
                    startActivity(new Intent(getContext(), ClassroomActivity.class));
                    MobclickAgent.onEvent(getContext(), "LaiClassEvent");
                    break;
                case Constants.LAI_CHEN:
                    LinearLayout mOld;
                    LinearLayout mNew;
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose, null);
                    mOld = (LinearLayout) dialogView.findViewById(R.id.ll_old);
                    mNew = (LinearLayout) dialogView.findViewById(R.id.ll_new);
//                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    mOld.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString(Contacts.CHOOSE_KEY, "old");
                            editor.apply();
                            mDialog.dismiss();
                            startActivity(new Intent(getContext(), LaibalanceActivity.class));
                        }
                    });
                    mNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString(Contacts.CHOOSE_KEY, "new");
                            editor.apply();
                            mDialog.dismiss();
                            startActivity(new Intent(getContext(), NewLaiBalanceActivity.class));
                        }
                    });
                    if (mDialog == null) {
                        mDialog = new AlertDialog.Builder(getActivity()).create();
                        mDialog.setView(dialogView, 0, 0, 0, 0);
                    }
                    String mode = mSharedPreferences.getString(Contacts.CHOOSE_KEY, "");
                    switch (mode) {
                        case "old":
                            startActivity(new Intent(getContext(), LaibalanceActivity.class));
                            break;
                        case "new":
                            startActivity(new Intent(getContext(), NewLaiBalanceActivity.class));
                            break;
                        default:
                            mDialog.show();

                            break;
                    }
                    MobclickAgent.onEvent(getContext(), "BalanceEvent");
                    break;
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
        filter.addAction(Constants.MESSAGE_CHAT_ACTION);
        getActivity().registerReceiver(mMessageReceiver, filter);

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
            } else if (Constants.MESSAGE_CHAT_ACTION.equals(intent.getAction())) {
                int unreadNum = intent.getIntExtra("count", 0);
                updateMessage(unreadNum);
            }
        }

    }

    public void updateMessage(int num) {
        //显示
        chartNum = num;
        int read = chartNum > 0 ? tiNum + chartNum : tiNum;
        ModelName tiModel = models.get(0);
        tiModel.unreadNum = read;
        modelAdapter.notifyDataSetChanged();
    }

}
