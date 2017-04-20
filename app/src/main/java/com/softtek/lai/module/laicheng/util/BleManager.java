package com.softtek.lai.module.laicheng.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.module.laicheng.MainBaseActivity;

import java.util.List;
import java.util.UUID;

public class BleManager {

    private static final long SCAN_PERIOD = 10000;//蓝牙扫描时间

    public static final UUID KERUIER_SERVICE_UUID = UUID.fromString("000018f0-0000-1000-8000-00805f9b34fb");//老秤4.0
    public static final UUID KERUIER_READ_CHARACTERISTIC_UUID = UUID.fromString("00002af0-0000-1000-8000-00805f9b34fb");
    public static final UUID KERUIER_WRITE_CHARACTERISTIC_UUID = UUID.fromString("00002af1-0000-1000-8000-00805f9b34fb");
    public static final UUID LIERDA_SERVICE_UUID = UUID.fromString("0000ff12-0000-1000-8000-00805f9b34fb");
    public static final UUID LIERDA_READ_CHARACTERISTIC_UUID = UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb");
    public static final UUID LIERDA_WRITE_CHARACTERISTIC_UUID = UUID.fromString("0000ff01-0000-1000-8000-00805f9b34fb");
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    /**
     * 蓝牙状态
     */
    public static final int BLUETOOTH_STATE_NONE = 0;//蓝牙初始状态，可以开始工作
    public static final int BLUETOOTH_STATE_SETTING_OPEN = 1;//蓝牙设置打开
    public static final int BLUETOOTH_STATE_SETTING_CLOSE = 2;//蓝牙设备关闭
    public static final int BLUETOOTH_STATE_SCANNING = 3;//正在扫描蓝牙设备
    public static final int BLUETOOTH_STATE_SCAN_FOUND = 4;//找到蓝牙设备
    public static final int BLUETOOTH_STATE_SCAN_FINISH = 5;//扫描完成
    public static final int BLUETOOTH_STATE_CONNECTED = 6;//蓝牙已经连接
    public static final int BLUETOOTH_STATE_CONNECT_LOST = 7;//连接断开
    public static final int BLUETOOTH_STATE_DISCOVER_SERVICES = 8;//扫描服务成功
    public static final int BLUETOOTH_STATE_DATA = 9;//蓝牙模块开始读写数据,接收到第一条数据的时候开始变成这个状态
    public static final int BLUETOOTH_STATE_READ = 10;//读取数据

    /**
     * 错误码
     */
    public static final int BLUETOOTH_ERROR_NONE = 0;//正确
    public static final int BLUETOOTH_HAS_DATA = 1;//开始传输数据了，不能断开或者连接其他设备
    public static final int BLUETOOTH_CONNECTED_THIS = 2;//连接的设备就是这个
    public static final int BLUETOOTH_CONNECT_ERROR = 3;//连接错误
    public static final int BLUETOOTH_UNABLE = 4;//蓝牙模块错误

    private boolean availableBluetooth = true;
    private int mBlueToothState = BLUETOOTH_STATE_NONE;

    private Activity mActivity;
    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothDevice mBluetoothDevice;//需要连接的蓝牙设备
    private List<BluetoothDevice> mBoundDeviceList;//已经配对的蓝牙信息

    private BluetoothGatt mBluetoothGatt;

    private boolean isReconnect = false;//是否是重新连接
    private boolean mScanning = false;

