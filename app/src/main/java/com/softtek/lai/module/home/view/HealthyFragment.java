/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.CommunityAdapter;
import com.softtek.lai.module.community.view.EditPersonalDynamicActivity;
import com.softtek.lai.module.community.view.MineHealthyFragment;
import com.softtek.lai.module.community.view.RecommendHealthyFragment;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.utils.DisplayUtil;
import com.sw926.imagefileselector.ImageFileSelector;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_healthy)
public class HealthyFragment extends LazyBaseFragment {

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    ViewPager tab_content;

    List<Fragment> fragments=new ArrayList<>();

    private ImageFileSelector imageFileSelector;
    private CommunityAdapter adapter;

    @Override
    protected void lazyLoad() {
        fragments.add(new RecommendHealthyFragment());
        fragments.add(new MineHealthyFragment());
        adapter=new CommunityAdapter(getChildFragmentManager(),fragments);
        tab_content.setAdapter(adapter);
        tab.setupWithViewPager(tab_content);
    }

    private CharSequence[] items={"拍照","从相册选择照片"};
    private static final int OPEN_SENDER_REQUEST=1;
    private static final int CAMERA_PREMISSION=100;

    @Override
    protected void initViews() {

        fl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                    //允许弹出提示
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);

                                } else {
                                    //不允许弹出提示
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
                                }
                            } else {
                                imageFileSelector.takePhoto(HealthyFragment.this);
                            }
                        } else if (which == 1) {
                            //照片
                            imageFileSelector.selectMutilImage(HealthyFragment.this,9);
                        }
                    }
                }).create().show();
            }
        });

        String token= UserInfoModel.getInstance().getToken();
        if(StringUtils.isEmpty(token)){
            fl_right.setVisibility(View.GONE);
        }else{
            fl_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initDatas() {
        int px=DisplayUtil.dip2px(getContext(),300);
        //*************************
        imageFileSelector=new ImageFileSelector(getContext());
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(60);
        imageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Intent intent=new Intent(getContext(),EditPersonalDynamicActivity.class);//跳转到发布动态界面
                UploadImage image=new UploadImage();
                image.setImage(new File(file));
                image.setUri(Uri.fromFile(new File(file)));
                intent.putExtra("uploadImage",image);
                startActivityForResult(intent,OPEN_SENDER_REQUEST);
            }

            @Override
            public void onMutilSuccess(List<String> files) {
                Intent intent=new Intent(getContext(),EditPersonalDynamicActivity.class);//跳转到发布动态界面
                ArrayList<UploadImage> uploadImages=new ArrayList<>();
                for (int i=files.size()-1;i>=0;i--){
                    UploadImage image=new UploadImage();
                    File file=new File(files.get(i));
                    image.setImage(file);
                    image.setUri(Uri.fromFile(file));
                    uploadImages.add(image);
                }
                intent.putParcelableArrayListExtra("uploadImages",uploadImages);
            }

            @Override
            public void onError() {

            }
        });
        //**************************
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);
        if(resultCode== -1){//result_ok
            if(requestCode==OPEN_SENDER_REQUEST){
                Log.i("dsadasd");
                tab_content.setCurrentItem(0);
                int size=fragments.size();
                if(size>=1){
                    ((RecommendHealthyFragment)fragments.get(0)).updateList();
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileSelector.takePhoto(HealthyFragment.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

}
