package com.softtek.lai.module.laicheng_new.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.widget.FrameLayout;
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
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.LaibalanceActivity;
import com.softtek.lai.module.laicheng.SelftestFragment;
import com.softtek.lai.module.laicheng.VisitorinfoActivity;
import com.softtek.lai.module.laicheng.adapter.BalanceAdapter;
import com.softtek.lai.module.laicheng.model.FragmentModel;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng.util.DeviceListDialog;
import com.softtek.lai.module.laicheng.util.SoundPlay;
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
import zilla.libcore.file.AddressManager;

import java.util.Date;

/**
 * Created by jia.lu on 2017/10/20.
 */

public class NewLaiBalanceActivity extends FragmentActivity implements View.OnClickListener, NewSelfFragment.StartLinkListener,NewVisitorFragment.StartVisitorLinkListener {
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
    private Disposable voiceDelay;
    private boolean isFindDevice = false;//是否找到过设备
    private boolean isStartTesting = true;//是否开始测量
    private AlertDialog dialog;
    private AlertDialog.Builder noVisitorBuilder;
    private int type = 1;//0自己，1访客

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laibalance_new);
        SoundPlay.getInstance().init(LaiApplication.getInstance().getApplicationContext());
        qnBleApi = QNApiManager.getApi(this);
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

    private QNUser createUser() {
        int gender;
        String birthdayString;
        Date birthday = null;
        int height;
        String userID;
//        if (pageIndex == 0) {
            gender = Integer.valueOf(UserInfoModel.getInstance().getUser().getGender());
            if (gender == 0) {
                gender = 1;
            } else {
                gender = 0;
            }
            birthdayString = UserInfoModel.getInstance().getUser().getBirthday();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
            try {
                birthday = dateFormat.parse(birthdayString);
            } catch (Exception e) {
                Log.d("maki", "birthday is not init");
            }
            height = Integer.valueOf(UserInfoModel.getInstance().getUser().getHight());
            userID = String.valueOf(UserInfoModel.getInstance().getUserId());
//        } else {
//
//        }
        return new QNUser(userID, height, gender, birthday);
    }

    private void createTimeoutDialog() {
        if (dialog == null) {
            Log.d("maki", "dialogNULL");
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.whiteDialog).setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.setMessage("测量超时，请重新测量");
            dialog = builder.create();
        }
        if (!dialog.isShowing()) {
            dialog.setMessage("测量超时，请重新测量");
            dialog.show();
        }
    }

    private void connectBleDevice(QNBleDevice bleDevice) {
        qnUser = createUser();
        qnBleApi.connectDevice(bleDevice, qnUser.getId(), qnUser.getHeight(), qnUser.getGender(), qnUser.getBirthday(), new QNBleCallback() {
            @Override
            public void onConnectStart(QNBleDevice qnBleDevice) {

            }

            @Override
            public void onConnected(QNBleDevice qnBleDevice) {
                selfFragment.setStateTip("已连接，请上秤");
                if (isVoiceHelp) {
                    SoundPlay.getInstance().play(R.raw.help_two);
                }
                dialogDismiss();
            }

            @Override
            public void onDisconnected(QNBleDevice qnBleDevice, int i) {
                Toast.makeText(getApplicationContext(), "设备连接断开，请重新连接", Toast.LENGTH_SHORT).show();
                selfFragment.setStateTip("点击按钮，连接莱秤");
            }

            @Override
            public void onUnsteadyWeight(QNBleDevice qnBleDevice, float v) {
                if (isStartTesting) {
                    isStartTesting = false;
                    dialogShow("亲，请稍等，测量中...");
                    if (isVoiceHelp) {
                        SoundPlay.getInstance().play(R.raw.help_three);
                    }
                    voiceDelay = Flowable.timer(3, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    voiceOfTesting = Flowable.interval(1, TimeUnit.SECONDS)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<Long>() {
                                                @Override
                                                public void accept(Long aLong) throws Exception {
                                                    if (isVoiceHelp) {
                                                        SoundPlay.getInstance().play(R.raw.help_four);
                                                    }
                                                }
                                            });
                                }
                            });
                    testingTimeout = Flowable.timer(20, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
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
                            });
                }
            }

            @Override
            public void onReceivedData(QNBleDevice qnBleDevice, QNData qnData) {
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
                }
            }

            @Override
            public void bluetoothClose() {
                deviceListDialog.dismiss();
            }
        });
    }

    private void doStartScan() {
        isFindDevice = false;
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
                        if (!isFindDevice) {
                            Toast.makeText(LaiApplication.getInstance().getApplicationContext(), "未发现设备，请检查莱秤是否开启", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        qnBleApi.startLeScan(null, null, new QNBleScanCallback() {
            @Override
            public void onScan(QNBleDevice qnBleDevice) {
                isFindDevice = true;
                if (!deviceListDialog.isShowing()) {
                    deviceListDialog.show();
                    dialogDismiss();
                }
                deviceListDialog.addBluetoothDevice(qnBleDevice);
            }

            @Override
            public void onCompete(int i) {

            }
        });
    }

    private void doStopScan() {
        if (!qnBleApi.isScanning()) {
            return;
        }
        qnBleApi.stopScan();
    }

    private void initUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NewLaiBalanceActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            } else {
            }
        } else {
        }

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
                if (position == 0){
                    selfFragment.refreshVoiceIcon();
                    type = 1;
                }else {
                    visitorFragment.refreshVoiceIcon();
                    type = 0;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_left:
                finish();
                break;
            case R.id.tv_title:
                finish();
                startActivity(new Intent(this, LaibalanceActivity.class));
        }
    }

    @Override
    public void onLinkListener() {
        doStartScan();
    }


    @Override
    public void onLinkVisitorListener() {
        if (visitorFragment.getVisitorModel() == null && type != 1) {
            showNoVisitorDialog();
            return;
        }
        doStartScan();
    }

    public void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = CustomProgress.build(this, value);
            progressDialog.show();

        }
    }

    public void dialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private void showNoVisitorDialog(){
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
        if (voiceDelay != null) {
            voiceDelay.dispose();
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
