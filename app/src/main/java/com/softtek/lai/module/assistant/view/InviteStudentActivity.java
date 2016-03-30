package com.softtek.lai.module.assistant.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.assistant.model.ContactListInfo;
import com.softtek.lai.module.assistant.presenter.AssistantManageImpl;
import com.softtek.lai.module.assistant.presenter.IAssistantManagePresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;

import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_invite_student_list)
public class InviteStudentActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener, BaseFragment.OnFragmentInteractionListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_student)
    ListView list_student;

    @InjectView(R.id.but_contant)
    Button but_contant;


    private IAssistantManagePresenter assistantManagePresenter;
    private ACache aCache;
    private User user;
    TelephonyManager tManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        but_contant.setOnClickListener(this);
        tManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        System.out.println("number:" + tManager.getLine1Number());

    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("参赛邀请");

    }

    @Override
    protected void initDatas() {
        assistantManagePresenter = new AssistantManageImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        user = (User) aCache.getAsObject(Constants.USER_ACACHE_KEY);
        String id = user.getUserid();
        String classId = SharedPreferenceService.getInstance().get("classId","");
        assistantManagePresenter.getNotInvitePC(classId, id, list_student);

    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;

            case R.id.but_contant:
                startActivity(new Intent(this, InviteContantActivity.class));
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
