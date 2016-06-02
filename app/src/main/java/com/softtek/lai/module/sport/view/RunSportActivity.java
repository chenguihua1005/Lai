package com.softtek.lai.module.sport.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
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
import com.amap.api.maps2d.model.PolylineOptions;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.JCountDownTimer;

import java.text.DecimalFormat;
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
    @InjectView(R.id.cb_control)
    CheckBox cb_control;
    @InjectView(R.id.ll_panel)
    LinearLayout ll_panel;

    @InjectView(R.id.ll_content1)
    LinearLayout ll_content1;
    @InjectView(R.id.ll_content2)
    LinearLayout ll_content2;

    //倒计时
    RunSportCountDown countDown;

    AMap aMap;
    //Polyline polyline;//画线专用
    PolylineOptions polylineOptions;
    //定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private OnLocationChangedListener listener;
    private List<LatLng> coordinates = new ArrayList<>();//坐标集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        iv_pause.setOnClickListener(this);
        iv_stop.setOnClickListener(this);
        cb_control.setOnClickListener(this);
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
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放控制按钮
        aMap.getUiSettings().setLogoPosition(2);
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
        aMapLocationClientOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        aMapLocationClientOption.setInterval(5000);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        //启动定位
        aMapLocationClient.startLocation();

        //初始化polyline
        polylineOptions = new PolylineOptions();
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        polylineOptions.zIndex(3);
    }
    LocationManager locationManager;
    @Override
    protected void initDatas() {
        tv_title.setText("运动");
        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.addGpsStatusListener(this);
        }

        countDown=new RunSportCountDown(60000,1000);
        countDown.start();
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(countDown!=null)countDown.cancel();
        aMapLocationClient.unRegisterLocationListener(this);
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
                Log.i("获取位置");
                LatLng latLng=new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.3f));
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
                //计算平均速度
                LatLng latLng1=coordinates.get(coordinates.size()-1);
                double distance=distanceOfTwoPoints(latLng.latitude,latLng.longitude,latLng1.latitude,latLng1.longitude);
                double speed=distance/(time*1f/3600);
                tv_avg_speed.setText(new DecimalFormat("#0.00").format(speed)+"km/h");

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                /*Log.i("ggx","定位失败, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());*/
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                doBack();
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
            case R.id.cb_control:
                //面板控制动画
                int stp= DisplayUtil.dip2px(this,270);
                int enp=DisplayUtil.dip2px(this,100);
                int stmp=DisplayUtil.dip2px(this,250);
                int edmp=DisplayUtil.dip2px(this,420);
                if(cb_control.isChecked()){
                    //收起
                    AnimatorSet set=new AnimatorSet();
                    ObjectAnimator mapAn=ObjectAnimator.ofInt(new LayoutWapper(mapView),"translateY",stmp,edmp);
                    mapAn.setDuration(100);
                    mapAn.setInterpolator(new AccelerateInterpolator());
                    ObjectAnimator animator=ObjectAnimator.ofInt(new LayoutWapper(ll_panel),"translateY",stp,enp)
                            .setDuration(150);
                    animator.setInterpolator(new OvershootInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            cb_control.setChecked(true);
                            ll_content1.setVisibility(View.GONE);
                            ll_content2.setVisibility(View.GONE);
                        }

                    });
                    set.playSequentially(animator,mapAn);
                    set.start();

                }else{
                    //展开
                    AnimatorSet set=new AnimatorSet();
                    ObjectAnimator mapAn=ObjectAnimator.ofInt(new LayoutWapper(mapView),"translateY",edmp,stmp);
                    mapAn.setDuration(100);
                    mapAn.setInterpolator(new OvershootInterpolator());
                    ObjectAnimator animator=ObjectAnimator.ofInt(new LayoutWapper(ll_panel),"translateY",enp,stp)
                            .setDuration(300);
                    animator.setInterpolator(new OvershootInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            cb_control.setChecked(false);
                            ll_content1.setVisibility(View.VISIBLE);
                            ll_content2.setVisibility(View.VISIBLE);
                        }
                    });
                    set.playSequentially(animator,mapAn);
                    set.start();
                }
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
            if (iv_gps!=null)
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
        } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            int count = 0;
            while (it.hasNext() && count <= maxSatellites) {
                count++;
            }
            if (count >= maxSatellites / 2) {//如果卫星数量大于最大卫星数量的一半则表示很强
                if(iv_gps!=null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_three));
            } else if (count >= maxSatellites / 4) {
                if(iv_gps!=null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_two));
            } else if (count >= 1) {
                if(iv_gps!=null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_one));
            } else {
                if(iv_gps!=null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
            }
        }else if(event==GpsStatus.GPS_EVENT_STARTED)
        {
            //定位启动
            if(iv_gps!=null)
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_one));
        }
        else if(event==GpsStatus.GPS_EVENT_STOPPED)
        {
            //定位结束
        }
    }

    long time=0;
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
            time++;
            int minutes= (int) (time/60);
            int hour= (int) (time/3600);
            int second= (int) (time%60);
            String show=(hour<10?"0"+hour:String.valueOf(hour))
                    +":"+(minutes<10?"0"+minutes:String.valueOf(minutes))
                    +":"+(second<10?"0"+second:String.valueOf(second));
            if(tv_clock!=null)
                tv_clock.setText(show);
        }


        @Override
        public void onFinish() {

            //重新启动
            countDown=new RunSportCountDown(60000,1000);
            countDown.start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            doBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doBack(){
        if(countDown!=null){
            countDown.pause();
        }
        AlertDialog dialog=new AlertDialog.Builder(this).setMessage("返回将丢失本次跑步数据")
                .setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(countDown!=null)countDown.cancel();
                        finish();
                    }
                })
                .setNegativeButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(countDown!=null)countDown.reStart();
                    }
                }).create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(countDown!=null)countDown.reStart();
            }
        });
        dialog.show();
    }

    private class LayoutWapper{
        private View target;

        public LayoutWapper(View target){
            this.target=target;
        }

        public void setTranslateY(int value){
            ViewGroup.LayoutParams params=target.getLayoutParams();
            params.height=value;
            target.setLayoutParams(params);
        }

    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离：单位为米
     */
    public  double distanceOfTwoPoints(double lat1,double lng1,
                                             double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    private static final double EARTH_RADIUS = 6378137;
    private  double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
