/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.CommunityAdapter;
import com.softtek.lai.module.community.model.UploadImageModel;
import com.softtek.lai.module.community.view.EditPersonalDynamicActivity;
import com.softtek.lai.module.community.view.MineHealthyFragment;
import com.softtek.lai.module.community.view.RecommendHealthyFragment;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.utils.FileUtils;
import com.softtek.lai.utils.SystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_healthy)
public class HealthyFragment extends BaseFragment implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.iv_email)
    ImageView iv_email;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    ViewPager tab_content;

    List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews() {
        ll_left.setVisibility(View.GONE);
        tv_title.setText("健康圈");
        iv_email.setBackgroundResource(R.drawable.camera);
        fl_right.setOnClickListener(this);
        RecommendHealthyFragment recommendHealthyFragment=new RecommendHealthyFragment();
        MineHealthyFragment mineHealthyFragment=new MineHealthyFragment();
        fragments.add(recommendHealthyFragment);
        fragments.add(mineHealthyFragment);
        tab_content.setAdapter(new CommunityAdapter(getFragmentManager(),fragments));
        tab.setupWithViewPager(tab_content);
    }

    @Override
    public void onStart() {
        super.onStart();
        String token= UserInfoModel.getInstance().getToken();
        if(null==token||"".equals(token)){
            fl_right.setVisibility(View.GONE);
        }else{
            fl_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initDatas() {

    }

    private CharSequence[] items={"拍照","从相册选择照片"};
    private static final int OPEN_CAMERA_REQUEST=1;
    private static final int OPEN_PICTURE_REQUEST=2;
    private static final String IMAGE_UPLOADS_DIR=Environment.getExternalStorageDirectory() + File.separator + "laiAppImage/";
    private File dir=new File(IMAGE_UPLOADS_DIR);
    private UploadImage uploadImage;
    private File cameraPhoto;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_right:
                //弹出dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            //拍照
                            //先验证手机是否有sdcard
                            String status = Environment.getExternalStorageState();
                            if (status.equals(Environment.MEDIA_MOUNTED)) {
                                FileUtils.deleteDir(dir);
                                dir.mkdirs();
                                String nameTemp= SystemClock.elapsedRealtime()+".png";
                                cameraPhoto= new File(dir, nameTemp);
                                startActivityForResult(SystemUtils.openCamera(Uri.fromFile(cameraPhoto)), OPEN_CAMERA_REQUEST);
                            } else {
                                Util.toastMsg("没有存储卡");
                            }
                        }else if(which==1){
                            //照片
                            startActivityForResult(SystemUtils.openPicture(),OPEN_PICTURE_REQUEST);
                        }
                    }
                }).create().show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode="+requestCode+";resultCode="+resultCode);
        if(resultCode== -1){//result_ok
            if(requestCode==OPEN_CAMERA_REQUEST){
                uploadImage=new UploadImage(cameraPhoto, BitmapFactory.decodeFile(cameraPhoto.getAbsolutePath()));
            }else if(requestCode==OPEN_PICTURE_REQUEST){
                Uri originalUri=data.getData();
                uploadImage=new UploadImage(new File(originalUri.getPath()),
                        SystemUtils.getThumbnail(getContext(),originalUri,100,100));
            }
            Intent intent=new Intent(getContext(),EditPersonalDynamicActivity.class);//跳转到发布动态界面
            intent.putExtra("uploadImage",uploadImage);
            startActivity(intent);
        }else if(resultCode==0){//返回取消
            uploadImage=null;
        }
    }
}
