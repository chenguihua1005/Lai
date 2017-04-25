package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.head.model.ClassDetailModel;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.presenter.ClassDetailPresenter;
import com.softtek.lai.module.message2.view.ZQSActivity;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_class_detail)
public class ClassDetailActivity extends BaseActivity<ClassDetailPresenter> implements View.OnClickListener, ClassDetailPresenter.ClassDetailView {
    @InjectView(R.id.cir_img)
    CircleImageView cir_img;//教练头像
    @InjectView(R.id.tv_coach_name)
    TextView tv_coach_name;//教练名称
    @InjectView(R.id.tv_classname)
    TextView tv_classname;//班级名称
    @InjectView(R.id.tv_classid)
    TextView tv_classid;//班级id
    @InjectView(R.id.tv_StaClassDate)
    TextView tv_StaClassDate;//开班日期
    @InjectView(R.id.tv_classPerNum)
    TextView tv_classPerNum;//班级人数
    @InjectView(R.id.tv_zhiqing)
    TextView tv_zhiqing;//跳转知情书说明
    @InjectView(R.id.btn_joinclass)
    Button btn_joinclass;//加入班级按钮
    @InjectView(R.id.tv_tip)
    TextView tv_tip;//提示文本
    @InjectView(R.id.cb_term)
    CheckBox cb_term;


    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    ClasslistModel classlistModel;
    ClassDetailModel classDetailModel;

    @Override
    protected void initViews() {
        tv_zhiqing.setOnClickListener(this);
        btn_joinclass.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_title.setText("班级推荐");
        cb_term.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btn_joinclass.setEnabled(true);
                    btn_joinclass.setBackground(ContextCompat.getDrawable(ClassDetailActivity.this,R.drawable.bg_joinclass_btn));
                } else {
                    btn_joinclass.setEnabled(false);
                    btn_joinclass.setBackground(ContextCompat.getDrawable(ClassDetailActivity.this,R.drawable.bg_joinclass_grey_btn));

                }
            }
        });
    }

    @Override
    protected void initDatas() {
        setPresenter(new ClassDetailPresenter(this));
        classlistModel = getIntent().getParcelableExtra("ClasslistModel");//接受对象
        if (classlistModel != null) {
            getPresenter().getClassDate(classlistModel.getClassId());
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zhiqing:
                startActivity(new Intent(this, ZQSActivity.class));
                break;
            case R.id.btn_joinclass:
                if(classlistModel!=null){
                    getPresenter().doJoinClass(classlistModel.getClassId());
                }
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getClassDate(ClassDetailModel model) {
        classDetailModel = model;
        if (classDetailModel != null) {
            if (!TextUtils.isEmpty(classDetailModel.getClassMasterPhoto())) {
                //教练头像
                Picasso.with(this).load(AddressManager.get("photoHost") + classDetailModel.getClassMasterPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(cir_img);
                Log.i("教练头像", AddressManager.get("photoHost") + classDetailModel.getClassMasterPhoto());
            } else {
                Picasso.with(this).load(R.drawable.img_default).into(cir_img);
            }
            tv_coach_name.setText(classDetailModel.getClassMasterName());//总教练名称
            tv_classname.setText(classDetailModel.getClassName());//班级名称
            tv_classid.setText(classDetailModel.getClassCode());//班级编号
            if (!TextUtils.isEmpty(classDetailModel.getClassStart())) {
                String[] date = classDetailModel.getClassStart().split("-");
                String[] date1 = date[2].split(" ");
                tv_StaClassDate.setText(date[0] + "年" + Long.parseLong(date[1]) + "月" + Long.parseLong(date1[0]) + "日");//开班日期
            }
            tv_classPerNum.setText(classDetailModel.getClassMemberNum() + "人");
            int IsSend = Integer.parseInt(classDetailModel.getIsSendMsg());
            switch (IsSend) {
                case 0:
                    break;
                case 1:
                    //是，隐藏申请按钮,勾选框不可点击选择，显示提示信息文本
                    btn_joinclass.setVisibility(View.GONE);
                    cb_term.setEnabled(false);
                    tv_tip.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    //是，隐藏申请按钮,勾选框不可点击选择，显示提示信息文本:您已在班级中,无法再次加入
                    btn_joinclass.setVisibility(View.GONE);
                    cb_term.setEnabled(false);
                    tv_tip.setVisibility(View.VISIBLE);
                    tv_tip.setText("您已在班级中,无法再次加入");
                    break;
            }
        }
    }

    @Override
    public void doFinish() {
        finish();
    }
}
