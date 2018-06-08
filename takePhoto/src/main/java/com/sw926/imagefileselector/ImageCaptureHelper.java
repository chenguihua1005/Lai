package com.sw926.imagefileselector;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

class ImageCaptureHelper {

    private static final String KEY_OUT_PUT_FILE = "key_out_put_file";
    private static final int CHOOSE_PHOTO_FROM_CAMERA = 0x702;

    private File mOutFile;
    private Callback mCallback;
    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<Fragment> mFragmentWeakReference;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (mOutFile != null) {
            outState.putString(KEY_OUT_PUT_FILE, mOutFile.getPath());
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String tempFilePath = savedInstanceState.getString(KEY_OUT_PUT_FILE);
            if (!TextUtils.isEmpty(tempFilePath)) {
                mOutFile = new File(tempFilePath);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            Log.i("aa","拍照返回啦啦啦啦啦");
            if (mOutFile != null && mOutFile.exists()) {
                saveImageToGallery(mOutFile);
                if (mCallback != null) {
                    mCallback.onSuccess(mOutFile.getPath());
                }
            } else {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        }
    }
    public void captureImage(Activity activity) {
        mOutFile = CommonUtils.generateExternalImageCacheFile(activity, ".jpg");
        mActivityWeakReference=new WeakReference(activity);
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri out;
            if(Build.VERSION.SDK_INT<=23){
                out=Uri.fromFile(mOutFile);
            }else {
                out= FileProvider.getUriForFile(activity.getApplicationContext(),"com.sw926.imagefileselector.fileprovider",mOutFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
            activity.startActivityForResult(intent, CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    public void captureImage(Fragment fragment) {
        mOutFile = CommonUtils.generateExternalImageCacheFile(fragment.getContext(), ".jpg");
        mFragmentWeakReference=new WeakReference(fragment);
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri out;
            if(Build.VERSION.SDK_INT<=23){
                out=Uri.fromFile(mOutFile);
            }else {
                out= FileProvider.getUriForFile(fragment.getContext().getApplicationContext(),"com.sw926.imagefileselector.fileprovider",mOutFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
            fragment.startActivityForResult(intent, CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    private void saveImageToGallery(File image){
        Context context=null;
        if(mFragmentWeakReference!=null){
            Fragment fragment=mFragmentWeakReference.get();
            if(fragment!=null){
                context=fragment.getContext();
            }
        }else if(mActivityWeakReference!=null){
            Activity activity=mActivityWeakReference.get();
            if(activity!=null){
                context=activity.getApplicationContext();
            }
        }
        if(context!=null){
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        image.getAbsolutePath(),image.getName(),null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(mOutFile)));
        }
    }

    public interface Callback {

        void onSuccess(String fileName);

        void onError();
    }
}
