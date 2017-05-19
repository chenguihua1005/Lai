package com.softtek.lai.module.sport.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.sportchart.presenter.GetProinfoImpl;
import com.softtek.lai.module.sportchart.presenter.IGetProinfopresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.message2.model.PhotosModel;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.KilometrePace;
import com.softtek.lai.module.sport.model.MineMovementModel;
import com.softtek.lai.module.sport.model.SportModel;
import com.softtek.lai.module.sport.model.Trajectory;
import com.softtek.lai.module.sport.net.SportService;
import com.softtek.lai.module.sport.util.ColorUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_history_sport)
public class HistorySportActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.map)
    MapView mapView;

    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;


    @InjectView(R.id.tv_clock)
    TextView tv_clock;
    @InjectView(R.id.tv_calorie)
    TextView tv_calorie;
    @InjectView(R.id.tv_step)
    TextView tv_step;
    @InjectView(R.id.tv_distance)
    TextView tv_distance;

    @InjectView(R.id.ll_panel)
    LinearLayout ll_panel;

    @InjectView(R.id.cb_map_switch)
    CheckBox cb_map_switch;

    //平均速度和GPS信号量
    @InjectView(R.id.tv_avg_speed)
    TextView tv_avg_speed;
    @InjectView(R.id.tv_create_time)
    TextView tv_create_time;

    AMap aMap;
    List<PolylineOptions> optionses=new ArrayList<>();
    PolylineOptions polylineOptions;
    GroundOverlayOptions groundOverlayOptions;
    MineMovementModel mineMovementModel;
    String url;
    String value;
    String title_value;
    SelectPicPopupWindow menuWindow;

    Bitmap bitmap_map;

    private IGetProinfopresenter iGetProinfopresenter;
    HistorySportModel model;
    private static final int LOCATION_PREMISSION = 100;

    private AMap.OnMapScreenShotListener onMapScreenShotListener = new AMap.OnMapScreenShotListener() {
        @Override
        public void onMapScreenShot(Bitmap bitmap) {
            bitmap_map = bitmap;
            if (ContextCompat.checkSelfPermission(HistorySportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(HistorySportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                com.github.snowdream.android.util.Log.i("检查权限。。。。");
                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                dialogDissmiss();
                if (
                        ActivityCompat.shouldShowRequestPermissionRationale(HistorySportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(HistorySportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //允许弹出提示
                    ActivityCompat.requestPermissions(HistorySportActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            LOCATION_PREMISSION);

                } else {
                    //不允许弹出提示
                    ActivityCompat.requestPermissions(HistorySportActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            LOCATION_PREMISSION);
                }
            } else {
                dialogShow("加载中");
                savePic(bitmap_map, "/sdcard/sport.png");
            }


        }

        @Override
        public void onMapScreenShot(Bitmap bitmap, int i) {

        }
    };

    //6.0权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PREMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dialogShow("加载中");
                    savePic(bitmap_map, "/sdcard/sport.png");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }

    }

    // 保存到sdcard
    public void savePic(Bitmap bitmap, String strFileName) {
        if (null == bitmap) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(strFileName);
            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b) {
                SportService service = ZillaApi.NormalRestAdapter.create(SportService.class);
                UserModel model = UserInfoModel.getInstance().getUser();
                service.getMineMovement(UserInfoModel.getInstance().getToken(), model.getUserid(), new RequestCallback<ResponseData<MineMovementModel>>() {
                    @Override
                    public void success(ResponseData<MineMovementModel> responseData, Response response) {
                        int status = responseData.getStatus();
                        switch (status) {
                            case 200:
                                mineMovementModel = responseData.getData();
                                iGetProinfopresenter.upload("/sdcard/sport.png");
                                break;

                            default:
                                dialogDissmiss();
                                Util.toastMsg(responseData.getMsg());
                                break;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        ZillaApi.dealNetError(error);
                    }
                });
            } else {
                dialogDissmiss();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEvent(PhotosModel photModel) {
        dialogDissmiss();
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        String path = AddressManager.get("shareHost");
        url = path + "ShareMineMoveMent?Movementid=" + model.getMid() + "&Imgpath=" + photModel.getImg();
        System.out.println("url:" + url);
        String[] time = model.getTimeLength().split(":");
        String times = time[0] + "时" + time[1] + "分" + time[2] + "秒";
        value = "我刚刚完成跑步" + model.getKilometre() + "km，用时" + times + "，平均速度" + model.getSpeed() + "km/h，消耗" + model.getCalories() + "大卡。快来和我一起运动吧！";
        System.out.println("value:" + value);
        title_value = "莱运动， 陪伴我运动第" + mineMovementModel.getRgTime() + "天";
        menuWindow = new SelectPicPopupWindow(HistorySportActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(HistorySportActivity.this.findViewById(R.id.ll_panel), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(HistorySportActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(HistorySportActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(HistorySportActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(title_value)
                            .withText(value)
                            .withTargetUrl(url)
                            .withMedia(new UMImage(HistorySportActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(HistorySportActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(value + url)
                            .withMedia(new UMImage(HistorySportActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ll_left.setOnClickListener(this);
        cb_map_switch.setOnClickListener(this);
        tv_title.setText("运动记录");
        iGetProinfopresenter = new GetProinfoImpl(this);
        iv_email.setVisibility(View.VISIBLE);
        iv_email.setImageResource(R.drawable.img_share_bt);
        iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);
    }
    LatLng end;
    LatLng start;
    @Override
    protected void initDatas() {
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放控制按钮
        aMap.getUiSettings().setAllGesturesEnabled(false);
        aMap.getUiSettings().setScrollGesturesEnabled(true);
        aMap.getUiSettings().setZoomGesturesEnabled(true);
        model = (HistorySportModel) getIntent().getSerializableExtra("history");
        tv_clock.setText(model.getTimeLength());
        tv_calorie.setText(model.getCalories());
        tv_step.setText(model.getTotal());
        tv_distance.setText(model.getKilometre());
        tv_avg_speed.setText(model.getSpeed()+"km/h");
        tv_create_time.setText(model.getCreatetime());
        String coords = model.getTrajectory();
        List<SportModel> list = drawPath(coords);
        if(list!=null&&!list.isEmpty()){
            start = new LatLng(list.get(0).getLatitude(),list.get(0).getLongitude());
            end = new LatLng(list.get(list.size()-1).getLatitude(),list.get(list.size()-1).getLongitude());
            setMarker(start,end);
            cb_map_switch.setEnabled(true);
        }else{
            cb_map_switch.setEnabled(false);
        }
    }


    private List<SportModel> drawPath(String coords) {
        List<SportModel> models=null;
        Gson gson = new Gson();
        Trajectory trajectory = gson.fromJson(coords, Trajectory.class);
        if (trajectory != null) {
            polylineOptions = new PolylineOptions().width(22).zIndex(150);
            models = trajectory.getTrajectory();
            List<KilometrePace> paceList=trajectory.getKilometrePaces();
            if(paceList==null||paceList.isEmpty()||paceList.size()==1){
                //表示不满一公里按照最后一个坐标的耗时在计算
                if(models!=null&&!models.isEmpty()){
                    //SportModel startModel=models.get(0);
                    SportModel lastModel=models.get(models.size()-1);
                    //计算两个坐标之间的平均速度获取1公里的耗时补足
                    double avgSpeed=(lastModel.getCurrentKM())/lastModel.getKilometreTime();
                    int time= (int) (1000/avgSpeed);
                    polylineOptions.color(ColorUtil.getSpeedColor(time, getBoolean(lastModel.getHasProblem())));
                    for (SportModel model : models) {
                        polylineOptions.add(new LatLng(model.getLatitude(), model.getLongitude()));
                    }
                }
                aMap.addPolyline(polylineOptions);
            }else {
                optionses=getRoute(paceList,models);
                for (PolylineOptions options:optionses){
                    aMap.addPolyline(options);
                }
            }
        }
        return models;
    }

    //拿到每一公里的坐标
    private List<PolylineOptions> getRoute(List<KilometrePace> paces,List<SportModel> models){
        List<PolylineOptions> polylineOptionses=new ArrayList<>();
        int index=0;
        int lastColor=0;
        for (int j=0;j<paces.size();j++) {
            KilometrePace pace=paces.get(j);
            //创建每一公里的坐标组
            List<LatLng> latLngs=new ArrayList<>();
            List<Integer> colorList = new ArrayList<>();
            int color;
            //最后一条可能不是真的公里节点，所以要判断一下
            //当当前的公里节点不是最后一条的时候，直接取正常的颜色，
            // 如果它到了最后一条了，那么在看看他是不是总路径的最后一条？
            //如果他不是总路径的最后一条成立则去正常颜色否则就是剩余路径

            if(j!=paces.size()-1||Integer.parseInt(pace.getIndex())!=Integer.parseInt(models.get(models.size()-1).getIndex())){
                color=ColorUtil.getSpeedColor(pace.getKilometreTime(), getBoolean(pace.getHasProblem()));
            }else {
                break;
            }
            for (int i = index; i < models.size(); i++) {
                SportModel model = models.get(i);
                if (Integer.parseInt(pace.getIndex()) == Integer.parseInt(model.getIndex())) {
                    //表示到了下一个公里节点的开始
                    //创建一个polylineOptions
                    polylineOptionses.add(new PolylineOptions().width(22)
                            .zIndex(150).useGradient(true).colorValues(colorList)
                            .addAll(latLngs));
                    //把索引指向下一个公里点开始的前10个坐标
                    int temp=Integer.parseInt(pace.getIndex())-10;
                    index=temp<0?0:temp;
                    lastColor=color;
                    break;
                }
                /**
                 * 判断是否是前一公里的坐标。
                 * 规则:
                 * 1.上一公里的颜色是否存在
                 * 2.本次坐标不是公里节点
                 */
                if(lastColor!=0&&Integer.parseInt(paces.get(j-1).getIndex()) != Integer.parseInt(model.getIndex())){
                    //如果是前一公里的坐标则使用上一公里的颜色
                    colorList.add(lastColor);
                }else {
                    lastColor=0;
                    //否则使用当前公里的颜色
                    colorList.add(color);
                }
                latLngs.add(new LatLng(model.getLatitude(), model.getLongitude()));
            }
        }
        /**
         * 或许还有剩余的公里数,画剩余的颜色和表记
         * 如果真有最后一点剩余数据那么
         * 那么公里节点的最后一个的index属性肯定比全部的数据要少
         */
        KilometrePace lastPace=paces.get(paces.size()-1);
        if(!getBoolean(lastPace.getIskilometre())) {
            index=Integer.parseInt(paces.get(paces.size()-2).getIndex());
            SportModel startModel = models.get(index);
            SportModel lastModel = models.get(models.size() - 1);
            //计算两个坐标之间的平均速度获取1公里的耗时补足
            double avgSpeed = (lastModel.getCurrentKM() - startModel.getCurrentKM()) / lastModel.getKilometreTime();
            int time = (int) (1000 / avgSpeed);
            int color = ColorUtil.getSpeedColor(time, getBoolean(lastModel.getHasProblem()));
            List<Integer> colorList = new ArrayList<>();
            List<LatLng> latLngs = new ArrayList<>();
            for (int i = index-4; i < models.size(); i++) {
                if(i<0){
                    continue;
                }
                SportModel model = models.get(i);
                if (lastColor != 0 && !getBoolean(model.getIskilometre())) {
                    //如果是前一公里的坐标则使用上一公里的颜色
                    colorList.add(lastColor);
                } else {
                    lastColor = 0;
                    //否则使用当前公里的颜色
                    colorList.add(color);
                }
                latLngs.add(new LatLng(model.getLatitude(), model.getLongitude()));
            }
            polylineOptionses.add(new PolylineOptions().width(22)
                    .zIndex(150).useGradient(true).colorValues(colorList)
                    .addAll(latLngs));
        }
        return polylineOptionses;
    }

    private boolean getBoolean(String value){
        boolean res;
        try {
            res=Integer.parseInt(value)==1;
        } catch (Exception e) {
            res=false;
        }
        return res;
    }

    private void setMarker(LatLng startPoint,LatLng endPoint) {
        addMarker(startPoint, R.drawable.location_mark_start);
        addMarker(endPoint, R.drawable.location_mark_end);
        addBgMarker(endPoint,R.drawable.ic_marker_bg_white);

        aMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition(endPoint,17,0,0)));
    }
   /* 添加起点终点Marker
    * @param ll
    * @param icon
    */
    public final void addMarker(LatLng ll, int icon) {
        final MarkerOptions mo = new MarkerOptions();
        mo.position(ll);
        mo.draggable(true);
        mo.zIndex(5);
        mo.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(this.getResources(), icon)));
        aMap.addMarker(mo);
    }

    /**
     *
     * Description: 添加障碍物
     *
     */
    public final void addBgMarker(LatLng endPoint,int icon) {
        groundOverlayOptions=new GroundOverlayOptions()
                .position(endPoint, 5*1000*1000, 5*1000*1000)
                .image(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(this.getResources(), icon)));
        groundOverlayOptions.visible(false);
        groundOverlayOptions.zIndex(100);
        aMap.addGroundOverlay(groundOverlayOptions);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        EventBus.getDefault().unregister(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_email:
            case R.id.fl_right:
                dialogShow("加载中");
                aMap.getMapScreenShot(onMapScreenShotListener);
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.cb_map_switch:
                aMap.clear();
                if (groundOverlayOptions.isVisible()) {
                    groundOverlayOptions.visible(false);
                    cb_map_switch.setChecked(true);
                } else {
                    groundOverlayOptions.visible(true);
                    cb_map_switch.setChecked(false);
                }
                aMap.addGroundOverlay(groundOverlayOptions);
                if(optionses.isEmpty()){
                    aMap.addPolyline(polylineOptions);
                }else {
                    for (PolylineOptions options:optionses){
                        aMap.addPolyline(options);
                    }
                }
                addMarker(start, R.drawable.location_mark_start);
                addMarker(end, R.drawable.location_mark_end);
                break;
        }
    }
}