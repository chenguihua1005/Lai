/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_modify_photo)
public class ModifyPhotoActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;


    @InjectView(R.id.iv_image)
    PhotoView iv_image;

    private UserModel model;
    private String photo;
    private CharSequence[] items = {"拍照", "照片"};
    private ImageFileCropSelector imageFileCropSelector;

    private ILoginPresenter presenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        int px = DisplayUtil.dip2px(this, 300);
        imageFileCropSelector = new ImageFileCropSelector(this);
        imageFileCropSelector.setOutPutImageSize(px, px);
        imageFileCropSelector.setQuality(30);
        imageFileCropSelector.setOutPutAspect(1, 1);
        imageFileCropSelector.setOutPut(px, px);
        imageFileCropSelector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                progressDialog.show();
                presenter.modifyPictures(UserInfoModel.getInstance().getUser().getUserid(), file, progressDialog);
            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    protected void initViews() {
        tv_title.setText("个人头像");
        iv_email.setImageResource(R.drawable.more_title);
        iv_email.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initDatas() {
        presenter = new LoginPresenterImpl(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");

        model = UserInfoModel.getInstance().getUser();
        photo = model.getPhoto();
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        Picasso.with(this).load(path + photo)
                .resize(DisplayUtil.getMobileWidth(this), DisplayUtil.getMobileHeight(this)).centerInside()
                .placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_image);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.fl_right:
            case R.id.iv_email:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if(ActivityCompat.checkSelfPermission(ModifyPhotoActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if(ActivityCompat.shouldShowRequestPermissionRationale(ModifyPhotoActivity.this,Manifest.permission.CAMERA)){
                                    //允许弹出提示
                                    ActivityCompat.requestPermissions(ModifyPhotoActivity.this,
                                            new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                }else{
                                    //不允许弹出提示
                                    ActivityCompat.requestPermissions(ModifyPhotoActivity.this,
                                            new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                }
                            }else {
                                imageFileCropSelector.takePhoto(ModifyPhotoActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileCropSelector.selectImage(ModifyPhotoActivity.this);
                        }
                    }
                }).create().show();
                break;

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

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
    private static final int CAMERA_PREMISSION=100;
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
                imageFileCropSelector.takePhoto(ModifyPhotoActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }
}
