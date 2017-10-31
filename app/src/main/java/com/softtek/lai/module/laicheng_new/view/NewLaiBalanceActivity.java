package com.softtek.lai.module.laicheng_new.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.mapcore.util.co;
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
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng.SelftestFragment;
import com.softtek.lai.module.laicheng.VisitorinfoActivity;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng.util.DeviceListDialog;
import com.softtek.lai.module.laicheng.util.SoundPlay;
import com.softtek.lai.module.laicheng_new.model.BleResponseData;
import com.softtek.lai.module.laicheng_new.model.PostQnData;
import com.softtek.lai.module.laicheng_new.net.NewBleService;
import com.softtek.lai.module.laicheng_new.util.Contacts;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.reactivestreams.Publisher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;

import java.util.Date;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewLaiBalanceActivity extends FragmentActivity implements View.OnClickListener, NewSelfFragment.StartLinkListener, NewVisitorFragment.StartVisitorLinkListener {
    private static int PERMISSION_REQUEST_COARSE_LOCATION = 233;
    private FrameLayout mLeftBack;
    private ViewPager mViewPager;
    private List<FragmentModel> fragmentModels = new ArrayList<>();
    private NewSelfFragment selfFragment;
    private NewVisitorFragment visitorFragment;
    private TabLayout mTab;
    private int pageIndex;
    private TextView mTitle;
    public static boolean isVoiceHelp = true;
    private QNBleApi qnBleApi;
    private QNUser qnUser;
    private DeviceListDialog deviceListDialog;
    private CustomProgress progressDialog;
    private Disposable connectTimeout;
    private Disposable testingTimeout;
    private Disposable voiceOfTesting;
    private boolean isStartTesting = true;//是否开始测量
    private AlertDialog testFailDialog;
    private AlertDialog.Builder noVisitorBuilder;
    private AlertDialog chooseDialog;

    private int type = 1;//0自己，1访客
    private QNBleDevice connectedDevice;
    private int count;
    private BleMainData mainData;
    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laibalance_new);
        SoundPlay.getInstance().init(LaiApplication.getInstance().getApplicationContext());
        qnBleApi = QNApiManager.getApi(this);
        qnBleApi.setScanMode(QNBleApi.SCAN_MODE_ALL);
        qnBleApi.setWeightUnit(QNBleApi.WEIGHT_UNIT_JIN);
        createLinkDialog();
        initUi();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("enter bleStateListener", "bleStateListener--------------");
            } else {
                Toast.makeText(this, "请先获取蓝牙位置权限", Toast.LENGTH_SHORT).show();
            }
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

            height = Integer.valueOf(UserInfoModel.getInstance().getUser().getHight());
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
                selfFragment.setStateTip("已连接，请上秤");
                visitorFragment.setStateTip("已连接，请上秤");
                selfFragment.setBleIcon(true);
                visitorFragment.setBleIcon(true);
                if (isVoiceHelp) {
                    SoundPlay.getInstance().play(R.raw.help_two);
                }
                dialogDismiss();
            }

            @Override
            public void onDisconnected(QNBleDevice qnBleDevice, int i) {
                Toast.makeText(getApplicationContext(), "设备连接断开，请重新连接", Toast.LENGTH_SHORT).show();
                selfFragment.setStateTip("点击连接莱秤");
                visitorFragment.setStateTip("点击连接莱秤");
                selfFragment.setBleIcon(false);
                visitorFragment.setBleIcon(false);
            }

            @Override
            public void onUnsteadyWeight(QNBleDevice qnBleDevice, float v) {
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
                if (qnData.getAll().size() < 3) {
                    testFail();
                    if (testingTimeout != null) {
                        testingTimeout.dispose();
                    }
                    Log.d("maki", "测量姿势不对");
                    return;
                }
                PostQnData data = createPostData(qnData);
                ZillaApi.NormalRestAdapter.create(NewBleService.class)
                        .uploadTestData(UserInfoModel.getInstance().getToken(),
                                (int) UserInfoModel.getInstance().getUserId(),
                                type,
                                "", data, new RequestCallback<ResponseData<BleResponseData>>() {
                                    @Override
                                    public void success(ResponseData<BleResponseData> bleResponseData, Response response) {
                                        if (bleResponseData.getStatus() == 200) {
                                            Log.d("maki", "访问成功");
                                            mainData.setRecordId(bleResponseData.getData().getRecordId());
                                            mainData.setBodyTypeTitle(bleResponseData.getData().getBodyTypeTile());
                                            mainData.setBodyTypeColor(bleResponseData.getData().getBodyTypeColor());
                                            selfFragment.updateUI(mainData);
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        super.failure(error);
                                        Log.d("maki", error.toString());
                                    }
                                });


                isStartTesting = true;
                dialogDismiss();
                if (isVoiceHelp) {
                    SoundPlay.getInstance().play(R.raw.help_five);
                }
                if (testingTimeout != null) {
                    testingTimeout.dispose();
                }
                if (voiceOfTesting != null) {
                    voiceOfTesting.dispose();
                }
                selfFragment.setStateTip("测量完成");
                visitorFragment.setStateTip("测量完成");
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
        postQnData.setGender(qnUser.getGender());
        postQnData.setAge(-1);
        postQnData.setWeight(qnData.getWeight());
        mainData.setWeight((int) qnData.getWeight() * 100 / 100);
        postQnData.setWeight_unit("");
        postQnData.setMeasure_time(new SimpleDateFormat("yyyy-MM-dd").format(qnData.getCreateTime()));
        Log.d("nishikinomaki", String.valueOf(qnData.getAll().size()));
        for (int i = 0; i < qnData.getAll().size(); i++) {
            int type = qnData.getAll().get(i).type;
            if (type == QNData.TYPE_BMI) {
                postQnData.setBmi(qnData.getAll().get(i).value);
                mainData.setBMI(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BMR) {
                postQnData.setBmr(qnData.getAll().get(i).value);
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BODYFAT) {
                postQnData.setBodyfat(qnData.getAll().get(i).value);
                mainData.setBodyFatRate(String.valueOf(qnData.getAll().get(i).value));
                mainData.setBodyFatRateUnit("%");
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BONE) {
                postQnData.setBone(qnData.getAll().get(i).value);
                mainData.setBonemass(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_VISFAT) {
                postQnData.setVisfat(qnData.getAll().get(i).value);
                mainData.setViscusFatIndex(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_WATER) {
                postQnData.setWater(qnData.getAll().get(i).value);
                mainData.setWaterContentUnit(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BODYWATER) {
                postQnData.setWater_mass(qnData.getAll().get(i).value);
                mainData.setWaterContent(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BODYAGE) {
                postQnData.setBodyage(qnData.getAll().get(i).value);
                mainData.setPhysicalAge(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_SINEW) {
                postQnData.setSinew(qnData.getAll().get(i).value);
                mainData.setMusclemass(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_FAT_FREE_WEIGHT) {
                postQnData.setFat_free_weight(qnData.getAll().get(i).value);
                mainData.setFatFreemass(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BODYFAT_MASS) {
                postQnData.setFat_mass(qnData.getAll().get(i).value);
                mainData.setBodyFat(String.valueOf(qnData.getAll().get(i).value));
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).value));
            } else if (type == QNData.TYPE_BODY_SHAPE) {
//                postQnData.setBody_shape(Integer.parseInt(qnData.getAll().get(i).valueStr));
                postQnData.setBody_shape(1);
                Log.d("nishikinomaki", String.valueOf(qnData.getAll().get(i).valueStr));
            } else if (type == QNData.TYPE_SCORE) {
                postQnData.setScore(qnData.getAll().get(i).value);
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
        deviceListDialog.create();
        deviceListDialog.setBluetoothDialogListener(new DeviceListDialog.BluetoothDialogListener() {
            @Override
            public void bluetoothDialogClick(int positions) {
                if (deviceListDialog.getQNBluetoothDevice(positions) != null && deviceListDialog.getQNBluetoothDevice(positions).getDeviceName() != null &&
                        deviceListDialog.getQNBluetoothDevice(positions).getMac() != null) {
                    deviceListDialog.dismiss();
                    connectBleDevice(deviceListDialog.getQNBluetoothDevice(positions));
                    connectedDevice = deviceListDialog.getQNBluetoothDevice(positions);
                }
            }

            @Override
            public void bluetoothClose() {
                deviceListDialog.dismiss();
            }
        });
    }

    /**
     * 开始扫描蓝牙设备
     */
    private void doStartScan() {
        if (qnBleApi.isScanning()) {
            return;
        }
        dialogShow("正在搜索设备");
        connectTimeout = Flowable.timer(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        dialogDismiss();
                        doStopScan();
                        count = 0;
                        Toast.makeText(LaiApplication.getInstance().getApplicationContext(), "未发现设备，请检查莱秤是否开启", Toast.LENGTH_SHORT).show();
                    }
                });

        qnBleApi.startLeScan(null, null, new QNBleScanCallback() {
            @Override
            public void onScan(QNBleDevice qnBleDevice) {
                count++;
                Log.d("maki", String.valueOf(count));
                deviceListDialog.addBluetoothDevice(qnBleDevice);
                if (count > 4) {
                    doStopScan();
                    Flowable.timer(1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    if (!deviceListDialog.isShowing()) {
                                        deviceListDialog.show();
                                    }
                                    dialogDismiss();
                                    count = 0;
                                    if (connectTimeout != null) {
                                        connectTimeout.dispose();
                                    }
                                }
                            });
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
        qnBleApi.stopScan();
    }

    /**
     * 初始化UI
     */
    private void initUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NewLaiBalanceActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            } else {

            }
        } else {

        }

        sharedPreferences = getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);
        selfFragment = NewSelfFragment.newInstance(null);
        visitorFragment = NewVisitorFragment.newInstance(null);

        mLeftBack = (FrameLayout) findViewById(R.id.fl_left);
        mLeftBack.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mTab = (TabLayout) findViewById(R.id.tab_balance);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setOnClickListener(this);

        fragmentModels.add(new FragmentModel("给自己测量", selfFragment));
        fragmentModels.add(new FragmentModel("给访客测", visitorFragment));
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new BalanceAdapter(getSupportFragmentManager(), fragmentModels));
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
                    visitorFragment.refreshVoiceIcon();
                    type = 0;
                }
                if (connectedDevice != null) {
                    qnBleApi.disconnectDevice(connectedDevice.getMac());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mainData = new BleMainData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_left:
                finish();
                break;
            case R.id.tv_title:
                createChangeDialog();
        }
    }

    private void createChangeDialog(){
        LinearLayout mOld;
        LinearLayout mNew;
        ImageView mChooseImage;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choose, null);
        mOld = (LinearLayout) dialogView.findViewById(R.id.ll_old);
        mNew = (LinearLayout) dialogView.findViewById(R.id.ll_new);
        mChooseImage = (ImageView)dialogView.findViewById(R.id.iv_new_choose);
        mChooseImage.setImageDrawable(getResources().getDrawable(R.drawable.radio_green));
        mOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Contacts.CHOOSE_KEY,"old");
                editor.apply();
                chooseDialog.dismiss();
                finish();
                startActivity(new Intent(NewLaiBalanceActivity.this,LaibalanceActivity.class));
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

    /**
     * 通用progressDialog
     *
     * @param value
     */
    public void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = CustomProgress.build(this, value);
            progressDialog.show();

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
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (qnBleApi != null) {
            qnBleApi.disconnectAll();
        }
    }

}
