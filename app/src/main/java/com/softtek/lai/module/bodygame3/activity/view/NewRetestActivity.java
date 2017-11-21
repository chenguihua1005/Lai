package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggx.widgets.view.CustomProgress;
import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleCallback;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNUser;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng.model.AcmidModel;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.util.DeviceListDialog;
import com.softtek.lai.module.laicheng.util.SoundPlay;
import com.softtek.lai.module.laicheng.util.StringMath;
import com.softtek.lai.module.laicheng_new.model.BleResponseData;
import com.softtek.lai.module.laicheng_new.model.PostQnData;
import com.softtek.lai.module.laicheng_new.net.NewBleService;
import com.softtek.lai.module.laicheng_new.util.Contacts;
import com.softtek.lai.module.laicheng_new.view.NewLaiBalanceActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.InjectView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

/**
 * Created by jia.lu on 11/20/2017.
 */

public class NewRetestActivity extends Activity implements View.OnClickListener {
    private static int PERMISSION_REQUEST_COARSE_LOCATION = 233;
    private boolean isVoiceHelp = true;
    private TextView mTitle;
    private LinearLayout mLeftBack;
    private ImageView mVoiceSwitch;
    private TextView mWeightCaption;
    private LinearLayout mInfoDataContent;
    private TextView mBodyFatRate;
    private TextView mBmi;
    private TextView mInternalFatRate;
    private TextView mWeight;
    CircleImageView mheadImg;
    TextView mName;
    TextView mMobile;
    TextView mClassName;
    TextView mPeriodStart;
    TextView mPeriodEnd;

    private SharedPreferences sharedPreferences;
    private AlertDialog chooseDialog;
    private AlertDialog testFailDialog;
    private BluetoothAdapter bluetoothAdapter;
    private DeviceListDialog deviceListDialog;
    private CustomProgress progressDialog;
    private QNBleApi qnBleApi;
    private QNUser qnUser;
    private Disposable connectTimeout;
    private Disposable testingTimeout;
    private Disposable voiceOfTesting;
    private Disposable dialogLag;
    private boolean isStartTesting = true;//是否开始测量
    private boolean isFindDevice = false;
    private boolean isReceiveData = true;//重命名对话框弹出的时候是不接受数据的开关
    private boolean chengliang_success = false;//默认称没有测量成果
    private int type = 4;//0自己，1访客
    private int type_new = -1;
    private QNBleDevice connectedDevice;

