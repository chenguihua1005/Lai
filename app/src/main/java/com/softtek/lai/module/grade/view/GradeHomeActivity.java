/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

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
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.counselor.view.InviteStudentActivity;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.BannerUpdateCallBack;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.grade.model.GradeInfoModel;
import com.softtek.lai.module.grade.model.GradeModel;
import com.softtek.lai.module.grade.model.PeopleModel;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.module.login.model.UserModel;
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
public class GradeHomeActivity extends BaseActivity implements View.OnClickListener, DialogInterface.OnClickListener
        , TextWatcher, BannerUpdateCallBack,GradeImpl.GradeCalllback,PullToRefreshBase.OnRefreshListener2 {

    private static final int GET_IMAGE_VIA_CAMERA = 1;//相机
    private static final int GET_IMAGE_VIA_PICTURE = 2;//图库
    private static final int CROP_VIA_IMAGE = 3;//裁剪
    private static final String localTempImgDir = "laiImage";//相机拍照临时存储目录
    private static final String localTempImgFileName = "GradeHomeActivityBanner.png";
    private static final String uploadloadImageDir = Environment.getExternalStorageDirectory() + File.separator + localTempImgDir;


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
    PullToRefreshListView lv_dynamic;

    @InjectView(R.id.ll_fooder)
    LinearLayout ll_footer;

    private EditText et_content;
    private TextView tv_dialog_title;
    private ImageView camera, picture;

    private View view;
    List<DynamicInfoModel> dynamicInfos = new ArrayList<>();

    private IGrade grade;
    private DynamicAdapter adapter;
    private ProgressDialog progressDialog;
    private AlertDialog dialog;
    private int count = 0;//计数器
    private int review_flag=0;
    private long classId=0;
    private long accountId=0;
    private int pageIndex;

    private File dir = new File(uploadloadImageDir);
    @Override
    protected void initViews() {
        review_flag=getIntent().getIntExtra("review",0);
        ll_back.setOnClickListener(this);
        ll_pc.setOnClickListener(this);
        ll_sr.setOnClickListener(this);
        tv_editor.setOnClickListener(this);
        ll_invite_pc.setOnClickListener(this);
        ll_invite_sr.setOnClickListener(this);
        ll_send_dynamic.setOnClickListener(this);
        UserModel user=UserInfoModel.getInstance().getUser();
        if(review_flag==0||String.valueOf(Constants.SR).equals(user.getUserrole())){
            ll_footer.setVisibility(View.GONE);
            tv_editor.setVisibility(View.GONE);
        }
        lv_dynamic.setOnRefreshListener(this);
        lv_dynamic.setMode(PullToRefreshBase.Mode.BOTH);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");
        if(String.valueOf(Constants.SR).equals(user.getUserrole())){
            ll_sr.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initDatas() {
        tv_title.setText("班级主页");
        accountId= Long.parseLong(UserInfoModel.getInstance().getUser().getUserid());
        classId = Long.parseLong(getIntent().getStringExtra("classId"));
        Log.i("classId="+classId);
        grade = new GradeImpl(this);
        progressDialog.show();
        grade.getGradeInfos(classId, progressDialog);
        adapter = new DynamicAdapter(this, dynamicInfos);
        lv_dynamic.setAdapter(adapter);
        grade.getClassDynamic(classId,1);

    }

    CharSequence[] items={"拍照","照片"};
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_editor:
                //点击编辑按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            //拍照
                            //先验证手机是否有sdcard
                            String status = Environment.getExternalStorageState();
                            if (status.equals(Environment.MEDIA_MOUNTED)) {
                                try {
                                    if (!dir.exists()) dir.mkdirs();
                                    File f = new File(dir, localTempImgFileName);
                                    startActivityForResult(SystemUtils.openCamera(Uri.fromFile(f)), GET_IMAGE_VIA_CAMERA);
                                } catch (ActivityNotFoundException e) {
                                    Util.toastMsg("没有找到存储目录");
                                }
                            } else {
                                Util.toastMsg("没有存储卡");
                            }
                        }else if(which==1){
                            //照片
                            startActivityForResult(SystemUtils.openPicture(), GET_IMAGE_VIA_PICTURE);
                        }
                    }
                }).create().show();
                break;
            case R.id.ll_pc:
                //点击学员条
                Intent studentListIntent=new Intent(this, StudentsActivity.class);
                studentListIntent.putExtra("classId",classId);
                studentListIntent.putExtra("review",review_flag);
                startActivity(studentListIntent);
                break;
            case R.id.ll_sr:
                //点击助教条
                Intent tutorIntent=new Intent(this, TutorActivity.class);
                tutorIntent.putExtra("classId",classId);
                tutorIntent.putExtra("review",review_flag);
                startActivity(tutorIntent);
                break;
            case R.id.ll_invite_tutor:
                Intent intent = new Intent(this, AssistantListActivity.class);
                intent.putExtra("classId", classId);
                startActivity(intent);
                //邀请助教按钮
                break;
            case R.id.ll_invite_student:
                Intent intents = new Intent(this, InviteStudentActivity.class);
                intents.putExtra("classId", classId);
                startActivity(intents);
                //邀请学员按钮
                break;
            case R.id.ll_send_dynamic:
                /*
                发布班级动态：打开dialog用户编辑输入
                 */
                view = getLayoutInflater().inflate(R.layout.activity_input_dynamic_alert, null);
                et_content = (EditText) view.findViewById(R.id.et_content);
                tv_dialog_title = (TextView) view.findViewById(R.id.tv__dialog_title);
                et_content.addTextChangedListener(this);
                AlertDialog.Builder alert = new AlertDialog.Builder(this).setView(view)
                        .setPositiveButton("确认", this)
                        .setNegativeButton("取消", this);
                alert.create().show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(GradeModel gradeModel) {
        //更新班级信息
        List<GradeInfoModel> grades = gradeModel.getClassInfo();
        if (grades != null && grades.size() > 0) {
            GradeInfoModel info = grades.get(0);
            tv_title.setText(info.getClassName());
            tv_title_date.setText(info.getStartDate()+"-"+info.getEndDate());
            tv_pc_num.setText(info.getPCNum() + "人");
            tv_sr_num.setText(info.getSRNum() + "人");
            Log.i("班级主页--------------------->Banner="+info.getClassBanner());
            Picasso.with(this).load(info.getClassBanner()).placeholder(R.drawable.default_pic)
                    .error(R.drawable.default_pic).into(iv_grade_banner);
        }
        //加载学员头像
        List<PeopleModel> pcs = gradeModel.getPCInfo();
        List<PeopleModel> srs = gradeModel.getSRInfo();
        for (int i = 0; i < pcs.size(); i++) {
            PeopleModel pc = pcs.get(i);
            switch (i) {
                case 0:
                    Picasso.with(this).load(pc.getPhoto())
                            .placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_pc_one);
                    break;
                case 1:
                    Picasso.with(this).load(pc.getPhoto())
                            .placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_pc_two);
                    break;
                case 2:
                    Picasso.with(this).load(pc.getPhoto())
                            .placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_pc_three);
                    break;
            }
        }
        for (int i = 0; i < srs.size(); i++) {
            PeopleModel sr = srs.get(i);
            switch (i) {
                case 0:
                    Picasso.with(this).load(sr.getPhoto())
                            .placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_sr_one);
                    break;
                case 1:
                    Picasso.with(this).load(sr.getPhoto())
                            .placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_sr_two);
                    break;
                case 2:
                    Picasso.with(this).load(sr.getPhoto())
                            .placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_sr_three);
                    break;
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListViewUpdate(DynamicInfoModel info) {
        dynamicInfos.add(0,info);
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
        if (which == DialogInterface.BUTTON_POSITIVE) {
            String content = et_content.getText().toString();
            if ("".equals(content.trim())) {
                return;
            }
            grade.sendDynamic(classId, "dsadas", content, Constants.SP_SEND,accountId );
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int surplus = ((100 - s.length()) < 0) ? 0 : 100 - s.length();
        et_content.setSelection(s.length());
        tv_dialog_title.setText("请输入100字以内的文字(" + surplus + ")");
        this.count = s.length();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (count > 100) {
            s.delete(100, et_content.getSelectionEnd());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_IMAGE_VIA_CAMERA:
                    //从指定目录获取图片原图
                    File image = new File(uploadloadImageDir,localTempImgFileName);
                    Log.i("拍完照后图片是否存在？=="+image.exists());
                    startActivityForResult(SystemUtils.crop(Uri.fromFile(image), null, 2, 1, 300, 300), CROP_VIA_IMAGE);
                    break;
                case GET_IMAGE_VIA_PICTURE:
                    Intent intent = SystemUtils.crop(data.getData(), null, 2, 1, 300, 300);
                    startActivityForResult(intent, CROP_VIA_IMAGE);
                    break;
                case CROP_VIA_IMAGE:
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File saveImage = new File(dir, SystemClock.elapsedRealtime()+".png");
                    SystemUtils.saveBitmap(bitmap, saveImage);
                    if (saveImage.exists()) {
                        progressDialog.setMessage("正在上传图片,请稍候...");
                        progressDialog.show();
                        grade.updateClassBanner(classId, "2", saveImage);
                    }
                    break;
            }
        }
    }

    @Override
    public void onSuccess(String bannerUrl, File image) {
        progressDialog.dismiss();
        Picasso.with(this).load(image).placeholder(R.drawable.default_pic)
                .error(R.drawable.img_default).into(iv_grade_banner);

    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void getDynamicCallback(List<DynamicInfoModel> dynamicInfoModels) {
        lv_dynamic.onRefreshComplete();
        if(dynamicInfoModels==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(dynamicInfoModels.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
         dynamicInfos.clear();
        }
        dynamicInfos.addAll(dynamicInfoModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex=1;
        grade.getClassDynamic(classId,1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        grade.getClassDynamic(classId,pageIndex);
    }
}
