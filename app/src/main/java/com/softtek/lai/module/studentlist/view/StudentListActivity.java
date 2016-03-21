package com.softtek.lai.module.studentlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.File.view.CreatFlleActivity;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student_list)
public class StudentListActivity extends BaseActivity implements View.OnClickListener{
    //toolbar布局控件
    @InjectView(R.id.tv_left)
    TextView tv_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.list_studentlist)
    ListView list_studentlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_left.setOnClickListener(this);
        //ArrayAdapter<String>adapter=new ArrayAdapter<String>(StudentListActivity.this,android.R.layout.simple_list_item_1,date);
        //list_studentlist.setAdapter(adapter);

    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        tv_title.setText("学员列表");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                startActivity(new Intent(StudentListActivity.this,CreatFlleActivity.class));
                finish();
                break;

        }
    }
}
