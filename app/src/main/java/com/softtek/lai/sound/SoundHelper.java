package com.softtek.lai.sound;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

/**
 * Created by jerry.guan on 10/10/2016.
 */
public class SoundHelper {

    private HashMap<String,Integer> soundMap;
    private SoundPool soundPool;
    private Context context;

    //使用applicationContext
    public SoundHelper(Context context,int maxStreams) {
        this.context=context;
        soundMap=new HashMap<>();
        if(Build.VERSION.SDK_INT<21){
            soundPool=new SoundPool(maxStreams,AudioManager.STREAM_MUSIC,5);
        }else {
            initSoundPoolApi21(maxStreams);
        }
        //加载音频资源 #example:soundMap.put("",soundPool.load(context,R.raw.xxx,1));

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
            soundPool.play(soundMap.get(name),1,1,0,0,1);
        }
    }

    //停止
    public void stop(String name){
        if(soundPool!=null&&soundMap!=null){
            soundPool.stop(soundMap.get(name));
        }
    }

    //暂停
    public void pause(String name){
        if(soundPool!=null&&soundMap!=null){
            soundPool.pause(soundMap.get(name));
        }
    }
    //继续
    public void resume(String name){
        if(soundPool!=null&&soundMap!=null){
            soundPool.resume(soundMap.get(name));
        }
    }

    //释放资源
    public void release(){
        if(soundPool!=null){
            soundPool.release();
        }
    }


}
