package com.softtek.lai.module.laicheng;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng.util.BleStateListener;
import com.softtek.lai.module.laicheng.util.MathUtils;

import java.lang.ref.WeakReference;
import java.util.Arrays;


/**
 * Created by nolan on 2015/7/28.
 */
public abstract class BleBaseActivity extends BaseActivity {


    private BleStateListener bleStateListener;

//    protected boolean isConnected = false;

    private class BlueGattReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!GATT_TAG.equals(intent.getAction())) {
                return;
            }
            String flag = intent.getStringExtra("flag");
            switch (flag) {
                case "Discovered":
                    sendMessage(BleManager.BLUETOOTH_STATE_DISCOVER_SERVICES);
                    break;
                case "CharacteristicChanged":
                    byte[] data = intent.getByteArrayExtra("data");
                    sendMessage(BleManager.BLUETOOTH_STATE_READ, data);
                    break;
                case "ConnectionStateChange":
                    int newState = intent.getIntExtra("newState", -20);
                    if (newState != -20) {
                        sendMessage(newState);
                    }
                    break;
            }
        }
    }

    private static class LeScanCallback implements BluetoothAdapter.LeScanCallback {

        private WeakReference<BleBaseActivity> weakReference;

        public LeScanCallback(BleBaseActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().sendMessage(BleManager.BLUETOOTH_STATE_SCAN_FOUND, device);
            }
        }

        public void recycle() {
            if (weakReference != null) {
                weakReference.clear();
                weakReference = null;
            }
        }
    }

    private LeScanCallback leScanCallback;
