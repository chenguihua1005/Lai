package com.softtek.lai.module.studentbasedate.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.studentbasedate.presenter.IStudentBaseDate;
import com.softtek.lai.module.studentbasedate.presenter.StudentBaseDateImpl;

import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_stdudent_base_date)
public class StudentBaseDateActivity extends BaseActivity {


    private IStudentBaseDate studentBaseDate;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        studentBaseDate=new StudentBaseDateImpl(this);
        studentBaseDate.getClassMemberInfoPC();
        studentBaseDate.getClassMemberInfoCurvePC();
        studentBaseDate.getClassDynamic(1);
    }
}
