package com.softtek.lai.module.sport2.view;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.sport.view.RunSportActivity;
import com.softtek.lai.sound.SoundHelper;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_count_down)
public class CountDownActivity extends BaseActivity {

    @InjectView(R.id.text_djs)
    TextView text_djs;

    Timer timer;
    int recLen;

    TimerTask task;
    private SoundHelper sounder;

    @Override
    protected void initViews() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        sounder=new SoundHelper(this,5);
        sounder.addAudio("countDown",R.raw.countdown);
        sounder.addAudio("startSport",R.raw.start_sport);
        sounder.addAudio("count_dwon3",R.raw.count_down_3);
        sounder.addAudio("count_dwon2",R.raw.count_down_2);
        sounder.addAudio("count_dwon1",R.raw.count_down_1);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sounder.play("countDown");
            }
        }, 1000);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startTimer();
            }
        },2000);
    }

    @Override
    protected void initDatas() {


    }

    private void startTimer() {
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
                            sounder.play("startSport");
                            startActivity(new Intent(CountDownActivity.this, RunSportActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        } else {
                            if (text_djs != null) {
                                text_djs.setText(recLen + "");
                            }
                            sounder.play("count_dwon"+recLen);
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
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        sounder.release();
    }
}
