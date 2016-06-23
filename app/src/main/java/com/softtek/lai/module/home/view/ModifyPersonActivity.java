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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_modify_person)
public class ModifyPersonActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.text_name)
    TextView text_name;

    @InjectView(R.id.text_sex)
    TextView text_sex;

    @InjectView(R.id.text_phone)
    TextView text_phone;

    @InjectView(R.id.rel_modofy_photo)
    RelativeLayout rel_modofy_photo;

    @InjectView(R.id.rel_modofy_name)
    RelativeLayout rel_modofy_name;

    @InjectView(R.id.img)
    ImageView img;
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
        rel_modofy_photo.setOnClickListener(this);
        rel_modofy_name.setOnClickListener(this);
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
                presenter.modifyPicture(UserInfoModel.getInstance().getUser().getUserid(),file,progressDialog,img);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        model = UserInfoModel.getInstance().getUser();
        photo = model.getPhoto();
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(photo) || "null".equals(photo) || photo == null) {
            Picasso.with(this).load("111").fit().error(R.drawable.img_default).into(img);
        } else {
            Picasso.with(this).load(path + photo).fit().error(R.drawable.img_default).into(img);
        }

        if (model.getNickname() == null || "".equals(model.getNickname())) {
            text_name.setText(model.getMobile());
        } else {
            text_name.setText(model.getNickname());
        }
        text_phone.setText(model.getMobile());
        if(model.isHasGender()){
            if ("1".equals(model.getGender())) {
                text_sex.setText("女");
            } else {
                text_sex.setText("男");
            }
        }else {
            text_sex.setText("");
        }

    }

    @Override
    protected void initViews() {
        tv_title.setText("个人信息");
    }

    @Override
    protected void initDatas() {
        presenter=new LoginPresenterImpl(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("加载中");
    }
    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;

            case R.id.rel_modofy_name:
                startActivity(new Intent(this,ModifyNameActivity.class));
                break;

            case R.id.rel_modofy_photo:
                if("".equals(photo)){
                    //弹出dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                //拍照
                                if(ActivityCompat.checkSelfPermission(ModifyPersonActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                    //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                    if(ActivityCompat.shouldShowRequestPermissionRationale(ModifyPersonActivity.this,Manifest.permission.CAMERA)){
                                        //允许弹出提示
                                        ActivityCompat.requestPermissions(ModifyPersonActivity.this,
                                                new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                    }else{
                                        //不允许弹出提示
                                        ActivityCompat.requestPermissions(ModifyPersonActivity.this,
                                                new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                    }
                                }else {
                                    imageFileCropSelector.takePhoto(ModifyPersonActivity.this);
                                }
                            } else if (which == 1) {
                                //照片
                                imageFileCropSelector.selectImage(ModifyPersonActivity.this);
                            }
                        }
                    }).create().show();
                }else {
                    startActivity(new Intent(this,ModifyPhotoActivity.class));
                }
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

    // Android 6.0的动态权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(ModifyPersonActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.onActivityResult(requestCode, resultCode, data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode, resultCode, data);
    }
}
