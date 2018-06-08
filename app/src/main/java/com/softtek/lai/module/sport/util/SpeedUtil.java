package com.softtek.lai.module.sport.util;

import android.content.Context;
import android.os.SystemClock;

import com.softtek.lai.R;
import com.softtek.lai.sound.SoundHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *Created by jerry.guan on 10/20/2016.
 * 开始运动界面的语音
 */
public class SpeedUtil {

    private SoundHelper sounder;
    private Map<String,Integer> resource;

    public SpeedUtil(Context context){
        sounder=new SoundHelper(context,37);
        sounder.addAudio("resume",R.raw.resume);
        sounder.addAudio("pause",R.raw.pause);
        sounder.addAudio("end",R.raw.end);
        sounder.addAudio("onek_useTime",R.raw.late_use_time_onek);
        sounder.addAudio("useTime",R.raw.use_time);

        sounder.addAudio("time",R.raw.time);
        sounder.addAudio("minutes",R.raw.minute);
        sounder.addAudio("seconds",R.raw.seconds);
        sounder.addAudio("has_sport",R.raw.has_sport);
        sounder.addAudio("kilometre",R.raw.kilometre);

        resource=new HashMap<>();
        resource.put("one",R.raw.one);
        resource.put("two",R.raw.two);
        resource.put("three",R.raw.three);
        resource.put("four",R.raw.four);
        resource.put("five",R.raw.five);
        resource.put("six",R.raw.six);
        resource.put("seven",R.raw.seven);
        resource.put("eight",R.raw.eight);
        resource.put("nine",R.raw.nine);
        resource.put("ten",R.raw.ten);

        resource.put("twenty",R.raw.twenty);
        resource.put("thirty",R.raw.thirty);
        resource.put("forty",R.raw.forty);
        resource.put("fifty",R.raw.fifty);
        resource.put("sixty",R.raw.sixty);
        resource.put("seventy",R.raw.seventy);
        resource.put("eighty",R.raw.eighty);
        resource.put("ninety",R.raw.ninety);
        resource.put("has_sport",R.raw.has_sport);

        resource.put("has_sport1k",R.raw.has_onek);
        resource.put("has_sport2k",R.raw.has_twok);
        resource.put("has_sport3k",R.raw.has_threek);
        resource.put("has_sport4k",R.raw.has_fourk);
        resource.put("has_sport5k",R.raw.has_fivek);
        resource.put("has_sport6k",R.raw.has_sixk);
        resource.put("has_sport7k",R.raw.has_sevenk);
        resource.put("has_sport8k",R.raw.has_eightk);
        resource.put("has_sport9k",R.raw.has_ninek);
        resource.put("has_sport10k",R.raw.has_tenk);

        resource.put("jiayou",R.raw.jiayou);
        resource.put("haobang",R.raw.haobang);
        resource.put("jianchi",R.raw.jianchi);
        resource.put("taibang",R.raw.taibang);
        resource.put("zhenlihai",R.raw.zhenlihai);

//-================================================

//        sounder.addAudio("one",R.raw.one);
//        sounder.addAudio("two",R.raw.two);
//        sounder.addAudio("three",R.raw.three);
//        sounder.addAudio("four",R.raw.four);
//        sounder.addAudio("five",R.raw.five);
//        sounder.addAudio("six",R.raw.six);
//        sounder.addAudio("seven",R.raw.seven);
//        sounder.addAudio("eight",R.raw.eight);
//        sounder.addAudio("nine",R.raw.nine);
//        sounder.addAudio("ten",R.raw.ten);
//
//        sounder.addAudio("twenty",R.raw.twenty);
//        sounder.addAudio("thirty",R.raw.thirty);
//        sounder.addAudio("forty",R.raw.forty);
//        sounder.addAudio("fifty",R.raw.fifty);
//        sounder.addAudio("sixty",R.raw.sixty);
//        sounder.addAudio("seventy",R.raw.seventy);
//        sounder.addAudio("eighty",R.raw.eighty);
//        sounder.addAudio("ninety",R.raw.ninety);

//        sounder.addAudio("has_sport1k",R.raw.has_onek);
//        sounder.addAudio("has_sport2k",R.raw.has_twok);
//        sounder.addAudio("has_sport3k",R.raw.has_threek);
//        sounder.addAudio("has_sport4k",R.raw.has_fourk);
//        sounder.addAudio("has_sport5k",R.raw.has_fivek);
//        sounder.addAudio("has_sport6k",R.raw.has_sixk);
//        sounder.addAudio("has_sport7k",R.raw.has_sevenk);
//        sounder.addAudio("has_sport8k",R.raw.has_eightk);
//        sounder.addAudio("has_sport9k",R.raw.has_ninek);
//        sounder.addAudio("has_sport10k",R.raw.has_tenk);

//        sounder.addAudio("jiayou",R.raw.jiayou);
//        sounder.addAudio("haobang",R.raw.haobang);
//        sounder.addAudio("jianchi",R.raw.jianchi);
//        sounder.addAudio("taibang",R.raw.taibang);
//        sounder.addAudio("zhenlihai",R.raw.zhenlihai);

    }

