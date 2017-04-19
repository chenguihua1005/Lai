package com.softtek.lai.stepcount.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.stepcount.net.StepNetService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.JCountDownTimer;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.TimeTickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

public class StepService extends Service implements SensorEventListener,TimeTickListener.OnTimeTick {

    public static final String STEP_CLOSE="com.softtek.lai.StepService.StepClose";
    public static final String STEP_CLOSE_SELF="com.softtek.lai.StepService.STEP_CLOSE_SELF";

    public static final int MSG_FROM_CLIENT=1;
    public static final int MSG_FROM_SERVER=10;

    //默认为30秒进行一次存储
    private static final int duration = 10000;
    private SensorManager sensorManager;
    private CloseReceive closeReceive;
    private WakeLock mWakeLock;
    private TimeCount time;
    private TimeTickListener tickListener;
    private static int currentStep;//当前计步器 得出的步数结果用与做数据使用
    private static int serverStep;//记录服务器上的步数
    private static int firstStep=0;//启动应用服务的时候的第一次步数
    private static int todayStep;//用于显示今日步数使用
    private static long originalStep;//用于其他使用

    private Messenger messenger = new Messenger(new MessengerHandler());

    //跨进程通信用于传递数据
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENT:
                    Messenger server=msg.replyTo;
                    Bundle  deviationBundle=msg.getData();//获取服务器误差数据
                    //服务器和本地步数修正
                    int deviation=deviationBundle.getInt("surplusStep",0);
                    if(deviation>0){
                        serverStep+=deviation;
                        todayStep=serverStep+currentStep;
                    }
                    Message message=Message.obtain(null,MSG_FROM_SERVER);
                    Bundle data=new Bundle();
                    //将今日步数传递过去
                    data.putInt("todayStep",todayStep);
                    data.putInt("serverStep",serverStep);
                    data.putLong("originalStep",originalStep);
                    message.setData(data);
                    try {
                        server.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isLoginOut=false;
        initTodayData();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();
        startTimeCount();
    }

    private void initTodayData() {
        //查询到今日的步数记录
        serverStep = StepUtil.getInstance().getCurrentStep(UserInfoModel.getInstance().getUserId()+"");
        lastStep = todayStep = currentStep + serverStep;
        SharedPreferenceService.getInstance().put("currentStep",todayStep);
        updateNotification(todayStep + "");
    }

    private void initBroadcastReceiver() {
        tickListener=new TimeTickListener(this);
        tickListener.startTick(this);
        closeReceive=new CloseReceive();
        LocalBroadcastManager.getInstance(this).registerReceiver(closeReceive,new IntentFilter(STEP_CLOSE_SELF));

    }

    private void startTimeCount() {
        time = new TimeCount(duration, 5000);
        time.start();
    }

    /**
     * 更新通知
     */
    private NotificationCompat.Builder builder;
    private NotificationManager nm;
    private void updateNotification(String content) {
        PendingIntent contentIntent = PendingIntent.getBroadcast(this,0,new Intent("com.soffteck.lai.step_notify"),0);
        builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);//设置不可清除
        //加载自定义布局
        RemoteViews contentView=new RemoteViews(getPackageName(),R.layout.step_notify_view);
        contentView.setTextViewText(R.id.tv_step,content);
        contentView.setImageViewResource(R.id.iv_lauch_icon,R.mipmap.ic_launcher);
        builder.setContent(contentView);
        Notification notification = builder.build();
        startForeground(0, notification);

