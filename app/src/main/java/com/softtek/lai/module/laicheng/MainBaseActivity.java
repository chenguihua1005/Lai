package com.softtek.lai.module.laicheng;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
import com.softtek.lai.module.laicheng.model.UploadImpedanceModel;
import com.softtek.lai.module.laicheng.model.UserInfoEntity;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.presenter.BleBasePresenter;
import com.softtek.lai.module.laicheng.shake.ShakeListener;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng.util.BleStateListener;
import com.softtek.lai.module.laicheng.util.DeviceListDialog;
import com.softtek.lai.module.laicheng.util.MathUtils;
import com.softtek.lai.module.laicheng.util.StringMath;
import com.softtek.lai.mpermission.MPermission;
import com.softtek.lai.sound.SoundHelper;

import java.text.SimpleDateFormat;
import java.util.List;

import uk.co.senab.photoview.log.LogManager;

public abstract class MainBaseActivity extends BleBaseActivity implements BleBasePresenter.BleBaseView {

    private BluetoothGattCharacteristic readCharacteristic;//蓝牙读写数据的载体
    private BluetoothGattCharacteristic writeCharacteristic;//蓝牙读写数据的载体
    private int state_current = CONNECTED_STATE_SHAKE_IT;
    //    private ScaleDetailEntity mErrorScaleDetail;//测量错误的数据
    private String scaleId = "";//访客模式称量后的id
    private boolean isGuest = false;//以前的访客模式的称量页和这个页面合成一个，方便以后维护
    private int shareType;
    protected boolean isVoiceHelp = true;

//    private int mCloseVoiceTimeOut = 60;

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

    private DeviceListDialog deviceListDialog;

    private int position;
    private int bluetoothPosition;
    private boolean needReDraw;

    protected ShakeListener mShakeListener;
    private Vibrator vibrator;

    private String BASE_URL = "http://qa-api.yunyingyang.com/";
    private String token;

    protected MPermission permission;

    protected SoundHelper soundHelper;

    private BleBasePresenter presenter;

    private volatile int voiceIndex = 0;

    private void shake() {
        openBluetoothSetting();
        if (voiceIndex != 1) {
            voiceIndex = 1;
            soundHelper.play("one");
        }
        mShakeListener.stop();
    }

