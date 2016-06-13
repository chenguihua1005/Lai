package com.softtek.lai.module.sport.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.TotalSportModel;
import com.softtek.lai.module.sport.presenter.SportManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_start_sport)
public class StartSportActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener, SportManager.GetHistoryTotalMovementCallBack {
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

    Timer timer;
    int recLen;

    private List<HistorySportModel> list = new ArrayList<HistorySportModel>();
    TimerTask task;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        text_total_distance.setOnClickListener(this);
        rel_start.setOnClickListener(this);
        rel_start1.setOnClickListener(this);
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
//        new Thread(){
//            @Override
//            public void run()
//            {
//                //把网络访问的代码放在这里
//                WheatherUtil util=new WheatherUtil();
//                String request = util.request("http://apis.baidu.com/apistore/aqiservice/aqi?city=乌鲁木齐");
//                //request=convert(request);
//                System.out.println("request:"+request);
//                Gson gson = new Gson();
//                ResultModel resultModel = gson.fromJson(request, ResultModel.class);
//                System.out.println("resultModel:"+resultModel);
//                System.out.println("level:"+resultModel.getRetData().getLevel());
//            }
//        }.start();
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
        }

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
            String km= StringUtils.isEmpty(model.getTotalKilometer())?"0":model.getTotalKilometer();
            text_total_distance.setText(km);
            text_total_time.setText(model.getTotalTime());
            text_total_count.setText(model.getCount());
        }
    }
}
