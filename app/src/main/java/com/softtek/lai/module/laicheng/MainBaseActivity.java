package com.softtek.lai.module.laicheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
import com.softtek.lai.module.laicheng.model.UploadImpedanceModel;
import com.softtek.lai.module.laicheng.model.UserInfoEntity;
import com.softtek.lai.module.laicheng.net.BleService;
import com.softtek.lai.module.laicheng.shake.ShakeListener;
import com.softtek.lai.module.laicheng.sound.SoundPlay;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng.util.BleStateListener;
import com.softtek.lai.module.laicheng.util.DeviceListDialog;
import com.softtek.lai.module.laicheng.util.MathUtils;
import com.softtek.lai.module.laicheng.util.PermissionHelper;
import com.softtek.lai.module.laicheng.util.StringMath;
import com.softtek.lai.mpermission.MPermission;
import com.softtek.lai.mpermission.PermissionFail;
import com.softtek.lai.mpermission.PermissionOK;
import com.softtek.lai.utils.RequestCallback;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.senab.photoview.log.LogManager;
import zilla.libcore.api.ZillaApi;

public abstract class MainBaseActivity extends BleBaseActivity {

    private BluetoothGattCharacteristic readCharacteristic;//蓝牙读写数据的载体
    private BluetoothGattCharacteristic writeCharacteristic;//蓝牙读写数据的载体
    private int state_current = CONNECTED_STATE_SHAKE_IT;
    //    private ScaleDetailEntity mErrorScaleDetail;//测量错误的数据
    private String scaleId = "";//访客模式称量后的id
    private boolean isGuest = false;//以前的访客模式的称量页和这个页面合成一个，方便以后维护
    private int shareType;
    private boolean isVoiceHelp = false;

    private int mCloseVoiceTimeOut = 60;

    private String newData = "";//蓝牙临时数据
    private String mHandData = "";//握手数据
    private String mFrequency07Data = "";//07频段的数据
    private String mFrequency04Data = "";//04频段的数据
    private boolean isResultTest = false;
    private int testTimeOut = 60;

    //蓝牙连接状态
    private static final int CONNECTED_STATE_SHAKE_IT = 0;//称前摇一摇
    private static final int CONNECTED_STATE_LOADING = 1;//正在连接中，请稍后...
    private static final int CONNECTED_STATE_FAILED = 2;//连接失败，请重试
    private static final int CONNECTED_STATE_SUCCESS = 3;//已连接, 请上秤
    private static final int CONNECTED_STATE_WEIGHT = 4;//秤第一次通信
    private static final int CONNECTED_STATE_UPLOADING = 5;//阻抗数据上传
    private static final int CONNECTED_STATE_UPLOADING_SUCCESS = 6;//计算数据成功
    private static final int CONNECTED_STATE_UPLOADING_FAIL = 7;//计算数据失败
    private static final int CONNECTED_STATE_UPLOADING_TIMEOUT = 8;//连接超时

    protected BleStateListener bleStateListener;

    private boolean isCheckPermission = false;

    private DeviceListDialog deviceListDialog;

    private int position;
    private int bluetoothPosition;
    private boolean needReDraw;

    private ShakeListener mShakeListener;
    private Vibrator vibrator;

    private String BASE_URL = "http://qa-api.yunyingyang.com/";
    private String token;

    protected MPermission permission;