    //您已运动10公里以上的拼接
    public void sayGt10K(final int num, long time,long kTime){
        if(num>10){
            final int hour = (int) (time / 3600);
            final int minutes = (int) (time % 3600 / 60);
            final int second = (int) (time % 3600 % 60);
            final int khour = (int) (kTime / 3600);
            final int kminutes = (int) (kTime % 3600 / 60);
            final int ksecond = (int) (kTime % 3600 % 60);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String[] shi = tenBitParts(num / 10);
                    int remain=num%10;
                    if(remain==0){
                        //整十数
                        sounder.play("has_sport",900);

                        sounder.lazyPlayAndDelay(shi[0],resource.get(shi[0]),Integer.parseInt(shi[1]));

                        sounder.play("kilometre",800);
                    }else {
                        String[] ge = aBitParts(remain);
                        sounder.play("has_sport",900);
                        sounder.lazyPlayAndDelay(shi[0],resource.get(shi[0]),Integer.parseInt(shi[1]));
                        sounder.lazyPlayAndDelay(ge[0],resource.get(ge[0]),Integer.parseInt(ge[1]));
                        sounder.play("kilometre",800);
                    }
                    sounder.play("useTime",576);
                    sayTime(hour,minutes,second);
                    sounder.play("onek_useTime",1400);
                    sayTime(khour,kminutes,ksecond);
                    sounder.lazyPlay("jianchi",resource.get("jianchi"));
                }
            }).start();

        }
    }

    /**
     *  说出1到10公里的语言
     *  您已经运动1-10公里 用时xxx，随机鼓励语
     */
    public void sayLt10K(final int num, final long time,final long kTime){
        if(num>0&&num<=10){
            final int hour = (int) (time / 3600);
            final int minutes = (int) (time % 3600 / 60);
            final int second = (int) (time % 3600 % 60);

            final int khour = (int) (kTime / 3600);
            final int kminutes = (int) (kTime % 3600 / 60);
            final int ksecond = (int) (kTime % 3600 % 60);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sounder.lazyPlay("has_sport"+num+"k",resource.get("has_sport"+num+"k"));
                    SystemClock.sleep(2000);
                    //用时
                    String encourage=sayEncourage();//鼓励语
                    sounder.play("useTime",576);
                    sayTime(hour,minutes,second);
                    if(num>=2){
                        sounder.play("onek_useTime",1400);
                        sayTime(khour,kminutes,ksecond);
                    }
                    sounder.lazyPlay(encourage,resource.get(encourage));
                }
            }).start();
        }
    }

    private String sayEncourage(){
        String say="";
        Random random=new Random();
        int ran=random.nextInt(6);
        switch (ran){
            case 0:
                say="jiayou";
                break;
            case 1:
                say="jiayou";
                break;
            case 2:
                say="haobang";
                break;
            case 3:
                say="jianchi";
                break;
            case 4:
                say="taibang";
                break;
            case 5:
                say="zhenlihai";
                break;
        }
        return say;
    }

    public void sayNormal(String name){
        if(!resource.containsKey(name)){
            sounder.play(name);
        }
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
                res[1]="488";
                return res;
            case 2:
                res[0]="twenty";
                res[1]="584";
                return res;
            case 3:
                res[0]="thirty";
                res[1]="673";
                return res;
            case 4:
                res[0]="forty";
                res[1]="701";
                return res;
            case 5:
                res[0]="fifty";
                res[1]="582";
                return res;
            case 6:
                res[0]="sixty";
                res[1]="599";
                return res;
            case 7:
                res[0]="seventy";
                res[1]="669";
                return res;
            case 8:
                res[0]="eighty";
                res[1]="595";
                return res;
            case 9:
                res[0]="ninety";
                res[1]="671";
                return res;
        }
        return res;
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
                res[1]="319";
                return res;
            case 3:
                res[0]="three";
                res[1]="468";
                return res;
            case 4:
                res[0]="four";
                res[1]="418";
                return res;
            case 5:
                res[0]="five";
                res[1]="371";
                return res;
            case 6:
                res[0]="six";
                res[1]="334";
                return res;
            case 7:
                res[0]="seven";
                res[1]="408";
                return res;
            case 8:
                res[0]="eight";
                res[1]="300";
                return res;
            case 9:
                res[0]="nine";
                res[1]="425";
                return res;
        }
        return null;
    }

    private String[] getNumberSpeed(int num){
        if(num>=100||num<=0){
            return null;
        }
        if(num>0&&num<10){
            //个数
            return aBitParts(num);
        }
        //十位以上的数先看看是不是整数
        String[] shi=tenBitParts(num/10);
        if(num%10==0){
            //整十数
            return shi;
        }else {
            String[] ge = aBitParts(num%10);
            return new String[]{shi[0],shi[1],ge[0],ge[1]};
        }
    }

    //播放时间
    private void sayTime(int hour,int minutes,int seconds){
        String[] sayHour=getNumberSpeed(hour);
        String[] sayMinutes=getNumberSpeed(minutes);
        String[] saySeconds=getNumberSpeed(seconds);
        if(sayHour!=null){
            if(sayHour.length==2){
                sounder.lazyPlayAndDelay(sayHour[0],resource.get(sayHour[0]),Integer.parseInt(sayHour[1]));
                //sounder.play(sayHour[0],Integer.parseInt(sayHour[1]));
            }else if(sayHour.length==4){
                sounder.lazyPlayAndDelay(sayHour[0],resource.get(sayHour[0]),Integer.parseInt(sayHour[1]));
                //sounder.play(sayHour[0],Integer.parseInt(sayHour[1]));
                sounder.lazyPlayAndDelay(sayHour[2],resource.get(sayHour[2]),Integer.parseInt(sayHour[3]));
                //sounder.play(sayHour[2],Integer.parseInt(sayHour[3]));
            }
            sounder.play("time",688);
        }
        if(sayMinutes!=null){
            if(sayMinutes.length==2){
                sounder.lazyPlayAndDelay(sayMinutes[0],resource.get(sayMinutes[0]),Integer.parseInt(sayMinutes[1]));
                //sounder.play(sayMinutes[0],Integer.parseInt(sayMinutes[1]));
            }else if(sayMinutes.length==4){
                sounder.lazyPlayAndDelay(sayMinutes[0],resource.get(sayMinutes[0]),Integer.parseInt(sayMinutes[1]));
                sounder.lazyPlayAndDelay(sayMinutes[2],resource.get(sayMinutes[2]),Integer.parseInt(sayMinutes[3]));
//                sounder.play(sayMinutes[0],Integer.parseInt(sayMinutes[1]));
//                sounder.play(sayMinutes[2],Integer.parseInt(sayMinutes[3]));
            }
            sounder.play("minutes",590);
        }

        if(saySeconds!=null){
            if(saySeconds.length==2){
                sounder.lazyPlayAndDelay(saySeconds[0],resource.get(saySeconds[0]),Integer.parseInt(saySeconds[1]));
                //sounder.play(saySeconds[0],Integer.parseInt(saySeconds[1]));
            }else if(saySeconds.length==4){
                sounder.lazyPlayAndDelay(saySeconds[0],resource.get(saySeconds[0]),Integer.parseInt(saySeconds[1]));
                sounder.lazyPlayAndDelay(saySeconds[2],resource.get(saySeconds[2]),Integer.parseInt(saySeconds[3]));
                //sounder.play(saySeconds[0],Integer.parseInt(saySeconds[1]));
                //sounder.play(saySeconds[2],Integer.parseInt(saySeconds[3]));
            }
            sounder.play("seconds",500);
        }

    }
}
