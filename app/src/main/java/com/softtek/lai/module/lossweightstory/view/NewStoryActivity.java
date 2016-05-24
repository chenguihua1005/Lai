package com.softtek.lai.module.lossweightstory.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.community.view.PreviewImageActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.lossweightstory.adapter.PhotoGridViewAdapter;
import com.softtek.lai.module.lossweightstory.model.LogStoryModel;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.module.lossweightstory.presenter.NewStoryManager;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CustomGridView;
import com.sw926.imagefileselector.ImageFileSelector;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_new_story)
public class NewStoryActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
    ,Validator.ValidationListener,ImageFileSelector.Callback{

    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.fl_right)
    FrameLayout fl;

    @InjectView(R.id.et_sender)
    EditText et_sender;

    /*@InjectView(R.id.et_log_title)
    EditText et_log_title;*/

    @Required(order = 1,message = "请选择减重后体重")
    @InjectView(R.id.et_weight_after)
    TextView tv_weight_after;

    @Required(order = 2,message = "请输入日志内容")
    @InjectView(R.id.et_content)
    EditText et_content;

    @InjectView(R.id.cgv)
    CustomGridView cgv;

    private List<UploadImage> images=new ArrayList<>();
    private PhotoGridViewAdapter adapter;
    private NewStoryManager storyManager;

    private ImageFileSelector imageFileSelector;

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("发布故事");
        tv_right.setText("发布");
        fl.setOnClickListener(this);
        cgv.setOnItemClickListener(this);
        tv_weight_after.setOnClickListener(this);
    }



    private boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    public int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }


    @Override
    protected void initDatas() {
        storyManager=new NewStoryManager(images,this);
        UserModel model= UserInfoModel.getInstance().getUser();
        et_sender.setText(StringUtils.isEmpty(model.getNickname())? StringUtil.filterPhonNumber(model.getMobile()):model.getNickname());
        et_sender.setEnabled(false);
        images.add(new UploadImage(null,BitmapFactory.decodeResource(getResources(), R.drawable.camera_sel)));
        adapter=new PhotoGridViewAdapter(images,this);
        cgv.setAdapter(adapter);
        int px=DisplayUtil.dip2px(this,300);

        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(30);
        imageFileSelector.setCallback(this);
    }

    //体重对话框
    public void show_weight_dialog() {
        AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(600);
        String gender=UserInfoModel.getInstance().getUser().getGender();
        if("0".equals(gender)){//男
            np1.setValue(150);
        }else{
            np1.setValue(100);
        }
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_weight_after.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        }).create().show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                exitEdit();
                break;
            case R.id.fl_right:
                //发布日志按钮
                /*String tit=et_log_title.getText().toString();
                if(StringUtils.isEmpty(tit)){
                    new AlertDialog.Builder(this)
                            .setMessage("请输入标题")
                            .create().show();
                }else if(length(tit)>30){
                    new AlertDialog.Builder(this)
                            .setMessage("标题不能超过15个汉字")
                            .create().show();
                }else{

                }*/
                validateLife.validate();
                break;
            case R.id.et_weight_after:
                show_weight_dialog();
                break;

        }
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
                        //打开照相机
                        //imageFileCropSelector.takePhoto(NewStoryActivity.this);
                        imageFileSelector.takePhoto(NewStoryActivity.this);
                    }else if(which==1){
                        //打开图库
                        //imageFileCropSelector.selectImage(NewStoryActivity.this);
                        imageFileSelector.selectImage(NewStoryActivity.this);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        imageFileSelector.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageFileSelector.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageFileSelector.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitEdit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitEdit(){
        new AlertDialog.Builder(this)
                .setMessage("退出此次编辑？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清楚本地编辑的图片
                        //FileUtils.deleteDir(dir);
                        finish();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    @Override
    public void onValidationSucceeded() {
        if(images.size()==1){
            new AlertDialog.Builder(this)
                    .setMessage("请上传照片且不能超过9张")
                    .create().show();
            return;
        }
        if(storyManager!=null){
            LogStoryModel model=new LogStoryModel();
            model.setAccountId(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
            model.setLogTitle("");
            model.setAfterWeight(tv_weight_after.getText().toString());
            model.setLogContent(et_content.getText().toString().trim());
            model.setStoryPeople(et_sender.getText().toString().trim());
            storyManager.sendLogStory(model);
        }


    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create().show();
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
