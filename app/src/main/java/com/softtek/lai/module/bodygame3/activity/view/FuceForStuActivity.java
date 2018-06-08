package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.healthyreport.HealthyReportActivity;
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng.MainBaseActivity;
import com.softtek.lai.module.laicheng.model.AcmidModel;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng_new.util.Contacts;
import com.softtek.lai.module.laicheng_new.view.NewLaiBalanceActivity;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

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
    @InjectView(R.id.tv_class_name)
    TextView tv_className;
    @InjectView(R.id.tv_period_start)
    TextView tv_period_start;
    @InjectView(R.id.tv_period_end)
    TextView tv_period_end;
    @InjectView(R.id.iv_ble_icon)
    ImageView mbleIcon;


    MeasuredDetailsModel fucDataModel;
    private String recordId;
    private Long AccountId;
    private String acmid = "";

    private int type;  //0：访客，1：自己，2：复测录入，3：复测初始录入
    private int type_new = -1;

    private String classId;
    private String from;
    private int isAudit = -1;

    private BleMainData result_model = null;

    private boolean chengliang_success = false;//默认称没有测量成果
    private AlertDialog dialog;
    private AlertDialog chooseDialog;
    private SharedPreferences sharedPreferences;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setBleStateListener(bleStateListener);
//                mShakeListener.start();
                Log.d("enter bleStateListener", "bleStateListener--------------");
            }
        }
//        permission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                showTipDialog();
                break;
            case R.id.fucecheck_entry: //复测审核
                Log.i(TAG, "recordId= " + recordId + " type= " + type + " classId = " + classId);
                progressDialog = new ProgressDialog(FuceForStuActivity.this);
                progressDialog.setMessage("请稍后...");
                progressDialog.show();
                FuceSevice sevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
                sevice.LBDataSubmit(UserInfoModel.getInstance().getToken(), recordId, type_new, classId, new Callback<ResponseData<AcmidModel>>() {
                    @Override
                    public void success(ResponseData<AcmidModel> bleMainDataResponseData, Response response) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        int status = bleMainDataResponseData.getStatus();
                        if (200 == status) {
                            if (bleMainDataResponseData.getData() != null) {
                                acmid = bleMainDataResponseData.getData().getAcmid();
                            }
                            Intent intent = new Intent(from);
                            intent.putExtra("acmid", acmid);
                            intent.putExtra("result_model", result_model);
                            LocalBroadcastManager.getInstance(FuceForStuActivity.this).sendBroadcast(intent);
                            finish();
                        } else {
                            Util.toastMsg("数据确认失败");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        ZillaApi.dealNetError(error);
                        Util.toastMsg("数据确认失败");
                    }
                });
                break;
            case R.id.heathyReport_entry:
                Intent health = new Intent(FuceForStuActivity.this, HealthyReportActivity.class);
                health.putExtra("reportId", recordId);
                health.putExtra("since", HealthyReportActivity.SINCE_LAICHEN);
                health.putExtra("isVisitor", HealthyReportActivity.VISITOR);
                startActivity(health);
                break;
            case R.id.tv_info_state:
                linkStart();
            case R.id.tv_title:
//                createChangeDialog();
        }
    }

//    private void createChangeDialog() {
//        LinearLayout mOld;
//        LinearLayout mNew;
//        ImageView mChooseImage;
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose, null);
//        mOld = dialogView.findViewById(R.id.ll_old);
//        mNew = dialogView.findViewById(R.id.ll_new);
//        mChooseImage = dialogView.findViewById(R.id.iv_new_choose);
//        mChooseImage.setImageDrawable(getResources().getDrawable(R.drawable.radio_green));
//        mOld.setOnClickListener(new View.OnClickListener() {
//            @Overridex
//            public void onClick(View v) {
//                chooseDialog.dismiss();
//            }
//        });
//        mNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(Contacts.CHOOSE_KEY, "new");
//                editor.apply();
//                finish();
//                startActivity(new Intent(FuceForStuActivity.this, NewRetestActivity.class));
//                chooseDialog.dismiss();
//            }
//        });
//        if (chooseDialog == null) {
//            chooseDialog = new AlertDialog.Builder(this).create();
//            chooseDialog.setView(dialogView, 0, 0, 0, 0);
//        }
//        chooseDialog.show();
//    }


    @OnClick(R.id.iv_voice)
    public void onClick() {
        if (MainBaseActivity.isVoiceHelp) {
            mVoice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.voice_icon_off));
            isVoiceHelp = false;
        } else {
            mVoice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.voice_icon_on));
            isVoiceHelp = true;
        }
    }

    @Override
    public void initUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FuceForStuActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);

            }
        }
        sharedPreferences = getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);
        setClosed(false);
        tv_title.setText("莱秤测量");
        tv_title.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        mBleState.setOnClickListener(this);

        fucDataModel = (MeasuredDetailsModel) getIntent().getSerializableExtra("fucedata");

        classId = getIntent().getStringExtra("classId");
        AccountId = getIntent().getLongExtra("AccountId", 0);
        from = getIntent().getStringExtra("from");
        type = getIntent().getIntExtra("type", -1); // 0：访客，1：自己，2：复测录入，3：复测初始录入

