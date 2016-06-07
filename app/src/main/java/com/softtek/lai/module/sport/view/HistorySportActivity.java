package com.softtek.lai.module.sport.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.LatLon;
import com.softtek.lai.module.sport.model.Trajectory;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
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

    //平均速度和GPS信号量
    @InjectView(R.id.tv_avg_speed)
    TextView tv_avg_speed;
    @InjectView(R.id.tv_create_time)
    TextView tv_create_time;

    AMap aMap;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("运动");
    }
    @Override
    protected void initDatas() {
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放控制按钮
        aMap.getUiSettings().setLogoPosition(2);
        HistorySportModel model= (HistorySportModel) getIntent().getSerializableExtra("history");
        tv_clock.setText(model.getTimeLength());
        tv_calorie.setText(model.getCalories());
        tv_step.setText(model.getTotal());
        tv_distance.setText(model.getKilometre());
        tv_avg_speed.setText(model.getSpeed());
        tv_create_time.setText(model.getCreatetime());
        String coords=model.getTrajectory();
        List<LatLng> list = readLatLngs(coords);
        if(!list.isEmpty()){
            aMap.addPolyline(new PolylineOptions().color(Color.RED)
                    .addAll(list).useGradient(true).width(20));
            LatLng start=list.get(0);
            LatLng end=list.get(list.size()-1);
            LatLngBounds bounds=new LatLngBounds.Builder().include(start).include(end).build();
            aMap.addMarker(new MarkerOptions().position(start).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_mark_start)));
            aMap.addMarker(new MarkerOptions().position(end).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_mark_end)));
            // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, DisplayUtil.getMobileWidth(this),DisplayUtil.getMobileHeight(this), 10));
        }

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
        }
    }
}