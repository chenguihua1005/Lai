package com.softtek.lai.module.laicheng.util;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.kitnew.ble.QNBleDevice;
import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.module.laicheng_new.util.Contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nolan on 2015/10/14.
 * Email：jy05892485@163.com
 */
public class DeviceListDialog extends Dialog {

    private Context mContext;
    private TextView cTitle;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private List<BluetoothDevice> bluetoothDeviceList;
    private List<QNBleDevice> qn_bluetoothDeviceList;
    private BluetoothDialogListener mBluetoothDialogListener;
    private SharedPreferences mSharedPreferences;

    public DeviceListDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Contacts.SHARE_NAME, Activity.MODE_PRIVATE);
    }

    public DeviceListDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);
        cTitle = (TextView) findViewById(R.id.title_paired_devices_main_title);

        ImageButton closeBtn = (ImageButton) findViewById(R.id.device_list_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBluetoothDialogListener != null)
                    mBluetoothDialogListener.bluetoothClose();
            }
        });

        mNewDevicesArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.device_name);
        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
    }

    public ArrayAdapter<String> getNewDevicesArrayAdapter() {
        return mNewDevicesArrayAdapter;
    }


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            if (mBluetoothDialogListener != null)
                mBluetoothDialogListener.bluetoothDialogClick(arg2);
        }
    };

    public void setBluetoothDialogListener(BluetoothDialogListener mBluetoothDialogListener) {
        this.mBluetoothDialogListener = mBluetoothDialogListener;
    }

    public void addBluetoothDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDeviceList == null)
            bluetoothDeviceList = new ArrayList<BluetoothDevice>();
        if (bluetoothDeviceList.size() != 0) {
            for (BluetoothDevice tem : bluetoothDeviceList) {
                if (tem.getAddress().equals(bluetoothDevice.getAddress()))
                    return;//排重
            }
        }
        bluetoothDeviceList.add(bluetoothDevice);
        mNewDevicesArrayAdapter.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
        mNewDevicesArrayAdapter.notifyDataSetChanged();
    }

    public void addBluetoothDevice(QNBleDevice qnBleDevice) {
        if (qn_bluetoothDeviceList == null)
            qn_bluetoothDeviceList = new ArrayList<>();
        if (qn_bluetoothDeviceList.size() != 0) {
            for (QNBleDevice tem : qn_bluetoothDeviceList) {
                if (tem.getMac().equals(qnBleDevice.getMac()))
                    return;//排重
            }
        }
        qn_bluetoothDeviceList.add(qnBleDevice);
        String name = mSharedPreferences.getString(qnBleDevice.getMac(), qnBleDevice.getMac());
        mNewDevicesArrayAdapter.add(name);
        mNewDevicesArrayAdapter.notifyDataSetChanged();
    }

    public BluetoothDevice getBluetoothDevice(int position) {
        if (bluetoothDeviceList == null || bluetoothDeviceList.size() == 0 || position > bluetoothDeviceList.size() - 1)
            return null;
        return bluetoothDeviceList.get(position);
    }

    public QNBleDevice getQNBluetoothDevice(int position) {
        if (qn_bluetoothDeviceList == null || qn_bluetoothDeviceList.size() == 0 || position > qn_bluetoothDeviceList.size() - 1)
            return null;
        return qn_bluetoothDeviceList.get(position);
    }

    public void clearBluetoothDevice() {
        if (bluetoothDeviceList != null)
            bluetoothDeviceList.clear();
        if (qn_bluetoothDeviceList != null)
            qn_bluetoothDeviceList.clear();
        if (mNewDevicesArrayAdapter != null){
            mNewDevicesArrayAdapter.clear();
        }
    }

    public void startScan() {
        bluetoothDeviceList = new ArrayList<BluetoothDevice>();
        if (cTitle != null) {
            cTitle.setText("正在搜索设备");
        }
    }

    public void finishScan() {
        if (cTitle != null)
            cTitle.setText(mContext.getText(R.string.select_device).toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public interface BluetoothDialogListener {
        void bluetoothDialogClick(int position);

        void bluetoothClose();
    }
}