//    private BluetoothGattCall bluetoothGattCall;

    private BlueGattReceiver gattReceiver;


    public static final String GATT_TAG = "com.ggx.gatt_reciver";

    @Override
    protected void onDestroy() {
        Log.i("finish", "1111111finishfinishfinishfinishfinishfinishfinishfinishfinishfinishfinish1111111111111111111111");
        mHandler.removeCallbacksAndMessages(null);
        cancelDiscoveryBluetooth();
        leScanCallback.recycle();
//        bluetoothGattCall.recycle();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(gattReceiver);
        try {
            unregisterReceiver(mSettingStateReceiver);
        } catch (IllegalArgumentException e) {
            //重复注销广播接收器
        }//来防止crash
        super.onDestroy();
    }

    @Override
    protected void initViews() {

        leScanCallback = new LeScanCallback(this);
//        bluetoothGattCall = new BluetoothGattCall(this);
        gattReceiver = new BlueGattReceiver();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(gattReceiver, new IntentFilter(GATT_TAG));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!BleManager.getInstance().blueToothModuleEnable()) {
            if (bleStateListener != null) bleStateListener.unableBleModule();
        }
    }

    @Override
    protected void initDatas() {

    }

    /**
     * 设置蓝牙模块状态变化的观察者
     *
     * @param bleStateListener
     */
    protected void setBleStateListener(BleStateListener bleStateListener) {
        this.bleStateListener = bleStateListener;
    }

    /**
     * 打开蓝牙设置
     */
    public void openBluetoothSetting() {
        if (BleManager.getInstance().getBluetoothAdapter().isEnabled()) {//蓝牙已经打开
            callOpenBluetoothSetting();
            IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            try {
                registerReceiver(mSettingStateReceiver, filter);
            } catch (IllegalArgumentException e) {
                //重复注册广播接收器
            }//来防止crash
        } else {
            setSettingBluetoothCtrl();
        }

    }

    public void openBluetoothSettings() {
        BleManager bleManager = BleManager.getInstance();
        if (!bleManager.blueToothModuleEnable()) return;
        if (!bleManager.mBluetoothAdapter.isEnabled()) {
            bleManager.mBluetoothAdapter.enable();
        } else {
            bleManager.mBlueToothState = BleManager.BLUETOOTH_STATE_SETTING_OPEN;
            sendMessage(BleManager.BLUETOOTH_STATE_SETTING_OPEN);
        }
    }

    public void setSettingBluetoothCtrl() {
        openBluetoothSettings();
//        BleManager.getInstance().openBluetoothSetting();
        //打开蓝牙设置监听
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        try {
            registerReceiver(mSettingStateReceiver, filter);
        } catch (IllegalArgumentException e) {
            //重复注册广播接收器
        }//来防止crash
    }

    /**
     * 扫描蓝牙设备，如果没有开启蓝牙设置先打开蓝牙
     */
    protected void startDiscoveryBluetooth() {
        if (!BleManager.getInstance().getBluetoothAdapter().isEnabled()) {
            return;
        }
        if (BleManager.getInstance().getBlueToothState() == BleManager.BLUETOOTH_STATE_SETTING_OPEN) {
            scanBluetooth(true);
        }
    }

    public void scanBluetooth(boolean enable) {
        if (enable) {
            if (!BleManager.getInstance().mScanning) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (BleManager.getInstance().stopScane(leScanCallback)) {
                            sendMessage(BleManager.BLUETOOTH_STATE_SCAN_FINISH);
                            Toast.makeText(LaiApplication.getInstance().getApplicationContext(), "未发现设备，请检查莱秤是否开启", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, BleManager.SCAN_PERIOD);

                BleManager.getInstance().startScane(leScanCallback);
                sendMessage(BleManager.BLUETOOTH_STATE_SCANNING);
            }
        } else {
            if (BleManager.getInstance().stopScane(leScanCallback)) {
                sendMessage(BleManager.BLUETOOTH_STATE_SCAN_FINISH);
            }
        }
    }


    /**
     * 关闭蓝牙扫描
     */
    protected void cancelDiscoveryBluetooth() {
        scanBluetooth(false);
    }

    protected void readData(BluetoothGattCharacteristic gattCharacteristic) {
        final int charaProp = gattCharacteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            BleManager.getInstance().getBluetoothGatt().readCharacteristic(gattCharacteristic);
        }
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            BleManager.getInstance().getBluetoothGatt().setCharacteristicNotification(
                    gattCharacteristic, true);
        }
    }

    protected int connectBluetooth(BluetoothDevice bluetoothDevice) {
        if (BleManager.getInstance().getBluetoothAdapter().isEnabled()) {
            //关闭扫描
            if (BleManager.getInstance().stopScane(leScanCallback)) {
                sendMessage(BleManager.BLUETOOTH_STATE_SCAN_FINISH);
            }
            //链接
            return BleManager.getInstance().connBluetooth(bluetoothDevice);
        }
        return 0;
    }

    protected void disconnectBluetooth() {
        BleManager.getInstance().disconnectBluetooth();
    }


    protected void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        BleManager.getInstance().getBluetoothGatt().readCharacteristic(characteristic);
    }

    protected void writeCharacteristicData(final String dataStr, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        byte[] send = MathUtils.hexStringToBytes(dataStr);
        bluetoothGattCharacteristic.setValue(send);
        Log.d("BleBaseActivityIng...", Arrays.toString(BleManager.getInstance().getWriteCharacteristic().getValue()));
        BleManager.getInstance().getBluetoothGatt().writeCharacteristic(bluetoothGattCharacteristic);
    }

    /**
     * 蓝牙打开，通知观察者，由于需要判断，所以放到一个函数里边，节省代码空间
     */
    void callOpenBluetoothSetting() {
        BleManager.getInstance().setBlueToothState(BleManager.BLUETOOTH_STATE_SETTING_OPEN);
        if (bleStateListener != null) {
            bleStateListener.openBleSettingSuccess();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BleManager.BLUETOOTH_STATE_SETTING_OPEN:
                    callOpenBluetoothSetting();
                    break;
                case BleManager.BLUETOOTH_STATE_SETTING_CLOSE:
                    BleManager.getInstance().setBlueToothState(BleManager.BLUETOOTH_STATE_NONE);
                    if (bleStateListener != null) bleStateListener.openBleSettingCancel();
                    break;
                case BleManager.BLUETOOTH_STATE_SCAN_FOUND:
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) msg.obj;
                    if (bleStateListener != null)
                        bleStateListener.scanBleScanFound(bluetoothDevice);
                    break;
                case BleManager.BLUETOOTH_STATE_SCAN_FINISH:
                    if (bleStateListener != null) bleStateListener.scanBleFinish();
                    break;
                case BleManager.BLUETOOTH_STATE_CONNECTED:
                    if (bleStateListener != null) bleStateListener.BleConnectSuccess();

                    break;
                case BleManager.BLUETOOTH_STATE_CONNECT_LOST:
                    if (bleStateListener != null) {
                        bleStateListener.BleConnectLost();
                    }
                    break;
                case BleManager.BLUETOOTH_STATE_DISCOVER_SERVICES:
                    bleStateListener.bleServicesDiscoveredSuccess(BleManager.getInstance().getBluetoothGatt());
                    break;
                case BleManager.BLUETOOTH_STATE_READ:
                    byte[] data = (byte[]) msg.obj;
                    bleStateListener.BleRead(data);
                    break;
            }
        }
    };

    private void sendMessage(final int what) {
        Message message = new Message();
        message.what = what;
        mHandler.sendMessage(message);
    }

    private void sendMessage(final int what, final byte[] str) {
        Message message = new Message();
        message.what = what;
        message.obj = str;
        mHandler.sendMessage(message);
    }

    public void sendMessage(final int what, final BluetoothDevice bluetoothDevice) {
        Message message = new Message();
        message.what = what;
        message.obj = bluetoothDevice;
        mHandler.sendMessage(message);
    }

    /**
     * 蓝牙设置更改广播接收器
     */
    private final BroadcastReceiver mSettingStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 这个是蓝牙设置的变化，不是连接的状态，但是也需要
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                BleManager bleManager = BleManager.getInstance();
                int bluetoothSettingState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                if (bluetoothSettingState == BluetoothAdapter.STATE_ON) {
                    sendMessage(BleManager.BLUETOOTH_STATE_SETTING_OPEN);
                } else if (bluetoothSettingState == BluetoothAdapter.STATE_OFF) {
                    if (bleManager.mBluetoothAdapter.isDiscovering())//如果在扫描需要关闭
                        bleManager.mBluetoothAdapter.cancelDiscovery();
                    if (bleManager.mBluetoothGatt != null) {
                        bleManager.mBluetoothGatt.disconnect();
                        bleManager.mBluetoothGatt.close();
                    }
                    sendMessage(BleManager.BLUETOOTH_STATE_SETTING_CLOSE);
                    bleManager.mBlueToothState = BleManager.BLUETOOTH_STATE_NONE;
                }
            }
        }
    };
}