    private void shake() {
        openBluetoothSetting();
        mShakeListener.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isGuest = isGuested();
        mShakeListener = new ShakeListener(this);
        initUi();
        SoundPlay.getInstance().init(this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                if (state_current != CONNECTED_STATE_SHAKE_IT) {
                    return;
                }
                LogManager.getLogger().d(TAG, "bluetooth shakeing!!!");
                vibrator.vibrate(200);
                shake();
            }
        });
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        deviceListDialog = new DeviceListDialog(MainBaseActivity.this, R.style.ActivityDialogStyle);
        deviceListDialog.setBluetoothDialogListener(new DeviceListDialog.BluetoothDialogListener() {
            @Override
            public void bluetoothDialogClick(int positions) {
                bluetoothPosition = positions;
                if (deviceListDialog.getBluetoothDevice(bluetoothPosition) != null && deviceListDialog.getBluetoothDevice(bluetoothPosition).getName() != null &&
                        deviceListDialog.getBluetoothDevice(bluetoothPosition).getAddress() != null) {
//                    dialogShow("正在校验设备");
                    connectBluetooth(deviceListDialog.getBluetoothDevice(bluetoothPosition));
                    deviceListDialog.dismiss();
//                    GetExistEquipment getExistEquipment = new GetExistEquipment(MainBaseActivity.this, httpListener);
//                    getExistEquipment.request(deviceListDialog.getBluetoothDevice(bluetoothPosition).getAddress());
                }
            }

            @Override
            public void bluetoothClose() {
                cancelDiscoveryBluetooth();
                deviceListDialog.dismiss();
            }
        });

        deviceListDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancelDiscoveryBluetooth();
                mShakeListener.start();
            }
        });
        deviceListDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                cancelDiscoveryBluetooth();
                mShakeListener.start();
            }
        });

        bleStateListener = new BleStateListener() {
            @Override
            public void unableBleModule() {

            }

            @Override
            public void openBleSettingSuccess() {
                if (deviceListDialog != null) {//不知道为什么作为访客模式，连接成功蓝牙，然后返回，在进入，会出现2个activity
                    deviceListDialog.clearBluetoothDevice();
                    if (!deviceListDialog.isShowing()) {
                        deviceListDialog.show();
                        deviceListDialog.startScan();
                    }
                    startDiscoveryBluetooth();
                }
            }

            @Override
            public void openBleSettingCancel() {

            }

            @Override
            public void getBoundListBle(List<BluetoothDevice> BleDevices) {

            }

            @Override
            public void scanBleScanFound(BluetoothDevice device) {
                if (!TextUtils.isEmpty(device.getName()) && !TextUtils.isEmpty(device.getAddress()) && device.getName().contains("SHHC")) {
                    Log.d("addBluetoothDevice", device.getName());
//                    connectBluetooth(device);
                    deviceListDialog.addBluetoothDevice(device);

                }
            }

            @Override
            public void scanBleFinish() {
                deviceListDialog.finishScan();
                if (deviceListDialog.getNewDevicesArrayAdapter().getCount() == 0) {
                    deviceListDialog.getNewDevicesArrayAdapter().add("找不到设备");
                }
            }

            @Override
            public void BleConnectSuccess() {
                changeConnectionState(CONNECTED_STATE_SUCCESS);
                mShakeListener.stop();
            }

            @Override
            public void BleConnectFail() {
                changeConnectionState(CONNECTED_STATE_SHAKE_IT);
                mShakeListener.start();
            }

            @Override
            public void bleServicesDiscoveredSuccess(BluetoothGatt bluetoothGatt) {
                BluetoothGattService bluetoothGattService = bluetoothGatt.getService(BleManager.LIERDA_SERVICE_UUID);
                if (bluetoothGattService != null) {
                    readCharacteristic = bluetoothGattService.getCharacteristic(BleManager.LIERDA_READ_CHARACTERISTIC_UUID);
                    setNotificationCharacteristic(readCharacteristic);
                    writeCharacteristic = bluetoothGattService.getCharacteristic(BleManager.LIERDA_WRITE_CHARACTERISTIC_UUID);
                } else {
                    bluetoothGattService = bluetoothGatt.getService(BleManager.KERUIER_SERVICE_UUID);
                    if (bluetoothGattService != null) {
                        readCharacteristic = bluetoothGattService.getCharacteristic(BleManager.KERUIER_READ_CHARACTERISTIC_UUID);
                        setNotificationCharacteristic(readCharacteristic);
                        writeCharacteristic = bluetoothGattService.getCharacteristic(BleManager.KERUIER_WRITE_CHARACTERISTIC_UUID);
                    } else {
                        disconnectBluetooth();
                    }
                }
            }

            @Override
            public void BleConnectLost() {
                if (state_current == CONNECTED_STATE_WEIGHT) {
//                    dismissLoadingDialog();
                    changeConnectionState(CONNECTED_STATE_FAILED);
                } else {
                    changeConnectionState(CONNECTED_STATE_SHAKE_IT);
                }
//                showExceptionMessage("设备连接断开，请重新连接");
                if (isVoiceHelp) {//如果语音打开
                    isVoiceHelp = false;
//                    cVoice.setImageResource(R.drawable.animation_voice);
//                    AnimationDrawable animationDrawable = (AnimationDrawable) cVoice.getDrawable();
//                    animationDrawable.stop();
//                    cVoice.setImageResource(R.drawable.voice_four);
                    SoundPlay.getInstance().stop();
                }
                mShakeListener.start();
            }

            @Override
            public void BleRead(byte[] datas) {
                Toast.makeText(MainBaseActivity.this, "读取蓝牙数据", Toast.LENGTH_SHORT).show();
                if (state_current == CONNECTED_STATE_SUCCESS || state_current == CONNECTED_STATE_WEIGHT) {//只有称量的时候才会接收数据
                    String readMessage = MathUtils.bytesToHexString(datas);
                    newData += readMessage;
                    Log.d("dataMessage", "从蓝牙获取到的message" + readMessage);
                    if (validateMessage()) {
                        parseData();
                    }
                }
            }
        };
        ZillaApi.getCustomRESTAdapter(BASE_URL + "oauth/token/", new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        }).create(BleService.class)
                .getBleToken("client_credentials", "shhcieurjfn734js", "qieow8572jkcv", new RequestCallback<BleTokenResponse>() {
                    @Override
                    public void success(BleTokenResponse bleTokenResponse, Response response) {
                        token = bleTokenResponse.getAccess_token();
//                        storeOrSendCalcRsData(74.2f, 288.5f, 293.8f, 27.0f, 251.3f, 244.4f, 255.2f, 260.5f, 23.4f, 216.0f, 211.0f);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                        Toast.makeText(getApplicationContext(), "获取token失败", Toast.LENGTH_SHORT).show();
                        token = "";
                    }
                });
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case HANDLER_REFRESH_BOTTOM:
//                    Float[] data = (Float[]) msg.obj;
//                    tv_fatrate.setText(data[0] == 0 ? "0" : "" + data[0]);
//                    tv_musclemass.setText(data[1] == 0 ? "0" : "" + data[1]);
//                    tv_basalmetabolicrate.setText("" + StringMath.fourRemoveFiveAdd("" + data[2]));
//                    tv_bodyage.setText("" + StringMath.fourRemoveFiveAdd("" + data[3]));
//                    break;
//                case HANDLER_REFRESH_NET:
//                    tv_fatrate.setText("0");
//                    tv_musclemass.setText("0");
//                    tv_basalmetabolicrate.setText("0");
//                    tv_bodyage.setText("0");
//                    int userId = (Integer) msg.obj;
//                    requestScaleDetailFromNet(userId);
//                    break;
            }
            super.handleMessage(msg);
        }
    };

    //阻抗数据错误，数据清空
    private void bluetoothDataError() {
        newData = "";
        mHandData = "";
        mFrequency04Data = "";
        mFrequency07Data = "";

        isResultTest = true;
//        dismissLoadingDialog();
        sendFatRateToDevice(0.0f);
        changeConnectionState(CONNECTED_STATE_UPLOADING_FAIL);
        testTimeOut = 0;//超时时间
    }

    //校验蓝牙数据
    private boolean validateMessage() {
        if (newData == null) {
            Log.d("validateMessage", "newDate==null 返回false");
            return false;
        }
        newData = newData.replaceAll(" ", "");
        if (!newData.startsWith("6495")) {
            if (newData.indexOf("64950102f2") >= 0) {
                newData = "64950102f2";

            }
            return false;
        }
        Log.d("validateMessag.", "newDate开头是6495");
        if (newData.length() < 8) {
            Log.d("validateMessag.", "newDate长度小于8验证失败");
            return false;
        }
        if (newData.substring(6, 8).equals("02")) {//握手
            Log.d("validateMessag.", "开始握手");
            mFrequency04Data = "";
            mFrequency07Data = "";
            int size = Integer.parseInt(newData.substring(4, 6), 16);//1
            if (newData.length() == (8 + size * 2)) {
                mHandData = newData;
                newData = "";
                Log.d("validateMessag.", "握手成功");
                return true;
            }
            Log.d("validateMessag.", "握手失败");
        } else if (newData.substring(6, 8).equals("08")) {//阻抗
            Log.d("validateMessag.", "阻抗数据开始验证");
            mHandData = "";
            int count = StringMath.howMany(newData, "6495");
            if (count == 1) {//2次频率的阻抗都传过来了在解析
                Log.d("validateMessag.", "总归6495的次数为1，继续等待下次传输");
                return false;
            } else if (count == 2) {
                Log.d("validateMessag.", "总归6495的次数为2，正常校验");
                String tem = newData.substring(newData.lastIndexOf("6495"), newData.length());
                int temSize = Integer.parseInt(tem.substring(4, 6), 16);
                Log.d("validateMessag.", "tem=" + tem + "且temLenght=" + tem.length() + ";temSize=" + temSize);
                if (tem.length() == (8 + temSize * 2)) {//数据完整
                    String hzType = tem.substring(8, 10);//频段号
                    Log.d("validateMessag.", "频段号=" + hzType);
                    if (hzType.equals("04") || hzType.equals("03")) {

                        mFrequency04Data = tem;
                        Log.d("validateMessag.", "第二位是04HZ，mFrequency04Data=" + mFrequency04Data);
                        tem = newData.substring(0, newData.lastIndexOf("6495"));
                        temSize = Integer.parseInt(tem.substring(4, 6), 16);
                        if (tem.length() == (8 + temSize * 2)) {//数据完整
                            mFrequency07Data = tem;
                            newData = "";
                            Log.d("validateMessag.", "第一位是07HZ，mFrequency07Data=" + mFrequency07Data);
                            return true;
                        } else {//数据不完整
                            bluetoothDataError();
                        }
                    } else if (hzType.equals("07") || hzType.equals("05")) {
                        mFrequency07Data = tem;
                        Log.d("validateMessag.", "第二位是07HZ，mFrequency07Data=" + mFrequency07Data);
                        tem = newData.substring(0, newData.lastIndexOf("6495"));
                        temSize = Integer.parseInt(tem.substring(4, 6), 16);
                        if (tem.length() == (8 + temSize * 2)) {//数据完整
                            mFrequency04Data = tem;
                            newData = "";
                            Log.d("validateMessag.", "第一位是04HZ，mFrequency04Data=" + mFrequency04Data);
                            return true;
                        } else {//数据不完整
                            bluetoothDataError();
                            Log.d("validateMessage", "数据不完整2");
                        }
                    } else {
                        //不会出现别的频段号
                        bluetoothDataError();
                        Log.d("validateMessage", "不会出现别的频段号");
                    }
                }//数据不完整
            } else {
                //不会出现超过2次的时候，如果出现就清空
                bluetoothDataError();
                Log.d("validateMessage", "不会出现超过2次的时候，如果出现就清空");
            }
        }

        //****************************************************************
        return false;
    }

    private void parseData() {
        if (!TextUtils.isEmpty(mHandData) && TextUtils.isEmpty(mFrequency04Data) && TextUtils.isEmpty(mFrequency07Data)) {//提交体重数据
            testTimeOut = 60;
            isResultTest = false;//是否有测量结果
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (testTimeOut == 0) {
                        if (!isResultTest) {//如果状态还属于连接成功，第一次交互，提交阻抗过程中
                            changeConnectionState(CONNECTED_STATE_UPLOADING_TIMEOUT);
                            Log.d("sendFatRateToDevice", "发送给莱称0.0f的数据");
                            sendFatRateToDevice(0.0f);
                        }
                    } else {
                        testTimeOut--;
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);//超时时间1分钟
            changeConnectionState(CONNECTED_STATE_WEIGHT);
            sendUserInfo();
        } else {//阻抗，上边已经校验过数据，这个函数除了握手就是阻抗数值已经正确了
            parseOriginalData();
        }
    }

    private void parseOriginalData() {
        Log.d("parseOriginalData", "进入上传服务器数据");
        int size = Integer.parseInt(mFrequency04Data.substring(4, 6), 16);//数据长度
        String data04 = mFrequency04Data.substring(8, 10 + size * 2 - 2);
        float weight = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data04.substring(2, 10)));
        int channelType = Integer.parseInt(data04.substring(10, 12), 16);//通道类型 0-双通道， 1-六通道， 2-五段法

        if (0 == channelType) {

        } else if (1 == channelType) {

        } else if (2 == channelType || 3 == channelType) {
            float f04RS1 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data04.substring(12, 20)));
            float f04RS2 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data04.substring(20, 28)));
            float f04RS3 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data04.substring(28, 36)));
            float f04RS4 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data04.substring(36, 44)));
            float f04RS5 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data04.substring(44, 52)));
            size = Integer.parseInt(mFrequency07Data.substring(4, 6), 16);//数据长度
            String data07 = mFrequency07Data.substring(8, 10 + size * 2 - 2);
            float f07RS1 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data07.substring(12, 20)));
            float f07RS2 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data07.substring(20, 28)));
            float f07RS3 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data07.substring(28, 36)));
            float f07RS4 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data07.substring(36, 44)));
            float f07RS5 = MathUtils.hexStrToFloat(MathUtils.calcSmallModeData(data07.substring(44, 52)));
            storeOrSendCalcRsData(weight, f04RS1, f04RS2, f04RS3, f04RS4, f04RS5, f07RS1, f07RS2, f07RS3, f07RS4, f07RS5);
            mFrequency04Data = "";
            mFrequency07Data = "";
        }

    }

    private void sendFatRateToDevice(float fatRate) {
        try {
            System.out.println("###fatRate = " + fatRate);
            fatRate = fatRate + 0.01f;
            System.out.println("###fatRate = " + fatRate);
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("64950517");
            StringBuilder stringBuilder = new StringBuilder();
            String fat = Integer.toHexString(Float.floatToIntBits(fatRate));
//    		String fat = Float.toHexString(fatRate);
            //4个字节
            if (fat.length() < 8) {
                for (int i = 0; i < 16 - fat.length(); i++) {
                    stringBuilder.append("0");
                }
            }
            stringBuilder.append(MathUtils.calcSmallModeData(fat));
            sBuilder.append(stringBuilder);
            sBuilder.append(MathUtils.xorHexString(sBuilder.toString()));
            LogManager.getLogger().d(TAG, "sendFatRateToDevice with data: " + sBuilder.toString());
            sendMessages(sBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessages(String message) {
        if (message.length() > 0) {
            writeCharacteristicData(message, writeCharacteristic);
        }
    }

    private void changeConnectionState(int state) {
        if (state != CONNECTED_STATE_LOADING) {
//            iv_connect_state.clearAnimation();
        }
        switch (state) {
            case CONNECTED_STATE_SHAKE_IT:
                //称前摇一摇
                state_current = CONNECTED_STATE_SHAKE_IT;
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_shake_it);
//                tv_connect_state.setText(R.string.mainpage_connection_shake_it);
                break;
            case CONNECTED_STATE_LOADING:
                state_current = CONNECTED_STATE_LOADING;
                //正在连接中，请稍后...
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_loading);
//                Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_aways);
//                anim.setInterpolator(new LinearInterpolator());
//                iv_connect_state.startAnimation(anim);
//                tv_connect_state.setText(R.string.mainpage_connection_loading);
                break;
            case CONNECTED_STATE_FAILED:
                //连接失败，请重试
                state_current = CONNECTED_STATE_FAILED;
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_failed);
//                tv_connect_state.setText(R.string.mainpage_connection_faild);
                break;
            case CONNECTED_STATE_SUCCESS:
                //已连接, 请上秤
                state_current = CONNECTED_STATE_SUCCESS;
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_success);
//                tv_connect_state.setText(R.string.mainpage_connection_success);
                if (isVoiceHelp) {
                    mCloseVoiceTimeOut = 60;
                    SoundPlay.getInstance().play(R.raw.help_two);
                }
                break;
            case CONNECTED_STATE_WEIGHT:
//                showNoCancelLoadingDialog("亲，请稍等，测量中...");
                state_current = CONNECTED_STATE_WEIGHT;
                if (isVoiceHelp) {
                    SoundPlay.getInstance().play(R.raw.help_three);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isVoiceHelp && state_current == CONNECTED_STATE_WEIGHT) {
                                SoundPlay.getInstance().playWait(R.raw.help_four);
                                handler.postDelayed(this, 2000);
                            }
                        }
                    }, 2000);
                }
