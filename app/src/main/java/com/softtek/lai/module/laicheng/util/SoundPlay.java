package com.softtek.lai.module.laicheng.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;

import java.io.IOException;

public class SoundPlay {

    private static SoundPlay instance;

    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean isStop;

    public static SoundPlay getInstance() {
        if (null == instance) {
            synchronized (SoundPlay.class) {
                if (null == instance) {
                    instance = new SoundPlay();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//播出完毕事件
            @Override
            public void onCompletion(MediaPlayer arg0) {
                if (isStop)
                    stop();
                else
                    mediaPlayer.reset();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {//错误处理事件
            @Override
            public boolean onError(MediaPlayer player, int arg1, int arg2) {
                mediaPlayer.reset();
                return false;
            }
        });
    }

    public void play(final int resourceId) {
        isStop = false;
        try {
            if (mediaPlayer.isPlaying()) {
                stop();
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        AssetFileDescriptor fileDesc = null;
        if (context != null) {
            fileDesc = context.getResources().openRawResourceFd(resourceId);
        }
        if (fileDesc != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                mediaPlayer.reset();
            }
        }
    }

    public void playAgain() {
        isStop = false;
        mediaPlayer.start();
    }

    Handler handler = new Handler();

    public void playWait(final int resourceId) {
        isStop = false;
        try {
            if (!mediaPlayer.isPlaying()) {
                AssetFileDescriptor fileDesc = context.getResources().openRawResourceFd(resourceId);
                if (fileDesc != null) {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (Exception e) {
                        mediaPlayer.reset();
                    }
                }
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }

    public void playAndStop(final int resourceId) {
        isStop = true;
        try {
            if (mediaPlayer.isPlaying()) {
                stop();
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        AssetFileDescriptor fileDesc = context.getResources().openRawResourceFd(resourceId);
        if (fileDesc != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                mediaPlayer.reset();
            }
        }
    }

    public void stop() {
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
        } catch (Exception e) {

        }

    }

    public void release() {
        mediaPlayer.release();
        context = null;
    }
}
