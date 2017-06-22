package com.softtek.lai.module.bodygame3.activity.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 *
 * Created by lareina.qiao on 12/19/2016.
 */
@InjectLayout(R.layout.activity_preview_layout)
public class PreViewPicActivity extends BaseActivity{
    @InjectView(R.id.im_show_pic)
    ImageView im_show_pic;
    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.im_takepic)
    ImageView im_takepic;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.im_left)
            ImageView im_left;
    File file;
    String images,photoname;
    int IsEdit;
    private ImageFileSelector imageFileSelector;
    private CharSequence[] items = {"拍照", "从相册选择照片"};
    private static final int CAMERA_PREMISSION = 100;
    @Override
    protected void initViews() {
        tv_title.setText("1/1");
        IsEdit=getIntent().getIntExtra("IsEdit",0);
        if (IsEdit!=1)
        {
            im_takepic.setVisibility(View.GONE);
        }
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        int px = DisplayUtil.dip2px(this, 300);
        //*************************
        imageFileSelector = new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px, px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                images=file+"";
                Picasso.with(PreViewPicActivity.this).load(new File(file)).fit().placeholder(R.drawable.default_icon_square).into(im_show_pic);
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                file = new File(files.get(0));
                images=file+"";
                Picasso.with(PreViewPicActivity.this).load(file).fit().placeholder(R.drawable.default_icon_square).centerCrop().into(im_show_pic);
            }

            @Override
            public void onError() {

            }
        });

        im_takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreViewPicActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(PreViewPicActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                    //允许弹出提示
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                } else {
                                    //不允许弹出提示
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                }
                            } else {
                                imageFileSelector.takePhoto(PreViewPicActivity.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileSelector.selectMutilImage(PreViewPicActivity.this, 1);
                        }
                    }
                }).create().show();
            }
        });
        im_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("images",images);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {
        images=getIntent().getStringExtra("images");
        photoname=getIntent().getStringExtra("photoname");
        if (TextUtils.isEmpty(images)) {
            Picasso.with(this).load(AddressManager.get("photoHost") + photoname)
                    .resize(DisplayUtil.getMobileWidth(this),
                            DisplayUtil.getMobileHeight(this) + DisplayUtil.getStatusHeight(this)).centerInside()
                    .placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(im_show_pic);
        }
        else {
            Picasso.with(this).load(new File(images))
                    .resize(DisplayUtil.getMobileWidth(this),
                            DisplayUtil.getMobileHeight(this) + DisplayUtil.getStatusHeight(this)).centerInside()
                    .placeholder(R.drawable.default_icon_square).error(R.drawable.default_icon_square).into(im_show_pic);
        }

    }
    //权限
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PREMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageFileSelector.takePhoto(this);
                }
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent=new Intent();
            intent.putExtra("images",images);
            setResult(RESULT_OK,intent);
//            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
