package com.softtek.lai.module.home.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 *
 * Created by jerry.guan on 3/30/2017.
 */

public class UpdateService extends Service implements Runnable{

    private static final String APK_URL="apkUrl";

    public static void startUpdate(Context context,String apkUrl){
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(APK_URL,apkUrl);
        context.startService(intent);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    int per=msg.arg1;
                    updateNotification(1,per+"%",per);
                    break;
                case 2:
                    updateNotification(2,0+"%",0);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 更新通知
     */
    private NotificationManager nm;
    private void updateNotification(int what,String per,int num) {
//        PendingIntent contentIntent = PendingIntent.getBroadcast(this,0,new Intent("com.soffteck.lai.step_notify"),0);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_HIGH)
//                .setContentIntent(contentIntent)
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setOngoing(true);//设置不可清除
        //加载自定义布局
        RemoteViews contentView=new RemoteViews(getPackageName(),R.layout.notification_item);
        String str="";
        switch (what){
            case 1:str="正在下载";
                break;
            case 2:str="最新apk链接异常";
                break;
            case 3:
                str="下载失败";
                break;
        }
        contentView.setTextViewText(R.id.notificationTitle, str);
        contentView.setTextViewText(R.id.notificationPercent, per);
        contentView.setProgressBar(R.id.notificationProgress, 100, num, false);
        builder.setContent(contentView);
        Notification notification = builder.build();
        startForeground(0, notification);

        //获取通知管理器
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //发送通知
        nm.notify(R.string.app_name, notification);


    }


    private  void sendHandler(int what,int per){
        Message msg=new Message();
        msg.what=what;
        msg.arg1=per;
        handler.sendMessage(msg);
    }
    private  void sendHandler(int what){
        Message msg=new Message();
        msg.what=what;
        handler.sendMessage(msg);
    }

    private boolean doing=false;
    private String apkUrl;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!doing){
            doing=true;
            apkUrl=intent.getStringExtra(APK_URL);
            new Thread(this).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void run() {
        sendHandler(1,0);
        if(TextUtils.isEmpty(apkUrl)){
            sendHandler(2);
            stopSelf();
            return;
        }
        File file = createFile();
        try {
            //AddressManager.get("photoHost") + "apk/app-release_221.apk"
            URL url = new URL(apkUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                conn.connect();
                if(conn.getResponseCode()==200){
                    writeFile2Disk(is, file,conn.getContentLength());
                }else {
                    sendHandler(3);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public File createFile() {
        File file;

        String state = Environment.getExternalStorageState();
        String apkName= SystemClock.currentThreadTimeMillis()+"-com-soffteck-lai.apk";
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ apkName);
        } else {
            file = new File(getExternalCacheDir().getAbsolutePath() +File.separator+ apkName);
        }
        Log.i("vivi", "file " + file.getAbsolutePath());

        return file;

    }

    public void writeFile2Disk(InputStream is, File file,long total) {
        long currentLength = 0;
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];

            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                double tempresult = currentLength*1.0d / total;
                DecimalFormat df1 = new DecimalFormat("#0.00"); // ##.00%
                // 百分比格式，后面不足2位的用0补齐
                String result = df1.format(tempresult);
                int num=(int) (Float.parseFloat(result) * 100);
                if(num%4==0){
                    sendHandler(1,num);
                }
            }
            openFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        doing=false;
        stopForeground(true);
        nm.cancelAll();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
