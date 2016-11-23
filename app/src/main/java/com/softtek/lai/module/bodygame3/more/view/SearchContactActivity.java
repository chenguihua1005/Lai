package com.softtek.lai.module.bodygame3.more.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.utils.SoftInputUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_search_contact)
public class SearchContactActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.edit)
    EditText edit;

    @Override
    protected void initViews() {
        overridePendingTransition(0,0);
        tv_title.setText("邀请小伙伴");
        ll_left.setOnClickListener(this);
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //判断是不是搜索
                if(i== EditorInfo.IME_ACTION_SEARCH){
                     /*隐藏软键盘*/
                    SoftInputUtil.hidden(SearchContactActivity.this);
                    Log.i("发送搜索");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
