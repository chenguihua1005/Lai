package com.softtek.lai.module.sport.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.LatLon;
import com.softtek.lai.module.sport.model.Trajectory;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_history_sport)
public class HistorySportActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.map)
    MapView mapView;

    @InjectView(R.id.tv_clock)
    TextView tv_clock;
    @InjectView(R.id.tv_calorie)
    TextView tv_calorie;
    @InjectView(R.id.tv_step)
    TextView tv_step;
    @InjectView(R.id.tv_distance)
    TextView tv_distance;

    @InjectView(R.id.cb_map_switch)
    CheckBox cb_map_switch;

    //平均速度和GPS信号量
    @InjectView(R.id.tv_avg_speed)
    TextView tv_avg_speed;
    @InjectView(R.id.tv_create_time)
    TextView tv_create_time;

    AMap aMap;
    PolygonOptions polygonOptions;
    PolylineOptions polylineOptions;
    MarkerOptions startMark;
    MarkerOptions endMark;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        cb_map_switch.setOnClickListener(this);
        tv_title.setText("运动记录");
    }
    @Override
    protected void initDatas() {
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放控制按钮
        aMap.getUiSettings().setAllGesturesEnabled(false);
        aMap.getUiSettings().setScrollGesturesEnabled(true);
        aMap.getUiSettings().setZoomGesturesEnabled(true);
        HistorySportModel model= (HistorySportModel) getIntent().getSerializableExtra("history");
        tv_clock.setText(model.getTimeLength());
        tv_calorie.setText(model.getCalories());
        tv_step.setText(model.getTotal());
        tv_distance.setText(model.getKilometre());
        tv_avg_speed.setText(model.getSpeed());
        tv_create_time.setText(model.getCreatetime());
        String coords=model.getTrajectory();
        List<LatLng> list = readLatLngs(coords);
        int color=Color.parseColor("#ffffff");
        if(!list.isEmpty()){
            polylineOptions=new PolylineOptions().color(Color.GREEN).addAll(list).width(20);
            polylineOptions.zIndex(150);
            aMap.addPolyline(polylineOptions);
            LatLng start=list.get(0);
            LatLng end=list.get(list.size()-1);
            polygonOptions=new PolygonOptions().addAll(createRectangle(start,5,5)).fillColor(color).strokeColor(color).strokeWidth(1);
            polygonOptions.visible(false);
            polygonOptions.zIndex(100);
            aMap.addPolygon(polygonOptions);
            LatLngBounds bounds=new LatLngBounds.Builder().include(start).include(end).build();
            startMark=new MarkerOptions().position(start).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_mark_start));
            endMark=new MarkerOptions().position(end).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_mark_end));
            aMap.addMarker(startMark);
            aMap.addMarker(endMark);
            // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, DisplayUtil.getMobileWidth(this),DisplayUtil.getMobileHeight(this),8));

        }
    }
    /**
     * 生成一个长方形的四个坐标点
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth,
                                         double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight,
                        center.longitude - halfWidth), new LatLng(center.latitude
                        - halfHeight, center.longitude + halfWidth), new LatLng(
                        center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude
                        - halfWidth));
    }

    private List<LatLng> readLatLngs(String coords) {
        List<LatLng> points = new ArrayList<>();
        Gson gson=new Gson();
        Trajectory trajectory=gson.fromJson(coords, Trajectory.class);
        if(trajectory!=null){
            List<LatLon> latLons=trajectory.getTrajectory();
            for (int i = 0; i < latLons.size(); i ++) {
                LatLon latLon=latLons.get(i);
                points.add(new LatLng(latLon.getLatitude(),latLon.getLongitude()));
            }
        }
        return points;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.cb_map_switch:
                aMap.clear();
                if(polygonOptions.isVisible()){
                    polygonOptions.visible(false);
                    cb_map_switch.setChecked(false);
                }else{
                    polygonOptions.visible(true);
                    cb_map_switch.setChecked(true);
                }
                aMap.addPolygon(polygonOptions);
                aMap.addPolyline(polylineOptions);
                aMap.addMarker(startMark);
                aMap.addMarker(endMark);
                break;
        }
    }
}
