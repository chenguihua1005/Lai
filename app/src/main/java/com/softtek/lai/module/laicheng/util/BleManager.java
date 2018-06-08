package com.softtek.lai.module.laicheng.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.module.laicheng.BleBaseActivity;
import com.softtek.lai.module.laicheng.MainBaseActivity;

import java.util.List;
import java.util.UUID;

import zilla.libcore.util.Util;

public class BleManager {
    private static volatile BleManager instance;
    public static final long SCAN_PERIOD = 10000;//蓝牙扫描时间

    private static final UUID KERUIER_SERVICE_UUID = UUID.fromString("000018f0-0000-1000-8000-00805f9b34fb");//老秤4.0
    private static final UUID KERUIER_READ_CHARACTERISTIC_UUID = UUID.fromString("00002af0-0000-1000-8000-00805f9b34fb");
    private static final UUID KERUIER_WRITE_CHARACTERISTIC_UUID = UUID.fromString("00002af1-0000-1000-8000-00805f9b34fb");
    private static final UUID LIERDA_SERVICE_UUID = UUID.fromString("0000ff12-0000-1000-8000-00805f9b34fb");
    private static final UUID LIERDA_READ_CHARACTERISTIC_UUID = UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb");
    private static final UUID LIERDA_WRITE_CHARACTERISTIC_UUID = UUID.fromString("0000ff01-0000-1000-8000-00805f9b34fb");
    private static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
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


    public static boolean EXIT = false;

    private boolean availableBluetooth = true;
    public int mBlueToothState = BLUETOOTH_STATE_NONE;
    public BluetoothAdapter mBluetoothAdapter;

    public BluetoothDevice mBluetoothDevice;//需要连接的蓝牙设备
    private List<BluetoothDevice> mBoundDeviceList;//已经配对的蓝牙信息

    public BluetoothGatt mBluetoothGatt;

    public boolean isReconnect = false;//是否是重新连接
    public boolean mScanning = false;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    private boolean isConnected = false;

    private BluetoothGattCharacteristic readCharacteristic;//蓝牙读写数据的载体

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Intent intent = new Intent();
            intent.setAction(BleBaseActivity.GATT_TAG);
            intent.putExtra("flag", "device");
            intent.putExtra("device", device);
            LocalBroadcastManager.getInstance(LaiApplication.getInstance().getApplicationContext()).sendBroadcast(intent);
        }
    };
//
//    public BluetoothGattCharacteristic getReadCharacteristic() {
//        return readCharacteristic;
//    }
//
//    public void setReadCharacteristic(BluetoothGattCharacteristic readCharacteristic) {
//        this.readCharacteristic = readCharacteristic;
//    }

    public BluetoothGattCharacteristic getWriteCharacteristic() {
        BluetoothGattService bluetoothGattService = mBluetoothGatt.getService(BleManager.LIERDA_SERVICE_UUID);
        if (bluetoothGattService != null) {
            writeCharacteristic = bluetoothGattService.getCharacteristic(BleManager.LIERDA_WRITE_CHARACTERISTIC_UUID);
        } else {
            bluetoothGattService = mBluetoothGatt.getService(BleManager.KERUIER_SERVICE_UUID);
            if (bluetoothGattService != null) {
                writeCharacteristic = bluetoothGattService.getCharacteristic(BleManager.KERUIER_WRITE_CHARACTERISTIC_UUID);
            } else {
                Log.d("getWriteCharacteristic", "NULL");
            }
        }
        return writeCharacteristic;
    }

