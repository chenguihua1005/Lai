/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.model.AssistantDetailInfoModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教详细页面
 */
@InjectLayout(R.layout.activity_assistant_detail)
public class AssistantDetailActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.text_name)
    TextView text_name;

    @InjectView(R.id.text_phone)
    TextView text_phone;

    @InjectView(R.id.text_count)
    TextView text_count;

    @InjectView(R.id.text_retest)
    TextView text_retest;

    @InjectView(R.id.text_total)
    TextView text_total;

    private IAssistantPresenter ssistantPresenter;
    private ACache aCache;

    private LinearLayout layout;
    private PopupWindow popupWindow;

    private String assistantId;
    private String classId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ll_left.setOnClickListener(this);
        //iv_email.setOnClickListener(this);
        fl_right.setOnClickListener(this);

    }

    @Subscribe
    public void onEvent(AssistantDetailInfoModel assistantDetailInfo) {
        text_name.setText(assistantDetailInfo.getUserName().toString());
        text_phone.setText(assistantDetailInfo.getMobile().toString());
        text_count.setText(assistantDetailInfo.getNum().toString());
        text_retest.setText(StringUtil.getValue(assistantDetailInfo.getMrate()));
        text_total.setText(StringUtil.getValue(assistantDetailInfo.getTotalWeight()));
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(assistantDetailInfo.getPhoto())) {
            Picasso.with(this).load("111").fit().error(R.drawable.img_default).into(img);
        } else {
            Picasso.with(this).load(path+assistantDetailInfo.getPhoto()).fit().error(R.drawable.img_default).into(img);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText(R.string.assistantDetail);
        //iv_email.setImageResource(R.drawable.more_title);
        //iv_email.setVisibility(View.VISIBLE);
        tv_right.setText("移除助教");

    }

    @Override
    protected void initDatas() {
        ssistantPresenter = new AssistantImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        assistantId = getIntent().getStringExtra("assistantId");
        classId = getIntent().getStringExtra("classId");
        dialogShow("加载中");
        ssistantPresenter.showAssistantDetails(assistantId, classId);
    }

    public void showPopupWindow(View parent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.login_out_title))
                .setMessage(getString(R.string.warning_value))
                .setPositiveButton(getString(R.string.app_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogShow("加载中");
                        ssistantPresenter.removeAssistantRoleByClass(assistantId, classId, "assistant");
                    }
                })
                .setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        dialogBuilder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_left:
                finish();
                break;

            case R.id.iv_email:
                showPopupWindow(iv_email);
                break;
            case R.id.fl_right:
                showPopupWindow(iv_email);
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
