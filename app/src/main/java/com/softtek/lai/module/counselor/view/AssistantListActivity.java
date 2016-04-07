/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.grade.view.GradeHomeActivity;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 邀请助教页面
 */
@InjectLayout(R.layout.activity_assistant_list)
public class AssistantListActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_assistant)
    ListView list_assistant;

    private IAssistantPresenter assistantPresenter;
    private ACache aCache;
    String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText(R.string.inviteTutor);

        tv_right.setText("完成");

    }

    @Override
    protected void initDatas() {
        assistantPresenter = new AssistantImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        classId = SharedPreferenceService.getInstance().get("classId", "");
        System.out.println("classId:" + classId);
        assistantPresenter.getAssistantList(classId, list_assistant);
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_right:
                Intent intent = new Intent(this, GradeHomeActivity.class);
                intent.putExtra("classId", classId);
                startActivity(intent);
                break;

            case R.id.ll_left:
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
