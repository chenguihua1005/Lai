package com.softtek.lai.module.sport.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.TotalSportModel;
import com.softtek.lai.module.sport.model.Weather;
import com.softtek.lai.module.sport.net.WeatherServer;
import com.softtek.lai.module.sport.presenter.SportManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_start_sport)
public class StartSportActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, SportManager.GetHistoryTotalMovementCallBack,
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

    @InjectView(R.id.rel_start)
    RelativeLayout rel_start;
    @InjectView(R.id.rel_start1)
    RelativeLayout rel_start1;

    @InjectView(R.id.rel_djs)
    RelativeLayout rel_djs;

    @InjectView(R.id.img_start)
    ImageView img_start;

    @InjectView(R.id.text_start)
    TextView text_start;

    @InjectView(R.id.text_djs)
    TextView text_djs;

    //***********天气状况
    @InjectView(R.id.ll)
    LinearLayout ll_ll;//运动状况
    @InjectView(R.id.iv_air_iocn)
    ImageView iv_air_icon;
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
    //***************

    Timer timer;
    int recLen;

    private List<HistorySportModel> list = new ArrayList<HistorySportModel>();
    TimerTask task;

    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        text_total_distance.setOnClickListener(this);
        rel_start.setOnClickListener(this);
        rel_start1.setOnClickListener(this);
        ll_ll.setOnClickListener(this);
        tv_sport.setOnClickListener(this);
        aMapLocationClient = new AMapLocationClient(this);
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
        aMapLocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(final AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            String city = aMapLocation.getCity();
            Log.i("当前城市>>>" + city.substring(0,city.length()-1));
            if (StringUtils.isNotEmpty(city)) {
                aMapLocationClient.stopLocation();
                ZillaApi.getCustomRESTAdapter("http://wthrcdn.etouch.cn", new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                    }
                }).create(WeatherServer.class).getWeather(city.substring(0,city.length()-1), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try {
                            Weather weather = paseXml(response.getBody().in());
                            if(weather!=null){
                                tv_sport.setText("运动:"+ StringUtil.withValue(weather.getSport()));
                                String index=StringUtil.withValue(weather.getAqi());
                                String temperature=StringUtils.isEmpty(weather.getWenDu())?"未知":weather.getWenDu()+"℃";
                                String quality=StringUtil.withValue(weather.getQuality());
                                tv_air_index.setText("空气指数:"+wrapperString(index,Color.parseColor("#75BA2B"),0,index.length()));
                                tv_air_temperature.setText("室外温度:"+wrapperString(temperature,Color.parseColor("#75BA2B"),0,temperature.length()));
                                tv_air_quality.setText("空气质量:"+wrapperString(quality,Color.parseColor("#75BA2B"),0,quality.length()));
                                tv_air_quality1.setText(" 空气 "+quality);
                            }
                            Log.i("天气状况" + weather != null ? weather.toString() : null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
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
     * @param sequence
     * @param color
     * @param start
     * @param end
     * @return
     */
    private SpannableString wrapperString(CharSequence sequence, int color,int start,int end){
        SpannableString ss=new SpannableString(sequence);
        ss.setSpan(new ForegroundColorSpan(color),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onResume() {
        super.onResume();
        SportManager manager = new SportManager(this);
        manager.getHistoryTotalMovement();
    }

    @Override
    protected void initDatas() {
        tv_title.setText("运动");
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

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                if (timer != null) {
                    timer.cancel();
                }
                finish();
                break;
            case R.id.rel_start1:
            case R.id.rel_start:
                if (isGpsEnable()) {
                    startBigAnimal();
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
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
                startActivity(new Intent(StartSportActivity.this, HistorySportListActivity.class));
                break;
            case R.id.tv_sport:
                //收起动画
                shouqiAnim();
                break;
            case R.id.ll:
                //滑出动画

                startX=tv_sport.getX();
                quality_x=startX-tv_air_quality.getWidth()-10;
                index_x=quality_x-tv_air_index.getWidth()-10;
                temperature_x=index_x-tv_air_index.getWidth()-20;
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha",0f, 1f);
                PropertyValuesHolder traslation_quality=PropertyValuesHolder.ofFloat("x",startX,quality_x);
                PropertyValuesHolder traslation_index=PropertyValuesHolder.ofFloat("x",quality_x,index_x);
                PropertyValuesHolder traslation_temperature=PropertyValuesHolder.ofFloat("x",index_x,temperature_x);
                AnimatorSet set_anim=new AnimatorSet();
                set_anim.setInterpolator(new OvershootInterpolator());
                ObjectAnimator quality_anim=ObjectAnimator.ofPropertyValuesHolder(tv_air_quality,traslation_quality,alpha).setDuration(300);
                ObjectAnimator index_anim=ObjectAnimator.ofPropertyValuesHolder(tv_air_index,traslation_index,alpha).setDuration(300);
                ObjectAnimator temperature_anim=ObjectAnimator.ofPropertyValuesHolder(tv_air_temperature,traslation_temperature,alpha).setDuration(300);
                set_anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        iv_gps.setVisibility(View.GONE);
                        ll_ll.setVisibility(View.GONE);
                        tv_sport.setVisibility(View.VISIBLE);
                        tv_air_quality.setVisibility(View.VISIBLE);
                        tv_air_index.setVisibility(View.VISIBLE);
                        tv_air_temperature.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {


                    }
                });
                set_anim.playSequentially(quality_anim,index_anim,temperature_anim);
                set_anim.start();
                break;
        }

    }
    private float startX;
    float quality_x;
    float index_x;
    float temperature_x;
    private void shouqiAnim(){
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha",1f, 0f);
        PropertyValuesHolder traslation_quality=PropertyValuesHolder.ofFloat("x",quality_x,startX);
        PropertyValuesHolder traslation_index=PropertyValuesHolder.ofFloat("x",index_x,startX);
        PropertyValuesHolder traslation_temperature=PropertyValuesHolder.ofFloat("x",temperature_x,startX);
        AnimatorSet set_anim=new AnimatorSet();
        set_anim.setInterpolator(new OvershootInterpolator(0));
        ObjectAnimator quality_anim=ObjectAnimator.ofPropertyValuesHolder(tv_air_quality,traslation_quality,alpha).setDuration(300);
        ObjectAnimator index_anim=ObjectAnimator.ofPropertyValuesHolder(tv_air_index,traslation_index,alpha).setDuration(200);
        ObjectAnimator temperature_anim=ObjectAnimator.ofPropertyValuesHolder(tv_air_temperature,traslation_temperature,alpha).setDuration(100);
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
        set_anim.playSequentially(quality_anim,index_anim,temperature_anim);
        set_anim.start();
    }

    // Gps是否可用
    public boolean isGpsEnable() {
        LocationManager locationManager =
                ((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void startBigAnimal() {
        ScaleAnimation sa = new ScaleAnimation(1f, 10.0f, 1f, 10.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(400);
        rel_start.startAnimation(sa);
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setDuration(400);
        text_start.startAnimation(aa);
        sa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startTimer();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startTimer() {
        rel_djs.setVisibility(View.VISIBLE);
        timer = new Timer();
        recLen = 3;
        task = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        if (recLen <= 0) {
                            timer.cancel();
                            startActivity(new Intent(StartSportActivity.this, RunSportActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rel_djs.setVisibility(View.GONE);
                                }
                            }, 500);
                        } else {
                            if (text_djs != null) {
                                text_djs.setText(recLen + "");
                            }
                            startSacaleBigAnimation();
                            recLen--;
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);
    }

    private void startSacaleBigAnimation() {
        ScaleAnimation sa = new ScaleAnimation(0f, 1f, 0f, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(300);
        sa.setFillAfter(true);
        text_djs.startAnimation(sa);
        sa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ScaleAnimation sas = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                sas.setDuration(700);
                text_djs.startAnimation(sas);
                AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
                aa.setDuration(300);
                AnimationSet set = new AnimationSet(true);
                set.addAnimation(sas);
                set.addAnimation(aa);
                set.setFillAfter(true);
                text_djs.startAnimation(set);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (timer != null) {
                timer.cancel();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void getHistoryTotalMovement(String type, TotalSportModel model) {
        if ("true".equals(type)) {
            String km = StringUtils.isEmpty(model.getTotalKilometer()) ? "0" : model.getTotalKilometer();
            text_total_distance.setText(km);
            text_total_time.setText(model.getTotalTime());
            text_total_count.setText(model.getCount());
        }
    }


}
