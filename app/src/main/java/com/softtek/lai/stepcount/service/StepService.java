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
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

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

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;

public class StepService extends Service implements SensorEventListener {

    public static final String UPLOAD_STEP="com.softtek.lai.StepService";
    public static final String STEP="com.softtek.lai.StepService.StepCount";

    public static final int MSG_FROM_CLIENT=1;
    public static final int MSG_FROM_SERVER=1;

    //默认为30秒进行一次存储
    private static int duration = 10000;
    //默认30分钟上传一次
    private static int durationUpload=10*60*1000;
    private SensorManager sensorManager;
    private StepDetector  stepDetector;
//    private BroadcastReceiver mBatInfoReceiver;
    private UploadStepReceive uploadStepReceive;
    private WakeLock mWakeLock;
    private TimeCount time;
    private int currentStep;//当前计步器 得出的步数结果用与做数据使用
    private int serverStep;//记录服务器上的步数
    private int firstStep=0;//启动应用服务的时候的第一次步数
    private static int todayStep;//用于显示今日步数使用

    private Messenger messenger = new Messenger(new MessengerHandler());

    //跨进程通信用于传递数据
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENT:
                    Messenger server=msg.replyTo;
                    Message message=Message.obtain(null,MSG_FROM_SERVER);
                    Bundle data=new Bundle();
                    //将今日步数传递过去
                    data.putInt("todayStep",todayStep);
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
        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();
        initTodayData();
        startTimeCount();
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
            SharedPreferenceService.getInstance().put("currentStep",todayStep);
            updateNotification("今日步数：" + todayStep + " 步");
        }
    }

    private void initBroadcastReceiver() {
        uploadStepReceive=new UploadStepReceive();
        IntentFilter upload=new IntentFilter(UPLOAD_STEP);
        upload.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(uploadStepReceive,upload);

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
                new Intent(), 0);
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

        return messenger.getBinder();
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
        getLock(this);
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);
        PackageManager pm=getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)){
            Log.i("该手机有SENSOR_STEP_COUNTER");
            addCountStepListener();
        }else {
            Log.i("该手机没有SENSOR_STEP_COUNTER");
            Log.i(" 选用重力加速度传感器");
            addBasePedoListener();
        }

    }
    private Sensor countSensor;
    private void addCountStepListener() {
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            Log.i("base", "countSensor");
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }else {
            Log.i("base", "Count sensor not available!");
            addBasePedoListener();
        }
    }



    private void addBasePedoListener() {
        stepDetector = new StepDetector(this);
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//获得传感器的类型，这里获得的类型是加速度传感器
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        stepDetector.setOnSensorChangeListener(new StepDetector.OnSensorChangeListener() {
            @Override
            public void onChange(int step) {
                calTodayStep(step);
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

    /**
     * 今日步数处理逻辑方法
     * 记录传感器第一次得到的步数，由于使用的是StepCount传感器，该传感器会在手机第一次启动的时候就开始几步因此要过滤掉之前的步数
     * 今日步数不光由currentStep组成 或许还有服务器上的旧步数,一次需要叠加服务器上的旧的步数
     * 然而 今日步数在每晚23点30分到24点之间开始不统计步数，所以要过滤这段时间的步数
     * 处理方法 ： 当系统检测到当前时间处于设定的时间之间，则当日步数保持不变即可
     * @param stepTemp 传感器获取的步数
     */
    private void calTodayStep(int stepTemp){
        //检查日期
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes=c.get(Calendar.MINUTE);
        //每晚的23点50分到24点之间
        if(hour==23&&minutes>50&&minutes<=59){
            //清空当天的临时步数
            serverStep=0;
            firstStep=0;
            todayStep=0;
            int tempStep=SharedPreferenceService.getInstance().get("currentStep",0);
            updateNotification("今日步数："+tempStep+"步");
            return;
        }
        //如果firstStep为0表示第一次开启应用 或者隔天了。
        if(firstStep==0){
            firstStep=stepTemp;
            lastStep=0;
        }
        currentStep=stepTemp-firstStep;
        todayStep =currentStep+ serverStep;
        SharedPreferenceService.getInstance().put("currentStep",todayStep);
        //发送广播
        Intent stepIntent=new Intent(STEP);
        stepIntent.putExtra("step",stepTemp);
        stepIntent.putExtra("currentStep",todayStep);
        LocalBroadcastManager.getInstance(this).sendBroadcast(stepIntent);
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
            String userId=StringUtils.isEmpty(model.getUserid())?"-1":model.getUserid();
            step.setAccountId(Long.parseLong(userId));
            step.setRecordTime(DateUtil.getInstance().getCurrentDate());
            step.setStepCount(todayStep);
            StepUtil.getInstance().saveStep(step);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消前台进程
        Log.i("test","计步服务结束");
        stopForeground(true);
        nm.cancelAll();
        unregisterReceiver(uploadStepReceive);
        if(stepDetector!=null){
            sensorManager.unregisterListener(stepDetector);
            stepDetector=null;
        }
        if (countSensor != null) {
            Log.i("base", "注销countSensor");
            sensorManager.unregisterListener(this, countSensor);
        }
        sensorManager=null;
        time.cancel();
        if(UserInfoModel.getInstance().getUser()!=null&&"1".equals(UserInfoModel.getInstance().getUser().getIsJoin())){
            Intent intent = new Intent(this, StepService.class);
            startService(intent);
        }else{
            todayStep =0;
            lastStep=0;
            serverStep =0;
            currentStep=0;
            Log.i("计步器服务不再执行");
        }
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
            if (hour >=50 || hour <= 6) {
                mWakeLock.acquire(5000);
            } else {
                mWakeLock.acquire(300000);
            }
        }

        return (mWakeLock);
    }

    public class UploadStepReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(Intent.ACTION_TIME_TICK.equals(action)){
                //系统time_tick
                //Log.i("系统发出了时间"+DateUtil.getInstance().getCurrentDate());
                //检查日期
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes=c.get(Calendar.MINUTE);
                //每晚的23点30分到24点之间
                if(hour==23&&minutes>50&&minutes<=59){
                    serverStep=0;
                    firstStep=0;
                    lastStep=0;
                    todayStep=0;
                    int tempStep=SharedPreferenceService.getInstance().get("currentStep",0);
                    updateNotification("今日步数："+tempStep+"步");
                }if(hour==0&&minutes==0){
                    SharedPreferenceService.getInstance().put("currentStep",0);
                    updateNotification("今日步数：0步");
                }
            }else if(UPLOAD_STEP.equals(action)) {
                UserModel model = UserInfoModel.getInstance().getUser();
                if (model == null || "0".equals(model.getIsJoin())) {
                    return;
                }
                //检查日期
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                //每晚的23点50分到24点之间
                if (hour == 23 && minutes > 50 && minutes <= 59) {
                    //清空当天的临时步数
                    return;
                }
                //做上传工作
                String userId = model.getUserid();
                int todayStep =StepService.todayStep;
                StringBuilder buffer = new StringBuilder();
                buffer.append(DateUtil.getInstance().getCurrentDate());
                buffer.append(",");
                buffer.append(todayStep);
                //提交数据
                com.github.snowdream.android.util.Log.i("步数数据开始上传.......................................");
                com.github.snowdream.android.util.Log.i("步数>>" + buffer.toString());
                ZillaApi.NormalRestAdapter.create(StepNetService.class)
                        .synStepCount(
                                UserInfoModel.getInstance().getToken(), Long.parseLong(userId), buffer.toString(), new RequestCallback<ResponseData>() {
                                    @Override
                                    public void success(ResponseData responseData, Response response) {
                                        com.github.snowdream.android.util.Log.i("上传成功");
                                        //发送广播
                                        Intent stepIntent=new Intent(UPLOAD_STEP);
                                        LocalBroadcastManager.getInstance(StepService.this).sendBroadcast(stepIntent);
                                    }
                                });
                context.startService(new Intent(context.getApplicationContext(), StepService.class));
            }
        }
    }
}
