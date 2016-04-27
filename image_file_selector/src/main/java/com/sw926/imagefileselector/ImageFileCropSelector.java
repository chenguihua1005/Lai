package com.sw926.imagefileselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.File;

@SuppressWarnings("unused")
public class ImageFileCropSelector {

    private static final String TAG = ImageFileCropSelector.class.getSimpleName();

    private Callback mCallback;
    private ImagePickHelper mImagePickHelper;
    private ImageCaptureHelper mImageTaker;
    private ImageCompressHelper mImageCompressHelper;
    private ImageCropHelper mImageCropperHelper;

    public ImageCropHelper getmImageCropperHelper() {
        return mImageCropperHelper;
    }

    public ImageFileCropSelector(final Context context) {
        mImagePickHelper = new ImagePickHelper(context);
        mImagePickHelper.setCallback(new ImagePickHelper.Callback() {
            @Override
            public void onSuccess(String file) {
                AppLogger.d(TAG, "select image from sdcard: " + file);
                handleResult(file, false);
            }

            @Override
            public void onError() {
                handleError();
            }
        });

        mImageTaker = new ImageCaptureHelper();
        mImageTaker.setCallback(new ImageCaptureHelper.Callback() {
            @Override
            public void onSuccess(String file) {
                AppLogger.d(TAG, "select image from camera: " + file);
                handleResult(file, true);
            }

            @Override
            public void onError() {
                handleError();
            }
        });

        mImageCompressHelper = new ImageCompressHelper(context);
        mImageCompressHelper.setCallback(new ImageCompressHelper.CompressCallback() {
            @Override
            public void onCallBack(String outFile) {
                AppLogger.d(TAG, "compress image output: " + outFile);
                Log.i("hahahhah","开始压缩啦 啦啦啦啦");
                if (mCallback != null) {
                    Log.i("hahahhah","开始压缩啦 啦啦啦啦aaaaa");
                    mCallback.onSuccess(outFile);
                }
            }
        });
        mImageCropperHelper =new ImageCropHelper();
        mImageCropperHelper.setCallback(new ImageCropHelper.ImageCropperCallback() {
            @Override
            public void onCropperCallback(ImageCropHelper.CropperResult result, File srcFile, File outFile) {
                //压缩
                Log.i("啦啦啦啦","裁剪完后啦啦啦啦啦");
                mImageCompressHelper.compress(outFile.getAbsolutePath(), true);
            }
        });
    }

    public static void setDebug(boolean debug) {
        AppLogger.DEBUG = debug;
    }

    public void setOutPut(int width, int height) {
       mImageCropperHelper.setOutPut(width, height);
    }

    public void setOutPutAspect(int width, int height) {
        mImageCropperHelper.setOutPutAspect(width, height);
    }

    public void setScale(boolean scale) {
        mImageCropperHelper.setScale(scale);
    }


    /**
     * 设置压缩后的文件大小
     *
     * @param maxWidth  压缩后文件宽度
     * @param maxHeight 压缩后文件高度
     */
    @SuppressWarnings("unused")
    public void setOutPutImageSize(int maxWidth, int maxHeight) {
        mImageCompressHelper.setOutPutImageSize(maxWidth, maxHeight);
    }

    /**
     * 设置压缩后保存图片的质量
     *
     * @param quality 图片质量 0 - 100
     */
    @SuppressWarnings("unused")
    public void setQuality(int quality) {
        mImageCompressHelper.setQuality(quality);
    }

    /**
     * set image compress format
     *
     * @param compressFormat compress format
     */
    @SuppressWarnings("unused")
    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        mImageCompressHelper.setCompressFormat(compressFormat);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImagePickHelper.onActivityResult(requestCode, resultCode, data);
        mImageTaker.onActivityResult(requestCode, resultCode, data);
        //mImageCropperHelper.onActivityResult(resultCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mImagePickHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onSaveInstanceState(Bundle outState) {
        mImageTaker.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mImageTaker.onRestoreInstanceState(savedInstanceState);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void selectImage(Activity activity) {
        mImagePickHelper.selectorImage(activity);
        mImageCropperHelper.imageCropper(activity);
    }

    public void selectImage(Fragment fragment) {
        mImagePickHelper.selectImage(fragment);
        mImageCropperHelper.imageCropper(fragment);
    }

    public void takePhoto(Activity activity) {
        mImageTaker.captureImage(activity);
        mImageCropperHelper.imageCropper(activity);
    }

    public void takePhoto(Fragment fragment) {
        mImageTaker.captureImage(fragment);
        mImageCropperHelper.imageCropper(fragment);
    }

    private void handleResult(String fileName, boolean deleteSrc) {
        File file = new File(fileName);
        if (file.exists()) {
            if(mImageCropperHelper !=null){
                mImageCropperHelper.cropImage(file);
            }
            //mImageCompressHelper.compress(fileName, deleteSrc);
        } else {
            if (mCallback != null) {
                mCallback.onSuccess(null);
            }
        }
    }

    private void handleError() {
        if (mCallback != null) {
            mCallback.onError();
        }
    }

    public interface Callback {
        void onSuccess(String file);

        void onError();
    }

}
