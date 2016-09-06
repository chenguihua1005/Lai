/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/22/2016.
 * 助教申请
 */
@InjectLayout(R.layout.activity_apply_assistant_list)
public class ApplyAssistantActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_apply_assistant)
    ListView list_apply_assistant;

    private IAssistantPresenter assistantPresenter;

    @Override
    protected void initViews() {
        tv_title.setText(R.string.applyAssistant);
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        assistantPresenter = new AssistantImpl(this);
        String id = UserInfoModel.getInstance().getUser().getUserid();
        dialogShow("加载中");
        assistantPresenter.showSRApplyList(id, list_apply_assistant);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

}