//        private int type;  //0：访客，1：自己，2：复测录入，3：复测初始录入
        if (2 == type) {
            type_new = 1;//复测类型：0：初始，1：复测
        } else if (3 == type) {
            type_new = 0;
        }

//        type = 4;
        isAudit = getIntent().getIntExtra("isAudit", -1);

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
        setType(4);

        setBleStateListener(bleStateListener);
//        mShakeListener.start();

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
                Log.d("data.getBodyFatRate()", data.getBodyFatRate());
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
            entity.setGender(Integer.parseInt(TextUtils.isEmpty(fucDataModel.getGender()) ? "1" : fucDataModel.getGender()));
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
        dialogShow("亲，请稍等，测量中...");
    }

    @Override
    public void showTimeoutDialog() {
        createDialog(true);
    }

    private void createDialog(boolean isTimeout) {
        if (isFinishing()) {
            return;
        }
        if (dialog == null) {
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
    public void setClickable(boolean available) {
        if (available) {
            mBleState.setEnabled(true);
        } else {
            mBleState.setEnabled(false);
        }
    }

    @Override
    public void setBleIcon(boolean alive) {
        if (alive){
            mbleIcon.setVisibility(View.VISIBLE);
        }else {
            mbleIcon.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showTipDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ProgressDialog progressDialog;

    private void showTipDialog() {
        if (chengliang_success) {
            // 创建退出对话框
            final AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("温馨提示");
            // 设置对话框消息
            isExit.setMessage("您尚未进行数据确认, 直接返回将导致本次测量数据丢失哦");
            // 添加选择按钮并注册监听
            isExit.setButton("数据确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i(TAG, "recordId= " + recordId + " type= " + type + " classId = " + classId);
                    progressDialog = new ProgressDialog(FuceForStuActivity.this);
                    progressDialog.setMessage("请稍后...");
                    progressDialog.show();
                    FuceSevice sevice = ZillaApi.NormalRestAdapter.create(FuceSevice.class);
                    sevice.LBDataSubmit(UserInfoModel.getInstance().getToken(), recordId, type_new, classId, new Callback<ResponseData<AcmidModel>>() {
                        @Override
                        public void success(ResponseData<AcmidModel> bleMainDataResponseData, Response response) {
                            int status = bleMainDataResponseData.getStatus();
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            if (200 == status) {
                                if (bleMainDataResponseData.getData() != null) {
                                    acmid = bleMainDataResponseData.getData().getAcmid();
                                }
                                Intent intent = new Intent(from);
                                intent.putExtra("acmid", acmid);
                                intent.putExtra("result_model", result_model);
                                LocalBroadcastManager.getInstance(FuceForStuActivity.this).sendBroadcast(intent);
                                finish();
                            } else {
                                Util.toastMsg("数据确认失败");
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            ZillaApi.dealNetError(error);
                            Util.toastMsg("数据确认失败");
                        }
                    });

                }
            });
            isExit.setButton2("直接返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // type  ======  >   0：访客，1：自己，2：复测录入，3：复测初始录入
                    if (2 == type && 0 == isAudit) { //复测录入，未审核列表
                        LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(FcAuditFragment.UPDATE_UI_FCCHECK_DAISHENHE_TABLIST));
                    } else if (3 == type && 0 == isAudit) {//复测初始录入 ,未审核列表
                        LocalBroadcastManager.getInstance(LaiApplication.getInstance()).sendBroadcast(new Intent(InitAuditFragment.UPDATE_UI_CHUSHI_DAISHENHE_TABLIST));
                    }
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
