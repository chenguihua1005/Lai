package com.softtek.lai.module.customermanagement.view;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/23/2017.
 */

@InjectLayout(R.layout.activity_add_remark)
public class AddRemarkActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.remark_et)
    EditText remark_et;

    @Override
    protected void initViews() {
        tv_title.setText("添加备注");
        tv_right.setText("保存");
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

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