    private Handler mHandler;

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            sendMessage(BLUETOOTH_STATE_SCAN_FOUND, bluetoothDevice);
        }
    };

    public BleManager(Activity activity, Handler handler) {
        mActivity = activity;
        mHandler = handler;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) availableBluetooth = false;
    }

    public void openBluetoothSetting() {
        if (!blueToothModuleEnable()) return;
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        } else {
            mBlueToothState = BLUETOOTH_STATE_SETTING_OPEN;
            sendMessage(BLUETOOTH_STATE_SETTING_OPEN);
        }
    }

    public int getBlueToothState() {
        return mBlueToothState;
    }

    public void setBlueToothState(int blueToothState) {
        this.mBlueToothState = blueToothState;
    }

    public void scanBluetooth(boolean enable) {
        if (enable) {
            if (!mScanning) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mScanning) {
                            mScanning = false;
                            mBlueToothState = BLUETOOTH_STATE_SCAN_FINISH;
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            sendMessage(BLUETOOTH_STATE_SCAN_FINISH);
                            Toast.makeText(LaiApplication.getInstance().getApplicationContext(), "未发现设备，请检查莱秤是否开启", Toast.LENGTH_SHORT).show();
                            Log.d("no found deviced", "未发现设备，请检查莱秤是否开启");
                        }
                    }
                }, SCAN_PERIOD);
                mScanning = true;
                mBlueToothState = BLUETOOTH_STATE_SCANNING;
                UUID[] uuids = {LIERDA_SERVICE_UUID};
                mBluetoothAdapter.startLeScan(mLeScanCallback);
                sendMessage(BLUETOOTH_STATE_SCANNING);
            }
        } else {
            if (mScanning) {
                mScanning = false;
                mBlueToothState = BLUETOOTH_STATE_SCAN_FINISH;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                sendMessage(BLUETOOTH_STATE_SCAN_FINISH);
            }
        }
    }

    public int connectBluetooth(final BluetoothDevice bluetoothDevice, final BluetoothGattCallback gattCallback) {
        if (mScanning) {
            mScanning = false;
            mBlueToothState = BLUETOOTH_STATE_SCAN_FINISH;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            sendMessage(BLUETOOTH_STATE_SCAN_FINISH);
        }
        if (mBlueToothState == BLUETOOTH_STATE_DATA) {
            return BLUETOOTH_HAS_DATA;
        } else if (mBlueToothState == BLUETOOTH_STATE_CONNECTED) {
            if (mBluetoothDevice.getAddress().equals(bluetoothDevice.getAddress())) {
                return BLUETOOTH_CONNECTED_THIS;
            }
            isReconnect = true;
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
        mBluetoothDevice = bluetoothDevice;
        try {
            isReconnect = false;
            mBluetoothGatt = mBluetoothDevice.connectGatt(mActivity, false, gattCallback);
            return BLUETOOTH_ERROR_NONE;
        } catch (Exception e) {
            return BLUETOOTH_CONNECT_ERROR;
        }
    }

    public void disconnectBluetooth() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
        mActivity = null;
    }

    /**
     * 重新连接
     *
     * @param gattCallback
     * @return
     */
    public int reConnectBluetooth(final BluetoothGattCallback gattCallback) {
        try {
            isReconnect = false;
            mBluetoothGatt = mBluetoothDevice.connectGatt(mActivity, false, gattCallback);
            return BLUETOOTH_ERROR_NONE;
        } catch (Exception e) {
            return BLUETOOTH_CONNECT_ERROR;
        }
    }

    /**
     * 查看蓝牙模块是否可用
     *
     * @return 是否可用
     */
    public boolean blueToothModuleEnable() {
        return availableBluetooth;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    /**
     * 蓝牙设置更改广播接收器
     */
    private final BroadcastReceiver mSettingStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 这个是蓝牙设置的变化，不是连接的状态，但是也需要
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                int bluetoothSettingState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                if (bluetoothSettingState == BluetoothAdapter.STATE_ON) {
                    sendMessage(BLUETOOTH_STATE_SETTING_OPEN);
                } else if (bluetoothSettingState == BluetoothAdapter.STATE_OFF) {
                    if (mBluetoothAdapter.isDiscovering())//如果在扫描需要关闭
                        mBluetoothAdapter.cancelDiscovery();
                    if (mBluetoothGatt != null) {
                        mBluetoothGatt.disconnect();
                        mBluetoothGatt.close();
                    }
                    sendMessage(BLUETOOTH_STATE_SETTING_CLOSE);
                    mBlueToothState = BLUETOOTH_STATE_NONE;
                }
            }
        }
    };

    public BroadcastReceiver getSettingStateReceiver() {
        return mSettingStateReceiver;
    }

    public BluetoothGatt getBluetoothGatt() {
        return mBluetoothGatt;
    }

    public boolean isScanning() {
        return mScanning;
    }

    public boolean isReconnect() {
        return isReconnect;
    }


    private void sendMessage(final int what) {
        Message message = new Message();
        message.what = what;
        mHandler.sendMessage(message);
    }

    private void sendMessage(final int what, final BluetoothDevice bluetoothDevice) {
        Message message = new Message();
        message.what = what;
        message.obj = bluetoothDevice;
        mHandler.sendMessage(message);
    }
}
