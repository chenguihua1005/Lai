package com.softtek.lai.module.bodygame3.activity.view;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;

import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by Terry on 2016/12/3.
 */

@InjectLayout(R.layout.activity_initwrite)
public class InitAuditStuActivity extends BaseActivity {
    FuceSevice fuceSevice;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        fuceSevice= ZillaApi.NormalRestAdapter.create(FuceSevice.class);
//        fuceSevice.doGetMeasuredDetails(UserInfoModel.getInstance().getToken(),).
    }
}
