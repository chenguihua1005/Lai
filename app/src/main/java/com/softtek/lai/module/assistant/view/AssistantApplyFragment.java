package com.softtek.lai.module.assistant.view;

import android.view.View;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.assistant.presenter.AssistantManageImpl;
import com.softtek.lai.module.assistant.presenter.IAssistantManagePresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/24/2016.
 */
@InjectLayout(R.layout.fragment_assistant_apply_list)
public class AssistantApplyFragment extends BaseFragment implements View.OnClickListener {

    private IAssistantManagePresenter assistantManagePresenter;
    private ACache aCache;
    User user;
    @InjectView(R.id.list)
    ListView list;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        assistantManagePresenter=new AssistantManageImpl(getContext());
        aCache= ACache.get(getContext(), Constants.USER_ACACHE_DATA_DIR);
        user=(User)aCache.getAsObject(Constants.USER_ACACHE_KEY);
        String id=user.getUserid();
        id="36";
        assistantManagePresenter.showAllApplyAssistants(id,list);
    }

    @Override
    public void onClick(View v) {

    }
}