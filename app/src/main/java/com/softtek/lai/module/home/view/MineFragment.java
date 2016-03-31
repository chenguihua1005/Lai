/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.view;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.InjectView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.login.view.LoginActivity;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_my)
public class MineFragment extends BaseFragment implements View.OnClickListener, Validator.ValidationListener {
    @InjectView(R.id.tv_title)
    TextView title;

    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.but_login_out)
    Button but_login_out;

    private ACache aCache;

    @Override
    protected void initViews() {
        tv_left.setVisibility(View.GONE);
        but_login_out.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        aCache = ACache.get(getContext(), Constants.USER_ACACHE_DATA_DIR);
        title.setText("我的");

    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(getContext());
        switch (v.getId()) {
            case R.id.but_login_out:
                clearData();
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;

            case R.id.img_choose:
                break;
        }
    }

    private void clearData() {
        UserInfoModel.getInstance().loginOut();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
}
