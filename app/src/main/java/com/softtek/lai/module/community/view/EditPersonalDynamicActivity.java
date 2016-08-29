package com.softtek.lai.module.community.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.adapter.CommunityPhotoGridViewAdapter;
import com.softtek.lai.module.community.model.CommunityModel;
import com.softtek.lai.module.community.presenter.PersionalDynamicManager;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CustomGridView;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_edit_personal_dynamic)
public class EditPersonalDynamicActivity extends BaseActivity implements View.OnClickListener
,Validator.ValidationListener,AdapterView.OnItemClickListener,ImageFileSelector.Callback{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @Required(order = 1,message = "请输入内容")
    @InjectView(R.id.et_content)
    EditText et_content;
    @InjectView(R.id.cgv)
    CustomGridView cgv;

    private List<UploadImage> images=new ArrayList<>();
    private CommunityPhotoGridViewAdapter adapter;

    private PersionalDynamicManager manager;

    private ImageFileSelector imageFileSelector;

    @Override
    protected void initViews() {
        tv_right.setText("发布");
        tv_title.setText("发布动态");
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        cgv.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        manager=new PersionalDynamicManager(images, this);
        UploadImage image= getIntent().getParcelableExtra("uploadImage");
        if(image!=null){
            try {
                image.setBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(image.getUri())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            images.add(image);
        }
        images.add(new UploadImage(null, BitmapFactory.decodeResource(getResources(), R.drawable.shizi)));
        adapter=new CommunityPhotoGridViewAdapter(images,this);
        cgv.setAdapter(adapter);

        int px=DisplayUtil.dip2px(this,300);
        //*************************

        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(30);
        imageFileSelector.setCallback(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                exitEdit();
                break;
            case R.id.fl_right:
                //发布个人动态按钮
                validateLife.validate();
                break;
        }
    }
    /**/
    private void exitEdit(){
        SoftInputUtil.hidden(this);
        new AlertDialog.Builder(this)
                .setMessage("退出此次编辑？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清楚本地编辑的图片
                        for(UploadImage image:images){
                            Bitmap bit=image.getBitmap();
                            if(bit!=null&&!bit.isRecycled()){
                                bit.recycle();
                            }
                        }
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }
    /**/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitEdit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onValidationSucceeded() {
        SoftInputUtil.hidden(this);
        if(images.size()==1){
            new AlertDialog.Builder(this)
                    .setMessage("请选择至少一张图片上传")
                    .create().show();
            return;
        }
        //开始发布
        if(manager!=null){
            CommunityModel model=new CommunityModel();
            model.setContent(et_content.getText().toString().trim());
            model.setHtype(1);
            model.setTitle("");
            model.setAccountId(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
            manager.sendDynamic(model);
//            sdfs
        }
    }
    /**/
    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create().show();
    }

    CharSequence[] options={"拍照","选择个人相册"};
    private static final int OPEN_PREVIEW=3;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UploadImage image=images.get(position);
        if(image.getImage()==null){
            new AlertDialog.Builder(this).setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        //检查权限
                        if(ActivityCompat.checkSelfPermission(EditPersonalDynamicActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                            if(ActivityCompat.shouldShowRequestPermissionRationale(EditPersonalDynamicActivity.this,Manifest.permission.CAMERA)){
                                //允许弹出提示
                                ActivityCompat.requestPermissions(EditPersonalDynamicActivity.this,
                                        new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                            }else{
                                //不允许弹出提示
                                ActivityCompat.requestPermissions(EditPersonalDynamicActivity.this,
                                        new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                            }
                        }else {
                            imageFileSelector.takePhoto(EditPersonalDynamicActivity.this);
                        }
                    }else if(which==1){
                        //打开图库
                        imageFileSelector.selectImage(EditPersonalDynamicActivity.this);
                    }
                }
            }).create().show();
        }else{
            Intent intent=new Intent(this,PreviewImageActivity.class);
            intent.putExtra("uri",Uri.fromFile(image.getImage()));
            intent.putExtra("position",position);
            startActivityForResult(intent, OPEN_PREVIEW);
        }
    }
    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileSelector.takePhoto(EditPersonalDynamicActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==OPEN_PREVIEW){
                int position= data.getIntExtra("position", 0);
                images.remove(position);
                if(images.get(images.size()-1).getImage()!=null){
                    images.add(new UploadImage(null, BitmapFactory.decodeResource(getResources(), R.drawable.shizi)));
                }
            }
            adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onSuccess(String file) {
        UploadImage image=new UploadImage();
        File outFile=new File(file);
        image.setImage(outFile);
        image.setBitmap(BitmapFactory.decodeFile(outFile.getAbsolutePath()));
        images.add(0, image);
        if(images.size()==10){
            images.remove(9);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }

}
