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
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

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

    private int prePer;
    private long totalApk;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what){
                case 1:
                    int per=msg.arg1;
                    updateNotification(1,per+"%",per);
                    break;
                case 2:
                    updateNotification(2,0+"%",0);
                    break;
                case 3:
                    updateNotification(3,"0%",0);
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
        final Notification notification = builder.build();
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
    private File apkFile;
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
        apkFile = createFile();
        HttpURLConnection conn = null;
        try {
            TrustManager tm=new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext=null;
            TrustManagerFactory trustManagerFactory=null;
            try {
                sslContext=SSLContext.getInstance("TLS");
                trustManagerFactory=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                sslContext.init(null,new TrustManager[]{tm},null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            URL url = new URL(apkUrl);
            conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream is = conn.getInputStream();
            conn.connect();
            if(conn.getResponseCode()==200){
                totalApk=conn.getContentLength();
                Log.i("文件总大小="+totalApk);
                writeFile2Disk(is, apkFile,conn.getContentLength());
            }else {
                sendHandler(3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
            Log.i("下载完成啦 文件大小"+apkFile.length());
            Log.i("文件路径="+apkFile.getAbsolutePath());
            openFile(apkFile);
        }
    }


    public File createFile() {
        File file;
        String state = Environment.getExternalStorageState();
        String apkName= new Date().getTime()+"-com-soffteck-lai.apk";
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File apkDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"laiju_apk");
            if(!apkDir.exists()){
                apkDir.mkdir();
            }else {
                for (File apk:apkDir.listFiles()){
                    apk.delete();
                }
            }
            file = new File(apkDir,apkName);
        } else {
            file = new File(getExternalCacheDir().getAbsolutePath() +File.separator+ apkName);
        }
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
                if(num%2==0){
                    if(num>prePer){
                        sendHandler(1,num);
                        prePer=num;
                    }
                }
            }

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