    private MeasuredDetailsModel fucDataModel;
    private Long AccountId;
    private String classId;
    private String from;
    private int isAudit = -1;
    private String acmid = "";
    private String recordId;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retest_new);
        SoundPlay.getInstance().init(LaiApplication.getInstance().getApplicationContext());
        qnBleApi = QNApiManager.getApi(this);
        qnBleApi.setScanMode(QNBleApi.SCAN_MODE_ALL);
        qnBleApi.setWeightUnit(QNBleApi.WEIGHT_UNIT_KG);
        createLinkDialog();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
        initView();
        if (2 == type) {
            type_new = 1;//复测类型：0：初始，1：复测
        } else if (3 == type) {
            type_new = 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (bluetoothAdapter != null) {
                    if (bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.enable();
                    }
                }
                Log.d("enter bleStateListener", "bleStateListener--------------");
            } else {
                Toast.makeText(this, "请先获取蓝牙位置权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        sharedPreferences = getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);
        mTitle = findViewById(R.id.tv_title);
        mLeftBack = findViewById(R.id.ll_left);
        mName = findViewById(R.id.tv_name);
        mheadImg = findViewById(R.id.iv_head);
        mMobile = findViewById(R.id.tv_mobile);
        mClassName = findViewById(R.id.tv_class_name);
        mMobile = findViewById(R.id.tv_mobile);
        mPeriodStart = findViewById(R.id.tv_period_start);
        mPeriodEnd = findViewById(R.id.tv_period_end);

        mLeftBack.setOnClickListener(this);
        mTitle.setText("莱秤测量");
        fucDataModel = (MeasuredDetailsModel) getIntent().getSerializableExtra("fucedata");

        classId = getIntent().getStringExtra("classId");
        AccountId = getIntent().getLongExtra("AccountId", 0);
        from = getIntent().getStringExtra("from");
        type = getIntent().getIntExtra("type", -1); // 0：访客，1：自己，2：复测录入，3：复测初始录入
        isAudit = getIntent().getIntExtra("isAudit", -1);
        chengliang_success = false;

        mName.setText(fucDataModel.getUserName());
        mClassName.setText(fucDataModel.getClassName());
        mMobile.setText(fucDataModel.getMobile());
        mPeriodStart.setText(fucDataModel.getStartDate());
        mPeriodEnd.setText(fucDataModel.getEndDate());

    }

    private void connectBleDevice(QNBleDevice bleDevice) {
        qnUser = createUser();
        assert qnUser != null;
        qnBleApi.connectDevice(bleDevice, qnUser.getId(), qnUser.getHeight(), qnUser.getGender(), qnUser.getBirthday(), new QNBleCallback() {
            @Override
            public void onConnectStart(QNBleDevice qnBleDevice) {

            }

            @Override
            public void onConnected(QNBleDevice qnBleDevice) {
//                selfFragment.setStateTip("已连接，请上秤");
//                visitorFragment.setStateTip("已连接，请上秤");
//                selfFragment.setBleIcon(true);
//                visitorFragment.setBleIcon(true);
//                selfFragment.setClickable(false);
//                visitorFragment.setClickable(false);
//                selfFragment.setRenameIcon(true);
//                visitorFragment.setRenameIcon(true);
                if (isVoiceHelp) {
                    SoundPlay.getInstance().play(R.raw.help_two);
                }
                dialogDismiss();
                connectedDevice = qnBleDevice;
                deviceListDialog.clearBluetoothDevice();
            }

            @Override
            public void onDisconnected(QNBleDevice qnBleDevice, int i) {
                Toast.makeText(getApplicationContext(), "设备连接断开，请重新连接", Toast.LENGTH_SHORT).show();
//                selfFragment.setStateTip("点击连接莱秤");
//                visitorFragment.setStateTip("点击连接莱秤");
//                selfFragment.setBleIcon(false);
//                visitorFragment.setBleIcon(false);
//                selfFragment.setRenameIcon(false);
//                visitorFragment.setRenameIcon(false);
//                selfFragment.setClickable(true);
//                visitorFragment.setClickable(true);
                connectedDevice = null;
            }

            @Override
            public void onUnsteadyWeight(QNBleDevice qnBleDevice, float v) {
                if (!isReceiveData) {
                    return;
                }
                if (v <= 10) {
                    return;
                }
                if (isStartTesting) {
                    isStartTesting = false;
                    dialogShow("亲，请稍等，测量中...");
                    voiceOfTesting = Flowable.interval(2, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    if (isVoiceHelp) {
                                        SoundPlay.getInstance().play(R.raw.help_four);
                                    }
                                }
                            });

                    testingTimeout = Flowable.timer(20, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    testFail();
                                }
                            });
                }
            }

            @Override
            public void onReceivedData(QNBleDevice qnBleDevice, QNData qnData) {
//                if (!isReceiveData) {
//                    return;
//                }
//                if (qnData.getAll().size() < 3) {
//                    testFail();
//                    if (testingTimeout != null) {
//                        testingTimeout.dispose();
//                    }
//                    Log.d("maki", "测量姿势不对");
//                    return;
//                }
//                PostQnData data = createPostData(qnData);
//                long accountId = 0;
//                if (type == 0) {
//                    accountId = visitorFragment.getVisitorModel().getVisitorId();
//                } else if (type == 1) {
//                    accountId = UserInfoModel.getInstance().getUserId();
//                }
//                ZillaApi.NormalRestAdapter.create(NewBleService.class)
//                        .uploadTestData(UserInfoModel.getInstance().getToken(),
//                                accountId,
//                                type,
//                                "", data, new RequestCallback<ResponseData<BleResponseData>>() {
//                                    @Override
//                                    public void success(ResponseData<BleResponseData> bleResponseData, Response response) {
//                                        if (bleResponseData.getStatus() == 200) {
//                                            Log.d("maki", "访问成功");
//                                            mainData.setRecordId(bleResponseData.getData().getRecordId());
//                                            mainData.setBodyTypeTitle(bleResponseData.getData().getBodyTypeTile());
//                                            mainData.setBodyTypeColor(bleResponseData.getData().getBodyTypeColor());
//                                            if (pageIndex == 0) {
//                                                selfFragment.updateUI(mainData);
//                                            } else if (pageIndex == 1) {
//                                                visitorFragment.updateData(mainData);
//                                            }
//                                            dialogDismiss();
//                                            if (isVoiceHelp) {
//                                                SoundPlay.getInstance().play(R.raw.help_five);
//                                            }
//                                            if (testingTimeout != null) {
//                                                testingTimeout.dispose();
//                                            }
//                                            if (voiceOfTesting != null) {
//                                                voiceOfTesting.dispose();
//                                            }
//                                            selfFragment.setStateTip("测量完成");
//                                            visitorFragment.setStateTip("测量完成");
//                                        }
//                                    }
//
//                                    @Override
//                                    public void failure(RetrofitError error) {
//                                        super.failure(error);
//                                        ZillaApi.dealNetError(error);
//                                        Log.d("maki", error.toString());
//                                        dialogDismiss();
//                                        if (testingTimeout != null) {
//                                            testingTimeout.dispose();
//                                        }
//                                        testFail();
//                                    }
//                                });


                isStartTesting = true;
            }

            @Override
            public void onReceivedStoreData(QNBleDevice qnBleDevice, List<QNData> list) {

            }

            @Override
            public void onDeviceModelUpdate(QNBleDevice qnBleDevice) {

            }

            @Override
            public void onLowPower() {

            }

            @Override
            public void onCompete(int i) {

            }
        });
    }

    private QNUser createUser() {
        int gender;
        String birthdayString;
        Date birthday = null;
        int height;
        String userID;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        if (fucDataModel == null) {
            return null;
        }
        gender = Integer.valueOf(fucDataModel.getGender());
        if (gender == 0) {
            gender = 1;
        } else {
            gender = 0;
        }
        birthdayString = fucDataModel.getBirthDate();
        height = Integer.valueOf(fucDataModel.getHeight());
        userID = String.valueOf(AccountId);
        try {
            birthday = dateFormat.parse(birthdayString);
        } catch (Exception e) {
            Log.d("maki", "birthday is not init");
        }
        return new QNUser(userID, height, gender, birthday);
    }

    private void createTimeoutDialog() {
        if (testFailDialog == null) {
            Log.d("maki", "dialogNULL");
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.whiteDialog).setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.setMessage("测量失败，请重新测量");
            testFailDialog = builder.create();
        }
        if (!testFailDialog.isShowing()) {
            testFailDialog.setMessage("测量失败，请重新测量");
            testFailDialog.show();
        }
    }

    private void testFail() {
        createTimeoutDialog();
        dialogDismiss();
        isStartTesting = true;
        if (voiceOfTesting != null) {
            voiceOfTesting.dispose();
        }
        if (isVoiceHelp) {
            SoundPlay.getInstance().play(R.raw.help_six);
        }
    }