//                if (testSuccessDialog != null && testSuccessDialog.isShowing()) {
//                    testSuccessDialog.dismiss();
//                }
//                if (testErrorDialog != null && testErrorDialog.isShowing()) {
//                    testErrorDialog.dismiss();
//                }
//                if (testFailDialog != null && testFailDialog.isShowing()) {
//                    testFailDialog.dismiss();
//                }
                break;
            case CONNECTED_STATE_UPLOADING:
                //阻抗数据上传
                state_current = CONNECTED_STATE_UPLOADING;
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_loading);
//                tv_connect_state.setText(R.string.mainpage_connection_uploading);
                break;
            case CONNECTED_STATE_UPLOADING_SUCCESS:
                state_current = CONNECTED_STATE_SUCCESS;
                SoundPlay.getInstance().stop();
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_success);
//                tv_connect_state.setText(R.string.mainpage_connection_success);
                if (isVoiceHelp) {
                    SoundPlay.getInstance().playAndStop(R.raw.help_five);
                    isVoiceHelp = false;
//                    cVoice.setImageResource(R.drawable.animation_voice);
//                    AnimationDrawable animationDrawable = (AnimationDrawable) cVoice.getDrawable();
//                    animationDrawable.stop();
//                    cVoice.setImageResource(R.drawable.voice_four);
                }
                break;
            case CONNECTED_STATE_UPLOADING_FAIL:
                state_current = CONNECTED_STATE_SUCCESS;
                SoundPlay.getInstance().stop();
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_success);
//                tv_connect_state.setText(R.string.mainpage_connection_success);
                if (isVoiceHelp) {
                    SoundPlay.getInstance().playAndStop(R.raw.help_six);
                    isVoiceHelp = false;
//                    cVoice.setImageResource(R.drawable.animation_voice);
//                    AnimationDrawable animationDrawable = (AnimationDrawable) cVoice.getDrawable();
//                    animationDrawable.stop();
//                    cVoice.setImageResource(R.drawable.voice_four);
                }
                AlertDialog.Builder failBuilder = new AlertDialog.Builder(this, R.style.whiteDialog);
                failBuilder.setMessage("测量失败，请重新测量");
                failBuilder.setTitle("提示");
                failBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
