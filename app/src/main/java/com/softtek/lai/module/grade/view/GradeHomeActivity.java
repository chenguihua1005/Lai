package com.softtek.lai.module.grade.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.model.GradeInfo;
import com.softtek.lai.module.grade.model.PhotoInfo;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.ui.InjectLayout;
import zilla.libzilla.dialog.LoadingDialog;

@InjectLayout(R.layout.activity_grade_home)
public class GradeHomeActivity extends BaseActivity implements View.OnClickListener{


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

    @InjectView(R.id.ll_tutor)
    LinearLayout ll_tutor;

    @InjectView(R.id.ll_students)
    LinearLayout ll_students;


    private IGrade grade;

    private ProgressDialog progressDialog;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_students.setOnClickListener(this);
        ll_tutor.setOnClickListener(this);
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
        grade.getGradeInfos(1,progressDialog);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send:
                //发布班级动态和公告
                grade.sendDynamic();
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_tutor:

                break;
            case R.id.ll_students:
                startActivity(new Intent(this,StudentsActivity.class));
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
        List<GradeInfo> grades=grade.getClassInfo();
        if(grades!=null&&grades.size()>0){
            GradeInfo info=grades.get(0);
            tv_grade_name.setText(info.getClassName());
            tv_date.setText(info.getStartDate());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        Log.i("onCreate");
    }


    @Override
    protected void onStart() {

        super.onStart();
        Log.i("onStart");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
