package com.softtek.lai.module.bodygame3.activity.view;

import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_act_text)
public class ActTextActivity extends BaseActivity implements View.OnClickListener {
    public static final int ADD_ACTIVITY_NAME=4;
    public static final int ADD_MARK=5;
    @InjectView(R.id.et_mark)
    EditText et_mark;
    @InjectView(R.id.et_title)
    EditText et_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_delete)
    ImageView iv_delete;
    @InjectView(R.id.iv_del)
    ImageView iv_del;
    @InjectView(R.id.frag1)
    FrameLayout frag1;
    @InjectView(R.id.frag2)
    FrameLayout frag2;
    @InjectView(R.id.tv_count)
    TextView tv_count;
    private int flag;
    @Override
    protected void initViews() {
        Intent intent=getIntent();
        flag=intent.getIntExtra("value",0);
        switch (flag){
            case ADD_MARK:
                frag2.setVisibility(View.GONE);
                tv_count.setText("仅限300个字");
                tv_title.setText("编辑活动说明");
                et_mark.setHint("活动说明");
                et_mark.setText(intent.getStringExtra("name_value"));
                Editable etext1=et_mark.getText();
                Selection.setSelection(etext1,etext1.length());
                break;
            case ADD_ACTIVITY_NAME:
                tv_count.setText("仅限30个字");
                frag1.setVisibility(View.GONE);
                et_mark.setSingleLine();
                tv_title.setText("编辑活动名称");
                et_title.setHint("活动名称");
                et_title.setText(intent.getStringExtra("name_value"));
                Editable etext2=et_title.getText();
                Selection.setSelection(etext2,etext2.length());
                break;
        }

        tv_right.setText("确定");
        tv_right.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                switch (flag){
                    case ADD_ACTIVITY_NAME:
                        Intent back1 = getIntent();
                        back1.putExtra("value_name", et_title.getText().toString());
                        setResult(RESULT_OK, back1);
                        finish();
                        break;
                    case ADD_MARK:
                        Intent back = getIntent();
                        back.putExtra("value_name", et_mark.getText().toString());
                        setResult(RESULT_OK, back);
                        finish();
                        break;
                }

                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.iv_delete:
                et_mark.setText("");
                break;
            case R.id.iv_del:
                et_title.setText("");
                break;
        }
    }

}