//                testFailDialog = failBuilder.create();
//                try {
//                    testFailDialog.show();
//                } catch (Exception e) {
//                    testFailDialog = null;
//                }
                break;
            case CONNECTED_STATE_UPLOADING_TIMEOUT:
                state_current = CONNECTED_STATE_SUCCESS;
//                dismissLoadingDialog();
                SoundPlay.getInstance().stop();
//                iv_connect_state.setBackgroundResource(R.drawable.pic_mainpage_connect_success);
//                tv_connect_state.setText(R.string.mainpage_connection_success);
                if (isVoiceHelp) {
                    SoundPlay.getInstance().playAndStop(R.raw.help_six);
                    isVoiceHelp = false;
//                    cVoice.setImageResource(R.drawable.animation_voice);
//                    AnimationDrawable animationDrawable = (AnimationDrawable) cVoice.getDrawable();
//                    animationDrawable.stop();
//                    cVoice.setImageResource(R.drawable.voice_four);
                }
//
                AlertDialog.Builder timeOutBuilder = new AlertDialog.Builder(MainBaseActivity.this, R.style.whiteDialog);
                timeOutBuilder.setMessage("测量超时，请重新测量");
                timeOutBuilder.setTitle("提示");
                timeOutBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
//                testFailDialog = timeOutBuilder.create();
//                try {
//                    testFailDialog.show();
//                } catch (Exception e) {
//                    testFailDialog = null;
//                } finally {
                newData = "";
                mHandData = "";
                mFrequency04Data = "";
                mFrequency07Data = "";
