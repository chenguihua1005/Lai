package com.softtek.lai.module.laicheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.model.BasicModel;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng_new.util.Contacts;
import com.softtek.lai.module.laicheng_new.view.NewLaiBalanceActivity;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_laibalance)
public class LaibalanceActivity extends MainBaseActivity implements SelftestFragment.VoiceListener,
        VisitortestFragment.ShakeSwitch, VisitortestFragment.VisitorVoiceListener, SelftestFragment.StartLinkListener ,
        VisitortestFragment.StartVisitorLinkListener,
        VisitortestFragment.SetTypeListener {

    @InjectView(R.id.tab_balance)
    TabLayout tab_balance;
    @InjectView(R.id.content)
    ViewPager content;
    @InjectView(R.id.ll_title)
    LinearLayout mTitle;

    private int pageIndex;

    private List<FragmentModel> fragmentModels = new ArrayList<>();

    private SelftestFragment selftestFragment;
    private VisitortestFragment visitortestFragment;

    private AlertDialog dialog;

    private AlertDialog.Builder noVisitorBuilder;
    private AlertDialog chooseDialog;
    private SharedPreferences sharedPreferences;

    private BasicModel basicModel;
    private boolean isJump = false;
    private TextView tv_tab;

    @OnClick(R.id.fl_left)
    public void doBack() {
        finish();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION){
            if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setBleStateListener(bleStateListener);
//                mShakeListener.start();
                Log.d("enter bleStateListener", "bleStateListener--------------");
            }
        }
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4006067818 "));
                startActivity(intent);
            }
        }
//        permission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void initUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LaibalanceActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
        sharedPreferences = getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);
        Intent intent = getIntent();
        if (intent != null){
            basicModel = intent.getParcelableExtra("model");
            isJump = intent.getBooleanExtra("isJump",false);
        }
        setClosed(true);
        selftestFragment = SelftestFragment.newInstance(null);
        visitortestFragment = new VisitortestFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isJump",isJump);
        bundle.putParcelable("model",basicModel);
        visitortestFragment.setArguments(bundle);
        pageIndex = content.getCurrentItem();
//        permission.apply(1, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
        fragmentModels.add(new FragmentModel("给自己测", selftestFragment));
        fragmentModels.add(new FragmentModel("给客户测", visitortestFragment));
        content.setOffscreenPageLimit(1);
        content.setAdapter(new BalanceAdapter(getSupportFragmentManager(), fragmentModels));
        if (isJump){
            content.setCurrentItem(1);
        }
        tab_balance.setupWithViewPager(content);
        final TabLayout.Tab self = tab_balance.getTabAt(0);
        if (self != null) {
            self.setCustomView(R.layout.self_tab);
            tv_tab = self.getCustomView().findViewById(R.id.tab_title);
            tv_tab.setText("给自己测");
            if (content.getCurrentItem() == 0){
                tv_tab.setTextColor(getResources().getColor(R.color.colorPrimary2));
            }else {
                tv_tab.setTextColor(getResources().getColor(R.color.gray_pressed));
            }
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
                    setType(1);
                    tv_tab.setTextColor(getResources().getColor(R.color.colorPrimary2));
//                    mShakeListener.start();
                    if (!isDestroyed()) {
                        selftestFragment.refreshVoiceIcon();
//                        selftestFragment.setStateTip("摇一摇，连接莱秤");
                    }


                } else {
                    setType(visitortestFragment.getType());
                    tv_tab.setTextColor(getResources().getColor(R.color.gray_pressed));
                    if (!isDestroyed()) {
                        visitortestFragment.refreshVoiceIcon();
//                        visitortestFragment.setStateTip("摇一摇，连接莱秤");
                    }

                }
                Log.d("index-------------", String.valueOf(pageIndex));
