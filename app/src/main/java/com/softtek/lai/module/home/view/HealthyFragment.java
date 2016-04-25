/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.softtek.lai.module.community.view.EditPersonalDynamicActivity;
import com.softtek.lai.module.community.view.MineHealthyFragment;
import com.softtek.lai.module.community.view.RecommendHealthyFragment;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.utils.DisplayUtil;
import com.sw926.imagefileselector.ImageCropper;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

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

    private ImageFileSelector imageFileSelector;
    private ImageCropper imageCropper;

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
        imageFileSelector=new ImageFileSelector(getContext());
        imageCropper=new ImageCropper(this);
        imageCropper.setScale(true);
        imageCropper.setOutPutAspect(1, 1);
        imageFileSelector.setQuality(100);
        int px=DisplayUtil.dip2px(getContext(),100);
        imageFileSelector.setOutPutImageSize(px,px);
        imageCropper.setCallback(new ImageCropper.ImageCropperCallback() {
            @Override
            public void onCropperCallback(ImageCropper.CropperResult result, File srcFile, File outFile) {
                Intent intent=new Intent(getContext(),EditPersonalDynamicActivity.class);//跳转到发布动态界面
                UploadImage image=new UploadImage();
                image.setImage(outFile);
                image.setBitmap(BitmapFactory.decodeFile(outFile.getAbsolutePath()));
                intent.putExtra("uploadImage",image);
                startActivityForResult(intent,OPEN_SENDER_REQUEST);
            }
        });
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                imageCropper.cropImage(new File(file));
            }

            @Override
            public void onError() {

            }
        });
    }

    private CharSequence[] items={"拍照","从相册选择照片"};
    private static final int OPEN_SENDER_REQUEST=1;
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
                            imageFileSelector.takePhoto(HealthyFragment.this);
                        }else if(which==1){
                            //照片
                            imageFileSelector.selectImage(HealthyFragment.this);
                        }
                    }
                }).create().show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, data);
        imageCropper.onActivityResult(requestCode,resultCode,data);
        Log.i("requestCode=" + requestCode + ";resultCode=" + resultCode);
        if(resultCode== -1){//result_ok
            if(requestCode==OPEN_SENDER_REQUEST){
                Log.i("健康圈我的发布完成返回");
                tab_content.setCurrentItem(1);
                ((MineHealthyFragment)fragments.get(1)).updateList();
            }

        }
    }
}