//    private PostQnData createPostData(QNData qnData) {
//        PostQnData postQnData = new PostQnData();
//        postQnData.setHeight(qnUser.getHeight());
//        int gender;
//        if (qnUser.getGender() == 0) {
//            gender = 1;
//        } else {
//            gender = 0;
//        }
//        postQnData.setGender(gender);
//        postQnData.setAge(0);
//        postQnData.setWeight(qnData.getWeight());
////        String weight = String.format("%.2f", qnData.getWeight() * 2);
//        String weight = String.valueOf(StringMath.fourRemoveFiveAdd2(String.valueOf(qnData.getWeight() * 2)));
//        mainData.setWeight(Double.parseDouble(weight));
//        postQnData.setWeight_unit("");
//        postQnData.setMeasure_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qnData.getCreateTime()));
//        Log.d("nishikinomaki", String.valueOf(qnData.getAll().size()));
//        for (int i = 0; i < qnData.getAll().size(); i++) {
//            int type = qnData.getAll().get(i).type;
//            switch (type) {
//                case QNData.TYPE_BMI:
//                    postQnData.setBmi(qnData.getAll().get(i).value);
//                    mainData.setBMI(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BMR:
//                    postQnData.setBmr(qnData.getAll().get(i).value);
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BODYFAT:
//                    postQnData.setBodyfat(qnData.getAll().get(i).value);
//                    mainData.setBodyFatRate(String.valueOf(qnData.getAll().get(i).value));
//                    mainData.setBodyFatRateUnit("%");
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BONE:
//                    postQnData.setBone(qnData.getAll().get(i).value);
//                    mainData.setBonemass(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_VISFAT:
//                    postQnData.setVisfat(qnData.getAll().get(i).value);
//                    mainData.setViscusFatIndex(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_WATER:
//                    postQnData.setWater(qnData.getAll().get(i).value);
//                    mainData.setWaterContentUnit(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BODYWATER:
//                    postQnData.setWater_mass(qnData.getAll().get(i).value);
//                    mainData.setWaterContent(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BODYAGE:
//                    postQnData.setBodyage(qnData.getAll().get(i).value);
//                    mainData.setPhysicalAge(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_SINEW:
//                    postQnData.setSinew(qnData.getAll().get(i).value);
//                    mainData.setMusclemass(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_FAT_FREE_WEIGHT:
//                    postQnData.setFat_free_weight(qnData.getAll().get(i).value);
//                    mainData.setFatFreemass(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BODYFAT_MASS:
//                    postQnData.setFat_mass(qnData.getAll().get(i).value);
//                    mainData.setBodyFat(String.valueOf(qnData.getAll().get(i).value));
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//                case QNData.TYPE_BODY_SHAPE:
//                    postQnData.setBody_shape(qnData.getAll().get(i).valueIndex);
////                postQnData.setBody_shape(Integer.parseInt(qnData.getAll().get(i).valueStr));
////                postQnData.setBody_shape(1);
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).valueStr));
//                    break;
//                case QNData.TYPE_SCORE:
//                    postQnData.setScore(qnData.getAll().get(i).value);
//                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
//                    break;
//            }
//
//
//        }
//
//        if (pageIndex == 0) {
//            postQnData.setBirthdate(UserInfoModel.getInstance().getUser().getBirthday());
//        } else {
//            postQnData.setBirthdate(visitorFragment.getVisitorModel().getBirthDate());
//        }
//        return postQnData;
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createLinkDialog() {
        deviceListDialog = new DeviceListDialog(this, R.style.ActivityDialogStyle);
        deviceListDialog.create();
        deviceListDialog.setBluetoothDialogListener(new DeviceListDialog.BluetoothDialogListener() {
            @Override
            public void bluetoothDialogClick(final int positions) {
                if (deviceListDialog.getQNBluetoothDevice(positions) != null && deviceListDialog.getQNBluetoothDevice(positions).getDeviceName() != null &&
                        deviceListDialog.getQNBluetoothDevice(positions).getMac() != null) {
                    deviceListDialog.dismiss();
//                    connectedDevice = deviceListDialog.getQNBluetoothDevice(positions);
                    dialogLag = Flowable.timer(500, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    connectBleDevice(deviceListDialog.getQNBluetoothDevice(positions));
                                }
                            });
                }
            }

            @Override
            public void bluetoothClose() {
                deviceListDialog.dismiss();
            }
        });

        deviceListDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
