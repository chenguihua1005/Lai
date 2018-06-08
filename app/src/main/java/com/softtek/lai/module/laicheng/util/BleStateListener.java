package com.softtek.lai.module.laicheng.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import java.util.List;

/**
 * Created by nolan on 2015/8/20.
 * Email：jy05892485@163.com
 */
public interface BleStateListener {

    void unableBleModule();//蓝牙模块不可用
    void openBleSettingSuccess();//蓝牙设置打开成功
    void openBleSettingCancel();//蓝牙设置关闭
    void getBoundListBle(List<BluetoothDevice> BleDevices);
    void scanBleScanFound(final BluetoothDevice device);//发现蓝牙设备
    void scanBleFinish();//蓝牙扫描完成
    void BleConnectSuccess();//蓝牙连接成功
    void BleConnectFail();//蓝牙连接失败
    void bleServicesDiscoveredSuccess(BluetoothGatt bluetoothGatt);//搜索服务完成
    void BleConnectLost();//连接的蓝牙断开
    void BleRead(byte[] datas);//读取数据
}