//                }
                break;

            default:
                break;
        }
    }

    private void sendUserInfo() {
        Log.d("sendUserInfo---------", "sendUserInfo");
        UserInfoEntity user = new UserInfoEntity();
        user.setId(556383);
        user.setGender(Integer.valueOf(UserInfoModel.getInstance().getUser().getGender()));
        user.setHeight(Float.valueOf(UserInfoModel.getInstance().getUser().getHight()));
        user.setBirthdate(UserInfoModel.getInstance().getUser().getBirthday());
        user.setId(556383);
        user.setPhone("13093096152");
        user.setUsername("test");
        try {
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("64950c02");
            int userId = user.getId();
            int height = (int) user.getHeight();
            int sex = 00;
            //	用户性别：1字节，
            //	00：男性
            //	01：女性，孕早期或孕前或产后（孕<12周）。
            //	02：女性，孕中期（孕13～27周）。
            //	其他：女性，孕晚期（孕28周～分娩）
            if (1 != user.getGender()) {
                sex = 01;
            }

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int age = MathUtils.getAgeByBirthday(sdf.parse(user.getBirthdate()));

            //构造数据
            StringBuilder stringBuilder = new StringBuilder();
            String userID = Long.toHexString(userId);
            //8个字节
            if (userID.length() < 16) {
                for (int i = 0; i < 16 - userID.length(); i++) {
                    stringBuilder.append("0");
                }
            }
            stringBuilder.append(userID);
            for (int i = 7; i >= 0; i--) {
                sBuilder.append(stringBuilder.toString().substring(i * 2, i * 2 + 2));
            }
            sBuilder.append(Integer.toHexString(height).length() == 1 ? "0" + Integer.toHexString(height) : Integer.toHexString(height));
            sBuilder.append(Integer.toHexString(sex).length() == 1 ? "0" + Integer.toHexString(sex) : Integer.toHexString(sex));
            sBuilder.append(Integer.toHexString(age).length() == 1 ? "0" + Integer.toHexString(age) : Integer.toHexString(age));
            sBuilder.append(MathUtils.xorHexString(sBuilder.toString()));
            Log.d(TAG, "sendUserInfo with data: " + sBuilder.toString());
            sendMessages(sBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void storeOrSendCalcRsData(final float weight, final float f04RS1, final float f04RS2, final float f04RS3,
                                       final float f04RS4, final float f04RS5, final float f07RS1, final float f07RS2,
                                       final float f07RS3, final float f07RS4, final float f07RS5) {

        UploadImpedanceModel model = new UploadImpedanceModel();
        model.setAccess_token(token);
        model.setR10(String.valueOf(f04RS1));
        model.setR11(String.valueOf(f04RS2));
        model.setR12(String.valueOf(f04RS3));
        model.setR13(String.valueOf(f04RS4));
        model.setR14(String.valueOf(f04RS5));
        model.setR20(String.valueOf(f07RS1));
        model.setR21(String.valueOf(f07RS2));
        model.setR22(String.valueOf(f07RS3));
        model.setR23(String.valueOf(f07RS4));
        model.setR24(String.valueOf(f07RS5));
        if (isGuest) {
            model.setWeight(String.valueOf(getGuestInfo().getWeight()));
            model.setHeight(String.valueOf(getGuestInfo().getHeight()));
            model.setBirthdate(String.valueOf(getGuestInfo().getBirthdate()));
            model.setGender(getGuestInfo().getGender());
        } else {
            model.setWeight(String.valueOf(weight));
            model.setHeight(UserInfoModel.getInstance().getUser().getHight());
            model.setBirthdate(String.valueOf(UserInfoModel.getInstance().getUser().getBirthday()));
            model.setGender(UserInfoModel.getInstance().getUser().getGender().equals("0") ? 2 : 1);
        }


        ZillaApi.getCustomRESTAdapter(BASE_URL + "DataSync/UploadData", new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        }).create(BleService.class).uploadImpedance(model, new RequestCallback<BleMainData>() {
            @Override
            public void success(BleMainData data, Response response) {
                initUiByBleSuccess(data);
                sendFatRateToDevice(2.333f);

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                initUiByBleFailed();
            }
        });
        Log.d("上传给服务器的逻辑------------", "storeOrSendCalcRsData上传");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectBluetooth();
        deviceListDialog = null;
        SoundPlay.getInstance().release();
        permission.recycle();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShakeListener.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SoundPlay.getInstance().stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShakeListener.start();
    }

    public abstract void initUi();

    public abstract void initUiByBleSuccess(BleMainData data);

    public abstract void initUiByBleFailed();

    public abstract boolean isGuested();

    public abstract UserInfoEntity getGuestInfo();
}
