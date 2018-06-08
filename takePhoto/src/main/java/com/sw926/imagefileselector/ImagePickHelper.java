package com.sw926.imagefileselector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.sw926.imagefileselector.ablum.ImageGridActivity;

import java.lang.ref.WeakReference;
import java.util.List;


class ImagePickHelper {

    private static final int SELECT_PIC = 0x701;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0x11;

    private Callback mCallback;

    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<Fragment> mFragmentWeakReference;

    private int limit;

    public ImagePickHelper() {

    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }


    public void selectorMutilImage(Fragment fragment,int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                this.limit=limit;
                mFragmentWeakReference = new WeakReference<>(fragment);
                fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);

            } else {
                doSelect(fragment,limit);
            }
        } else {
            doSelect(fragment,limit);
        }

    }

    public void selectorMutilImage(Activity activity,int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                this.limit=limit;
                mActivityWeakReference = new WeakReference<>(activity);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);

            } else {
                doSelect(activity,limit);
            }
        } else {
            doSelect(activity,limit);
        }

    }

    public void selectorSystemImage(Activity activity){
        mActivityWeakReference = new WeakReference<>(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);

            } else {
                doSelect(activity);
            }
        } else {
            doSelect(activity);
        }
    }
    public void selectorSystemImage(Fragment fragment){
        mFragmentWeakReference = new WeakReference<>(fragment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);

            } else {
                doSelect(fragment);
            }
        } else {
            doSelect(fragment);
        }
    }

    private void doSelect(Activity activity,int limit){
        if(limit<=1){
            limit=1;
            //selectorImage(activity);
        }
        activity.startActivityForResult(new Intent(activity,
                    ImageGridActivity.class).putExtra("limit",limit),SELECT_PIC);

    }
    private void doSelect(Fragment fragment,int limit){
        if(limit<=1){
            limit=1;
//            selectImage(fragment);
        }
        fragment.startActivityForResult(new Intent(fragment.getContext(),
                ImageGridActivity.class).putExtra("limit",limit),SELECT_PIC);
    }
    private void doSelect(Activity activity) {
        Intent intent = createIntent();
        activity.startActivityForResult(intent, SELECT_PIC);
    }


    private void doSelect(Fragment fragment) {
        Intent intent = createIntent();
        fragment.startActivityForResult(intent, SELECT_PIC);
    }

    private Intent createIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);//ACTION_OPEN_DOCUMENT
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == SELECT_PIC) {
            List<String> imgs=intent.getStringArrayListExtra("imgs");
            if(imgs!=null){
                if (mCallback != null) {
                    mCallback.onMutilSussess(imgs);
                }
            }else {
                Uri uri = intent.getData();
                Context context = null;
                if(mActivityWeakReference!=null&&mActivityWeakReference.get()!=null){
                    context=mActivityWeakReference.get();
                }else if(mFragmentWeakReference!=null&&mFragmentWeakReference.get()!=null){
                    context=mFragmentWeakReference.get().getContext();
                }
                if(context!=null){
                    String path = Compatibility.getPath(context, uri);
                    if (mCallback != null) {
                        mCallback.onSystemImageSuccess(path);
                    }
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mFragmentWeakReference != null) {
                    Fragment fragment = mFragmentWeakReference.get();
                    if (fragment != null) {
                        doSelect(fragment,limit);
                    }
                } else if (mActivityWeakReference != null) {
                    Activity activity = mActivityWeakReference.get();
                    if (activity != null) {
                        doSelect(activity,limit);
                    }
                } else if (mCallback != null) {
                    mCallback.onError();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        }
    }

    public interface Callback {

        void onMutilSussess(List<String> imgs);

        void onSystemImageSuccess(String img);
        void onError();
    }


}
