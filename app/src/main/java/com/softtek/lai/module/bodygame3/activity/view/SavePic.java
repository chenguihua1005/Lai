package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 5/5/2016.
 */
public class SavePic{

    String uri;
Context context;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                Util.toastMsg("保存失败");
            }else {
                Util.toastMsg("保存成功");
            }
        }

    };



    private static int READ_WRITER=0X10;

    public void GetPicUrl(final Context context, final String url)
    {
//        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
//            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    ||ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                //允许弹出提示
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
//                        ,READ_WRITER);
//
//            } else {
//                //不允许弹出提示
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
//                        ,READ_WRITER);
//            }
//        }else {
//            //保存
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Bitmap bitmap = getHttpBitmap(AddressManager.get("photoHost")+uri);//从网络获取图片
//                    saveImageToGallery(getContext(),bitmap);
//                }
//            }).start();

//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getHttpBitmap(AddressManager.get("photoHost")+url);//从网络获取图片
                saveImageToGallery(context,bitmap);
            }
        }).start();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==READ_WRITER) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = getHttpBitmap(AddressManager.get("photoHost")+uri);//从网络获取图片
                        saveImageToGallery(context,bitmap);
                    }
                }).start();
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    public  void saveImageToGallery(Context context,Bitmap bmp) {
        if (bmp == null){
            handler.sendEmptyMessage(0);
            return;
        }
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "lai_img");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = uri;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            handler.sendEmptyMessage(0);
            e.printStackTrace();
        } catch (IOException e) {
            handler.sendEmptyMessage(0);
            e.printStackTrace();
        }catch (Exception e){
            handler.sendEmptyMessage(0);
            e.printStackTrace();
        }

         //最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        handler.sendEmptyMessage(1);
    }

    public Bitmap getHttpBitmap(String url)
    {
        Bitmap bitmap = null;
        try
        {
            URL pictureUrl = new URL(url);
            InputStream in = pictureUrl.openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
//
//    @Override
//    public void onDestroyView() {
//        Bitmap cache=iv_image.getDrawingCache();
//        if(cache!=null&&!cache.isRecycled()){
//            cache.recycle();
//        }
//        super.onDestroyView();
//    }
}
