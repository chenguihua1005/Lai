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
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.stepcount.db.StepUtil;
import com.softtek.lai.stepcount.model.StepDcretor;
import com.softtek.lai.stepcount.model.UserStep;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.JCountDownTimer;

import java.util.Calendar;

import zilla.libcore.file.SharedPreferenceService;

public class StepService extends Service implements SensorEventListener {

    public static final String UPLOAD_STEP="com.softtek.lai.StepService";
    public static final String STEP="com.softtek.lai.StepService.StepCount";

    //默认为30秒进行一次存储
    private static int duration = 30000;
    //默认30分钟上传一次
    private static int durationUpload=10*60*1000;
    private SensorManager sensorManager;
    private StepDcretor stepDetector;
    private BroadcastReceiver mBatInfoReceiver;
    private WakeLock mWakeLock;
    private TimeCount time;
    private int currentStep;//当前计步器 得出的步数结果用与做数据使用
    private int serverStep;//记录服务器上的步数
    private int firstStep=0;//启动应用服务的时候的第一次步数
    public static int todayStep;//用于显示今日步数使用


    @Override
    public void onCreate() {
        super.onCreate();

        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();
        startTimeCount();
        initTodayData();
    }

    private void initTodayData() {
        //获取当天的步数用于展示
        UserModel model=UserInfoModel.getInstance().getUser();
        if(model!=null) {
            //查询到今日的步数记录
            String userId=model.getUserid();
            serverStep = StepUtil.getInstance().getCurrentStep(userId);
            SharedPreferenceService.getInstance().put("serverStep",serverStep);
            lastStep = todayStep = currentStep + serverStep;
            updateNotification("今日步数：" + todayStep + " 步");
        }else{
            serverStep=SharedPreferenceService.getInstance().get("serverStep",0);
            lastStep = todayStep = currentStep + serverStep;
        }
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
                new Intent(this, HomeActviity.class), 0);
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

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启动定时上传功能
        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent upIntent=new Intent(UPLOAD_STEP);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,upIntent,0);
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
        //detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            Log.i("base", "countSensor");
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }/*else if (detectorSensor != null) {
            Log.i("base", "detector");
            sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_UI);
        }*/else {
            Log.i("base", "Count sensor not available!");
            addBasePedoListener();
        }
    }

    private void addBasePedoListener() {
        stepDetector = new StepDcretor(this);
        Sensor sensor= sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        stepDetector.setOnSensorChangeListener(new StepDcretor.OnSensorChangeListener() {

                    @Override
                    public void onChange(int step) {
                        calTodayStep(step);
                        /*currentStep=step;
                        todayStep =currentStep+ serverStep;
                        updateNotification("今日步数：" + todayStep + " 步");*/
                    }
                });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int stepTemp = (int) event.values[0];
            calTodayStep(stepTemp);
        } /*else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                //originalStep++;
        }*/

    }

    /**
     * 今日步数处理逻辑方法
     * 记录传感器第一次得到的步数，由于使用的是StepCount传感器，该传感器会在手机第一次启动的时候就开始几步因此要过滤掉之前的步数
     * 今日步数不光由currentStep组成 或许还有服务器上的旧步数,一次需要叠加服务器上的旧的步数
     * 然而 今日步数在每晚23点30分到24点之间开始不统计步数，所以要过滤这段时间的步数
     * 处理方法 ： 当系统检测到当前时间处于设定的时间之间，则当日步数保持不变即可
     * @param stepTemp 传感器获取的步数
     */
    private void calTodayStep(int stepTemp){
        //发送广播
        Intent stepIntent=new Intent(STEP);
        stepIntent.putExtra("step",stepTemp);
        LocalBroadcastManager.getInstance(this).sendBroadcast(stepIntent);
        //检查日期
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes=c.get(Calendar.MINUTE);
        //每晚的23点30分到24点之间
        if(hour==23&&minutes>=30&&minutes<=59){
            //清空当天的临时步数
            serverStep=0;
            firstStep=0;
            SharedPreferenceService.getInstance().put("serverStep",serverStep);
            updateNotification("今日步数：" + todayStep + " 步");
            return;
        }
        //如果firstStep为0表示第一次开启应用 或者隔天了。
        if(firstStep==0){
            firstStep=stepTemp;
            lastStep=0;
        }
        currentStep=stepTemp-firstStep;
        todayStep =currentStep+ serverStep;
        updateNotification("今日步数：" + todayStep + " 步");
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
        UserModel model = UserInfoModel.getInstance().getUser();
        if (model != null && todayStep > lastStep) {
            lastStep = todayStep;//记录上一次保存的值
            UserStep step = new UserStep();
            step.setAccountId(Long.parseLong(model.getUserid()));
            step.setRecordTime(DateUtil.getInstance().getCurrentDate());
            step.setStepCount(todayStep);
            StepUtil.getInstance().saveStep(step);
        } else {
            com.github.snowdream.android.util.Log.i("步数相同不保存");
        }

    }


    @Override
    public void onDestroy() {
        //取消前台进程
        Log.i("test","计步服务结束");
        stopForeground(true);
        unregisterReceiver(mBatInfoReceiver);
        if (countSensor != null) {
            Log.i("base", "注销countSensor");
            sensorManager.unregisterListener(this, countSensor);
        }
        if (detectorSensor != null) {
            Log.i("base", "注销detector");
            sensorManager.unregisterListener(this, detectorSensor);
        }
        if(UserInfoModel.getInstance().getUser()!=null&&"1".equals(UserInfoModel.getInstance().getUser().getIsJoin())){
            Intent intent = new Intent(this, StepService.class);
            startService(intent);
        }else{
            nm.cancelAll();
            todayStep =0;
            lastStep=0;
            serverStep =0;
            currentStep=0;
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
