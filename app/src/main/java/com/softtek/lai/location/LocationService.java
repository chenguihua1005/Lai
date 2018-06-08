package com.softtek.lai.location;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocationService extends Service implements AMapLocationListener {

    //定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;

    public static final String LOCATION_SERIVER="com.softtek.lai.locationservice.LocationService";

    @Override
    public void onCreate() {
        super.onCreate();
        aMapLocationClient = new AMapLocationClient(this);
        aMapLocationClient.setLocationListener(this);
        //初始化定位参数
        aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        aMapLocationClientOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为5000ms
        aMapLocationClientOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        aMapLocationClient.stopLocation();
        if(aMapLocationClient!=null)aMapLocationClient.unRegisterLocationListener(this);
        super.onDestroy();
    }

    public static final int MSG_FROM_CLIENT=100;
    public static final int MSG_FROM_SERVER=200;
    private static AMapLocation aMapLocation;
    private Messenger messenger=new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENT:
                    //接受从客户端发来的信息并恢复给客户端
                    Messenger messenger=msg.replyTo;
                    Message message=Message.obtain(null,MSG_FROM_SERVER);
                    Bundle data=new Bundle();
                    data.putParcelable("location",aMapLocation);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return messenger.getBinder();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null){
            this.aMapLocation=aMapLocation;
            Intent locIntent=new Intent(LOCATION_SERIVER);
            locIntent.putExtra("location",aMapLocation);
            LocalBroadcastManager.getInstance(this).sendBroadcast(locIntent);
        }
    }


}
