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
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
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
import com.softtek.lai.locationservice.LocationService;
import com.softtek.lai.module.sport.model.LatLon;
import com.softtek.lai.module.sport.model.SportData;
import com.softtek.lai.module.sport.model.Trajectory;
import com.softtek.lai.module.sport.presenter.SportManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.JCountDownTimer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * 开始跑步地图界面
 *
 * @author jerry.guan
 *         2016/5/30
 */
@InjectLayout(R.layout.activity_run_sport)
public class RunSportActivity extends BaseActivity implements LocationSource
        , View.OnClickListener, GpsStatus.Listener {

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

    private OnLocationChangedListener listener;
    private ArrayList<LatLon> coordinates = new ArrayList<>();//坐标集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            time=savedInstanceState.getLong("time",0);
            startStep=savedInstanceState.getInt("startStep",startStep);
            isFirst=savedInstanceState.getBoolean("isFirst",true);
            previousDistance=savedInstanceState.getDouble("previousDistance",0);
            lastLatLon=savedInstanceState.getParcelable("lastLatLon");
            ArrayList<LatLon> coordinates=savedInstanceState.getParcelableArrayList("coordinates");
            if(coordinates!=null&&!coordinates.isEmpty()){
                this.coordinates.clear();
                for(LatLon latLon:coordinates){
                    this.coordinates.add(latLon);
                    polylineOptions.add(new LatLng(latLon.getLatitude(),latLon.getLongitude()));
                }
                LatLon firstLatLon=coordinates.get(0);
                LatLng latLng=new LatLng(firstLatLon.getLatitude(),firstLatLon.getLongitude());
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.5f));
                aMap.addMarker(new MarkerOptions().position(latLng).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.location_mark_start)));
            }
            aMap.addPolyline(polylineOptions);
        }
        startCountDown();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }


    private static final int LOCATION_PREMISSION = 100;
    private Intent intent;

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
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        //初始化polyline
        polylineOptions = new PolylineOptions();
        polylineOptions.width(15);
        polylineOptions.color(Color.RED);
        polylineOptions.zIndex(3);

        /**
         * Android 6.0动态权限申请
         * PackageManager.PERMISSION_GRANTED:允许使用权限
         * PackageManager.PERMISSION_DENIED:不允许使用权限
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("检查权限。。。。");
            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //允许弹出提示
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        LOCATION_PREMISSION);

            } else {
                //不允许弹出提示
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        LOCATION_PREMISSION);
            }
        } else {
            //执行获取权限后的操作
            //启动定位
            Log.i("权限通过");
            intent = new Intent(this, LocationService.class);
            startService(intent);
        }

        ViewGroup.LayoutParams params = mapView.getLayoutParams();
        params.height = DisplayUtil.getMobileHeight(RunSportActivity.this) - DisplayUtil.dip2px(RunSportActivity.this, 345);
        mapView.setLayoutParams(params);
    }


    //6.0权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PREMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    intent = new Intent(this, LocationService.class);
                    startService(intent);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }

    }

    LocationManager locationManager;
    StepReceive receive;
    LocationReceiver locationReceiver;
    int startStep = 0;
    long aDay = 3600 * 24000;

    @Override
    protected void initDatas() {
        tv_title.setText("运动");
        manager = new SportManager();
        //注册步数接收器
        receive = new StepReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.softtek.lai.StepService.StepCount");
        LocalBroadcastManager.getInstance(this).registerReceiver(receive, filter);
        locationReceiver = new LocationReceiver();
        IntentFilter locationFilter = new IntentFilter();
        locationFilter.addAction(LocationService.LOCATION_SERIVER);
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver, locationFilter);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.addGpsStatusListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (intent != null) stopService(intent);
        if (receive != null) LocalBroadcastManager.getInstance(this).unregisterReceiver(receive);
        if (locationReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
        //在这里保存一些数据
        outState.putLong("time",time);//保存当前计时
        outState.putInt("startStep",startStep);//保存开始计时的时候步数
        outState.putBoolean("isFirst",isFirst);//保存是否是第一次定位
        outState.putDouble("previousDistance",previousDistance);//保存距离
        outState.putParcelable("lastLatLon",lastLatLon);//保存上一次坐标
        outState.putParcelableArrayList("coordinates",coordinates);//保存做坐标集合

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
        switch (v.getId()) {
            case R.id.iv_location:
                if (lastLatLon != null) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLon, 15.5f));
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
                }, 500);
                if (countDown != null) {
                    if (countDown.isPaused()) {
                        countDown.reStart();
                        iv_pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));

                    } else if (countDown.isRunning()) {
                        countDown.pause();
                        iv_pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.go_on));
                    }
                }
                break;
            case R.id.iv_stop:
                if (countDown != null) countDown.cancel();
                if (coordinates.isEmpty() && Integer.parseInt(tv_step.getText().toString()) == 0) {
                    AlertDialog dialog = new AlertDialog.Builder(this).setMessage("您还没有运动哦，确定结束运动吗?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startCountDown();
                                }
                            }).setCancelable(false).create();
                    dialog.show();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(this).setMessage("确认结束运动并提交本次数据")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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
                                    manager.submitSportData(RunSportActivity.this, data);
                                }
                            })
                            .setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startCountDown();
                                }
                            }).setCancelable(false).create();
                    dialog.show();
                }
                break;
            case R.id.cb_control:
                //面板控制动画
                final int stp = DisplayUtil.dip2px(this, 270);
                int enp = DisplayUtil.dip2px(this, 100);
                if (cb_control.isChecked()) {
                    //收起
                    ObjectAnimator animator = ObjectAnimator.ofInt(new LayoutWapper(ll_panel), "translateY", stp, enp)
                            .setDuration(500);
                    animator.setInterpolator(new OvershootInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            ViewGroup.LayoutParams params = mapView.getLayoutParams();
                            params.height = DisplayUtil.getMobileHeight(RunSportActivity.this) - DisplayUtil.dip2px(RunSportActivity.this, 175);
                            mapView.setLayoutParams(params);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            cb_control.setChecked(true);
                            ll_content1.setVisibility(View.GONE);
                            ll_content2.setVisibility(View.GONE);
                        }

                    });
                    animator.start();

                } else {
                    //展开
                    ObjectAnimator animator = ObjectAnimator.ofInt(new LayoutWapper(ll_panel), "translateY", enp, stp)
                            .setDuration(300);
                    animator.setInterpolator(new OvershootInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            cb_control.setChecked(false);
                            ll_content1.setVisibility(View.VISIBLE);
                            ll_content2.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ViewGroup.LayoutParams params = mapView.getLayoutParams();
                            params.height = DisplayUtil.getMobileHeight(RunSportActivity.this) - DisplayUtil.dip2px(RunSportActivity.this, 345);
                            mapView.setLayoutParams(params);
                        }
                    });
                    animator.start();
                }
                break;
        }
    }

    //GPS状态监听
    @Override
    public void onGpsStatusChanged(int event) {
        if (locationManager == null) {
            iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
            return;
        }
        GpsStatus status = locationManager.getGpsStatus(null); //取当前状态
        if (status == null) {//卫星数量为0
            if (iv_gps != null)
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
        } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            int count = 0;
            while (it.hasNext() && count <= maxSatellites) {
                count++;
            }
            if (count >= maxSatellites / 4) {//如果卫星数量大于最大卫星数量的一半则表示很强
                if (iv_gps != null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_three));
            } else if (count >= maxSatellites / 8) {
                if (iv_gps != null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_two));
            } else if (count >= 1) {
                if (iv_gps != null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_one));
            } else {
                if (iv_gps != null)
                    iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_empty));
            }
        } else if (event == GpsStatus.GPS_EVENT_STARTED) {
            //定位启动
            if (iv_gps != null)
                iv_gps.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gps_one));
        } else if (event == GpsStatus.GPS_EVENT_STOPPED) {
            //定位结束
        }
    }

    long time = 0;//总耗时

    private class RunSportCountDown extends JCountDownTimer {

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
            int hour = (int) (time / 3600);
            int minutes = (int) (time % 3600 / 60);
            int second = (int) (time % 3600 % 60);
            String show = (hour < 10 ? "0" + hour : String.valueOf(hour))
                    + ":" + (minutes < 10 ? "0" + minutes : String.valueOf(minutes))
                    + ":" + (second < 10 ? "0" + second : String.valueOf(second));
            if (tv_clock != null)
                tv_clock.setText(show);
        }


        @Override
        public void onFinish() {
            Log.i("定时器结束");
            //重新启动
            startCountDown();

        }
    }

    private void startCountDown() {
        countDown = new RunSportCountDown(aDay, 1000);
        countDown.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            doBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void doSubmitResult(int resultCode) {
        dialogDissmiss();
        if (resultCode == 200) {
            finish();
        }
    }

    private void doBack() {
        if (countDown != null) countDown.cancel();
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage("返回将丢失本次跑步数据")
                .setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCountDown();
                    }
                }).setCancelable(false).create();
        dialog.show();
    }

    private class LayoutWapper {
        private View target;

        public LayoutWapper(View target) {
            this.target = target;
        }

        public void setTranslateY(int value) {
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = value;
            target.setLayoutParams(params);
        }

    }

    //注册步数接收器
    private class StepReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int tempStep = intent.getIntExtra("step", 0);
            if (startStep == 0) startStep = tempStep;
            int step = (tempStep - startStep) <= 0 ? 0 : (tempStep - startStep);
            int calori = step / 35;
            tv_step.setText(step + "");
            tv_calorie.setText(calori + "");
            if (coordinates.isEmpty()) {//如果还没有定位到则使用步数来计算公里数
                DecimalFormat format = new DecimalFormat("#0.00");
                previousDistance = step * 1000 / 1428f;
                double speed = (previousDistance / 1000) / (time * 1f / 3600);
                tv_avg_speed.setText(format.format(speed) + "km/h");
                tv_distance.setText(format.format((previousDistance) / (1000 * 1.0)));
            }

        }
    }

    double previousDistance;//旧的距离
    boolean isFirst = true;
    LatLng lastLatLon;

    //位置接收器
    private class LocationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            AMapLocation location = intent.getParcelableExtra("location");
            if (listener != null) {
                listener.onLocationChanged(location);
            }
            if (/*location.getErrorCode() == 0&&*/location.getAccuracy() <= 100 && location.getAccuracy() > 0) {
                //当坐标改变之后开始添加标记 画线
                Log.i("获取位置");
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if (isFirst) {
                    isFirst = false;
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.5f));
                    aMap.addMarker(new MarkerOptions().position(latLng).icon(
                            BitmapDescriptorFactory.fromResource(R.drawable.location_mark_start)));
                }
                //计算平均速度
                if (lastLatLon != null) {
                    double distance = AMapUtils.calculateLineDistance(lastLatLon, latLng);
                    previousDistance += distance;
                    if (distance >= 6) {
                        polylineOptions.add(latLng);
                        coordinates.add(new LatLon(latLng.longitude, latLng.latitude));
                        aMap.addPolyline(polylineOptions);
                    }
                } else {
                    //如果是第一次定位到
                    polylineOptions.add(latLng);
                    coordinates.add(new LatLon(latLng.longitude, latLng.latitude));
                    aMap.addPolyline(polylineOptions);
                }
                lastLatLon = latLng;//暂存上一次坐标
                DecimalFormat format = new DecimalFormat("#0.00");
                double speed = (previousDistance / 1000) / (time * 1f / 3600);
                tv_avg_speed.setText(format.format(speed) + "km/h");
                tv_distance.setText(format.format((previousDistance) / (1000 * 1.0)));
            }
        }
    }
}
