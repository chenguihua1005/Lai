package com.softtek.lai.module.sport.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.github.snowdream.android.util.Log;
import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sport.model.LatLon;
import com.softtek.lai.module.sport.model.SportData;
import com.softtek.lai.module.sport.model.Trajectory;
import com.softtek.lai.module.sport.presenter.SportManager;
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
    @InjectView(R.id.iv_location)
    ImageView iv_location;

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
    private SportManager manager;
    AMap aMap;

    PolylineOptions polylineOptions;
    //定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private OnLocationChangedListener listener;
    private List<LatLon> coordinates = new ArrayList<>();//坐标集合

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
        iv_location.setOnClickListener(this);
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
        aMap.getUiSettings().setAllGesturesEnabled(false);
        aMap.getUiSettings().setScrollGesturesEnabled(true);
        aMap.getUiSettings().setTiltGesturesEnabled(true);
        aMap.getUiSettings().setZoomGesturesEnabled(true);
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
        polylineOptions.width(15);
        polylineOptions.color(Color.RED);
        polylineOptions.zIndex(3);

    }
    LocationManager locationManager;
    StepReceive receive;
    int startStep=0;
    long aDay=60*60*24*1000;
    @Override
    protected void initDatas() {
        tv_title.setText("运动");
        manager=new SportManager();
        //注册步数接收器
        receive=new StepReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.softtek.lai.StepService.StepCount");
        LocalBroadcastManager.getInstance(this).registerReceiver(receive,filter);
        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.addGpsStatusListener(this);
        }

        countDown=new RunSportCountDown(aDay,1000);
        countDown.start();
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(countDown!=null)countDown.cancel();
        if(aMapLocationClient!=null)aMapLocationClient.unRegisterLocationListener(this);
        if(receive!=null)LocalBroadcastManager.getInstance(this).unregisterReceiver(receive);
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

    double previousDistance;//旧的距离
    boolean isFirst=true;
    LatLng lastLatLon;
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(listener!=null&&aMapLocation!=null) {
            listener.onLocationChanged(aMapLocation);
            //tv_jindu.setText(aMapLocation.getAccuracy()+"");
            if (aMapLocation.getErrorCode() == 0&&aMapLocation.getAccuracy()<20&&aMapLocation.getAccuracy()>0) {
                //当坐标改变之后开始添加标记 画线
                Log.i("获取位置");
                LatLng latLng=new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                if(isFirst){
                    isFirst=false;
                    aMap.addMarker(new MarkerOptions().position(latLng).icon(
                            BitmapDescriptorFactory.fromResource(R.drawable.location_mark_start)));
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.3f));
                }
                //计算平均速度
                if(lastLatLon!=null){
                    double distance= AMapUtils.calculateLineDistance(lastLatLon,latLng);
                    previousDistance +=distance;
                    if(distance>=6){
                        polylineOptions.add(latLng);
                        coordinates.add(new LatLon(latLng.longitude,latLng.latitude));
                        aMap.addPolyline(polylineOptions);
                    }
                }else{
                    //如果是第一次定位到
                    polylineOptions.add(latLng);
                    coordinates.add(new LatLon(latLng.longitude,latLng.latitude));
                    aMap.addPolyline(polylineOptions);
                }
                lastLatLon=latLng;//暂存上一次坐标
                DecimalFormat format=new DecimalFormat("#0.00");
                double speed=(previousDistance/1000)/(time*1f/3600);
                tv_avg_speed.setText(format.format(speed)+"km/h");
                tv_distance.setText(format.format((previousDistance)/(1000*1.0)));
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
            case R.id.iv_location:
                if(lastLatLon!=null){
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLon, 16.3f));
                }
                break;
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
                if(countDown!=null)countDown.pause();
                if(coordinates.isEmpty()){
                    AlertDialog dialog=new AlertDialog.Builder(this).setMessage("您还没有运动哦，确定结束运动吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(countDown!=null)countDown.cancel();
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(countDown!=null)countDown.reStart();
                        }
                    }).setCancelable(false).create();
                    dialog.show();
                }else {
                    AlertDialog dialog = new AlertDialog.Builder(this).setMessage("确认结束运动并提交本次数据")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (countDown != null) countDown.cancel();
                                    SportData data = new SportData();
                                    data.setAccountId(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
                                    data.setCalories(tv_calorie.getText().toString());
                                    data.setKilometre(tv_distance.getText().toString());
                                    data.setMType(0);
                                    data.setSpeed(tv_avg_speed.getText().toString());
                                    data.setTimeLength(tv_clock.getText().toString() + ";" + time);
                                    data.setTotal(Integer.parseInt(tv_step.getText().toString()));
                                    data.setTrajectory(new Gson().toJson(new Trajectory(coordinates)));
                                    dialogShow("正在提交");
                                    //Log.i("保存数据为"+data.toString());
                                    manager.submitSportData(RunSportActivity.this, data);
                                }
                            })
                            .setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (countDown != null) countDown.reStart();

                                }
                            }).setCancelable(false).create();
                    dialog.show();
                }
                break;
            case R.id.cb_control:
                //面板控制动画
                int stp= DisplayUtil.dip2px(this,270);
                int enp=DisplayUtil.dip2px(this,100);
                /*int stmp=DisplayUtil.dip2px(this,250);
                int edmp=DisplayUtil.dip2px(this,420);*/
                if(cb_control.isChecked()){
                    //收起
                    //AnimatorSet set=new AnimatorSet();
                    //ObjectAnimator mapAn=ObjectAnimator.ofInt(new LayoutWapper(mapView),"translateY",stmp,edmp);
                    //mapAn.setDuration(100);
                    //mapAn.setInterpolator(new AccelerateInterpolator());
                    ObjectAnimator animator=ObjectAnimator.ofInt(new LayoutWapper(ll_panel),"translateY",stp,enp)
                            .setDuration(200);
                    animator.setInterpolator(new OvershootInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            cb_control.setChecked(true);
                            ll_content1.setVisibility(View.GONE);
                            ll_content2.setVisibility(View.GONE);
                        }

                    });
                    animator.start();
                    //set.playSequentially(mapAn,animator);
                    //set.start();

                }else{
                    //展开
                    //AnimatorSet set=new AnimatorSet();
                    //ObjectAnimator mapAn=ObjectAnimator.ofInt(new LayoutWapper(mapView),"translateY",edmp,stmp);
                    //mapAn.setDuration(100);
                    //mapAn.setInterpolator(new OvershootInterpolator());
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
                    animator.start();
                    //set.playSequentially(animator,mapAn);
                    //set.start();
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

    long time=0;//总耗时
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
            int hour= (int) (time/3600);
            int minutes= (int) (time%3600/60);
            int second= (int) (time%3600%60);
            String show=(hour<10?"0"+hour:String.valueOf(hour))
                    +":"+(minutes<10?"0"+minutes:String.valueOf(minutes))
                    +":"+(second<10?"0"+second:String.valueOf(second));
            if(tv_clock!=null)
                tv_clock.setText(show);
        }


        @Override
        public void onFinish() {
            //重新启动
            countDown=new RunSportCountDown(aDay,1000);
            countDown.start();
        }
    }

    boolean flag=true;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(flag){
                flag=false;
                doBack();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flag=true;
                    }
                },500);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void doSubmitResult(int resultCode){
        dialogDissmiss();
        if(resultCode==200){
            if(countDown!=null)countDown.cancel();
            finish();
        }
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

    //注册步数接收器
    private class StepReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int tempStep=intent.getIntExtra("step",0);
            if(startStep==0)startStep=tempStep;
            int step=(tempStep-startStep)<=0?0:(tempStep-startStep);
            int calori=step/35;
            tv_step.setText(step+"");
            tv_calorie.setText(calori+"");

        }
    }
}
