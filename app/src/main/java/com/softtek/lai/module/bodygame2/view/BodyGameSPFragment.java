package com.softtek.lai.module.bodygame2.view;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame_sp)
public class BodyGameSPFragment extends LazyBaseFragment implements View.OnClickListener{
    @InjectView(R.id.re_student_three)
    RelativeLayout re_student_three;
    @InjectView(R.id.re_student_two)
    RelativeLayout re_student_two;
    @InjectView(R.id.re_student_one)
    RelativeLayout re_student_one;
    @Override
    protected void initViews() {
        re_student_three.setOnClickListener(this);
        re_student_two.setOnClickListener(this);
        re_student_one.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }


    @Override
    protected void lazyLoad() {
        Log.i("BodyGameSPFragment 加载数据");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //进入学员3详情个人资料页面
            case R.id.re_student_three:
                Intent intent=new Intent(getContext(),PersonalDataActivity.class);
                intent.putExtra("userId",72);
                intent.putExtra("classId",15);
                startActivity(intent);
                break;
            //进入学员2详情个人资料页面
            case R.id.re_student_two:
                Intent intent1=new Intent(getContext(),PersonalDataActivity.class);
                intent1.putExtra("userId",72);
                intent1.putExtra("classId",15);
                startActivity(intent1);
                break;
            case R.id.re_student_one:
                Intent intent2=new Intent(getContext(),PersonalDataActivity.class);
                intent2.putExtra("userId",72);
                intent2.putExtra("classId",15);
                startActivity(intent2);
                break;
        }
    }
}
