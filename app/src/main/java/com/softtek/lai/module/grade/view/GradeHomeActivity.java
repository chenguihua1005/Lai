/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.BannerUpdateCallBack;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.grade.model.GradeInfoModel;
import com.softtek.lai.module.grade.model.GradeModel;
import com.softtek.lai.module.grade.model.PeopleModel;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import org.apache.commons.lang3.StringUtils;
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
        , TextWatcher, BannerUpdateCallBack,GradeImpl.GradeCalllback,PullToRefreshBase.OnRefreshListener2
,ImageFileCropSelector.Callback{

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

    @InjectView(R.id.sr_first_icon)
    ImageView sr_first_icon;
    @InjectView(R.id.sr_second_icon)
    ImageView sr_second_icon;
    @InjectView(R.id.sr_third_icon)
    ImageView sr_third_icon;
    @InjectView(R.id.pc_first_icon)
    ImageView pc_first_icon;
    @InjectView(R.id.pc_second_icon)
    ImageView pc_second_icon;
    @InjectView(R.id.pc_third_icon)
    ImageView pc_third_icon;

    private EditText et_content;
    //private TextView tv_dialog_title;

    private View view;
    List<DynamicInfoModel> dynamicInfos = new ArrayList<>();

    private IGrade grade;
    private DynamicAdapter adapter;
    private ProgressDialog progressDialog;
    private int count = 0;//计数器
    private int review_flag=0;
    private long classId=0;
    private long accountId=0;
    private int pageIndex;

    private ImageFileCropSelector imageFileCropSelector;
    private IStudentPresenter studentPresenter;

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
        studentPresenter=new StudentImpl(this);
        progressDialog.show();
        grade.getGradeInfos(classId, progressDialog);
        adapter = new DynamicAdapter(this, dynamicInfos);
        lv_dynamic.setAdapter(adapter);
        grade.getClassDynamic(classId,1);

        imageFileCropSelector=new ImageFileCropSelector(this);
        imageFileCropSelector.setQuality(80);
        imageFileCropSelector.setOutPutAspect(1, 1);
        imageFileCropSelector.setOutPut(DisplayUtil.getMobileWidth(this),DisplayUtil.dip2px(this,190));
        imageFileCropSelector.setCallback(this);

    }

    CharSequence[] items={"拍照","照片"};
    private static final int CAMERA_PREMISSION=100;
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
                            if(ActivityCompat.checkSelfPermission(GradeHomeActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                                //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                                if(ActivityCompat.shouldShowRequestPermissionRationale(GradeHomeActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)||
                                        ActivityCompat.shouldShowRequestPermissionRationale(GradeHomeActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)){
                                    //允许弹出提示
                                    ActivityCompat.requestPermissions(GradeHomeActivity.this,
                                            new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                                }else{
                                    //不允许弹出提示
                                    ActivityCompat.requestPermissions(GradeHomeActivity.this,
                                            new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                                }
                            }else {
                                imageFileCropSelector.takePhoto(GradeHomeActivity.this);
                            }
                        }else if(which==1){
                            //照片
                            imageFileCropSelector.selectImage(GradeHomeActivity.this);
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
                dialogShow("加载中");
                studentPresenter.classInvitePCISOK(classId+"","0");
                //邀请助教按钮
                break;
            case R.id.ll_invite_student:
                dialogShow("加载中");
                studentPresenter.classInvitePCISOK(classId+"","1");
                //邀请学员按钮
                break;
            case R.id.ll_send_dynamic:
                /*
                发布班级动态：打开dialog用户编辑输入
                 */
                view = getLayoutInflater().inflate(R.layout.activity_input_dynamic_alert, null);
                et_content = (EditText) view.findViewById(R.id.et_content);
                //tv_dialog_title = (TextView) view.findViewById(R.id.tv__dialog_title);
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
            String startDate=info.getStartDate();
            String endDate=info.getEndDate();
            tv_title_date.setText(DateUtil.getInstance(DateUtil.yyyy_MM_dd).getYear(startDate)+"年"+
                                  DateUtil.getInstance(DateUtil.yyyy_MM_dd).getMonth(startDate)+"月"+"-"+
                    DateUtil.getInstance(DateUtil.yyyy_MM_dd).getYear(endDate)+"年"+
                    DateUtil.getInstance(DateUtil.yyyy_MM_dd).getMonth(endDate)+"月");
            tv_pc_num.setText(info.getPCNum() + "人");
            tv_sr_num.setText(info.getSRNum() + "人");
            if (StringUtils.isNotEmpty(info.getClassBanner())){
                Picasso.with(this).load(info.getClassBanner()).fit().centerCrop().placeholder(R.drawable.default_icon_rect)
                        .error(R.drawable.default_icon_rect).centerCrop().into(iv_grade_banner);
            }
        }
        //加载学员头像
        List<PeopleModel> pcs = gradeModel.getPCInfo();
        List<PeopleModel> srs = gradeModel.getSRInfo();
        for (int i = 0; i < pcs.size(); i++) {
            PeopleModel pc = pcs.get(i);
            switch (i) {
                case 0:
                    cir_pc_one.setVisibility(View.VISIBLE);
                    pc_first_icon.setVisibility(View.VISIBLE);
                    if(StringUtils.isNotEmpty(pc.getPhoto())){
                        Picasso.with(this).load(pc.getPhoto()).fit()
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default).into(cir_pc_one);
                    }
                    break;
                case 1:
                    cir_pc_two.setVisibility(View.VISIBLE);
                    pc_second_icon.setVisibility(View.VISIBLE);
                    if(StringUtils.isNotEmpty(pc.getPhoto())) {
                        Picasso.with(this).load(pc.getPhoto()).fit()
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default).into(cir_pc_two);
                    }
                    break;
                case 2:
                    cir_pc_three.setVisibility(View.VISIBLE);
                    pc_third_icon.setVisibility(View.VISIBLE);
                    if(StringUtils.isNotEmpty(pc.getPhoto())) {
                        Picasso.with(this).load(pc.getPhoto()).fit()
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default).into(cir_pc_three);
                    }
                    break;
            }
        }
        for (int i = 0; i < srs.size(); i++) {
            PeopleModel sr = srs.get(i);
            switch (i) {
                case 0:
                    cir_sr_one.setVisibility(View.VISIBLE);
                    sr_first_icon.setVisibility(View.VISIBLE);
                    if(StringUtils.isNotEmpty(sr.getPhoto())) {
                        Picasso.with(this).load(sr.getPhoto()).fit()
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default).into(cir_sr_one);
                    }
                    break;
                case 1:
                    cir_sr_two.setVisibility(View.VISIBLE);
                    sr_second_icon.setVisibility(View.VISIBLE);
                    if(StringUtils.isNotEmpty(sr.getPhoto())) {
                        Picasso.with(this).load(sr.getPhoto()).fit()
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default).into(cir_sr_two);
                    }
                    break;
                case 2:
                    cir_sr_three.setVisibility(View.VISIBLE );
                    sr_third_icon.setVisibility(View.VISIBLE);
                    if(StringUtils.isNotEmpty(sr.getPhoto())) {
                        Picasso.with(this).load(sr.getPhoto()).fit()
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default).into(cir_sr_three);
                    }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileCropSelector.takePhoto(GradeHomeActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == DialogInterface.BUTTON_POSITIVE) {
            String content = et_content.getText().toString();
            if (StringUtil.length(content)>200) {
                Util.toastMsg("动态字数不能超过100汉字");
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
        //tv_dialog_title.setText("请输入100字以内的文字(" + surplus + ")");
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
        imageFileCropSelector.onActivityResult(requestCode,resultCode,data);
        imageFileCropSelector.getmImageCropperHelper().onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onSuccess(String bannerUrl, File image) {
        progressDialog.dismiss();
        Picasso.with(this).load(image).fit().centerCrop().placeholder(R.drawable.default_icon_rect)
                .error(R.drawable.default_icon_rect).into(iv_grade_banner);

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


    @Override
    public void onSuccess(String file) {
        progressDialog.setMessage("正在上传图片,请稍候...");
        progressDialog.show();
        grade.updateClassBanner(classId, "2", new File(file));
    }

    @Override
    public void onError() {

    }
}
