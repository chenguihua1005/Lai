package com.softtek.lai.module.sport.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.JCountDownTimer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * 开始跑步地图界面
 * @author jerry.guan
 *  2016/5/30
 */
@InjectLayout(R.layout.activity_run_sport)
public class RunSportActivity extends BaseActivity implements LocationSource, AMapLocationListener
        , View.OnClickListener,GpsStatus.Listener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.map)
    MapView mapView;
    @InjectView(R.id.iv_pause)
    ImageView iv_pause;
    @InjectView(R.id.iv_stop)
    ImageView iv_stop;
    @InjectView(R.id.tv_clock)
    TextView tv_clock;
    @InjectView(R.id.tv_calorie)
    TextView tv_calorie;
    @InjectView(R.id.tv_step)
    TextView tv_step;
    @InjectView(R.id.tv_distance)
    TextView tv_distance;

    //平均速度和GPS信号量
    @InjectView(R.id.tv_avg_speed)
    TextView tv_avg_speed;
    @InjectView(R.id.iv_gps)
    ImageView iv_gps;

    //倒计时
    RunSportCountDown countDown;



    AMap aMap;
    Polyline polyline;//画线专用
    PolylineOptions polylineOptions;
    //定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private OnLocationChangedListener listener;
    private List<LatLng> coordinates = new ArrayList<>();//坐标集合

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        iv_pause.setOnClickListener(this);
        iv_stop.setOnClickListener(this);
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //我的位置样式
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        locationStyle.strokeWidth(1f);//圆形的边框宽度
        locationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        aMap.setMyLocationStyle(locationStyle);
        aMap.setLocationSource(this);//设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        aMap.getUiSettings().setLogoPosition(-1);
        aMap.setMyLocationEnabled(true);

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
        aMapLocationClientOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        aMapLocationClientOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        //启动定位
        aMapLocationClient.startLocation();

        //初始化polyline
        polylineOptions = new PolylineOptions();
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        polylineOptions.zIndex(3);
        polyline = aMap.addPolyline(polylineOptions);
    }
    LocationManager locationManager;
    @Override
    protected void initDatas() {
        tv_title.setText("运动");
        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.addGpsStatusListener(this);
            return;
        }
        countDown=new RunSportCountDown(1000,1000);
        countDown.start();
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(countDown!=null)countDown.cancel();
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(listener!=null&&aMapLocation!=null) {
            listener.onLocationChanged(aMapLocation);
            if (aMapLocation.getErrorCode() == 0) {
                //当坐标改变之后开始添加标记 画线
                LatLng latLng=new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,aMap.getMaxZoomLevel()-2)
                ,2000,null);
                if(coordinates.isEmpty()){
                    coordinates.add(latLng);
                    polylineOptions.add(latLng);
                }else {
                    LatLng latLng1=coordinates.get(coordinates.size()-1);
                    if(latLng1.latitude!=latLng.latitude||latLng1.longitude!=latLng.longitude){
                        coordinates.add(latLng);
                        polylineOptions.add(latLng);
                    }
                }

                aMap.addPolyline(polylineOptions);
                aMap.invalidate();
            } else {
                /*Toast.makeText(this,"定位失败, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo(),Toast.LENGTH_SHORT).show();*/
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.i("ggx","定位失败, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }
    int second;
    int minute;
    int hour;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:

                break;
            case R.id.iv_pause:
                iv_pause.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_pause.setEnabled(true);
                    }
                },500);
               if(countDown!=null){
                   if(countDown.isPaused()){
                       countDown.reStart();
                       iv_pause.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pause));

                   }else if(countDown.isRunning()){
                       countDown.pause();
                       iv_pause.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.go_on));
                   }
               }
                break;
            case R.id.iv_stop:

                break;
        }
    }
    //GPS状态监听
    @Override
    public void onGpsStatusChanged(int event) {
        if(locationManager==null){
            iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
            return;
        }
        GpsStatus status = locationManager.getGpsStatus(null); //取当前状态
        if (status == null) {//卫星数量为0
            iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
        } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            int count = 0;
            while (it.hasNext() && count <= maxSatellites) {
                count++;
            }
            if (count >= maxSatellites / 2) {//如果卫星数量大于最大卫星数量的一半则表示很强
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_three));
            } else if (count >= maxSatellites / 4) {
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_two));
            } else if (count >= 1) {
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_one));
            } else {
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
            }
        }
    }

    private class RunSportCountDown extends JCountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public RunSportCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }


        @Override
        public void onFinish() {
            //当倒计时结束刷新时间
            second++;
            if(second==60){
                second=0;
                minute++;
                if(minute==60){
                    minute=0;
                    hour++;
                    if(hour==60){
                        hour=0;
                    }
                }
            }
            String time=(hour<10?"0"+hour:String.valueOf(hour))
                    +":"+(minute<10?"0"+minute:String.valueOf(minute))
                    +":"+(second<10?"0"+second:String.valueOf(second));
            if(tv_clock!=null)
                tv_clock.setText(time);
            //重新启动
            countDown=new RunSportCountDown(1000,1000);
            countDown.start();
        }
    }
}