    protected void setGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }

    private boolean getGuest() {
        return isGuest;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permission = MPermission.with(this);
        mShakeListener = new ShakeListener(this);
        addVoice();

        presenter = new BleBasePresenter(this);

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

        //选择的蓝牙的对话框
        deviceListDialog = new DeviceListDialog(MainBaseActivity.this, R.style.ActivityDialogStyle);
        deviceListDialog.setBluetoothDialogListener(new DeviceListDialog.BluetoothDialogListener() {
            @Override
            public void bluetoothDialogClick(int positions) {
                bluetoothPosition = positions;
                if (deviceListDialog.getBluetoothDevice(bluetoothPosition) != null && deviceListDialog.getBluetoothDevice(bluetoothPosition).getName() != null &&
                        deviceListDialog.getBluetoothDevice(bluetoothPosition).getAddress() != null) {
                    dialogShow("正在校验设备");
                    presenter.checkMac(token, deviceListDialog.getBluetoothDevice(positions).getAddress());
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
                voiceIndex = 0;
                mShakeListener.start();
            }
        });
        deviceListDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                cancelDiscoveryBluetooth();
                voiceIndex = 0;
                mShakeListener.start();
            }
        });


        //蓝牙回调
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

            @SuppressLint("LongLogTag")
            @Override
            public void BleConnectSuccess() {
                changeConnectionState(CONNECTED_STATE_SUCCESS);
                Log.e("CONNECTED_STATE_SUCCESS-----", "CONNECTED_STATE_SUCCESS");
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
                    dialogDissmiss();
                    changeConnectionState(CONNECTED_STATE_FAILED);
                } else {
                    changeConnectionState(CONNECTED_STATE_SHAKE_IT);
                }
                Toast.makeText(getApplicationContext(), "设备连接断开，请重新连接", Toast.LENGTH_SHORT).show();
                mShakeListener.start();
            }

            @Override
            public void BleRead(byte[] datas) {
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
        presenter.getToken();
        initUi();
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
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    if (testTimeOut == 0) {
                        if (!isResultTest) {//如果状态还属于连接成功，第一次交互，提交阻抗过程中
                            changeConnectionState(CONNECTED_STATE_UPLOADING_TIMEOUT);
                            Log.d("CONNECTED_STATE_UPLOADING_TIMEOUT-------", "超时---------");
                            sendFatRateToDevice(0.0f);
                        }
                    } else {
                        testTimeOut--;
                        handler.postDelayed(this, 1000);
                        if (!isConnected) {
                            testTimeOut = 0;
                        }
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

    protected void changeConnectionState(int state) {
        switch (state) {
            case CONNECTED_STATE_SHAKE_IT:
                //称前摇一摇
                state_current = CONNECTED_STATE_SHAKE_IT;
                setStateTip("摇一摇，链接莱秤");

                break;
            case CONNECTED_STATE_LOADING:
                state_current = CONNECTED_STATE_LOADING;
                //正在连接中，请稍后...
                break;
            case CONNECTED_STATE_FAILED:
                //连接失败，请重试
                state_current = CONNECTED_STATE_FAILED;
                break;
            case CONNECTED_STATE_SUCCESS:
                //已连接, 请上秤
                setStateTip("已链接，请上秤");
                state_current = CONNECTED_STATE_SUCCESS;
                if (voiceIndex != 2) {
                    voiceIndex = 2;
                    soundHelper.play("two");
                }
                break;
            case CONNECTED_STATE_WEIGHT:
                showProgressDialog();
                state_current = CONNECTED_STATE_WEIGHT;
                if (voiceIndex != 3) {
                    voiceIndex = 3;
                    soundHelper.play("three");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (state_current == CONNECTED_STATE_WEIGHT) {
                            voiceIndex = 4;
                            soundHelper.play("four");
                            handler.postDelayed(this, 2000);
                        }
                    }
                }, 2000);
                break;
            case CONNECTED_STATE_UPLOADING:
                //阻抗数据上传
                state_current = CONNECTED_STATE_UPLOADING;
                break;
            case CONNECTED_STATE_UPLOADING_SUCCESS:
                dialogDissmiss();
                state_current = CONNECTED_STATE_SUCCESS;
                if (voiceIndex != 5) {
                    voiceIndex = 5;
                    soundHelper.play("five");
                }
                break;
            case CONNECTED_STATE_UPLOADING_FAIL:
                setStateTip("测量失败，请重新测量");
                dialogDissmiss();
                state_current = CONNECTED_STATE_SUCCESS;
                if (voiceIndex != 6) {
                    voiceIndex = 6;
                    soundHelper.play("six");
                }
                showUploadFailedDialog();
                break;
            case CONNECTED_STATE_UPLOADING_TIMEOUT:
                state_current = CONNECTED_STATE_SUCCESS;
                setStateTip("测量失败，请重新测量");
                dialogDissmiss();
                if (voiceIndex != 6) {
                    voiceIndex = 6;
                    soundHelper.play("six");
                }
                showTimeoutDialog();

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
        model.setWeight(String.valueOf(weight));
        if (getGuest()) {
            model.setHeight(String.valueOf(getGuestInfo().getHeight()));
            model.setBirthdate(String.valueOf(getGuestInfo().getAge()));
            model.setGender(getGuestInfo().getGender());
        } else {
            model.setHeight(UserInfoModel.getInstance().getUser().getHight());
            model.setBirthdate(String.valueOf(UserInfoModel.getInstance().getUser().getBirthday()));
            model.setGender(UserInfoModel.getInstance().getUser().getGender().equals("0") ? 2 : 1);
        }

        presenter.upLoadImpedance(model);

        Log.d("上传给服务器的逻辑------------", "storeOrSendCalcRsData上传");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectBluetooth();
        deviceListDialog = null;
        soundHelper.release();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mShakeListener.start();
    }

    public void stopVoice() {
        soundHelper.release();
    }

    public void addVoice() {
        soundHelper = new SoundHelper(this, 6);
        soundHelper.addAudio("one", R.raw.help_one);
        soundHelper.addAudio("two", R.raw.help_two);
        soundHelper.addAudio("three", R.raw.help_three);
        soundHelper.addAudio("four", R.raw.help_four);
        soundHelper.addAudio("five", R.raw.help_five);
        soundHelper.addAudio("six", R.raw.help_six);

    }

    //初始化页面
    public abstract void initUi();

    //获取最后上传的数据成功
    public abstract void initUiByBleSuccess(BleMainData data);

    //获取最后上传到数据失败
    public abstract void initUiByBleFailed();

    //获取非自己的人员信息
    public abstract VisitorModel getGuestInfo();

    //设置蓝牙状态提示
    public abstract void setStateTip(String state);

    //进入测量的时候显示dialog
    public abstract void showProgressDialog();

    //超时对话框
    public abstract void showTimeoutDialog();

    //上传数据失败对话框
    public abstract void showUploadFailedDialog();

    @Override
    public void checkMacSuccess() {
        connectBluetooth(deviceListDialog.getBluetoothDevice(bluetoothPosition));
        deviceListDialog.dismiss();
        dialogDissmiss();
    }

    @Override
    public void checkMacFailed() {
        dialogDissmiss();
    }

    @Override
    public void getTokenSuccess(BleTokenResponse response) {
        token = response.getAccess_token();
    }

    @Override
    public void getTokenFailed() {
        token = "";
    }

    @Override
    public void upLoadImpedanceSuccess(BleMainData data) {
        changeConnectionState(CONNECTED_STATE_UPLOADING_SUCCESS);
        sendFatRateToDevice((float) data.getBodyfatrate());
        isResultTest = true;
        initUiByBleSuccess(data);
    }

    @Override
    public void upLoadImpedanceFailed() {
        initUiByBleFailed();
        changeConnectionState(CONNECTED_STATE_UPLOADING_FAIL);
    }
}
