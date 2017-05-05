package com.softtek.lai.module.laicheng;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laicheng.util.BleManager;
import com.softtek.lai.module.laicheng.util.BleStateListener;
import com.softtek.lai.module.laicheng.util.MathUtils;


/**
 * Created by nolan on 2015/7/28.
 */
public abstract class BleBaseActivity extends BaseActivity {

    public static BleManager mBleManager;
    private BleStateListener bleStateListener;

    protected boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        cancelDiscoveryBluetooth();
        try {
            unregisterReceiver(mBleManager.getSettingStateReceiver());
        } catch (IllegalArgumentException e) {
            //重复注销广播接收器
        }//来防止crash
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        mBleManager = new BleManager(this, mHandler);
        if (!mBleManager.blueToothModuleEnable()) {
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
        if (mBleManager.getBluetoothAdapter().isEnabled()) {//蓝牙已经打开
            callOpenBluetoothSetting();
            IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            try {
                registerReceiver(mBleManager.getSettingStateReceiver(), filter);
            } catch (IllegalArgumentException e) {
                //重复注册广播接收器
            }//来防止crash
        } else {
            setSettingBluetoothCtrl();
        }

    }

    public void setSettingBluetoothCtrl() {
        mBleManager.openBluetoothSetting();
        //打开蓝牙设置监听
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        try {
            registerReceiver(mBleManager.getSettingStateReceiver(), filter);
        } catch (IllegalArgumentException e) {
            //重复注册广播接收器
        }//来防止crash
    }

    /**
     * 扫描蓝牙设备，如果没有开启蓝牙设置先打开蓝牙
     */
    protected void startDiscoveryBluetooth() {
        if (!mBleManager.getBluetoothAdapter().isEnabled()) {
            return;
        }
        if (mBleManager.getBlueToothState() == BleManager.BLUETOOTH_STATE_SETTING_OPEN) {
            mBleManager.scanBluetooth(true);
        }
    }

    /**
     * 关闭蓝牙扫描
     */
    protected void cancelDiscoveryBluetooth() {
        mBleManager.scanBluetooth(false);
    }

    protected void readData(BluetoothGattCharacteristic gattCharacteristic) {
        final int charaProp = gattCharacteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            mBleManager.getBluetoothGatt().readCharacteristic(gattCharacteristic);
        }
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mBleManager.getBluetoothGatt().setCharacteristicNotification(
                    gattCharacteristic, true);
        }
    }

    protected void connectBluetooth(final BluetoothDevice bluetoothDevice) {
        if (mBleManager.getBluetoothAdapter().isEnabled()) {
            mBleManager.connectBluetooth(bluetoothDevice, new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {//当蓝牙设备已经连接
                        if (!isConnected) {
                            sendMessage(BleManager.BLUETOOTH_STATE_CONNECTED);
                            isConnected = true;
                        }
                        mBleManager.getBluetoothGatt().discoverServices();
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//当设备无法连接
                        if (mBleManager.isReconnect()) {
//                            mBleManager.reConnectBluetooth(this);
                        } else {
                            isConnected = false;
                            sendMessage(BleManager.BLUETOOTH_STATE_CONNECT_LOST);
                        }
                    }
                }

                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    Log.d("discovered","discovered----------");
                    sendMessage(BleManager.BLUETOOTH_STATE_DISCOVER_SERVICES);
                }

                @Override
                public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                }

                @Override
                public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                    super.onCharacteristicWrite(gatt, characteristic, status);
                }

                @Override
                public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                    String uuid = characteristic.getUuid().toString();
                    byte[] data = characteristic.getValue();
                    Log.d("onCharacteristicChanged","进入onCharacteristicChanged");
                    sendMessage(BleManager.BLUETOOTH_STATE_READ, data);
                }

                @Override
                public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                    super.onDescriptorRead(gatt, descriptor, status);
                }

                @Override
                public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                    super.onDescriptorWrite(gatt, descriptor, status);
                }

                @Override
                public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                    super.onReliableWriteCompleted(gatt, status);
                }

                @Override
                public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                    super.onReadRemoteRssi(gatt, rssi, status);
                }
            });
        }
    }

    protected void disconnectBluetooth() {
        mBleManager.disconnectBluetooth();
    }

    protected void setNotificationCharacteristic(BluetoothGattCharacteristic characteristic) {
        mBleManager.getBluetoothGatt().setCharacteristicNotification(
                characteristic, true);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBleManager.getBluetoothGatt().writeDescriptor(descriptor);
    }

    protected void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        mBleManager.getBluetoothGatt().readCharacteristic(characteristic);
    }

    protected void writeCharacteristicData(final String dataStr, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        byte[] send = MathUtils.hexStringToBytes(dataStr);
        bluetoothGattCharacteristic.setValue(send);
        mBleManager.getBluetoothGatt().writeCharacteristic(bluetoothGattCharacteristic);
    }

    /**
     * 蓝牙打开，通知观察者，由于需要判断，所以放到一个函数里边，节省代码空间
     */
    void callOpenBluetoothSetting() {
        mBleManager.setBlueToothState(BleManager.BLUETOOTH_STATE_SETTING_OPEN);
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
                    mBleManager.setBlueToothState(BleManager.BLUETOOTH_STATE_NONE);
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
                    bleStateListener.bleServicesDiscoveredSuccess(mBleManager.getBluetoothGatt());
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

}