//                disconnectBluetooth();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setBleStateListener(bleStateListener);
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChangeDialog();
            }
        });
    }

    private void createChangeDialog(){
        LinearLayout mOld;
        LinearLayout mNew;
        ImageView mChooseImage;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose, null);
        mOld = (LinearLayout) dialogView.findViewById(R.id.ll_old);
        mNew = (LinearLayout) dialogView.findViewById(R.id.ll_new);
        mChooseImage = (ImageView)dialogView.findViewById(R.id.iv_old_choose);
        mChooseImage.setImageDrawable(getResources().getDrawable(R.drawable.radio_green));
        mOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog.dismiss();
            }
        });
        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Contacts.CHOOSE_KEY,"new");
                editor.apply();
                chooseDialog.dismiss();
                Intent intent = new Intent(LaibalanceActivity.this,NewLaiBalanceActivity.class);
                intent.putExtra("isJump",isJump);
                intent.putExtra("model",basicModel);
                startActivity(intent);
                finish();
            }
        });
        if (chooseDialog == null) {
            chooseDialog = new AlertDialog.Builder(this).create();
            chooseDialog.setView(dialogView, 0, 0, 0, 0);
        }
        chooseDialog.show();
    }

    private void setVoice() {
        if (isVoiceHelp) {
//            stopVoice();
            isVoiceHelp = false;
        } else {
//            addVoice();
            isVoiceHelp = true;
        }
    }

    @Override
    public void initUiByBleSuccess(BleMainData data) {
        if (pageIndex == 0) {
            if (selftestFragment.isCreatedView() && !selftestFragment.isDetached()) {
                selftestFragment.updateUI(data);
                selftestFragment.setStateTip("测量完成");
            }

        } else {

            if (visitortestFragment.isCreatedView() && !visitortestFragment.isDetached()) {
                visitortestFragment.updateData(data);
                visitortestFragment.setStateTip("测量完成");
            }
        }
        dialogDissmiss();
    }

    @Override
    public void initUiByBleFailed() {
        dialogDissmiss();
        sendFatRateToDevice(0.0f);
    }

    VisitorModel visitorModel;

    @Override
    public VisitorModel getGuestInfo() {
        visitorModel = visitortestFragment.getVisitorModel();
        if (visitorModel != null) {
            Log.i("ddd", visitorModel.toString());
        }
        if (pageIndex == 0) {
            return null;
        }
        return visitorModel;
    }


    @Override
    public void setStateTip(String state) {
        selftestFragment.setStateTip(state);
        visitortestFragment.setStateTip(state);
    }

    @SuppressLint("LongLogTag")
    private void createDialog(boolean isTimeout) {
        if (dialog == null) {
            Log.d("dialogNULL-------------------", "dialogNULL");
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.whiteDialog).setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!BleManager.getInstance().isConnected()) {
//                                mShakeListener.start();
                                changeConnectionState(0);
                            }
                            dialog.dismiss();
                        }
                    });
            if (isTimeout) {
                builder.setMessage("测量超时，请重新测量");
            } else {
                builder.setMessage("测量失败，请重新测量");
            }
            dialog = builder.create();
        }
        if (!dialog.isShowing()) {
            if (isTimeout) {
                dialog.setMessage("测量超时，请重新测量");
            } else {
                dialog.setMessage("测量失败，请重新测量");
            }
            dialog.show();
        }
    }

    @Override
    public void showTimeoutDialog() {
        if (!LaibalanceActivity.this.isFinishing()) {
            createDialog(true);
        }
    }

    @Override
    public void showUploadFailedDialog() {
        if (!LaibalanceActivity.this.isFinishing()) {
            createDialog(false);
        }
    }

    @Override
    public void showSearchBleDialog() {
        dialogShow("正在搜索设备...");
    }

    @Override
    public void refreshUi(LastInfoData data) {
        if (pageIndex == 0) {
            if (selftestFragment.isCreatedView() && !selftestFragment.isDetached()) {
                selftestFragment.refreshUi(data);
            }
        }
    }

    @Override
    public void showNoVisitorDialog() {
        Log.d("showNoVisitorDialog", "showNoVisitorDialog");
        if (noVisitorBuilder == null) {
            noVisitorBuilder = new AlertDialog.Builder(this, R.style.whiteDialog);
        }
        noVisitorBuilder.setMessage("您还没有录入完整的访客信息，请完善");
        noVisitorBuilder.setTitle("提示");
        noVisitorBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LaibalanceActivity.this, VisitorinfoActivity.class));
                dialog.dismiss();
            }
        });
        noVisitorBuilder.create();
        noVisitorBuilder.show();
//        mShakeListener.stop();
    }

    @Override
    public void setClickable(boolean available) {
        selftestFragment.setClickable(available);
        visitortestFragment.setClickable(available);
    }

    @Override
    public void setBleIcon(boolean alive) {
        if (alive){
            selftestFragment.setBleIcon(true);
            visitortestFragment.setBleIcon(true);
        }else {
            selftestFragment.setBleIcon(false);
            visitortestFragment.setBleIcon(false);
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
//        mShakeListener.start();
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onVisitorVoiceListener() {
        setVoice();
        boolean test = getGuestInfo() != null;
        Log.d("testGuestInfo-------------", String.valueOf(test));
    }

    @OnClick(R.id.fl_right)
    public void goToInstructions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CALL_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4006067818 "));
            startActivity(intent);
        }
//        startActivity(new Intent(LaibalanceActivity.this, InstructionsActivity.class));
    }

    @Override
    public void onLinkListener() {
        linkStart();
    }

    @Override
    public void onLinkVisitorListener() {
        linkStart();
    }

    @Override
    public void onSetType(int type) {
        this.setType(type);
    }
}
