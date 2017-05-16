package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.MainBaseActivity;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.mpermission.PermissionFail;
import com.softtek.lai.mpermission.PermissionOK;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 4/7/2017.
 */

@InjectLayout(R.layout.activity_forstu)
public class FuceForStuActivity extends MainBaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_weight_caption)
    TextView mWeightCaption;

    @InjectView(R.id.tv_info_state)
    TextView mBleState;
    @InjectView(R.id.ll_info_data)
    LinearLayout mInfoDataContent;
    @InjectView(R.id.tv_body_fat_rate)
    TextView mBodyFatRate;
    @InjectView(R.id.tv_bmi)
    TextView mBmi;
    @InjectView(R.id.tv_internal_fat_rate)
    TextView mInternalFatRate;
    @InjectView(R.id.tv_weight)
    TextView mWeight;
    @InjectView(R.id.iv_voice)
    ImageView mVoice;

    @InjectView(R.id.menu_layout)
    LinearLayout menu_layout;

    @InjectView(R.id.fucecheck_entry)  //复测审核入口
            TextView fucecheck_entry;

    @InjectView(R.id.heathyReport_entry) //健康报告
            TextView heathyReport_entry;

    @InjectView(R.id.img_head)
    CircleImageView img_head;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_mobile)
    TextView tv_mobile;
    @InjectView(R.id.tv_className)
    TextView tv_className;
    @InjectView(R.id.tv_period_start)
    TextView tv_period_start;
    @InjectView(R.id.tv_period_end)
    TextView tv_period_end;


    MeasuredDetailsModel fucDataModel;
    private String recordId;
    private Long AccountId;

    private int type;  //0：访客，1：自己，2：复测录入，3：复测初始录入
    private String classId;

    private String from;

    private BleMainData result_model = null;

    private boolean chengliang_success = false;//默认称没有测量成果
    private AlertDialog dialog;


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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                showTipDialog();
                break;
            case R.id.fucecheck_entry: //复测审核
                Intent intent = new Intent(from);
                intent.putExtra("ACMID", recordId);
                intent.putExtra("result_model", result_model);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                finish();

                break;
            case R.id.heathyReport_entry:
                Intent health = new Intent(FuceForStuActivity.this, HealthyReportActivity.class);
                health.putExtra("reportId", recordId);
                health.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
                health.putExtra("isVisitor", HealthyReportActivity.VISITOR);
                startActivity(health);
                break;

        }
    }


    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon_off));
//            stopVoice();
            isVoiceHelp = false;
        } else {
            mVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_icon));
//            addVoice();
            isVoiceHelp = true;
        }
    }

    @Override
    public void initUi() {
        setClosed(false);
        tv_title.setText("莱秤测量");
        ll_left.setOnClickListener(this);


        fucDataModel = (MeasuredDetailsModel) getIntent().getSerializableExtra("fucedata");

        classId = getIntent().getStringExtra("classId");
        type = getIntent().getIntExtra("type", -1); // 0：访客，1：自己，2：复测录入，3：复测初始录入
        AccountId = getIntent().getLongExtra("AccountId", 0);
        from = getIntent().getStringExtra("from");

        chengliang_success = false;

        tv_name.setText(fucDataModel.getUserName());
        tv_className.setText(fucDataModel.getClassName());
        tv_mobile.setText(fucDataModel.getMobile());
        tv_period_start.setText(fucDataModel.getStartDate());
        tv_period_end.setText(fucDataModel.getEndDate());

        if (!TextUtils.isEmpty(fucDataModel.getPhoto())) {
            Picasso.with(this).load(AddressManager.get("photoHost") + fucDataModel.getPhoto()).placeholder(R.drawable.img_default).centerCrop()
                    .fit().into(img_head);
        } else {
            Picasso.with(this).load(R.drawable.img_default).centerCrop()
                    .fit().into(img_head);
        }

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "font/wendy.ttf");
        mWeight.setTypeface(tf);
        permission.apply(1, Manifest.permission.ACCESS_COARSE_LOCATION);
//        setGuest(true);
        setType(type);

        setBleStateListener(bleStateListener);
        mShakeListener.start();

        fucecheck_entry.setOnClickListener(this);
        heathyReport_entry.setOnClickListener(this);
    }

    @Override
    public void initUiByBleSuccess(BleMainData data) {
        if (isFinishing()) {
            return;
        }

        if (data != null) {
            recordId = data.getRecordId();
            menu_layout.setVisibility(View.VISIBLE);
            chengliang_success = true;

            mWeight.setText(data.getWeight() + "");
            if (data.getBodyTypeTitle() != null) {
                mWeightCaption.setText(data.getBodyTypeTitle());
            }
            if (data.getBodyTypeColor() != null) {
                mWeightCaption.setTextColor(Color.parseColor("#" + data.getBodyTypeColor()));
            }
            if (data.getBodyFatRate() != null) {
                Log.d("data.getBodyFatRate()",data.getBodyFatRate());
                mBodyFatRate.setText(data.getBodyFatRate() + data.getBodyFatRateUnit());
            }
            if (data.getBMI() != null) {
                mBmi.setText(data.getBMI() + "");
            }
            if (data.getViscusFatIndex() != null) {
                mInternalFatRate.setText(data.getViscusFatIndex());
            }

            result_model = data;
        }


    }

    @Override
    public void initUiByBleFailed() {
        dialogDissmiss();
        changeConnectionState(7);
    }


    @Override
    public VisitorModel getGuestInfo() {
        VisitorModel entity = new VisitorModel();
        if (fucDataModel != null) {
            entity.setName(fucDataModel.getUserName());
            entity.setBirthDate(fucDataModel.getBirthDate());

            entity.setHeight(TextUtils.isEmpty(fucDataModel.getHeight()) ? 0 : Float.parseFloat(fucDataModel.getHeight()));
            entity.setGender(Integer.parseInt(TextUtils.isEmpty(fucDataModel.getGender())?"1":fucDataModel.getGender()));
            entity.setPhoneNo(fucDataModel.getMobile());
            entity.setVisitorId(AccountId);

        }

        entity.setClassId(classId);
        return entity;

    }


    @Override
    public void setStateTip(String state) {
        if (mBleState != null) {
            mBleState.setText(state);
        }
    }


    @Override
    public void showProgressDialog() {
//        if (isFinishing()) {
//            return;
//        }
        dialogShow("亲，请稍等，测量中...");
    }

    @Override
    public void showTimeoutDialog() {
        createDialog(true);
    }

    private void createDialog(boolean isTimeout) {
        if (isFinishing()){
            return;
        }
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.whiteDialog).setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!BleManager.getInstance().isConnected()) {
                                mShakeListener.start();
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
            dialog.show();
        }
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
//        if (menu_layout != null) {
//            menu_layout.setVisibility(View.INVISIBLE);
//        }

//        chengliang_success = false;
    }

    @Override
    public void showNoVisitorDialog() {

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            showTipDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showTipDialog() {
        if (chengliang_success) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("温馨提示");
            // 设置对话框消息
            isExit.setMessage("您尚未进行数据确认, 直接返回将导致本次测量数据丢失哦");
            // 添加选择按钮并注册监听
            isExit.setButton("数据确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(from);
//                intent.putExtra("ACMID", recordId);
                    intent.putExtra("result_model", result_model);
                    LocalBroadcastManager.getInstance(FuceForStuActivity.this).sendBroadcast(intent);
                    finish();
                }
            });
            isExit.setButton2("直接返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            // 显示对话框
            isExit.show();
        } else {
            finish();
        }
    }


}
