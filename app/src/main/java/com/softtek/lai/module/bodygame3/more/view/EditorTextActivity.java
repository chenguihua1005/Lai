package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import retrofit.http.PUT;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_editor_text)
public class EditorTextActivity extends BaseActivity {

    public static final int UPDATE_CLASS_NAME=1;
    public static final int UPDATE_GROUP_NAME=2;
    public static final int ADD_GROUP_NAME=3;
    public static final int ADD_ACTIVITY_NAME=4;
    public static final int ADD_MARK=5;

    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.et_value)
    EditText et_value;

    @InjectView(R.id.iv_delete)
    ImageView iv_delete;

    @Override
    protected void initViews() {
        Intent intent=getIntent();
        int flag=intent.getIntExtra("flag",0);
        switch (flag){
            case UPDATE_CLASS_NAME:
                tv_title.setText("编辑班级名称");
                et_value.setHint("班级名称");
                et_value.setText(intent.getStringExtra("name"));
                Editable etext=et_value.getText();
                Selection.setSelection(etext,etext.length());
                break;
            case UPDATE_GROUP_NAME:
                tv_title.setText("修改组名");
                et_value.setHint("小组名称");
                et_value.setText(intent.getStringExtra("name"));
                Editable etext1=et_value.getText();
                Selection.setSelection(etext1,etext1.length());
                break;
            case ADD_GROUP_NAME:
                tv_title.setText("添加小组");
                et_value.setHint("小组名称");
                break;
            case ADD_ACTIVITY_NAME:
                tv_title.setText("编辑活动名称");
                et_value.setHint("活动名称");
                break;
            case ADD_MARK:
                tv_title.setText("编辑活动说明");
                et_value.setHint("活动说明");
                break;
        }
        tv_right.setText("确定");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=getIntent();
                back.putExtra("value",et_value.getText().toString());
                setResult(RESULT_OK,back);
                finish();
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_value.setText("");
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
