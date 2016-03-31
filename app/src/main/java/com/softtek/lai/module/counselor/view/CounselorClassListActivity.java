package com.softtek.lai.module.counselor.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.counselor.presenter.CounselorClassImpl;
import com.softtek.lai.module.counselor.presenter.ICounselorClassPresenter;
import com.softtek.lai.module.grade.view.GradeHomeActivity;
import com.softtek.lai.utils.SoftInputUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
/**
 * Created by jarvis.liu on 3/22/2016.
 * 体管赛，班级列表
 */
@InjectLayout(R.layout.activity_counselor_classlist)
public class CounselorClassListActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.expand_list)
    ListView expand_list;

    @InjectView(R.id.lin_create_class)
    LinearLayout lin_create_class;


    private ICounselorClassPresenter counselorClassPresenter;
    private CounselorClassAdapter adapter;
    List<ClassInfo> list;
    List<String> time_month_list=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        lin_create_class.setOnClickListener(this);
        expand_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CounselorClassListActivity.this,GradeHomeActivity.class);
                ClassInfo classInfo = (ClassInfo) expand_list.getAdapter().getItem(position);
                SharedPreferenceService.getInstance().put("classId", classInfo.getClassId());
                intent.putExtra("classId",classInfo.getClassId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("体管赛");

    }

    @Override
    protected void initDatas() {
        counselorClassPresenter = new CounselorClassImpl(this);
        counselorClassPresenter.getClassList(expand_list,lin_create_class);

    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.lin_create_class:        //我要开班
                startActivity(new Intent(this, CreateCounselorClassActivity.class));
                break;
            case R.id.tv_left:
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }

}