//    public void setWriteCharacteristic(BluetoothGattCharacteristic writeCharacteristic) {
//        this.writeCharacteristic = writeCharacteristic;
//    }

    private BluetoothGattCharacteristic writeCharacteristic;//蓝牙读写数据的载体

    private BleManager() {
        EXIT = false;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) availableBluetooth = false;
    }

    public static BleManager getInstance() {
        if (instance == null) {
            synchronized (BleManager.class) {
                if (instance == null) {
                    instance = new BleManager();
                }
            }
        }
        return instance;
    }

    public boolean stopScane() {
        if (isScanning()) {
            mScanning = false;
            mBlueToothState = BleManager.BLUETOOTH_STATE_SCAN_FINISH;
            mBluetoothAdapter.stopLeScan(leScanCallback);
            return true;
        }
        return false;
    }

    public boolean startScane() {
        mScanning = true;
        mBlueToothState = BLUETOOTH_STATE_SCANNING;
        UUID[] uuids = {LIERDA_SERVICE_UUID};
        mBluetoothAdapter.startLeScan(leScanCallback);
        return true;
    }


    public int connBluetooth(BluetoothDevice bluetoothDevice) {
        if (mBlueToothState == BleManager.BLUETOOTH_STATE_DATA) {
            return BleManager.BLUETOOTH_HAS_DATA;
        } else if (mBlueToothState == BleManager.BLUETOOTH_STATE_CONNECTED) {
            if (mBluetoothDevice.getAddress().equals(bluetoothDevice.getAddress())) {
                return BleManager.BLUETOOTH_CONNECTED_THIS;
            }
            isReconnect = true;
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
        mBluetoothDevice = bluetoothDevice;
        try {
            isReconnect = false;

            mBluetoothGatt = mBluetoothDevice.connectGatt(LaiApplication.getInstance().getApplicationContext(), false, new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    int key = -1;
                    if (newState == BluetoothProfile.STATE_CONNECTED) {//当蓝牙设备已经连接
                        if (!isConnected()) {
                            key = BLUETOOTH_STATE_CONNECTED;
                            setConnected(true);
                        }
                        BleManager.getInstance().getBluetoothGatt().discoverServices();
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//当设备无法连接
                        if (isReconnect()) {
//                            BleManager.getInstance().reConnectBluetooth(this);
                        } else {
                            setConnected(false);
                            key = BLUETOOTH_STATE_CONNECT_LOST;
                            loss();
                        }
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {//失去链接中
                        setConnected(false);
                        key = BLUETOOTH_STATE_CONNECT_LOST;
                        loss();
                    }
                    if (key != -1) {
                        Intent intent = new Intent(BleBaseActivity.GATT_TAG);
                        intent.putExtra("flag", "ConnectionStateChange");
                        intent.putExtra("newState", key);
                        LocalBroadcastManager.getInstance(LaiApplication.getInstance().getApplicationContext())
                                .sendBroadcast(intent);
                    }
                }

                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    android.util.Log.d("discovered", "discovered----------");
                    BluetoothGattService bluetoothGattService = mBluetoothGatt.getService(BleManager.LIERDA_SERVICE_UUID);
                    if (bluetoothGattService != null) {
                        readCharacteristic = bluetoothGattService.getCharacteristic(BleManager.LIERDA_READ_CHARACTERISTIC_UUID);
                        setNotificationCharacteristic(readCharacteristic);
                        writeCharacteristic = bluetoothGattService.getCharacteristic(BleManager.LIERDA_WRITE_CHARACTERISTIC_UUID);
                    } else {
                        bluetoothGattService = mBluetoothGatt.getService(BleManager.KERUIER_SERVICE_UUID);
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
                    android.util.Log.d("onCharacteristicChanged", "进入onCharacteristicChanged");
                    Intent intent = new Intent(BleBaseActivity.GATT_TAG);
                    intent.putExtra("flag", "CharacteristicChanged");
                    intent.putExtra("data", data);
                    LocalBroadcastManager.getInstance(LaiApplication.getInstance().getApplicationContext())
                            .sendBroadcast(intent);
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
            return BleManager.BLUETOOTH_ERROR_NONE;
        } catch (Exception e) {
            return BleManager.BLUETOOTH_CONNECT_ERROR;
        }
    }

    protected void setNotificationCharacteristic(BluetoothGattCharacteristic characteristic) {
        BleManager.getInstance().getBluetoothGatt().setCharacteristicNotification(
                characteristic, true);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        BleManager.getInstance().getBluetoothGatt().writeDescriptor(descriptor);
    }


    public int getBlueToothState() {
        return mBlueToothState;
    }

    public void setBlueToothState(int blueToothState) {
        this.mBlueToothState = blueToothState;
    }


    public void disconnectBluetooth() {
        try {
            if (mBluetoothGatt != null) {
                mBluetoothGatt.disconnect();
            }
            if(mBluetoothGatt!=null){
                mBluetoothGatt.close();
            }
        } catch (Exception e) {
            Util.toastMsg("蓝牙关闭失败");
        }
        instance = null;
        EXIT = true;
        MainBaseActivity.isConnecting = false;
    }

    private void loss() {
        instance = null;
        EXIT = true;
        mBluetoothGatt = null;
        MainBaseActivity.isConnecting = false;
    }

//    /**
//     * 重新连接
//     *
//     * @param gattCallback
//     * @return
//     */
//    public int reConnectBluetooth(final BluetoothGattCallback gattCallback) {
//        try {
//            isReconnect = false;
//            mBluetoothGatt = mBluetoothDevice.connectGatt(LaiApplication.getInstance().getApplicationContext(), false, gattCallback);
//            return BLUETOOTH_ERROR_NONE;
//        } catch (Exception e) {
//            return BLUETOOTH_CONNECT_ERROR;
//        }
//    }

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


    public BluetoothGatt getBluetoothGatt() {
        return mBluetoothGatt;
    }

    public boolean isScanning() {
        return mScanning;
    }

    public boolean isReconnect() {
        return isReconnect;
    }

}
