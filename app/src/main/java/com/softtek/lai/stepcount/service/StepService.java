package com.softtek.lai.stepcount.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.mygrades.view.MyGradesActivity;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.StepDcretor;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.JCountDownTimer;

import java.util.Calendar;

public class StepService extends Service implements SensorEventListener {

    public static final String UPLOAD_STEP="com.softtek.lai.StepService";
    public static final String STEP="com.softtek.lai.StepService.StepCount";

    //默认为30秒进行一次存储
    private static int duration = 30000;
    //默认30分钟上传一次
    private static int durationUpload=2*60*1000;
    private SensorManager sensorManager;
    private StepDcretor stepDetector;
    private BroadcastReceiver mBatInfoReceiver;
    private WakeLock mWakeLock;
    private TimeCount time;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();
        startTimeCount();
        initTodayData();
        updateNotification("今日步数：" + StepDcretor.CURRENT_SETP + " 步");

    }

    private void initTodayData() {
        //获取当天的步数用于展示
        String userId=UserInfoModel.getInstance().getUser().getUserid();
        //查询到今日的步数记录
        StepDcretor.CURRENT_SETP= StepUtil.getInstance().getCurrentStep(userId);
        Log.i("xf", " 数据库中的步数>>"+StepDcretor.CURRENT_SETP);
    }

    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d("xf", "screen on");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d("xf", "screen off");
                    //改为60秒一存储
                    //duration = 60000;
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d("xf", "screen unlock");
                    save();
                    //改为30秒一存储
                    duration = 30000;
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.i("xf", " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                    //保存一次
                    save();
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    Log.i("xf", " receive ACTION_SHUTDOWN");
                    save();
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    private void startTimeCount() {
        time = new TimeCount(duration, 1000);
        time.start();
    }

    /**
     * 更新通知
     */
    private NotificationCompat.Builder builder;
    private NotificationManager nm;
    private void updateNotification(String content) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MyGradesActivity.class), 0);

        builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_MIN)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.app_name))
                .setContentTitle(getString(R.string.app_name))
                .setOngoing(true)//设置不可清除
                .setContentText(content);
        Notification notification = builder.build();

        startForeground(0, notification);

        //获取通知管理器
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //发送通知
        nm.notify(R.string.app_name, notification);
        //发送广播
        if(localBroadcastManager!=null) {
            Intent stepIntent=new Intent(STEP);
            stepIntent.putExtra("step",StepDcretor.CURRENT_SETP);
            localBroadcastManager.sendBroadcast(stepIntent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启动定时上传功能
        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,new Intent(UPLOAD_STEP),0);
        long triggerAtTime=SystemClock.elapsedRealtime()+durationUpload;
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtTime,
                pi);
        return START_STICKY;
    }

    private void startStepDetector() {
        if (sensorManager != null && stepDetector != null) {
            sensorManager.unregisterListener(stepDetector);
            sensorManager = null;
            stepDetector = null;
        }
        getLock(this);
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);
        //android4.4以后可以使用计步传感器
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if (VERSION_CODES >= 19) {
            Log.i("xf", " 选用安卓自带的计步器功能");
            addCountStepListener();
        } else {
            Log.i("xf", " 选用重力加速度传感器");
            addBasePedoListener();
        }
    }
    private Sensor detectorSensor;
    private Sensor countSensor;
    private void addCountStepListener() {
        detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        //countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        /*if (countSensor != null) {
            Log.i("base", "countSensor");
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else */
        if (detectorSensor != null) {
            Log.i("base", "detector");
            sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_UI);
        }
        if(countSensor == null&&detectorSensor == null) {
            Log.i("base", "Count sensor not available!");
            addBasePedoListener();
        }
    }

    private void addBasePedoListener() {
        stepDetector = new StepDcretor(this);
        // 获得传感器的类型，这里获得的类型是加速度传感器
        // 此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_UI);
        stepDetector.setOnSensorChangeListener(new StepDcretor.OnSensorChangeListener() {

                    @Override
                    public void onChange() {
                        updateNotification("今日步数：" + StepDcretor.CURRENT_SETP + " 步");
                    }
                });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            for(float value:event.values){
                com.github.snowdream.android.util.Log.i("传感器获取的步数数据为>>>"+value);
            }
            int step=(int) event.values[0];
            StepDcretor.CURRENT_SETP = (int) event.values[0];
        } else*/
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0]==1.0) {
                StepDcretor.CURRENT_SETP++;
            }
        }
        updateNotification("今日步数：" + StepDcretor.CURRENT_SETP + " 步");
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

    //存入数据库
    private void save() {
        long tempStep = StepDcretor.CURRENT_SETP;
        UserModel model=UserInfoModel.getInstance().getUser();
        if(model!=null){
            UserStep step=new UserStep();
            step.setAccountId(Long.parseLong(model.getUserid()));
            step.setRecordTime(DateUtil.getInstance("yyyy-MM-dd HH:mm:ss").getCurrentDate());
            step.setStepCount(tempStep);
            StepUtil.getInstance().saveStep(step);
        }
    }


    @Override
    public void onDestroy() {
        //取消前台进程
        Log.i("test","计步服务结束");
        stopForeground(true);
        unregisterReceiver(mBatInfoReceiver);
        //if(manager!=null&&pi!=null)manager.cancel(pi);
        if (countSensor != null) {
            Log.i("base", "注销countSensor");
            sensorManager.unregisterListener(this, countSensor);
        }
        if (detectorSensor != null) {
            Log.i("base", "注销detector");
            sensorManager.unregisterListener(this, detectorSensor);
        }
        if(UserInfoModel.getInstance().getUser()!=null){
            Intent intent = new Intent(this, StepService.class);
            startService(intent);
        }else{
            com.github.snowdream.android.util.Log.i("计步器服务不再执行");
        }
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    synchronized private WakeLock getLock(Context context) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld())
                mWakeLock.release();
            mWakeLock = null;
        }

        if (mWakeLock == null) {
            PowerManager mgr = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    StepService.class.getName());
            mWakeLock.setReferenceCounted(true);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (hour >= 23 || hour <= 6) {
                mWakeLock.acquire(5000);
            } else {
                mWakeLock.acquire(300000);
            }
        }

        return (mWakeLock);
    }

}
