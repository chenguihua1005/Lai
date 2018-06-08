package com.softtek.lai.module.sport2.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Xml;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.sport.model.SportModel;
import com.softtek.lai.module.sport.model.TotalSportModel;
import com.softtek.lai.module.sport.model.Weather;
import com.softtek.lai.module.sport.net.WeatherServer;
import com.softtek.lai.module.sport.presenter.SportManager;
import com.softtek.lai.module.sport.util.SportUtil;
import com.softtek.lai.module.sport.view.HistorySportListActivity;
import com.softtek.lai.module.sport.view.RunSportActivity;
import com.softtek.lai.sound.SoundHelper;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.RippleLayout;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_sport)
public class SportFragment extends LazyBaseFragment implements View.OnClickListener, SportManager.GetHistoryTotalMovementCallBack,
        AMapLocationListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.text_total_distance)
    TextView text_total_distance;

    @InjectView(R.id.text_total_count)
    TextView text_total_count;

    @InjectView(R.id.text_total_time)
    TextView text_total_time;


    @InjectView(R.id.text_start)
    TextView text_start;
    @InjectView(R.id.ripple)
    RippleLayout ripple;

    //***********天气状况
    @InjectView(R.id.ll)
    LinearLayout ll_ll;//运动状况

    @InjectView(R.id.tv_sport)
    TextView tv_sport;
    @InjectView(R.id.tv_air_quality)
    TextView tv_air_quality;
    @InjectView(R.id.tv_air_index)
    TextView tv_air_index;
    @InjectView(R.id.tv_air_temperature)
    TextView tv_air_temperature;
    @InjectView(R.id.tv_air_quality1)
    TextView tv_air_quality1;
    @InjectView(R.id.iv_gps)
    ImageView iv_gps;


    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private static final int LOCATION_PREMISSION = 100;

    private SoundHelper speed;

    public SportFragment() {

    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    protected void initViews() {
        speed=new SoundHelper(getContext(),1);
        speed.addAudio("ready",R.raw.sport_ready);
        ll_left.setOnClickListener(this);
        text_total_distance.setOnClickListener(this);
        ll_ll.setOnClickListener(this);
        tv_sport.setOnClickListener(this);
        ll_ll.setEnabled(false);
        tv_sport.setEnabled(false);
        text_start.setOnClickListener(this);
        aMapLocationClient = new AMapLocationClient(getContext());
        aMapLocationClientOption = new AMapLocationClientOption();
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
        //设置定位间隔,单位毫秒,默认为3000ms
        aMapLocationClientOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.setLocationListener(this);
        /**
         * Android 6.0动态权限申请
         * PackageManager.PERMISSION_GRANTED:允许使用权限
         * PackageManager.PERMISSION_DENIED:不允许使用权限
         */
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //允许弹出提示
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PREMISSION);
            } else {
                //不允许弹出提示
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PREMISSION);
            }
        } else {
            //执行获取权限后的操作
            //启动定位
            aMapLocationClient.startLocation();
        }

    }


    SportManager manager;

    @Override
    protected void initDatas() {
        tv_title.setText("运动");
        manager = new SportManager(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("运动onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("运动onResume");
        ripple.post(new Runnable() {
            public void run() {
                ripple.init(ripple.getWidth() / 2,//中心点x
                        ripple.getHeight() / 2,//中心点y
                        text_start.getWidth()/2,//波纹的初始半径
                        Math.min(ripple.getWidth(), ripple.getHeight()) / 2,//波纹的结束半径
                        1500,//时常
                        Color.parseColor("#D7F3BA"),//颜色
                        10,//圆圈宽度
                        new DecelerateInterpolator());//开始快,后来慢

            }
        });
        manager.getHistoryTotalMovement();
    }

    @Override
    public void onPause() {
        super.onPause();
        ripple.stopRipple();
        Log.i("运动onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("运动onStop");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PREMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    aMapLocationClient.startLocation();

                }
                break;
        }
    }

    @Override
    public void onLocationChanged(final AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            String city = aMapLocation.getCity();
            if (StringUtils.isNotEmpty(city)) {
                aMapLocationClient.stopLocation();
                ZillaApi.getCustomRESTAdapter("http://wthrcdn.etouch.cn", new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                    }
                }).create(WeatherServer.class).getWeather(city.substring(0, city.length() - 1), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        if (ll_ll != null) ll_ll.setEnabled(true);
                        if (tv_sport != null) tv_sport.setEnabled(true);
                        try {
                            Weather weather = paseXml(response.getBody().in());
                            if (weather != null) {
                                tv_sport.setText("运动：" + StringUtil.withValue(weather.getSport()));
                                String index = "空气指数：" + StringUtil.withValue(weather.getAqi());
                                String temperature = "室外温度：" + (StringUtils.isEmpty(weather.getWenDu()) ? "未知" : weather.getWenDu() + "℃");
                                String quality = "空气质量：" + StringUtil.withValue(weather.getQuality());
                                tv_air_index.setText(wrapperString(index, Color.parseColor("#75BA2B"), 5, index.length()));
                                tv_air_temperature.setText(wrapperString(temperature, Color.parseColor("#75BA2B"), 5, temperature.length()));
                                tv_air_quality.setText(wrapperString(quality, Color.parseColor("#75BA2B"), 5, quality.length()));
                                tv_air_quality1.setText(" 空气 " + StringUtil.withValue(weather.getQuality()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        aMapLocationClient.startLocation();
                    }
                });
            }
        }
    }

    /**
     * 包裹字符串表示在字符串的某处用什么颜色替换
     */
    private SpannableString wrapperString(CharSequence sequence, int color, int start, int end) {
        SpannableString ss = new SpannableString(sequence);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }



    //解析Xml
    private Weather paseXml(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        Weather weather = null;
        parser.setInput(is, "utf-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    weather = new Weather();
                    break;
                case XmlPullParser.START_TAG:
                    String tagName = parser.getName();
                    if (tagName.equals("city")) {
                        parser.next();
                        weather.setCity(parser.getText());
                    } else if (tagName.equals("wendu")) {
                        weather.setWenDu(parser.nextText());
                    } else if (tagName.equals("aqi")) {
                        weather.setAqi(parser.nextText());
                    } else if (tagName.equals("quality")) {
                        weather.setQuality(parser.nextText());
                    } else if (tagName.equals("zhishu")) {
                        parser.next();
                        if (parser.getName().equals("name")) {
                            String sport = parser.nextText();
                            if (sport.equals("运动指数")) {
                                parser.next();
                                if (parser.getName().equals("value")) {
                                    parser.next();
                                    weather.setSport(parser.getText());
                                }
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;

            }
            eventType = parser.next();
        }

        return weather;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                getActivity().startActivity(new Intent(getActivity(), HomeActviity.class));
                break;
            case R.id.text_start:
                speed.play("ready");
                if (isGpsEnable()) {
                    //先检查是否有异常记录
                    final ArrayList<SportModel> list = (ArrayList<SportModel>) SportUtil.getInstance().querySport(UserInfoModel.getInstance().getUserId() + "");
                    if (!list.isEmpty()) {//如果不是空则表示有异常记录未提交
                        new AlertDialog.Builder(getContext())
                                .setTitle(getString(R.string.login_out_title))
                                .setMessage("您上一次运动记录非正常退出，是否需要延续上次运动继续")
                                .setPositiveButton("接着运动", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent sport = new Intent(getContext(), RunSportActivity.class);
                                        sport.putParcelableArrayListExtra("sportList", list);
                                        startActivity(sport);
                                    }
                                })
                                .setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SportUtil.getInstance().deleteSport();
                                        startActivity(new Intent(getContext(),CountDownActivity.class));
                                    }
                                }).create().show();
                    } else {
                        startActivity(new Intent(getContext(),CountDownActivity.class));
                    }
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                            .setTitle(getString(R.string.login_out_title))
                            .setMessage("为了正常记录你的运动数据，莱聚+需要你开启GPS定位功能。")
                            .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 转到手机设置界面，用户设置GPS
                                    Intent intent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                                }
                            })
                            .setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    dialogBuilder.create().show();
                }
                break;
            case R.id.text_total_distance:
                startActivity(new Intent(getContext(), HistorySportListActivity.class));
                break;
            case R.id.tv_sport:
                //收起动画
                shouqiAnim();
                break;
            case R.id.ll:
                //滑出动画
                startX = tv_sport.getX();
                quality_x = startX - tv_air_quality.getWidth() - 15;
                index_x = quality_x - tv_air_index.getWidth() - 15;
                temperature_x = index_x - tv_air_index.getWidth() - 40;
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
                PropertyValuesHolder traslation_quality = PropertyValuesHolder.ofFloat("x", startX, quality_x);
                PropertyValuesHolder traslation_index = PropertyValuesHolder.ofFloat("x", quality_x, index_x);
                PropertyValuesHolder traslation_temperature = PropertyValuesHolder.ofFloat("x", index_x, temperature_x);
                AnimatorSet set_anim = new AnimatorSet();
                //set_anim.setInterpolator(new OvershootInterpolator());
                ObjectAnimator quality_anim = ObjectAnimator.ofPropertyValuesHolder(tv_air_quality, traslation_quality, alpha).setDuration(200);
                quality_anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        iv_gps.setVisibility(View.GONE);
                        ll_ll.setVisibility(View.GONE);
                        tv_sport.setVisibility(View.VISIBLE);
                        tv_air_quality.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tv_air_index.setVisibility(View.VISIBLE);
                    }
                });
                ObjectAnimator index_anim = ObjectAnimator.ofPropertyValuesHolder(tv_air_index, traslation_index, alpha).setDuration(300);
                index_anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tv_air_temperature.setVisibility(View.VISIBLE);
                    }
                });
                ObjectAnimator temperature_anim = ObjectAnimator.ofPropertyValuesHolder(tv_air_temperature, traslation_temperature, alpha).setDuration(300);
                set_anim.playSequentially(quality_anim, index_anim, temperature_anim);
                set_anim.start();
                break;
        }

    }

    private float startX;
    float quality_x;
    float index_x;
    float temperature_x;

    private void shouqiAnim() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder traslation_quality = PropertyValuesHolder.ofFloat("x", quality_x, startX);
        PropertyValuesHolder traslation_index = PropertyValuesHolder.ofFloat("x", index_x, startX);
        PropertyValuesHolder traslation_temperature = PropertyValuesHolder.ofFloat("x", temperature_x, startX);
        AnimatorSet set_anim = new AnimatorSet();
        set_anim.setInterpolator(new OvershootInterpolator(0));
        ObjectAnimator quality_anim = ObjectAnimator.ofPropertyValuesHolder(tv_air_quality, traslation_quality, alpha).setDuration(300);
        ObjectAnimator index_anim = ObjectAnimator.ofPropertyValuesHolder(tv_air_index, traslation_index, alpha).setDuration(200);
        ObjectAnimator temperature_anim = ObjectAnimator.ofPropertyValuesHolder(tv_air_temperature, traslation_temperature, alpha).setDuration(100);
        set_anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                iv_gps.setVisibility(View.VISIBLE);
                tv_air_quality.setVisibility(View.GONE);
                tv_air_index.setVisibility(View.GONE);
                tv_air_temperature.setVisibility(View.GONE);
                ll_ll.setVisibility(View.VISIBLE);
                tv_sport.setVisibility(View.INVISIBLE);
            }
        });
        set_anim.playSequentially(quality_anim, index_anim, temperature_anim);
        set_anim.start();
    }

    // Gps是否可用
    public boolean isGpsEnable() {
        LocationManager locationManager =
                ((LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        aMapLocationClient.stopLocation();
        speed.release();
    }


    @Override
    public void getHistoryTotalMovement(String type, TotalSportModel model) {
        try {
            if ("true".equals(type)) {
                String km = StringUtils.isEmpty(model.getTotalKilometer()) ? "0" : model.getTotalKilometer();
                if (text_total_distance != null)
                    text_total_distance.setText(km);
                if (text_total_time != null) {
                    text_total_time.setText(TextUtils.isEmpty(model.getTotalTime())?"0":model.getTotalTime());
                    SpannableString ss=new SpannableString("小时");
                    ss.setSpan(new AbsoluteSizeSpan(24,true),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text_total_time.append(ss);
                }
                if (text_total_count != null) {
                    text_total_count.setText(TextUtils.isEmpty(model.getCount())?"0":model.getCount());
                    SpannableString ss=new SpannableString("次");
                    ss.setSpan(new AbsoluteSizeSpan(24,true),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text_total_count.append(ss);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
