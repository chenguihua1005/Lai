package com.softtek.lai.module.laicheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.mpermission.PermissionFail;
import com.softtek.lai.mpermission.PermissionOK;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_laibalance)
public class LaibalanceActivity extends MainBaseActivity implements SelftestFragment.VoiceListener, VisitortestFragment.ShakeSwitch, VisitortestFragment.VisitorVoiceListener {

    @InjectView(R.id.tab_balance)
    TabLayout tab_balance;
    @InjectView(R.id.content)
    ViewPager content;

    private int pageIndex;

    private List<FragmentModel> fragmentModels = new ArrayList<>();

    private SelftestFragment selftestFragment;
    private VisitortestFragment visitortestFragment;

    private AlertDialog.Builder builder;


    @OnClick(R.id.fl_left)
    public void doBack() {
        finish();
    }

    @SuppressLint("LongLogTag")
    @PermissionOK(id = 1)
    private void initPermissionSuccess() {
        setBleStateListener(bleStateListener);
        mShakeListener.start();
        Log.d("enter bleStateListener --------", "bleStateListener");
    }

    @PermissionFail(id = 1)
    private void initPermissionFail() {
        mShakeListener.stop();
        new AlertDialog.Builder(this)
                .setMessage("拒绝授权将无法正常运行软件！")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:" + "com.softtek.lai");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void initUi() {
        selftestFragment = SelftestFragment.newInstance(null);
        visitortestFragment = new VisitortestFragment();
        pageIndex = content.getCurrentItem();
        permission.apply(1, Manifest.permission.ACCESS_COARSE_LOCATION);
        fragmentModels.add(new FragmentModel("给自己测", selftestFragment));
        fragmentModels.add(new FragmentModel("给访客测", visitortestFragment));
        content.setOffscreenPageLimit(1);
        content.setAdapter(new BalanceAdapter(getSupportFragmentManager(), fragmentModels));
        tab_balance.setupWithViewPager(content);
        final TabLayout.Tab self = tab_balance.getTabAt(0);
        if (self != null) {
            self.setCustomView(R.layout.self_tab);
            TextView tv_tab = (TextView) self.getCustomView().findViewById(R.id.tab_title);
            tv_tab.setText("给自己测");
            @SuppressLint("WrongViewCast")
            CircleImageView civ = (CircleImageView) self.getCustomView().findViewById(R.id.iv_head);
            Picasso.with(this).load(AddressManager.get("photoHost") + UserInfoModel.getInstance().getUser().getPhoto())
                    .fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        }
        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                if (pageIndex == 0) {
                    setGuest(false);
                    selftestFragment.refreshVoiceIcon();
                } else {
                    setGuest(true);
//                    if (visitortestFragment.visitorLastData != null && !TextUtils.isEmpty(visitortestFragment.visitorLastData.getRecordId())) {
//                        mShakeListener.start();
//                    } else {
//                        mShakeListener.stop();
//                    }
                    visitortestFragment.refreshVoiceIcon();
                }
                Log.d("index-------------", String.valueOf(pageIndex));
                disconnectBluetooth();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setVoice() {
        if (isVoiceHelp) {
            stopVoice();
            isVoiceHelp = false;
        } else {
            addVoice();
            isVoiceHelp = true;
        }
    }

    @Override
    public void initUiByBleSuccess(BleMainData data) {
        if (pageIndex == 0) {
            selftestFragment.updateUI(data);
            selftestFragment.setStateTip("测量完成");
        } else {
            visitortestFragment.UpdateData(data);
            visitortestFragment.setStateTip("测量完成");
        }
        dialogDissmiss();
    }

    @Override
    public void initUiByBleFailed() {
        dialogDissmiss();
        isResultTest = true;
        testTimeOut = 0;
        sendFatRateToDevice(0.0f);
    }

    VisitorModel visitorModel;

    @Override
    public VisitorModel getGuestInfo() {
        visitorModel = visitortestFragment.getVisitorModel();
        return visitorModel;
    }


    @Override
    public void setStateTip(String state) {
        if (pageIndex == 0) {
            selftestFragment.setStateTip(state);
        } else {
            visitortestFragment.setStateTip(state);
        }
    }

    private void createDialog(boolean isTimeout) {
        if (builder == null) {
            builder = new AlertDialog.Builder(this, R.style.whiteDialog);
        }
        if (isTimeout) {
            builder.setMessage("测量超时，请重新测量");
        } else {
            builder.setMessage("测量失败，请重新测量");
        }
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isConnected) {
                    mShakeListener.start();
                    changeConnectionState(0);
                }
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void showTimeoutDialog() {
        createDialog(true);
    }

    @Override
    public void showUploadFailedDialog() {
        createDialog(false);
    }

    @Override
    public void showSearchBleDialog() {
        dialogShow("正在搜索设备...");
    }

    @Override
    public void refreshUi(LastInfoData data) {
        if (pageIndex == 0) {
            selftestFragment.refreshUi(data);
        }
    }

    @Override
    public void showProgressDialog() {
        dialogShow("亲，请稍等，测量中...");
    }

    @Override
    public void onVoiceListener() {
        setVoice();
    }


    @Override
    public void setOnShakeON() {
        mShakeListener.start();
    }

    @Override
    public void setOnShakeSTOP() {
        mShakeListener.stop();
    }

    @Override
    public void onVisitorVoiceListener() {
        setVoice();
    }
}
