package com.softtek.lai.sound;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.SystemClock;

import java.util.HashMap;

/**
 * Created by jerry.guan on 10/10/2016.
 */
public class SoundHelper {

    private HashMap<String,Integer> soundMap;
    private SoundPool soundPool;
    private Context context;

    /**
     *
     * @param context
     * @param maxStreams 同时支持多少音频流一起播放
     */
    public SoundHelper(Context context,int maxStreams) {
        this.context=context;
        soundMap=new HashMap<>();
        if(Build.VERSION.SDK_INT<21){
            soundPool=new SoundPool(maxStreams,AudioManager.STREAM_MUSIC,5);
        }else {
            initSoundPoolApi21(maxStreams);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initSoundPoolApi21(int maxStreams){
        SoundPool.Builder builder=new SoundPool.Builder();
        builder.setMaxStreams(maxStreams);
        AudioAttributes.Builder attributes=new AudioAttributes.Builder();
        attributes.setLegacyStreamType(AudioManager.STREAM_MUSIC);
        builder.setAudioAttributes(attributes.build());
        soundPool=builder.build();
    }

    //添加音频
    public void addAudio(String name,int res){
        if(soundPool!=null&&soundMap!=null){
            soundMap.put(name,soundPool.load(context,res,1));
        }
    }

    //播放
    public void play(String name){
        if(soundPool!=null&&soundMap!=null){
            if(soundMap.containsKey(name)) {
                soundPool.play(soundMap.get(name), 1, 1, 0, 0, 1);
            }
        }
    }

    public void play(String name,int delay){
        if(soundPool!=null&&soundMap!=null){
            if(soundMap.containsKey(name)){
                soundPool.play(soundMap.get(name),1,1,0,0,1);
                SystemClock.sleep(delay);
            }
        }
    }

    //按顺序播放
    public void playSequence(String[] name,int[] delay){
        if(soundPool!=null&&soundMap!=null){
            for (int i=0;i<name.length;i++){
                String key=name[i];
                if(soundMap.containsKey(key)){
                    soundPool.play(soundMap.get(key),1,1,0,0,1);
                    SystemClock.sleep(delay[i]);
                }
            }
        }
    }

    //停止
    public void stop(String name){
        if(soundPool!=null&&soundMap!=null){
            if(soundMap.containsKey(name)) {
                soundPool.stop(soundMap.get(name));
            }
        }
    }

    //暂停
    public void pause(String name){
        if(soundPool!=null&&soundMap!=null){
            if(soundMap.containsKey(name)) {
                soundPool.pause(soundMap.get(name));
            }
        }
    }
    //继续
    public void resume(String name){
        if(soundPool!=null&&soundMap!=null){
            if(soundMap.containsKey(name)) {
                soundPool.resume(soundMap.get(name));
            }
        }
    }

    //释放资源
    public void release(){
        if(soundPool!=null){
            soundPool.release();
            soundMap.clear();
        }
    }


}
