package com.softtek.lai.module.lossweightstory.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
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
import com.softtek.lai.utils.FileUtils;
import com.softtek.lai.utils.SystemUtils;
import com.softtek.lai.widgets.CustomGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_new_story)
public class NewStoryActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
    ,Validator.ValidationListener{

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
    @InjectView(R.id.et_log_title)
    EditText et_log_title;
    @Required(order = 3,message = "请填写减重后体重")
    @InjectView(R.id.et_weight_after)
    EditText et_weight_after;
    @Required(order = 4,message = "请填写说明")
    @InjectView(R.id.et_content)
    EditText et_content;
    @InjectView(R.id.cgv)
    CustomGridView cgv;
    private File dir=new File(Environment.getExternalStorageDirectory()+File.separator+"imgload/");
    private List<UploadImage> images=new ArrayList<>();
    private PhotoGridViewAdapter adapter;
    private NewStoryManager storyManager;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        tv_title.setText("发布故事");
        tv_right.setText("发布");
        fl.setOnClickListener(this);
        cgv.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        storyManager=new NewStoryManager(images,this);
        UserModel model= UserInfoModel.getInstance().getUser();
        et_sender.setText(model.getNickname());
        et_sender.setSelection(et_sender.getText().toString().length());
        images.add(new UploadImage(null,BitmapFactory.decodeResource(getResources(), R.drawable.camera_sel)));
        adapter=new PhotoGridViewAdapter(images,this);
        cgv.setAdapter(adapter);
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

        }
    }

    CharSequence[] options={"拍照","选择个人相册"};
    private static final int OPEN_PICTUR=1;
    private static final int OPEN_CAMERA=2;
    private static final int OPEN_PREVIEW=3;
    File file;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UploadImage image=images.get(position);
        if(image.getImage()==null){
            new AlertDialog.Builder(this).setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        //打开照相机
                        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                            Util.toastMsg("您没有存储卡");
                            return;
                        }
                        if(!dir.exists())dir.mkdirs();
                        file=new File(dir, SystemClock.elapsedRealtime()+".png");
                        startActivityForResult(SystemUtils.openCamera(Uri.fromFile(file)),OPEN_CAMERA);
                    }else if(which==1){
                        //打开图库
                        startActivityForResult(SystemUtils.openPicture(),OPEN_PICTUR);
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
        if(resultCode==RESULT_OK){
            if(requestCode==OPEN_PICTUR){
                Uri imageUri = data.getData();
                images.add(0, new UploadImage(
                        new File(SystemUtils.getPathForSystemPic(this,imageUri)),
                        SystemUtils.getThumbnail(this, imageUri, 100, 100)));
                if(images.size()==10){
                    images.remove(9);
                }
            }else if(requestCode==OPEN_CAMERA){
                //处理mOutPutFileUri中的完整图像
                images.add(0,new UploadImage(file,BitmapFactory.decodeFile(file.getAbsolutePath())));
                if(images.size()==10){
                    images.remove(9);
                }
            }else if(requestCode==OPEN_PREVIEW){
                int position= data.getIntExtra("position",0);
                images.remove(position);
                if(images.get(images.size()-1).getImage()!=null){
                    images.add(new UploadImage(null,BitmapFactory.decodeResource(getResources(),R.drawable.shizi)));
                }
            }
            adapter.notifyDataSetChanged();
        }
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
                        FileUtils.deleteDir(dir);
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
            model.setAfterWeight(et_weight_after.getText().toString());
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
}
