package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_editor_text)
public class EditorTextActivity extends BaseActivity {

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.et_value)
    EditText et_value;

    @Override
    protected void initViews() {
        tv_title.setText("添加小组");
        tv_right.setText("确定");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=getIntent();
                back.putExtra("value",et_value.getText().toString());
                setResult(RESULT_OK,back);
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
