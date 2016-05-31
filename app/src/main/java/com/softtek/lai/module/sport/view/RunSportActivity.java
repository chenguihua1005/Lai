package com.softtek.lai.module.sport.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * 开始跑步地图界面
 * @author jerry.guan
 * @date 2016/5/30
 */
@InjectLayout(R.layout.activity_run_sport)
public class RunSportActivity extends BaseActivity implements LocationSource,AMapLocationListener {
    @InjectView(R.id.map)
    MapView mapView;

    AMap aMap;
    Polyline polyline;//画线专用
    PolylineOptions polylineOptions;
    //定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private OnLocationChangedListener listener;
    private List<LatLng> coordinates=new ArrayList<>();//坐标集合

    @Override
    protected void initViews() {
        aMap=mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //aMap.animateCamera(CameraUpdateFactory.zoomOut());
        //我的位置样式
        MyLocationStyle locationStyle=new MyLocationStyle();
        locationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        locationStyle.strokeWidth(1f);//圆形的边框宽度
        locationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        aMap.setMyLocationStyle(locationStyle);
        aMap.setLocationSource(this);//设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        aMap.getUiSettings().setLogoPosition(-1);
        aMap.setMyLocationEnabled(true);

        aMapLocationClient=new AMapLocationClient(this);
        aMapLocationClient.setLocationListener(this);
        //初始化定位参数
        aMapLocationClientOption=new AMapLocationClientOption();
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
        polylineOptions=new PolylineOptions();
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        polylineOptions.zIndex(3);
        polyline=aMap.addPolyline(polylineOptions);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
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
                //aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,aMap.getMaxZoomLevel()));
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,aMap.getMaxZoomLevel())
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
                Toast.makeText(this,"定位失败, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo(),Toast.LENGTH_SHORT).show();
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
}
