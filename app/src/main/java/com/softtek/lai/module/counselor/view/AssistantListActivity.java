package com.softtek.lai.module.counselor.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.adapter.CounselorClassAdapter;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.CounselorClassImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.counselor.presenter.ICounselorClassPresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_assistant_list)
public class AssistantListActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_assistant)
    ListView list_assistant;

    private IAssistantPresenter assistantPresenter;
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("邀请助教");

        tv_right.setText("完成");

    }

    @Override
    protected void initDatas() {
        assistantPresenter = new AssistantImpl(this);
        aCache= ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        String classId=getIntent().getStringExtra("classId");
        System.out.println("classId:"+classId);
        assistantPresenter.getAssistantList(classId,list_assistant);
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_right:

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
