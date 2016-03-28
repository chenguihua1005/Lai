package com.softtek.lai.module.counselor.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.model.ClassInfo;
import com.softtek.lai.module.counselor.presenter.CounselorClassImpl;
import com.softtek.lai.module.counselor.presenter.ICounselorClassPresenter;
import com.softtek.lai.module.login.presenter.IPasswordPresenter;
import com.softtek.lai.module.login.presenter.IRegistPresenter;
import com.softtek.lai.module.login.presenter.PasswordPresnter;
import com.softtek.lai.module.login.presenter.RegistPresenterImpl;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RegexUtil;
import com.softtek.lai.utils.SoftInputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

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
