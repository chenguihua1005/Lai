package com.softtek.lai.module.studentbasedate.view;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.studentbasedate.adapter.BaseDataFragmentAdapter;
import com.softtek.lai.module.studentbasedate.model.StudentBaseInfoModel;
import com.softtek.lai.module.studentbasedate.presenter.IStudentBaseDate;
import com.softtek.lai.module.studentbasedate.presenter.StudentBaseDateImpl;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_stdudent_base_date)
public class StudentBaseDateActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener,StudentBaseDateImpl.StudentBaseDataCallback
    ,View.OnClickListener{


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
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.civ_header_image)
    CircleImageView cir_header_image;
    @InjectView(R.id.banner)
    ImageView banner;


    private IStudentBaseDate studentBaseDate;
    List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

        studentBaseDate=new StudentBaseDateImpl(this);
        BaseDateFragment baseDateFragment=BaseDateFragment.getInstance(null);
        ClassDynamicFragment classDynamicFragment=ClassDynamicFragment.getInstance();
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
            Log.i("基础数据"+studentBaseInfoModel);
            tv_title.setText(studentBaseInfoModel.getClassName());
            StringBuffer strDate=new StringBuffer();
            StringBuffer endDate=new StringBuffer();
            String start=studentBaseInfoModel.getStartDate();
            String end=studentBaseInfoModel.getEndDate();
            if(StringUtils.isNotEmpty(start)){
                strDate.append(DateUtil.getInstance().getYear(start));
                strDate.append("年");
                strDate.append(DateUtil.getInstance().getMonth(start));
                strDate.append("月");
            }
            if(StringUtils.isNotEmpty(end)){
                endDate.append(DateUtil.getInstance().getYear(end));
                endDate.append("年");
                endDate.append(DateUtil.getInstance().getMonth(end));
                endDate.append("月");
            }
            tv_title_date.setText(strDate.toString()+"-"+endDate.toString());
            tv_name.setText(studentBaseInfoModel.getUserName());
            if(StringUtils.isNotEmpty(studentBaseInfoModel.getBanner())){
                Picasso.with(this).load(studentBaseInfoModel.getBanner()).fit()
                        .placeholder(R.drawable.default_icon_rect).centerCrop()
                        .error(R.drawable.default_icon_rect).centerCrop().into(banner);
            }
            if(StringUtils.isNotEmpty(studentBaseInfoModel.getUserPhoto())){
                Picasso.with(this).load(studentBaseInfoModel.getUserPhoto()).fit()
                        .placeholder(R.drawable.img_default)
                        .error(R.drawable.img_default).into(cir_header_image);
            }
            ((BaseDateFragment)fragments.get(0)).updateData(studentBaseInfoModel);
            ((ClassDynamicFragment)fragments.get(1)).loadDynamic(studentBaseInfoModel.getClassId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
