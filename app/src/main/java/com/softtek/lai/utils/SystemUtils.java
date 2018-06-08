/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.github.snowdream.android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jerry.guan on 3/29/2016.
 * 常用系统工具
 * 调用系统相机
 * 调用系统图片库
 * 调用系统裁剪
 */
public class SystemUtils {

    /**
     * 调用系统裁剪工具
     *
     * @param aspectX 裁剪框的比例x
     * @param aspectY 裁剪框的比例Y
     * @param outputX 裁剪后输出图片的宽
     * @param outputY 裁剪后输出图片的高
     * @param uri     需要裁剪的图片Uri
     */
    public static Intent crop(Uri uri, Uri out, float aspectX, float aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        //intent.putExtra("scale", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        // outputX,outputY 是剪裁图片的宽高
        //if(outputX<=0||outputY<=0){
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

        // }
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        if (out != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * 获取拍完照后返回图片的路径
     *
     * @param resolver
     * @param uri      资源
     * @return 资源路径
     */
    public static String getImagePath(Uri uri, ContentResolver resolver) {
        String path = null;
        if (uri == null) {
            return path;
        }
        //获取图片路径
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //在系统中查找指定对应的图片
        Cursor cursor = resolver.query(uri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            path = cursor.getString(columnIndex);
        }
        cursor.close();
        return path;
    }

    /**
     * 将bitmap保存到指定的目录
     *
     * @param bitmap
     * @param file
     */
    public static void saveBitmap(Bitmap bitmap, File file) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            Log.i("文件保存成功...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用系统相机拍摄
     *
     * @param uri 指定拍摄完成后将图片的存储位置，如果为null则不存储
     * @return
     */
    public static Intent openCamera(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        if (uri != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 获取当前系统的sdk版本
     * @return
     */
    public static int getSDKInt(){
        return Build.VERSION.SDK_INT;
    }

    public static Intent openPicture(){
        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType("image/*");
        return getAlbum;
    }

    /**
     * 根据图片路径返回指定大小的缩略图
     * 用于从系统相册选取图片可直接返回缩略图
     * 对于拍照的无效，拍照之后系统默认返回的就是所缩略图
     * sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
     * @param uri 原图片地址
     * @param height 指定缩略图的高度 单位:dp
     * @param width 指定缩略图的宽度 单位:dp
     */
    public static Bitmap getThumbnail(Context context,Uri uri,int width,int height){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        width=DisplayUtil.dip2px(context,width);
        height=DisplayUtil.dip2px(context,height);
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(picturePath),width,height);
    }

    /**
     * 获取图片路径系统相册图的原始路径
     */
    public static String getPathForSystemPic(Context context,Uri uri){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
