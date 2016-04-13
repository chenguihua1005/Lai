package com.softtek.lai.module.studentbasedate.view;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.studentbasedate.adapter.BaseDataFragmentAdapter;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studentbasedate.presenter.IStudentBaseDate;
import com.softtek.lai.module.studentbasedate.presenter.StudentBaseDateImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_stdudent_base_date)
public class StudentBaseDateActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener,StudentBaseDateImpl.StudentBaseDataCallback{


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_title_date)
    TextView tv_title_date;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    ViewPager tab_content;


    private IStudentBaseDate studentBaseDate;
    List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        studentBaseDate=new StudentBaseDateImpl(this);
        dialogShow("正在加载...");
        studentBaseDate.getClassMemberInfoPC();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getClassMemberInfo(StudentBaseInfoModel studentBaseInfoModel) {
        dialogDissmiss();
        if(studentBaseInfoModel!=null){
            BaseDateFragment baseDateFragment=BaseDateFragment.getInstance(studentBaseInfoModel);
            ClassDynamicFragment classDynamicFragment=ClassDynamicFragment.getInstance(studentBaseInfoModel.getClassId()+"");
            fragments.add(baseDateFragment);
            fragments.add(classDynamicFragment);
            tab_content.setAdapter(new BaseDataFragmentAdapter(getSupportFragmentManager(),fragments));
            tab.setupWithViewPager(tab_content);
            View tab1 = getLayoutInflater().inflate(R.layout.tab1_basedata, null);
            View tab2 = getLayoutInflater().inflate(R.layout.tab2_basedata, null);
            tab.removeAllTabs();
            tab.addTab(tab.newTab().setCustomView(tab1), 0, true);
            tab.addTab(tab.newTab().setCustomView(tab2), 1);
            tab.setTabMode(TabLayout.MODE_FIXED);
        }
    }
}
