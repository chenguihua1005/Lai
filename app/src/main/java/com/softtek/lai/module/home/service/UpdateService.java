package com.softtek.lai.module.home.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
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

import zilla.libcore.file.AddressManager;

/**
 *
 * Created by jerry.guan on 3/30/2017.
 */

public class UpdateService extends IntentService {


    public UpdateService() {
        super("test");
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    int per=msg.arg1;
                    updateNotification(per+"%",per);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 更新通知
     */
    private NotificationManager nm;
    private void updateNotification(String per,int num) {
//        PendingIntent contentIntent = PendingIntent.getBroadcast(this,0,new Intent("com.soffteck.lai.step_notify"),0);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_HIGH)
//                .setContentIntent(contentIntent)
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setOngoing(true);//设置不可清除
        //加载自定义布局
        RemoteViews contentView=new RemoteViews(getPackageName(),R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, "正在下载");
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


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    private  void sendHandler(int per){
        Message msg=new Message();
        msg.what=1;
        msg.arg1=per;
        handler.sendMessage(msg);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        updateNotification("0%",0);
        sendHandler(0);
        File file = createFile();
        try {
            URL url = new URL(AddressManager.get("photoHost") + "apk/app-release_221.apk");
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                conn.connect();
                writeFile2Disk(is, file,conn.getContentLength());
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

        if (state.equals(Environment.MEDIA_MOUNTED)) {

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com-soffteck-lai.apk");
        } else {
            file = new File(getCacheDir().getAbsolutePath() + "/com-soffteck-lai.apk");
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
                    sendHandler(num);
                }
//                Log.i("vivi", "当前进度:" + result+"；已读取多少="+currentLength+"；总共"+total);
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
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("更新服务结束啦啊啦啦啦");
        stopForeground(true);
        nm.cancelAll();
        super.onDestroy();
    }
}
