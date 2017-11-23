package com.softtek.lai.module.customermanagement.view;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 11/21/2017.   SearchContactActivity
 */

@InjectLayout(R.layout.activity_search_contact)
public class SearchCustomerActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.edit)
    EditText edit;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;
    @InjectView(R.id.lv)
    ListView lv;

    @InjectView(R.id.pb)
    ProgressBar pb;

    @Override
    protected void initViews() {
        tv_title.setText("查找客户");
        edit.setHint("请输入手机号或姓名查找客户");
        tv_cancel.setOnClickListener(this);
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                SoftInputUtil.hidden(SearchCustomerActivity.this);
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
