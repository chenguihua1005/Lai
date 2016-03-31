/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.view;

import android.view.View;
import android.widget.ListView;
import butterknife.InjectView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.ACache;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/24/2016.
 * 助教申请审核页面
 */
@InjectLayout(R.layout.fragment_assistant_apply_list)
public class AssistantApplyFragment extends BaseFragment implements View.OnClickListener {

    private IAssistantPresenter ssistantPresenter;
    private ACache aCache;
    UserModel userModel;
    @InjectView(R.id.list)
    ListView list;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        ssistantPresenter = new AssistantImpl(getContext());
        aCache = ACache.get(getContext(), Constants.USER_ACACHE_DATA_DIR);
        userModel = (UserModel) aCache.getAsObject(Constants.USER_ACACHE_KEY);
        String id = userModel.getUserid();
        ssistantPresenter.showAllApplyAssistants(id, list);
    }

    @Override
    public void onClick(View v) {

    }
}