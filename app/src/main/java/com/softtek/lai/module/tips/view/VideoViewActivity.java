package com.softtek.lai.module.tips.view;

import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_video_view)
public class VideoViewActivity extends BaseActivity {

    /**
     * View播放
     */
    private VideoView videoView;
    /**
     * 加载预览进度条
     */
    private ProgressBar progressBar;

    /**
     * 设置view播放控制条
     */
    private MediaController mediaController;

    /**
     * 标记当视频暂停时播放位置
     */
    private int intPositionWhenPause=-1;

    /**
     * 设置窗口模式下的videoview的宽度
     */
    private int videoWidth;

    /**
     * 设置窗口模式下videoview的高度
     */
    private int videoHeight;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

}
