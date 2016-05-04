package com.softtek.lai.module.lossweightstory.view;

import android.app.Dialog;
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
import com.mobsandgeeks.saripaar.annotation.TextRule;
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

    @Required(order = 1,message = "请填写故事人")
    @InjectView(R.id.et_sender)
    EditText et_sender;
    @Required(order = 2,message = "请填写故事标题")
    @TextRule(order = 3,maxLength = 20,message = "标题过长")
    @InjectView(R.id.et_log_title)
    EditText et_log_title;
    @Required(order = 4,message = "请填写减重后体重")
    @InjectView(R.id.et_weight_after)
    TextView tv_weight_after;
    @Required(order = 5,message = "请填写说明")
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
        final AlertDialog.Builder birdialog=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog,null);
        final NumberPicker np = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np.setMaxValue(600);
        String gender=UserInfoModel.getInstance().getUser().getGender();
        if("0".equals(gender)){//男
            np.setValue(150);
        }else{
            np.setValue(100);
        }
        np.setMinValue(20);
        np.setWrapSelectorWheel(false);
        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (np.getValue() < 80) {
                    Dialog dialog1 = new AlertDialog.Builder(NewStoryActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight_after.setText(String.valueOf(np.getValue())); //set the value to textview
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            show_weight_dialog();
                                        }
                                    }).create();
                    dialog1.show();
                    dialog1.setCanceledOnTouchOutside(false);
                } else {
                    tv_weight_after.setText(String.valueOf(np.getValue())); //set the value to textview
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
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
            new AlertDialog.Builder(this).setTitle("验证失败")
                    .setMessage("请选择至少一张图片上传")
                    .create().show();
            return;
        }
        if(storyManager!=null){
            LogStoryModel model=new LogStoryModel();
            model.setAccountId(Long.parseLong(UserInfoModel.getInstance().getUser().getUserid()));
            model.setLogTitle(et_log_title.getText().toString().trim());
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
