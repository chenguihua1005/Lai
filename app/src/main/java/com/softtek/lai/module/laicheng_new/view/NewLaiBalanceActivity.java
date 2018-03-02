package com.softtek.lai.module.laicheng_new.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.kitnew.ble.QNBleScanCallback;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNUser;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.model.BasicModel;
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng.VisitorinfoActivity;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.module.laicheng.util.DeviceListDialog;
import com.softtek.lai.module.laicheng.util.SoundPlay;
import com.softtek.lai.module.laicheng.util.StringMath;
import com.softtek.lai.module.laicheng_new.model.BleResponseData;
import com.softtek.lai.module.laicheng_new.model.PostQnData;
import com.softtek.lai.module.laicheng_new.net.NewBleService;
import com.softtek.lai.module.laicheng_new.util.Contacts;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewLaiBalanceActivity extends FragmentActivity implements View.OnClickListener,
        NewSelfFragment.StartLinkListener,
        NewVisitorFragment.StartVisitorLinkListener,
        NewSelfFragment.RenameListener, NewVisitorFragment.RenameVisitorListener,
        NewSelfFragment.ChangeStyleListener,
        NewVisitorFragment.ChangeStyleListener,
        NewVisitorFragment.SetTypeListener {
    private static int PERMISSION_REQUEST_COARSE_LOCATION = 233;
    private static int PERMISSION_REQUEST_CALL_PHONE = 2333;
    private FrameLayout mLeftBack;
    private ViewPager mViewPager;
    private List<FragmentModel> fragmentModels = new ArrayList<>();
    private NewSelfFragment selfFragment;
    private NewVisitorFragment visitorFragment;
    private TabLayout mTab;
    private int pageIndex;
    private LinearLayout mTitle;
    private FrameLayout mTel;
    public static boolean isVoiceHelp = true;
    private QNBleApi qnBleApi;
    private QNUser qnUser;
    private DeviceListDialog deviceListDialog;
    private CustomProgress progressDialog;
    private Disposable connectTimeout;
    private Disposable testingTimeout;
    private Disposable voiceOfTesting;
    private Disposable dialogLag;
    private Disposable dialogDismissLag;
    private boolean isStartTesting = true;//是否开始测量
    private boolean isFindDevice = false;
    private AlertDialog testFailDialog;
    private AlertDialog.Builder noVisitorBuilder;
    private AlertDialog chooseDialog;
    private AlertDialog.Builder changeTpyeBuilder;
    private AlertDialog changeTypeDialog;

    private int type = 0;//0访客，1自己,4其他
    private QNBleDevice connectedDevice;
    private BleMainData mainData;
    private SharedPreferences sharedPreferences;
    private BluetoothAdapter bluetoothAdapter;
    private int algorithmType;

    private boolean isReceiveData = true;//重命名对话框弹出的时候是不接受数据的开关
    private BasicModel basicModel;
    private boolean isJump = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE );
        Intent intent = getIntent();
        if (intent != null){
            basicModel = intent.getParcelableExtra("model");
            isJump = intent.getBooleanExtra("isJump",false);
        }
        super.onCreate(savedInstanceState);
        LaiApplication.getInstance().setContext(new WeakReference<Context>(this));
        setContentView(R.layout.activity_laibalance_new);
        SoundPlay.getInstance().init(LaiApplication.getInstance().getApplicationContext());
        qnBleApi = QNApiManager.getApi(this);
        qnBleApi.setScanMode(QNBleApi.SCAN_MODE_ALL);
        qnBleApi.setWeightUnit(QNBleApi.WEIGHT_UNIT_KG);
        qnBleApi.setAlgorithm(QNBleApi.ALGORITHM_V1);
        createLinkDialog();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NewLaiBalanceActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
        initUi();
    }

    @SuppressLint("MissingPermission")
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
        } else if (requestCode == PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4009982913"));
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "请先获取拨打电话权限", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * 创建qn用户
     * @return
     */
    private QNUser createUser() {
        int gender;
        String birthdayString;
        Date birthday = null;
        int height;
        String userID;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        if (pageIndex == 0) {
            gender = Integer.valueOf(UserInfoModel.getInstance().getUser().getGender());
            if (gender == 0) {
                gender = 1;
            } else {
                gender = 0;
            }
            birthdayString = UserInfoModel.getInstance().getUser().getBirthday();
            double heightTemp = Double.valueOf(UserInfoModel.getInstance().getUser().getHight());
            height = (int) heightTemp;
            userID = String.valueOf(UserInfoModel.getInstance().getUserId());
        } else {
            if (visitorFragment.getVisitorModel() == null) {
                return null;
            }
            gender = visitorFragment.getVisitorModel().getGender();
            if (gender == 0) {
                gender = 1;
            } else {
                gender = 0;
            }
            birthdayString = visitorFragment.getVisitorModel().getBirthDate();
            height = (int) visitorFragment.getVisitorModel().getHeight();
            userID = String.valueOf(visitorFragment.getVisitorModel().getVisitorId());
        }
        try {
            birthday = dateFormat.parse(birthdayString);
        } catch (Exception e) {
            Log.d("maki", "birthday is not init");
        }
        return new QNUser(userID, height, gender, birthday);
    }

    /***
     * 测量超时对话框
     */
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

    /***
     * 链接蓝牙
     * @param bleDevice
     */
    private void connectBleDevice(QNBleDevice bleDevice) {
        qnUser = createUser();
        if (qnUser == null && type == 0) {
            showNoVisitorDialog();
            return;
        }
        assert qnUser != null;
        qnBleApi.connectDevice(bleDevice, qnUser.getId(), qnUser.getHeight(), qnUser.getGender(), qnUser.getBirthday(), new QNBleCallback() {
            @Override
            public void onConnectStart(QNBleDevice qnBleDevice) {

            }

            @Override
            public void onConnected(QNBleDevice qnBleDevice) {
                selfFragment.setStateTip("已连接");
                visitorFragment.setStateTip("已连接");
                selfFragment.setBleIcon(true);
                visitorFragment.setBleIcon(true);
                selfFragment.setClickable(false);
                visitorFragment.setClickable(false);
                selfFragment.setRenameIcon(true);
                visitorFragment.setRenameIcon(true);
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
                selfFragment.setStateTip("点击连接莱秤");
                visitorFragment.setStateTip("点击连接莱秤");
                if (!isFinishing()) {
                    selfFragment.setBleIcon(false);
                    visitorFragment.setBleIcon(false);
                    selfFragment.setRenameIcon(false);
                    visitorFragment.setRenameIcon(false);
                    selfFragment.setClickable(true);
                    visitorFragment.setClickable(true);
                    selfFragment.setInvisible();
                }
                Log.d("maki", "isStartTesting " + String.valueOf(isStartTesting));
                if (!isStartTesting) {
                    testFail();
                    if (testingTimeout != null) {
                        testingTimeout.dispose();
                    }
                }
                connectedDevice = null;
            }

            @Override
            public void onUnsteadyWeight(QNBleDevice qnBleDevice, float v) {
                if (connectTimeout != null){
                    if (!connectTimeout.isDisposed()) {
                        connectTimeout.dispose();
                    }
                }
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

                    testingTimeout = Flowable.timer(35, TimeUnit.SECONDS)
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
                if (connectTimeout != null){
                    if (!connectTimeout.isDisposed()) {
                        connectTimeout.dispose();
                    }
                }
                if (!isReceiveData) {
                    return;
                }
                if (qnData.getAll().size() < 3) {
                    testFail();
                    if (testingTimeout != null) {
                        testingTimeout.dispose();
                    }
                    Log.d("maki", "测量姿势不对");
                    return;
                }
                PostQnData data = createPostData(qnData);
                long accountId = 0;
                if (type != 1) {
                    accountId = visitorFragment.getVisitorModel().getVisitorId();
                } else {
                    accountId = UserInfoModel.getInstance().getUserId();
                }
                if (testingTimeout != null) {
                    testingTimeout.dispose();
                }
                ZillaApi.NormalRestAdapter.create(NewBleService.class)
                        .uploadTestData(UserInfoModel.getInstance().getToken(),
                                accountId,
                                type,
                                "", data, new RequestCallback<ResponseData<BleResponseData>>() {
                                    @Override
                                    public void success(ResponseData<BleResponseData> bleResponseData, Response response) {
                                        if (bleResponseData.getStatus() == 200) {
                                            Log.d("maki", "访问成功");
                                            mainData.setRecordId(bleResponseData.getData().getRecordId());
                                            mainData.setBodyTypeTitle(bleResponseData.getData().getBodyTypeTile());
                                            mainData.setBodyTypeColor(bleResponseData.getData().getBodyTypeColor());
                                            if (pageIndex == 0) {
                                                selfFragment.updateUI(mainData);
                                            } else if (pageIndex == 1) {
                                                visitorFragment.updateData(mainData);
                                            }
                                            dialogDismiss();
                                            if (isVoiceHelp) {
                                                SoundPlay.getInstance().play(R.raw.help_five);
                                            }

                                            if (voiceOfTesting != null) {
                                                voiceOfTesting.dispose();
                                            }
                                            selfFragment.setStateTip("测量完成");
                                            visitorFragment.setStateTip("测量完成");
                                        } else {
                                            testFail();
                                            if (testingTimeout != null) {
                                                testingTimeout.dispose();
                                            }
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                        ZillaApi.dealNetError(error);
                                        Log.d("maki", error.toString());
                                        if (voiceOfTesting != null) {
                                            voiceOfTesting.dispose();
                                        }
                                        dialogDismiss();
                                        testFail();
                                    }
                                });


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

    private PostQnData createPostData(QNData qnData) {
        PostQnData postQnData = new PostQnData();
        postQnData.setHeight(qnUser.getHeight());
        int gender;
        if (qnUser.getGender() == 0) {
            gender = 1;
        } else {
            gender = 0;
        }
        postQnData.setGender(gender);
        postQnData.setAge(0);
        postQnData.setWeight(qnData.getWeight());
//        String weight = String.format("%.2f", qnData.getWeight() * 2);
        String weight = String.valueOf(StringMath.fourRemoveFiveAdd2(String.valueOf(qnData.getWeight() * 2)));
        mainData.setWeight(Double.parseDouble(weight));
        postQnData.setWeight_unit("");
        postQnData.setMeasure_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qnData.getCreateTime()));
        Log.d("nishikinomaki", String.valueOf(qnData.getAll().size()));
        for (int i = 0; i < qnData.getAll().size(); i++) {
            int type = qnData.getAll().get(i).type;
            switch (type) {
                case QNData.TYPE_BMI:
                    postQnData.setBmi(qnData.getAll().get(i).value);
                    mainData.setBMI(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BMR:
                    postQnData.setBmr(qnData.getAll().get(i).value);
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BODYFAT:
                    postQnData.setBodyfat(qnData.getAll().get(i).value);
                    mainData.setBodyFatRate(String.valueOf(qnData.getAll().get(i).value));
                    mainData.setBodyFatRateUnit("%");
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BONE:
                    postQnData.setBone(qnData.getAll().get(i).value);
                    mainData.setBonemass(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_VISFAT:
                    postQnData.setVisfat(qnData.getAll().get(i).value);
                    mainData.setViscusFatIndex(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_WATER:
                    postQnData.setWater(qnData.getAll().get(i).value);
                    mainData.setWaterContentUnit(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BODYWATER:
                    postQnData.setWater_mass(qnData.getAll().get(i).value);
                    mainData.setWaterContent(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BODYAGE:
                    postQnData.setBodyage(qnData.getAll().get(i).value);
                    mainData.setPhysicalAge(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_SINEW:
                    postQnData.setSinew(qnData.getAll().get(i).value);
                    mainData.setMusclemass(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_FAT_FREE_WEIGHT:
                    postQnData.setFat_free_weight(qnData.getAll().get(i).value);
                    mainData.setFatFreemass(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BODYFAT_MASS:
                    postQnData.setFat_mass(qnData.getAll().get(i).value);
                    mainData.setBodyFat(String.valueOf(qnData.getAll().get(i).value));
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_BODY_SHAPE:
                    postQnData.setBody_shape(qnData.getAll().get(i).valueIndex);
//                postQnData.setBody_shape(Integer.parseInt(qnData.getAll().get(i).valueStr));
//                postQnData.setBody_shape(1);
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).valueStr));
                    break;
                case QNData.TYPE_SCORE:
                    postQnData.setScore(qnData.getAll().get(i).value);
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_HEART_INDEX:
                    postQnData.setHeart_index(qnData.getAll().get(i).value);
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
                    break;
                case QNData.TYPE_HEART_RATE:
                    postQnData.setHeart_rate((int) qnData.getAll().get(i).value);
                    Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            }


        }

        if (pageIndex == 0) {
            postQnData.setBirthdate(UserInfoModel.getInstance().getUser().getBirthday());
        } else {
            postQnData.setBirthdate(visitorFragment.getVisitorModel().getBirthDate());
        }
        return postQnData;
    }

    /**
     * 选择连接蓝牙的对话框
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createLinkDialog() {
        deviceListDialog = new DeviceListDialog(this, R.style.ActivityDialogStyle);
//        deviceListDialog.create();
        deviceListDialog.setBluetoothDialogListener(new DeviceListDialog.BluetoothDialogListener() {
            @Override
            public void bluetoothDialogClick(final int positions) {
                deviceListDialog.dismiss();
                if (deviceListDialog.getQNBluetoothDevice(positions) != null && deviceListDialog.getQNBluetoothDevice(positions).getDeviceName() != null &&
                        deviceListDialog.getQNBluetoothDevice(positions).getMac() != null) {
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
                dialogLag = Flowable.timer(600, TimeUnit.MILLISECONDS)
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

    /**
     * 开始扫描蓝牙设备
     */
    private void doStartScan() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
        if (qnBleApi.isScanning()) {
            return;
        }
        dialogShow("正在搜索设备");
        connectTimeout = Flowable.timer(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        if (!isFindDevice) {
                            dialogDismiss();
                            doStopScan();
                            Toast.makeText(LaiApplication.getInstance().getApplicationContext(), "未发现设备，请检查莱秤是否开启", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });

        qnBleApi.startLeScan(null, null, new QNBleScanCallback() {
            @Override
            public void onScan(QNBleDevice qnBleDevice) {
                isFindDevice = true;
                if (!deviceListDialog.isShowing()) {
                    deviceListDialog.show();
                }
                if (isFindDevice) {
                    deviceListDialog.addBluetoothDevice(qnBleDevice);
                }
                dialogDismiss();
                if (connectTimeout != null) {
                    if (!connectTimeout.isDisposed()) {
                        connectTimeout.dispose();
                    }
                }
            }

            @Override
            public void onCompete(int i) {

            }
        });
    }

    /**
     * 停止扫描设备
     */
    private void doStopScan() {
        if (!qnBleApi.isScanning()) {
            return;
        }
//        isFindDevice = false;
        qnBleApi.stopScan();
    }

    /**
     * 初始化UI
     */
    private void initUi() {
        sharedPreferences = getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);
        algorithmType = sharedPreferences.getInt(Contacts.MAKI_STYLE,QNBleApi.ALGORITHM_V1);
        selfFragment = NewSelfFragment.newInstance(isJump);
        visitorFragment = NewVisitorFragment.newInstance(isJump,basicModel);

        mLeftBack = findViewById(R.id.fl_left);
        mLeftBack.setOnClickListener(this);
        mViewPager = findViewById(R.id.vp_content);
        mTab = findViewById(R.id.tab_balance);
        mTitle = findViewById(R.id.ll_title);
        mTel = findViewById(R.id.fl_right);


        mTel.setOnClickListener(this);
        mTitle.setOnClickListener(this);

        fragmentModels.add(new FragmentModel("给自己测量", selfFragment));
        fragmentModels.add(new FragmentModel("给客户测", visitorFragment));
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new BalanceAdapter(getSupportFragmentManager(), fragmentModels));
        if (isJump) {
            mViewPager.setCurrentItem(1);
        }
        pageIndex = mViewPager.getCurrentItem();
        mTab.setupWithViewPager(mViewPager);
        final TabLayout.Tab tab = mTab.getTabAt(0);
        if (tab != null) {
            tab.setCustomView(R.layout.self_tab);
            TextView tv_tab = (TextView) tab.getCustomView().findViewById(R.id.tab_title);
            tv_tab.setText("给自己测");
            @SuppressLint("WrongViewCast")
            CircleImageView civ = (CircleImageView) tab.getCustomView().findViewById(R.id.iv_head);
            Picasso.with(this).load(AddressManager.get("photoHost") + UserInfoModel.getInstance().getUser().getPhoto())
                    .fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(civ);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageIndex = position;
            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                if (position == 0) {
                    selfFragment.refreshVoiceIcon();
                    type = 1;
                } else {
                    type = visitorFragment.getType();
                    visitorFragment.refreshVoiceIcon();
                }
                if (connectedDevice != null) {
                    qnBleApi.disconnectDevice(connectedDevice.getMac());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (mViewPager.getCurrentItem() == 0) {
            type = 0;
        } else {
            type = visitorFragment.getType();
        }
        mainData = new BleMainData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_left:
//                showGuideView();
                finish();
                break;
            case R.id.ll_title:
                createChangeDialog();
                break;
            case R.id.fl_right:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSION_REQUEST_CALL_PHONE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4009982913"));
                    startActivity(intent);
                }

        }
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
                Intent intent = new Intent(NewLaiBalanceActivity.this, LaibalanceActivity.class);
                intent.putExtra("isJump",isJump);
                intent.putExtra("model",basicModel);
                startActivity(intent);
                finish();
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

    private void createChooseTypeDialog() {
        Log.d("maki", String.valueOf(algorithmType) + "  algorithm");
        String message = "";
        if (algorithmType == QNBleApi.ALGORITHM_V1) {
            message = "您即将从”悠闲生活方式“切换到”健康锻炼生活方式”，哪一种生活方式符合您目前的生活状态呢？ 切换到”健康锻炼生活方式";
        } else if (algorithmType == QNBleApi.ALGORITHM_V2) {
            message = "您即将从”健康锻炼生活方式“切换到”悠闲生活方式”，哪一种生活方式符合您目前的生活状态呢？ 切换到”悠闲生活方式";
        }
        changeTpyeBuilder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (algorithmType == QNBleApi.ALGORITHM_V2) {
                            algorithmType = QNBleApi.ALGORITHM_V1;
                        } else {
                            algorithmType = QNBleApi.ALGORITHM_V2;
                        }
                        selfFragment.changeStyleImg(algorithmType);
                        visitorFragment.changeStyleImg(algorithmType);
                        qnBleApi.setAlgorithm(algorithmType);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(Contacts.MAKI_STYLE,algorithmType);
                        editor.apply();
                        changeTypeDialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeTypeDialog.dismiss();
                    }
                });
        changeTypeDialog = changeTpyeBuilder.create();
        if (!changeTypeDialog.isShowing()) {
            changeTypeDialog.show();
        }
    }

    /**
     * fragment的点击链接按钮
     */
    @Override
    public void onLinkListener() {
        doStartScan();
    }


    /**
     * fragment的点击链接按钮
     */
    @Override
    public void onLinkVisitorListener() {
        if (visitorFragment.getVisitorModel() == null && type != 1) {
            showNoVisitorDialog();
            return;
        }
        if (visitorFragment.isNeedReconnect()) {
            visitorFragment.setNeedReconnect(false);
            if (connectedDevice != null) {
                connectBleDevice(connectedDevice);
            }
        } else {
            doStartScan();
        }
    }

    private AlertDialog renameDialog;

    private void createRenameDialog() {
        isReceiveData = false;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText mInput = dialogView.findViewById(R.id.edt_rename);
        Button mRename = dialogView.findViewById(R.id.btn_rename);
        Button mCancel = dialogView.findViewById(R.id.btn_cancel);
        if (connectedDevice != null) {
            String name = sharedPreferences.getString(connectedDevice.getMac(), connectedDevice.getMac());
            mInput.setText(name);
        }
        mRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameDialog.dismiss();
                isReceiveData = true;

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (connectedDevice != null && !mInput.getText().toString().equals("")) {
                    editor.putString(connectedDevice.getMac(), mInput.getText().toString());
                    if (!LaiApplication.macLists.contains(connectedDevice.getMac())) {
                        LaiApplication.macLists.add(connectedDevice.getMac());
                    }
                    editor.apply();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameDialog.dismiss();
                isReceiveData = true;
            }
        });
        if (renameDialog == null) {
            renameDialog = new AlertDialog.Builder(this).create();
            renameDialog.setView(dialogView, 0, 0, 0, 0);
        }
        renameDialog.show();
    }

    @Override
    public void onRenameListener() {
        createRenameDialog();
    }

    @Override
    public void onVRenameListener() {
        createRenameDialog();
    }

    /**
     * 通用progressDialog
     *
     * @param value
     */
    public void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            if (!NewLaiBalanceActivity.this.isFinishing()) {
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

    /**
     * 没有访客信息时候显示对话框
     */
    private void showNoVisitorDialog() {
        if (noVisitorBuilder == null) {
            noVisitorBuilder = new AlertDialog.Builder(this, R.style.whiteDialog);
        }
        noVisitorBuilder.setMessage("您还没有录入完整的访客信息，请完善");
        noVisitorBuilder.setTitle("提示");
        noVisitorBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(NewLaiBalanceActivity.this, VisitorinfoActivity.class));
                dialog.dismiss();
            }
        });
        noVisitorBuilder.create();
        noVisitorBuilder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectTimeout != null) {
            connectTimeout.dispose();
        }
        if (testingTimeout != null) {
            testingTimeout.dispose();
        }
        if (dialogLag != null) {
            dialogLag.dispose();
        }
        if (voiceOfTesting != null) {
            voiceOfTesting.dispose();
        }
        if (dialogDismissLag != null){
            dialogDismissLag.dispose();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (qnBleApi != null) {
            if (connectedDevice != null) {
                qnBleApi.disconnectDevice(connectedDevice.getMac());
            }
        }
        dialogDismiss();
        if (voiceOfTesting != null) {
            voiceOfTesting.dispose();
        }
        isStartTesting = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onStyleTypeListener() {
        createChooseTypeDialog();
    }

    @Override
    public void onVisitorStyleTypeListener() {
        createChooseTypeDialog();
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }
}
