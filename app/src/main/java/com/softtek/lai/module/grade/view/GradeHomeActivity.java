package com.softtek.lai.module.grade.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.DynamicInfo;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.model.GradeInfo;
import com.softtek.lai.module.grade.model.People;
import com.softtek.lai.module.grade.model.PhotoInfo;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.ui.InjectLayout;
import zilla.libzilla.dialog.LoadingDialog;

@InjectLayout(R.layout.activity_grade_home)
public class GradeHomeActivity extends BaseActivity implements View.OnClickListener, TextWatcher{


    @InjectView(R.id.et_dynamic)
    EditText et_dynamic;

    @InjectView(R.id.tv_send)
    TextView tv_send;

    @InjectView(R.id.iv_grade_pic)
    ImageView iv_grade_pic;

    @InjectView(R.id.tv_grade_name)
    TextView tv_grade_name;

    @InjectView(R.id.tv_date)
    TextView tv_date;

    @InjectView(R.id.iv_editor)
    ImageView iv_editor;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.ll_tutor)
    LinearLayout ll_tutor;

    @InjectView(R.id.ll_students)
    LinearLayout ll_students;

    @InjectView(R.id.tv_pc_num)
    TextView tv_pc_num;
    @InjectView(R.id.tv_sr_num)
    TextView tv_sr_num;
    @InjectView(R.id.civ_pc_one)
    CircleImageView cir_pc_one;
    @InjectView(R.id.civ_pc_two)
    CircleImageView cir_pc_two;
    @InjectView(R.id.civ_pc_three)
    CircleImageView cir_pc_three;
    @InjectView(R.id.civ_sr_one)
    CircleImageView cir_sr_one;
    @InjectView(R.id.civ_sr_two)
    CircleImageView cir_sr_two;
    @InjectView(R.id.civ_sr_three)
    CircleImageView cir_sr_three;

    @InjectView(R.id.lv_dynamic)
    ListView lv_dynamic;
    List<DynamicInfo> dynamicInfos=new ArrayList<>();

    private IGrade grade;

    private ProgressDialog progressDialog;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_students.setOnClickListener(this);
        ll_tutor.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        et_dynamic.addTextChangedListener(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getResources().getString(zilla.libcore.R.string.dialog_loading));
        progressDialog.setMessage("正在加载内容...");
    }

    @Override
    protected void initDatas() {
        tv_title.setText("班级主页");
        tv_right.setText("邀请参赛");

        grade=new GradeImpl();
        progressDialog.show();
        grade.getGradeInfos(1,progressDialog);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send:
                //发布班级动态和公告
                String dynamicContent=et_dynamic.getText().toString();
                grade.sendDynamic(1,"dsadas",dynamicContent, Constants.SP_SEND,1);
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tutor:

                break;
            case R.id.ll_students:
                startActivity(new Intent(this,StudentsActivity.class));
                break;
            case R.id.tv_right:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(Grade grade){
        //加载班级Banner
        List<PhotoInfo> banners=grade.getPhotoInfo();
        if(banners!=null&&banners.size()>0){
            Picasso.with(this).load(grade.getPhotoInfo().get(0).getImg_Addr()).into(iv_grade_pic);
        }
        //更新班级信息
        List<GradeInfo> grades=grade.getClassInfo();
        if(grades!=null&&grades.size()>0){
            GradeInfo info=grades.get(0);
            tv_grade_name.setText(info.getClassName());
            tv_date.setText(info.getStartDate());
        }
        //加载学员头像
        List<People> pcs=grade.getPCInfo();
        List<People> srs=grade.getSRInfo();
        tv_pc_num.setText(pcs.size()+"人");
        tv_sr_num.setText(srs.size()+"人");
        dynamicInfos=grade.getDynamicInfo();
        //更新班级动态
        if(lv_dynamic.getAdapter()==null){
            lv_dynamic.setAdapter(new DynamicAdapter(this,dynamicInfos));
        }else{
            dynamicInfos.clear();
            dynamicInfos.addAll(grade.getDynamicInfo());
            ((DynamicAdapter)lv_dynamic.getAdapter()).notifyDataSetChanged();
        }
        for(int i=0;i<pcs.size();i++){
            People pc=pcs.get(i);
            switch (i){
                case 0:
                    Picasso.with(this).load(pc.getPhoto()).into(cir_pc_one);
                    cir_pc_one.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    Picasso.with(this).load(pc.getPhoto()).into(cir_pc_two);
                    cir_pc_two.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    Picasso.with(this).load(pc.getPhoto()).into(cir_pc_three);
                    cir_pc_three.setVisibility(View.VISIBLE);
                    break;
            }
        }
        for(int i=0;i<srs.size();i++){
            People sr=srs.get(i);
            switch (i){
                case 0:
                    Picasso.with(this).load(sr.getPhoto()).into(cir_sr_one);
                    cir_sr_one.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    Picasso.with(this).load(sr.getPhoto()).into(cir_sr_two);
                    cir_sr_two.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    Picasso.with(this).load(sr.getPhoto()).into(cir_sr_three);
                    cir_sr_three.setVisibility(View.VISIBLE);
                    break;
            }
        }

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length()==0){
            tv_send.setEnabled(false);
        }else{
            tv_send.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
