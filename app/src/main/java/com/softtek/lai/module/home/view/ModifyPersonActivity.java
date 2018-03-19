/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.PhotoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.net.LoginService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;
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
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

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

    @InjectView(R.id.tv_birth)
    TextView tv_birth;

    @InjectView(R.id.tv_height)
    TextView tv_height;

//    @InjectView(R.id.tv_weight)
//    TextView tv_weight;

    @InjectView(R.id.img)
    ImageView img;
    private UserModel model;
    private String photo;
    private CharSequence[] items = {"拍照", "照片"};
    private ImageFileCropSelector imageFileCropSelector;


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
                            Picasso.with(ModifyPersonActivity.this).load(file).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img);
                            UserModel userModel = UserInfoModel.getInstance().getUser();
                            userModel.setPhoto(photoModel.ThubImg);
                            UserInfoModel.getInstance().saveUserCache(userModel);
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
    public void onResume() {
        super.onResume();
        model = UserInfoModel.getInstance().getUser();
        photo = model.getPhoto();
        String path = AddressManager.get("photoHost");
        if (TextUtils.isEmpty(photo)) {
            Picasso.with(this).load(R.drawable.img_default).placeholder(R.drawable.img_default).into(img);
        } else {
            Picasso.with(this).load(path + photo).fit().placeholder(R.drawable.img_default).
                    centerCrop().error(R.drawable.img_default).into(img);
        }

        if (TextUtils.isEmpty(model.getNickname())) {
            text_name.setText(model.getMobile());
        } else {
            text_name.setText(model.getNickname());
        }
        text_phone.setText(model.getMobile());
        if (model.isHasGender()) {
            if ("1".equals(model.getGender())) {
                text_sex.setText("女");
            } else {
                text_sex.setText("男");
            }
        } else {
            text_sex.setText("");
        }
        tv_birth.setText(model.getBirthday());
        if (!TextUtils.isEmpty(model.getHight()))
        {
            tv_height.setText(model.getHight()+"cm");
        }
//        tv_weight.setText(model.getWeight()+"斤");

    }

    @Override
    protected void initViews() {
        tv_title.setText("详细资料");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rel_modofy_name:
                startActivity(new Intent(this, ModifyNameActivity.class));
                break;
            case R.id.rel_modofy_photo:
                if (TextUtils.isEmpty(photo)) {
                    //弹出dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                //拍照
                                if (ActivityCompat.checkSelfPermission(ModifyPersonActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                        || ActivityCompat.checkSelfPermission(ModifyPersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                        ActivityCompat.checkSelfPermission(ModifyPersonActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(ModifyPersonActivity.this, Manifest.permission.CAMERA) ||
                                            ActivityCompat.shouldShowRequestPermissionRationale(ModifyPersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                            ActivityCompat.shouldShowRequestPermissionRationale(ModifyPersonActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        ActivityCompat.requestPermissions(ModifyPersonActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                    } else {
                                        ActivityCompat.requestPermissions(ModifyPersonActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                    }
                                } else {
                                    imageFileCropSelector.takePhoto(ModifyPersonActivity.this);
                                }
                            } else if (which == 1) {
                                //照片
                                if (ActivityCompat.checkSelfPermission(ModifyPersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                        ActivityCompat.checkSelfPermission(ModifyPersonActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(ModifyPersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                            ActivityCompat.shouldShowRequestPermissionRationale(ModifyPersonActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        ActivityCompat.requestPermissions(ModifyPersonActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                                    } else {
                                        ActivityCompat.requestPermissions(ModifyPersonActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                                    }
                                } else {
                                    imageFileCropSelector.selectSystemImage(ModifyPersonActivity.this);// 图库选择图片
                                }
                            }
                        }
                    }).create().show();
                } else {
                    startActivity(new Intent(this, ModifyPhotoActivity.class));
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
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(ModifyPersonActivity.this);

            } if(requestCode == 200){
                imageFileCropSelector.selectImage(ModifyPersonActivity.this);// 图库选择图片
            }else {

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
