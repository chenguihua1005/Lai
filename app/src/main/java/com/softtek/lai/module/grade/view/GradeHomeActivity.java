package com.softtek.lai.module.grade.view;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.view.InviteStudentActivity;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.BannerUpdateCallBack;
import com.softtek.lai.module.grade.model.DynamicInfo;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.model.GradeInfo;
import com.softtek.lai.module.grade.model.People;
import com.softtek.lai.module.grade.model.PhotoInfo;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.utils.SystemUtils;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_grade_home)
public class GradeHomeActivity extends BaseActivity implements View.OnClickListener,DialogInterface.OnClickListener
, TextWatcher,BannerUpdateCallBack{

    private static final int GET_IMAGE_VIA_CAMERA=1;//相机
    private static final int GET_IMAGE_VIA_PICTURE=2;//图库
    private static final int CROP_VIA_IMAGE=3;//裁剪
    private static final String localTempImgDir="laiImage";//相机拍照临时存储目录
    private static final String localTempImgFileName="GradeHomeActivityBanner.png";
    private static final String uploadloadImageDir=Environment.getExternalStorageDirectory()+File.separator+localTempImgDir;


    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_title_date)
    TextView tv_title_date;
    @InjectView(R.id.ll_left)
    LinearLayout ll_back;
    @InjectView(R.id.tv_editor)
    TextView tv_editor;

    @InjectView(R.id.civ_tutorfirst)
    CircleImageView cir_sr_one;
    @InjectView(R.id.civ_tutorsecond)
    CircleImageView cir_sr_two;
    @InjectView(R.id.civ_tutorthird)
    CircleImageView cir_sr_three;
    @InjectView(R.id.civ_stufirst)
    CircleImageView cir_pc_one;
    @InjectView(R.id.civ_stusecond)
    CircleImageView cir_pc_two;
    @InjectView(R.id.civ_stuthird)
    CircleImageView cir_pc_three;
    @InjectView(R.id.tv_tutor_num)
    TextView tv_sr_num;
    @InjectView(R.id.tv_student_num)
    TextView tv_pc_num;

    @InjectView(R.id.ll_sr)
    LinearLayout ll_sr;
    @InjectView(R.id.ll_pc)
    LinearLayout ll_pc;

    @InjectView(R.id.ll_invite_tutor)
    LinearLayout ll_invite_sr;

    @InjectView(R.id.ll_invite_student)
    LinearLayout ll_invite_pc;

    @InjectView(R.id.ll_send_dynamic)
    LinearLayout ll_send_dynamic;

    @InjectView(R.id.iv_banner)
    ImageView iv_grade_banner;

    @InjectView(R.id.lv_dynamic)
    ListView lv_dynamic;

    private EditText et_content;
    private TextView tv_dialog_title;
    private ImageView camera,picture;

    private View view;
    List<DynamicInfo> dynamicInfos=new ArrayList<>();

    private IGrade grade;
    private DynamicAdapter adapter;
    private ProgressDialog progressDialog;
    private AlertDialog dialog;
    private int count=0;

    private File dir=new File(uploadloadImageDir);

    @Override
    protected void initViews() {
        ll_back.setOnClickListener(this);
        ll_pc.setOnClickListener(this);
        ll_sr.setOnClickListener(this);
        tv_editor.setOnClickListener(this);
        ll_invite_pc.setOnClickListener(this);
        ll_invite_sr.setOnClickListener(this);
        ll_send_dynamic.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");

    }

    @Override
    protected void initDatas() {
        tv_title.setText("班级主页");
        grade=new GradeImpl(this);
        progressDialog.show();
        grade.getGradeInfos(1, progressDialog);
        adapter=new DynamicAdapter(this, dynamicInfos);
        lv_dynamic.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_editor:
                //点击编辑按钮
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                View view1=getLayoutInflater().inflate(R.layout.camera_or_picture,null);
                camera= (ImageView) view1.findViewById(R.id.camera);
                picture= (ImageView) view1.findViewById(R.id.picture);
                camera.setOnClickListener(this);
                picture.setOnClickListener(this);
                builder.setTitle("请选择").setView(view1).create();
                dialog=builder.show();
                break;
            case R.id.ll_pc:
                //点击学员条
                startActivity(new Intent(this,StudentsActivity.class));
                break;
            case R.id.ll_sr:
                //点击助教条
                startActivity(new Intent(this,TutorActivity.class));
                break;
            case R.id.ll_invite_tutor:
                Intent intent = new Intent(this, AssistantListActivity.class);
                intent.putExtra("classId",getIntent().getStringExtra("classId"));
                startActivity(intent);
                //邀请助教按钮
                break;
            case R.id.ll_invite_student:
                Intent intents = new Intent(this, InviteStudentActivity.class);
                intents.putExtra("classId",getIntent().getStringExtra("classId"));
                startActivity(intents);
                //邀请学员按钮
                break;
            case R.id.ll_send_dynamic:
                /*
                发布班级动态：打开dialog用户编辑输入
                 */
                view=getLayoutInflater().inflate(R.layout.activity_input_dynamic_alert,null);
                et_content= (EditText) view.findViewById(R.id.et_content);
                tv_dialog_title= (TextView) view.findViewById(R.id.tv__dialog_title);
                et_content.addTextChangedListener(this);
                AlertDialog.Builder alert=new AlertDialog.Builder(this).setView(view)
                        .setPositiveButton("确认", this)
                        .setNegativeButton("取消", this);
                alert.create().show();
                break;
            case R.id.camera:
                //选择相机
                //先验证手机是否有sdcard
                String status= Environment.getExternalStorageState();
                if(status.equals(Environment.MEDIA_MOUNTED)){
                    try {
                        if(!dir.exists())dir.mkdirs();
                        File f=new File(dir, localTempImgFileName);
                        startActivityForResult(SystemUtils.openCamera(Uri.fromFile(f)), GET_IMAGE_VIA_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        Util.toastMsg("没有找到存储目录");
                    }
                }else{
                    Util.toastMsg("没有存储卡");
                }
                if(dialog!=null){
                    dialog.dismiss();
                }
                break;
            case R.id.picture:
                //选择图片
                Intent getAlbum = new Intent(Intent.ACTION_PICK);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum,GET_IMAGE_VIA_PICTURE);
                if(dialog!=null){
                    dialog.dismiss();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(Grade grade){
        //加载班级Banner
        List<PhotoInfo> banners=grade.getPhotoInfo();
        if(banners!=null&&banners.size()>0){
            Picasso.with(this).load(grade.getPhotoInfo().get(0).getImg_Addr()).into(iv_grade_banner);
        }
        //更新班级信息
        List<GradeInfo> grades=grade.getClassInfo();
        if(grades!=null&&grades.size()>0){
            GradeInfo info=grades.get(0);
            tv_title.setText(info.getClassName());
            tv_title_date.setText(info.getStartDate());
        }
        //加载学员头像
        List<People> pcs=grade.getPCInfo();
        List<People> srs=grade.getSRInfo();
        tv_pc_num.setText(pcs.size()+"人");
        tv_sr_num.setText(srs.size()+"人");

        //更新班级动态
        dynamicInfos.clear();
        dynamicInfos.addAll(grade.getDynamicInfo());
        adapter.notifyDataSetChanged();

        for(int i=0;i<pcs.size();i++){
            People pc=pcs.get(i);
            switch (i){
                case 0:
                    Picasso.with(this).load(pc.getPhoto()).into(cir_pc_one);
                    break;
                case 1:
                    Picasso.with(this).load(pc.getPhoto()).into(cir_pc_two);
                    break;
                case 2:
                    Picasso.with(this).load(pc.getPhoto()).into(cir_pc_three);
                    break;
            }
        }
        for(int i=0;i<srs.size();i++){
            People sr=srs.get(i);
            switch (i){
                case 0:
                    Picasso.with(this).load(sr.getPhoto()).into(cir_sr_one);
                    break;
                case 1:
                    Picasso.with(this).load(sr.getPhoto()).into(cir_sr_two);
                    break;
                case 2:
                    Picasso.with(this).load(sr.getPhoto()).into(cir_sr_three);
                    break;
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListViewUpdate(DynamicInfo info){
        dynamicInfos.add(info);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if(which==DialogInterface.BUTTON_POSITIVE){
            String content=et_content.getText().toString();
            if("".equals(content.trim())){
                return;
            }
            grade.sendDynamic(1, "dsadas", content, Constants.SP_SEND, 1);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int surplus=((100-s.length())<0)?0:100-s.length();
        et_content.setSelection(s.length());
        tv_dialog_title.setText("请输入100字以内的文字("+surplus+")");
        this.count=s.length();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(count>100){
            s.delete(100,et_content.getSelectionEnd());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case GET_IMAGE_VIA_CAMERA:
                    //从指定目录获取图片原图
                    String imagePath=Environment.getExternalStorageDirectory() + File.separator+localTempImgDir+
                            File.separator+localTempImgFileName;
                    File image=new File(imagePath);
                    startActivityForResult(SystemUtils.crop(Uri.fromFile(image),null,3,2,300, 300),CROP_VIA_IMAGE);
                    break;
                case GET_IMAGE_VIA_PICTURE:
                    Intent intent=SystemUtils.crop(data.getData(),null,3,2,0,0);
                    startActivityForResult(intent,CROP_VIA_IMAGE);
                    break;
                case CROP_VIA_IMAGE:
                    Bitmap bitmap=data.getParcelableExtra("data");
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    File saveImage=new File(dir,localTempImgFileName);
                    SystemUtils.saveBitmap(bitmap,saveImage);
                    Log.i("文件存在吗？"+saveImage.exists());
                    if(saveImage.exists()){
                        progressDialog.setMessage("正在上传图片,请稍候...");
                        progressDialog.show();
                        grade.updateClassBanner(1,"2",saveImage);
                    }
                    break;
            }
        }
    }

    @Override
    public void onSuccess(String bannerUrl, File image) {
        progressDialog.dismiss();
        Picasso.with(this).load(image).into(iv_grade_banner);
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
