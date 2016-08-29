package com.softtek.lai.module.lossweightstory.view;

import android.Manifest;
import android.app.Dialog;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.stepcount.service.StepService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.FileUtils;
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
@InjectLayout(R.layout.activity_picture)
public class PictureMoreFragment extends BaseFragment{

    @InjectView(R.id.iv_image)
    PhotoView iv_image;
    private CharSequence[] items = {"保存"};

    private static PictureMoreFragment fragment;
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
    String uri;

    public static PictureMoreFragment getInstance(String path_image){
        fragment=new PictureMoreFragment();
        Bundle bundle=new Bundle();
        bundle.putString("image_path",path_image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {
        iv_image.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        iv_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //保存
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    Bitmap bitmap = getHttpBitmap(AddressManager.get("photoHost")+uri);//从网络获取图片
                                    saveImageToGallery(getContext(),bitmap);
                                }
                            }).start();
                        } else if (which == 1) {


                        }
                    }
                }).create().show();
                return false;
            }
        });
    }
    public  void saveImageToGallery(Context context, Bitmap bmp) {
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

        // 最后通知图库更新
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
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
    @Override
    protected void initDatas() {
        uri=getArguments().getString("image_path");
        Bitmap cache=iv_image.getDrawingCache();
        if(cache!=null&&!cache.isRecycled()){
            cache.recycle();
        }
        //int px=Math.min(DisplayUtil.getMobileHeight(getContext()),DisplayUtil.getMobileWidth(getContext()));
        Picasso.with(getContext()).load(AddressManager.get("photoHost")+uri)
                .resize(DisplayUtil.getMobileWidth(getContext()),
                        DisplayUtil.getMobileHeight(getContext())+DisplayUtil.getStatusHeight(getActivity())).centerInside()
                .placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(iv_image);

    }

    @Override
    public void onDestroyView() {
        Bitmap cache=iv_image.getDrawingCache();
        if(cache!=null&&!cache.isRecycled()){
            cache.recycle();
        }
        super.onDestroyView();
    }
}