//                deviceListDialog.clearBluetoothDevice();
            }
        });
        deviceListDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                doStopScan();
                dialogLag = Flowable.timer(550, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                deviceListDialog.clearBluetoothDevice();
                            }
                        });
            }
        });
    }

    private void createChangeDialog() {
        LinearLayout mOld;
        LinearLayout mNew;
        ImageView mChooseImage;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose, null);
        mOld = dialogView.findViewById(R.id.ll_old);
        mNew = dialogView.findViewById(R.id.ll_new);
        mChooseImage = dialogView.findViewById(R.id.iv_new_choose);
        mChooseImage.setImageDrawable(getResources().getDrawable(R.drawable.radio_green));
        mOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Contacts.CHOOSE_KEY, "old");
                editor.apply();
                chooseDialog.dismiss();
                finish();
                startActivity(new Intent(NewRetestActivity.this, FuceForStuActivity.class));
            }
        });
        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog.dismiss();
            }
        });
        if (chooseDialog == null) {
            chooseDialog = new AlertDialog.Builder(this).create();
            chooseDialog.setView(dialogView, 0, 0, 0, 0);
        }
        chooseDialog.show();
    }

    /**
     * 停止扫描设备
     */
    private void doStopScan() {
        if (!qnBleApi.isScanning()) {
            return;
        }
        isFindDevice = false;
        qnBleApi.stopScan();
    }


    /**
     * 通用progressDialog
     *
     * @param value
     */
    public void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            if (!isFinishing()) {
                progressDialog = CustomProgress.build(this, value);
                progressDialog.show();
            }
        }
    }

    /**
     * 通用progressDialog
     */
    public void dialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

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
                    progressDialog = new ProgressDialog(NewRetestActivity.this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                createChangeDialog();
                break;
            case R.id.fl_left:
                showTipDialog();
                break;
            case R.id.fucecheck_entry: //复测审核
                progressDialog = new ProgressDialog(NewRetestActivity.this);
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
        }
    }

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
}
