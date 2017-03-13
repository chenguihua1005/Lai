package com.softtek.lai.module.laiClassroom;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.dl7.player.media.IjkPlayerView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseActivity2;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_video_detail)
public class VideoDetailActivity extends BaseActivity2 {

    @InjectView(R.id.player_view)
    IjkPlayerView playerView;

    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    private static final String IMAGE_URL = "http://vimg2.ws.126.net/image/snapshot/2016/11/I/M/VC62HMUIM.jpg";
    @Override
    protected void initViews() {
        Picasso.with(this).load(IMAGE_URL).fit().into(playerView.mPlayerThumb);
        playerView.init()
                .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                .setSkipTip(1000*60*1)
                .setVideoSource(null, VIDEO_URL, VIDEO_HD_URL, null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
                .start();
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        playerView.onPause();
    }

    @Override
    protected void onDestroy() {
        playerView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (playerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
