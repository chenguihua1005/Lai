package com.softtek.lai.module.laijumine.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.home.view.ModifyPhotoActivity;
import com.softtek.lai.module.login.model.PhotoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_test)
public class TestActivity extends BaseActivity {
@InjectView(R.id.im_test)
    CircleImageView im_test;
    private ImageFileCropSelector imageFileCropSelector;
    private static final int CAMERA_PREMISSION=100;
    private CharSequence[] items = {"拍照", "照片"};

    @Override
    protected void initViews() {
        int px = DisplayUtil.dip2px(this, 300);
        imageFileCropSelector = new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutImageSize(px, px);
        imageFileCropSelector.setQuality(30);
        imageFileCropSelector.setOutPutAspect(1, 1);
        imageFileCropSelector.setOutPut(px, px);
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                dialogShow("上传图片");
                upload(new File(file));
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                dialogShow("上传图片");
                upload(new File(files.get(0)));
            }

            @Override
            public void onError() {

            }
        });
        im_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if(ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if(ActivityCompat.shouldShowRequestPermissionRationale(TestActivity.this,Manifest.permission.CAMERA)){
                                    //允许弹出提示
                                    ActivityCompat.requestPermissions(TestActivity.this,
                                            new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                }else{
                                    //不允许弹出提示
                                    ActivityCompat.requestPermissions(TestActivity.this,
                                            new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                }
                            }else {
                                imageFileCropSelector.takePhoto(TestActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileCropSelector.selectImage(TestActivity.this);
                        }
                    }
                }).create().show();
            }
        });

    }

    @Override
    protected void initDatas() {

    }
    private void upload(final File file){
        ZillaApi.NormalRestAdapter.create(LoginService.class).modifyPicture(UserInfoModel.getInstance().getToken(),
                UserInfoModel.getInstance().getUserId(), new TypedFile("image/*", file), new Callback<ResponseData<PhotoModel>>() {
                    @Override
                    public void success(ResponseData<PhotoModel> responseData, Response response) {
                        dialogDissmiss();
                        try {
                            int status = responseData.getStatus();
                            switch (status) {
                                case 200:
                                    PhotoModel photoModel = responseData.getData();
                                    UserModel userModel = UserInfoModel.getInstance().getUser();
                                    userModel.setPhoto(photoModel.ThubImg);
                                    UserInfoModel.getInstance().saveUserCache(userModel);
//                                    im_test.setImageResource(file);
                                    Picasso.with(getParent()).load(file).centerCrop().placeholder(R.drawable.img_default).fit().into(im_test);
//                                    finish();
                                    break;
                                default:
                                    Util.toastMsg(responseData.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        dialogDissmiss();
                        ZillaApi.dealNetError(error);
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(TestActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }
}
