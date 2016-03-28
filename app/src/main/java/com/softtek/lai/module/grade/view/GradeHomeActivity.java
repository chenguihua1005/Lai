package com.softtek.lai.module.grade.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
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
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_grade_home)
public class GradeHomeActivity extends BaseActivity implements View.OnClickListener{

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


    List<DynamicInfo> dynamicInfos=new ArrayList<>();

    private IGrade grade;

    private ProgressDialog progressDialog;


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
        progressDialog.setMessage(getResources().getString(zilla.libcore.R.string.dialog_loading));
        progressDialog.setMessage("正在加载内容...");
    }

    @Override
    protected void initDatas() {
        tv_title.setText("班级主页");

        grade=new GradeImpl();
        progressDialog.show();
        grade.getGradeInfos(1, progressDialog);
        for(int i=0;i<10;i++){
            DynamicInfo info=new DynamicInfo();
            info.setCreateDate("2016年2月20日");
            info.setDyContent("你好我是管国祥 我今天加入了这个班级的哈哈哈哈");
            dynamicInfos.add(info);
        }
        lv_dynamic.setAdapter(new DynamicAdapter(this, dynamicInfos));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left:
                finish();
                break;
            case R.id.tv_editor:
                //点击编辑按钮
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
                //邀请助教按钮
                break;
            case R.id.ll_invite_student:
                //邀请学员按钮
                break;
            case R.id.ll_send_dynamic:
                /*
                发布班级动态：打开dialog用户编辑输入
                 */

                //grade.sendDynamic(1, "dsadas", dynamicContent, Constants.SP_SEND, 1);
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

}