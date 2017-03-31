/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.home.model.Version;
import com.softtek.lai.module.home.service.UpdateService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_about_me)
public class AboutMeActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_version)
    TextView tv_version;

    @Override
    protected void initViews() {
        tv_title.setText("关于我们");
        tv_right.setText("检查更新");
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        tv_version.setText("V "+ DisplayUtil.getAppVersionName(this));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                }else {
                    //启动更新服务
                    startService(new Intent(getApplicationContext(),UpdateService.class));
                }
//                ZillaApi.NormalRestAdapter.create(HomeService.class)
//                        .checkNew(new RequestCallback<ResponseData<Version>>() {
//                            @Override
//                            public void success(ResponseData<Version> versionResponseData, Response response) {
//                                if(versionResponseData.getStatus()==200){
//                                    Version version=versionResponseData.getData();
//                                    try {
//                                        show(version);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startService(new Intent(getApplicationContext(),UpdateService.class));
            }
        }
    }

    private void show(Version version){
        int v_code=DisplayUtil.getAppVersionCode(this);
        if(v_code<version.getAppVisionCode()){
            String str="莱聚+ v "+version.getAppVisionNum()+"版本\n新版本已发布快去应用市场更新吧！\n更新于："+version.getUpdateTime();
            new AlertDialog.Builder(this)
                    .setTitle("版本有更新")
                    .setMessage(str)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }else {
            new AlertDialog.Builder(this)
                    .setMessage("当前已是最新版本！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public static void writeFile2Disk(InputStream is, File file){


        long currentLength = 0;
        OutputStream os =null;

//        InputStream is = null;
//        long totalLength =response.getBody().length();

        try {
//            is = response.getBody().in();
            os = new FileOutputStream(file);
            int len ;

            byte [] buff = new byte[1024];

            while((len=is.read(buff))!=-1){
                os.write(buff,0,len);
                currentLength+=len;
                Log.i("vivi","当前进度:"+currentLength);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  File createFile(){


        File file=null;
        String state = Environment.getExternalStorageState();

        if(state.equals(Environment.MEDIA_MOUNTED)){

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.apk");
        }else {
            file = new File(getCacheDir().getAbsolutePath()+"/test.apk");
        }

        Log.i("vivi","file "+file.getAbsolutePath());

        return file;

    }

    protected File downLoadFile(String httpUrl) {
        // TODO Auto-generated method stub
        final String fileName = "updata.apk";

        final File file = createFile();

        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                conn.connect();
                writeFile2Disk(is,file);
//                double count = 0;
//                if (conn.getResponseCode() >= 400) {
//                    Toast.makeText(AboutMeActivity.this, "连接超时", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    while (count <= 100) {
//                        System.out.println("一直在下载");
//                        if (is != null) {
//                            int numRead = is.read(buf);
//                            if (numRead <= 0) {
//                                break;
//                            } else {
//                                fos.write(buf, 0, numRead);
//                            }
//
//                        } else {
//                            break;
//                        }
//
//                    }
//                }
//
//                conn.disconnect();
//                fos.close();
//                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return file;
    }
}
