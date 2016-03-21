package com.softtek.lai.module.grade.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_grade_home)
public class GradeHomeActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.et_dynamic)
    EditText et_dynamic;

    @InjectView(R.id.tv_send)
    TextView tv_send;

    private IGrade grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grade=new GradeImpl();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send:
                //发布班级动态和公告
                grade.sendDynamic();
                break;
        }
    }
}