        //获取通知管理器
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //发送通知
        nm.notify(R.string.app_name, notification);


    }

    @Override
    public IBinder onBind(Intent intent) {

        return messenger.getBinder();
    }

    boolean isExit=false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            isExit=intent.getBooleanExtra("isExit",false);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startStepDetector() {
        getLock(this);
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);
        PackageManager pm=getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)){
            stepCounterListener();
        }else if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)){
            stepAccelerometerListener();
        }else{
        }

    }
    private Sensor countSensor;
    private void stepCounterListener() {
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }else {
            stepAccelerometerListener();
        }
    }


    StepDetector stepDetector;
    StepCount stepCount;
    private void stepAccelerometerListener() {
        if(sensorManager!=null&&stepDetector!=null){
            sensorManager.unregisterListener(stepDetector);
            sensorManager=null;
            stepDetector=null;
        }
        stepDetector=new StepDetector();
        stepCount=new StepCount();
        Sensor sensor = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//获得传感器的类型，这里获得的类型是加速度传感器
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        stepDetector.setStepListeners(stepCount);
        stepCount.setmListeners(new StepPaseValueListener() {
            @Override
            public void stepsChanged(int step) {
                calTodayStepByCustome(step);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int stepTemp = (int) event.values[0];
            calTodayStep(stepTemp);
        }

    }

    boolean isNextDay;
    private void calTodayStep(int stepTemp){
        //检查日期
        originalStep=stepTemp;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes=c.get(Calendar.MINUTE);
        //每晚的23点50分到24点之间
        if(hour==23&&minutes>=50&&minutes<=59){
            //清空当天的临时步数
            firstStep=0;
            currentStep=0;
            isNextDay=true;
            SharedPreferenceService.getInstance().put("phoneStep",-1);
            int tempStep=SharedPreferenceService.getInstance().get("currentStep",0);
            updateNotification(tempStep+"");
            return;
        }
        //如果firstStep为0表示第一次开启应用 或者隔天了。
        String now=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        if(firstStep==0){
            firstStep=stepTemp;
            lastStep=0;
            if(!isExit&&!isNextDay){//如果不是正常的情况下且不是跨天退出的则需要把中间的插值补上
                String time=SharedPreferenceService.getInstance().get("recordTime","");
                int phoneStep=SharedPreferenceService.getInstance().get("phoneStep",-1);
                //首先判断是否是同一天,只有是同一天的才会补足
                if (phoneStep>=0&&now.compareTo(time)==0){
                    //判断是否关过机
                    boolean isShutDown=SharedPreferenceService.getInstance().get("shutDown",false);
                    String shutDownTime=SharedPreferenceService.getInstance().get("shutDownTime","");
                    //如果当天重启过
                    if (isShutDown&&now.compareTo(shutDownTime)==0){
                        firstStep=0;
                        SharedPreferenceService.getInstance().put("shutDown",false);
                        SharedPreferenceService.getInstance().put("shutDownTime", now);
                    }else {
                        if(stepTemp>=phoneStep){
                            firstStep=firstStep-(stepTemp-phoneStep);
                        }
                    }
                }

            }
        }
        //first=first -temp;if step<first 123-1234
        currentStep=stepTemp-firstStep;
        todayStep =currentStep+ serverStep;
        SharedPreferenceService.getInstance().put("currentStep",todayStep);
        //存储本次启动计步器的时候手机的本身步数
        SharedPreferenceService.getInstance().put("phoneStep",stepTemp);
        SharedPreferenceService.getInstance().put("recordTime",now);
        updateNotification(todayStep + "");
    }
    //模拟计步传感器所使用的计算方法
    private void calTodayStepByCustome(int stepTemp){
        originalStep=stepTemp;
        //检查日期
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes=c.get(Calendar.MINUTE);
        //每晚的23点50分到24点之间
        if(hour==23&&minutes>=50&&minutes<=59){
            //清空当天的临时步数
            firstStep=0;
            currentStep=0;
            int tempStep=SharedPreferenceService.getInstance().get("currentStep",0);
            updateNotification(tempStep+"");
            return;
        }
        //如果firstStep为0表示第一次开启应用 或者隔天了。
        if(firstStep==0){
            firstStep=stepTemp-5;
            firstStep=firstStep<0?0:firstStep;
            lastStep=0;
        }
        currentStep=stepTemp-firstStep;
        todayStep =currentStep+ serverStep;
        SharedPreferenceService.getInstance().put("currentStep",todayStep);
        updateNotification(todayStep + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    class TimeCount extends JCountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 如果计时器正常结束，则开始计步
            time.cancel();
            save();
            startTimeCount();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

    }


    int lastStep;
    //存入数据库
    private void save() {
        if(todayStep>lastStep){
            lastStep = todayStep;//记录上一次保存的值
            UserStep step = new UserStep();
            step.setAccountId(UserInfoModel.getInstance().getUserId());
            step.setRecordTime(DateUtil.getInstance().getCurrentDate());
            step.setStepCount(todayStep);
            try {
                StepUtil.getInstance().saveStep(step);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void onDestroy() {
        save();
        super.onDestroy();
        if(!isLoginOut){
            sendBroadcast(new Intent(STEP_CLOSE));
        }else {
            Log.i("清楚数据");
            todayStep =0;
            lastStep=0;
            serverStep =0;
            currentStep=0;
            stopForeground(true);
            nm.cancelAll();

        }
        tickListener.stopTick();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(closeReceive);
        time.cancel();
        if(stepDetector!=null){
            sensorManager.unregisterListener(stepDetector);
            stepDetector=null;
        }
        if (countSensor != null) {
            sensorManager.unregisterListener(this, countSensor);
        }
        sensorManager=null;
        if (mWakeLock != null) {
            if (mWakeLock.isHeld())
                mWakeLock.release();
            mWakeLock = null;
        }
    }

    synchronized private WakeLock getLock(Context context) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld())
                mWakeLock.release();
            mWakeLock = null;
        }

        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getCanonicalName());
            mWakeLock.setReferenceCounted(false);//不计数锁
            mWakeLock.acquire();//获得锁
        }
        return mWakeLock;
    }
    boolean isLoginOut;
    public class CloseReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            isLoginOut=true;
            firstStep=0;
            SharedPreferenceService.getInstance().put("phoneStep",-1);
            stopSelf();
        }
    }

    int index=4;
    @Override
    public void onTick(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes=calendar.get(Calendar.MINUTE);
        if(hour==23&&minutes>=50&&minutes<=59){
            firstStep=0;
            lastStep=0;
            SharedPreferenceService.getInstance().put("phoneStep",-1);
            int tempStep=SharedPreferenceService.getInstance().get("currentStep",0);
            updateNotification(tempStep+"");
        }if(hour==0&&minutes==0){
            index=4;
            serverStep=0;
            todayStep=0;
            firstStep=0;
            lastStep=0;
            SharedPreferenceService.getInstance().put("currentStep",0);
            updateNotification(0+"");
        }else {
            index--;
            if(index==0) {
                index=4;
                //做上传工作
                UserModel model = UserInfoModel.getInstance().getUser();
                if (model != null) {
                    String userId = model.getUserid();
                    int step = todayStep;
                    StringBuilder buffer = new StringBuilder();
                    buffer.append(DateUtil.getInstance().getCurrentDate());
                    buffer.append(",");
                    buffer.append(step);
                    //提交数据
                    ZillaApi.NormalRestAdapter.create(StepNetService.class)
                            .synStepCount(
                                    UserInfoModel.getInstance().getToken(), Long.parseLong(userId), buffer.toString(), new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }
                                    });
                }
            }
        }
    }
}
