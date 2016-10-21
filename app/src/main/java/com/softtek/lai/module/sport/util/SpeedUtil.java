package com.softtek.lai.module.sport.util;

import android.content.Context;

import com.softtek.lai.R;
import com.softtek.lai.sound.SoundHelper;

/**
 *Created by jerry.guan on 10/20/2016.
 * 开始运动界面的语音
 */
public class SpeedUtil {

    private SoundHelper sounder;
    public SpeedUtil(Context context){
        sounder=new SoundHelper(context,37);
        sounder.addAudio("one",R.raw.one);
        sounder.addAudio("two",R.raw.two);
        sounder.addAudio("three",R.raw.three);
        sounder.addAudio("four",R.raw.four);
        sounder.addAudio("five",R.raw.five);
        sounder.addAudio("six",R.raw.six);
        sounder.addAudio("seven",R.raw.seven);
        sounder.addAudio("eight",R.raw.eight);
        sounder.addAudio("nine",R.raw.nine);
        sounder.addAudio("ten",R.raw.ten);

        sounder.addAudio("twenty",R.raw.twenty);
        sounder.addAudio("thirty",R.raw.thirty);
        sounder.addAudio("forty",R.raw.forty);
        sounder.addAudio("fifty",R.raw.fifty);
        sounder.addAudio("sixty",R.raw.sixty);
        sounder.addAudio("seventy",R.raw.seventy);
        sounder.addAudio("eighty",R.raw.eighty);
        sounder.addAudio("ninety",R.raw.ninety);
        sounder.addAudio("has_sport",R.raw.has_sport);
        sounder.addAudio("kilometre",R.raw.kilometre);

        sounder.addAudio("has_sport1k",R.raw.has_onek);
        sounder.addAudio("has_sport2k",R.raw.has_twok);
        sounder.addAudio("has_sport3k",R.raw.has_threek);
        sounder.addAudio("has_sport4k",R.raw.has_fourk);
        sounder.addAudio("has_sport5k",R.raw.has_fivek);
        sounder.addAudio("has_sport6k",R.raw.has_sixk);
        sounder.addAudio("has_sport7k",R.raw.has_sevenk);
        sounder.addAudio("has_sport8k",R.raw.has_eightk);
        sounder.addAudio("has_sport9k",R.raw.has_ninek);
        sounder.addAudio("has_sport10k",R.raw.has_tenk);

        sounder.addAudio("jiayou",R.raw.jiayou);
        sounder.addAudio("haobang",R.raw.haobang);
        sounder.addAudio("jianchi",R.raw.jianchi);
        sounder.addAudio("taibang",R.raw.taibang);
        sounder.addAudio("zhenlihai",R.raw.zhenlihai);
        sounder.addAudio("onek_useTime",R.raw.late_use_time_onek);
        sounder.addAudio("gps_low",R.raw.gps_low);
    }

    //您已运动10公里以上的拼接
    public void sayGt10K(int num){
        final int number=num;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] shi = tenBitParts(number / 10);
                int remain=number%10;
                if(remain==0){
                    //整十数
                    String[] name = {"has_sport", shi[0], "kilometre"};
                    long[] delay = {900, Integer.parseInt(shi[1]), 800};
                    sounder.playSequence(name, delay);
                }else {
                    String[] ge = aBitParts(remain);
                    String[] name = {"has_sport", shi[0], ge[0], "kilometre"};
                    long[] delay = {900, Integer.parseInt(shi[1]), Integer.parseInt(ge[1]), 800};
                    sounder.playSequence(name, delay);
                }
            }
        }).start();
    }

    /**
     *  说出1到10公里的语言
     *  您已经运动1-10公里
     */
    public void sayLt10K(int num){
        if(num>0&&num<=10){
            final int number=num;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(number==1){
                        String[] name = {"has_sport"+number+"k", "jiayou",};
                        long[] delay = {2000, 0};
                        sounder.playSequence(name, delay);
                    }else if(number>1&&number<4){
                        String[] name = {"has_sport"+number+"k", "jianchi",};
                        long[] delay = {2000, 0};
                        sounder.playSequence(name, delay);
                    }else if(number>=4&&number<6){
                        String[] name = {"has_sport"+number+"k", "jianchi",};
                        long[] delay = {2000, 0};
                        sounder.playSequence(name, delay);
                    }else if(number>=6&&number<8){
                        String[] name = {"has_sport"+number+"k", "haobang",};
                        long[] delay = {2000, 0};
                        sounder.playSequence(name, delay);
                    }else if(number>=8&&number<=10){
                        String[] name = {"has_sport"+number+"k", "taibang","zhenlihai"};
                        long[] delay = {2000, 1000,0};
                        sounder.playSequence(name, delay);
                    }
                }
            }).start();
        }
    }

    public void sayNormal(String name){
        sounder.play(name);
    }

    public void release(){
        sounder.release();
    }

    //获取十位部分的名字和时长
    private String[] tenBitParts(int num){
        String[] res=new String[2];
        switch (num){
            case 1:
                res[0]="ten";
                res[1]="500";
                return res;
            case 2:
                res[0]="twenty";
                res[1]="600";
                return res;
            case 3:
                res[0]="thirty";
                res[1]="700";
                return res;
            case 4:
                res[0]="forty";
                res[1]="750";
                return res;
            case 5:
                res[0]="fifty";
                res[1]="582";
                return res;
            case 6:
                res[0]="sixty";
                res[1]="600";
                return res;
            case 7:
                res[0]="seventy";
                res[1]="700";
                return res;
            case 8:
                res[0]="eighty";
                res[1]="600";
                return res;
            case 9:
                res[0]="ninety";
                res[1]="700";
                return res;
        }
        return null;
    }

    //获取个位部分的名字和时长
    private String[] aBitParts(int num){
        String[] res=new String[2];
        switch (num){
            case 1:
                res[0]="one";
                res[1]="300";
                return res;
            case 2:
                res[0]="two";
                res[1]="350";
                return res;
            case 3:
                res[0]="three";
                res[1]="500";
                return res;
            case 4:
                res[0]="four";
                res[1]="450";
                return res;
            case 5:
                res[0]="five";
                res[1]="400";
                return res;
            case 6:
                res[0]="six";
                res[1]="400";
                return res;
            case 7:
                res[0]="seven";
                res[1]="450";
                return res;
            case 8:
                res[0]="eight";
                res[1]="300";
                return res;
            case 9:
                res[0]="nine";
                res[1]="450";
                return res;
        }
        return null;
    }

}
